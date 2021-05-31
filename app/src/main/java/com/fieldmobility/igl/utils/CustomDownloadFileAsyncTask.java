package com.fieldmobility.igl.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import com.fieldmobility.igl.R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

public class CustomDownloadFileAsyncTask extends AsyncTask<String, Integer, Boolean> {
    private static final String TAG = CustomDownloadFileAsyncTask.class.getSimpleName();
    private boolean isForPdfView = false;
    private final int PROGRESS_MAX = 100;
    public static final String NOTIFICATION_CHANNEL_ID = "101";
    public static final String NOTIFICATION_CHANNEL_NAME = "IGL";

    private WeakReference<Context> mContext;
    private DownloadCompleteListener mDownloadListener;
    private String mFileName;
    private int mNotificationId;
    private File mFile;
    private String mFileType;

    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    public CustomDownloadFileAsyncTask(Context context, String fileName, int notificationId, File file, String fileType) {
        mContext = new WeakReference<>(context);
        mFileName = fileName;
        mNotificationId = notificationId;
        if (context instanceof DownloadCompleteListener) {
            mDownloadListener = (DownloadCompleteListener) context;
        }

        mFileType = fileType;
        if (file == null) {
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "BeatRoute");
            boolean isFolderCreated = false;
            if (!folder.exists())
                isFolderCreated = folder.mkdir();
            else isFolderCreated = true;

            if (isFolderCreated)
                mFile = new File(isFolderCreated ? folder : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        } else {
            mFile = file;
        }
    }

    private void initializeNotificationManager() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = mContext.get().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager = NotificationManagerCompat.from(mContext.get());
        notificationBuilder = new NotificationCompat.Builder(mContext.get(), NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setContentTitle(mFileName)
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setContentIntent(getPendingIntent())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int PROGRESS_CURRENT = 0;
        notificationBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(mNotificationId, notificationBuilder.build());
    }


    private PendingIntent getPendingIntent() {
        PendingIntent pendingIntent = null;
        if (mFile.exists()) {
            Intent viewIntent = new Intent();
            viewIntent.setAction(Intent.ACTION_VIEW);
            viewIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri path = FileProvider.getUriForFile(mContext.get(), "com.gz.vitalwires", mFile);
            viewIntent.setDataAndType(path, Utils.getIntentType(mFileType));

            if (viewIntent.resolveActivity(mContext.get().getPackageManager()) != null) {
                mContext.get().startActivity(Intent.createChooser(viewIntent, "View File using"));
            }
            pendingIntent = PendingIntent.getActivity(mContext.get(), 0, viewIntent, 0);
        }
        return pendingIntent;
    }



    @Override
    protected void onPostExecute(Boolean isFileDownloaded) {
        super.onPostExecute(isFileDownloaded);
        if (mDownloadListener != null) mDownloadListener.onDownloadComplete(isFileDownloaded);
        if (isFileDownloaded) {
            if (!isForPdfView) {
                notificationBuilder.setContentText("Download complete").setProgress(0, 0, false).setContentIntent(getPendingIntent());
                notificationManager.notify(mNotificationId, notificationBuilder.build());
            }
        } else {
            Utils.showToast(mContext.get(), mContext.get().getString(R.string.error_load_document));
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(mFile);

            byte data[] = new byte[1024];

            int total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                total += count;
            }
            int lengthOfFile = total;
            total = 0;

            InputStream input1 = new BufferedInputStream(url.openStream(), 8192);
            int inputCount = 0;
            while ((inputCount = input1.read(data)) != -1) {
                total += inputCount;
                publishProgress((int) ((total * 100) / lengthOfFile));
                output.write(data, 0, inputCount);
            }

            output.flush();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (mDownloadListener != null) mDownloadListener.onProgressUpdate(progress);
        if (!isForPdfView) {
            notificationBuilder.setProgress(PROGRESS_MAX, progress, false);
            notificationManager.notify(mNotificationId, notificationBuilder.build());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!isForPdfView) {
            initializeNotificationManager();
        }
    }

    public interface DownloadCompleteListener {
        void onDownloadComplete(boolean isSuccessful);

        void onProgressUpdate(int progress);
    }
}
