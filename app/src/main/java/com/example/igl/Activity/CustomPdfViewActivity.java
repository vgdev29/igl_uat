package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.igl.R;
import com.example.igl.utils.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomPdfViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private CustomPdfViewActivity mActivity;
    private boolean isDocOpened = false;
    private int pageNumber = 0, pageCount = 0;
    private static final String TAG = CustomPdfViewActivity.class.getSimpleName();



    private PDFView pdfView;
    private RelativeLayout rlControlPagePanel;
    private Button btnControlPageInputOk;
    private LinearLayout llControlPrev, llControlZoomIn, llControlZoomOut, llControlJump, llControlNext, llControlShare;
    private ImageView ivRefreshImage;
    private LinearLayout llControlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pdf_view);

        pdfView = findViewById(R.id.pdfView);
        llControlPanel = findViewById(R.id.control_panel);
        llControlNext = findViewById(R.id.ll_control_next);
        llControlPrev = findViewById(R.id.ll_control_previous);
        llControlZoomIn = findViewById(R.id.ll_control_zoom_in);
        llControlZoomOut = findViewById(R.id.ll_control_zoom_out);
        rlControlPagePanel = findViewById(R.id.control_page_panel);
        btnControlPageInputOk = findViewById(R.id.control_page_input_ok);
        ivRefreshImage = findViewById(R.id.refreshImage);
        llControlJump = findViewById(R.id.ll_control_jump);
        llControlShare = findViewById(R.id.ll_control_share);
        //progressBarCircular = findViewById(R.id.circular_progress_bar);
        mActivity = this;

        if (getIntent() != null) {
            String mPath = getIntent().getStringExtra("DownloadUrl");
            Utils.showToast(this, mPath);

            if (!TextUtils.isEmpty(mPath)) {

                downloadAndOpenPDF(mActivity,mPath);
            }

        }
    }
    public  void downloadAndOpenPDF(final Context context, final String pdfUrl) {
        // Get filename
        String filename = "";
        try {
            filename = new GetFileInfo().execute(pdfUrl).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

        Log.e(TAG, "File Path:" + tempFile);
        if (tempFile.exists()) {
            // If we have downloaded the file before, just go ahead and show it.
            //openPDF(context, Uri.fromFile(tempFile));
            Uri uri = Uri.fromFile(new File(tempFile.getAbsolutePath()));
            displayFromUri(uri);
            return;
        }

        // Show progress dialog while downloading
        final ProgressDialog progress = ProgressDialog.show(context, "", "Please wait...", true);

        // Create the download request
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl));
        r.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!progress.isShowing()) {
                    return;
                }
                context.unregisterReceiver(this);

                progress.dismiss();
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        displayFromUri(Uri.fromFile(tempFile));
                    }else {
                        progress.dismiss();
                    }
                }
                c.close();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // Enqueue the request
        dm.enqueue(r);

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        this.pageCount = pageCount;
        pageNumber = page;

    }

    @Override
    public void loadComplete(int nbPages) {

        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        t.printStackTrace();
    }

    class GetFileInfo extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            URL url;
            String filename = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                conn.setInstanceFollowRedirects(false);
                if (conn.getHeaderField("Content-Disposition") != null) {
                    String depo = conn.getHeaderField("Content-Disposition");

                    String depoSplit[] = depo.split("filename=");
                    filename = depoSplit[1].replace("filename=", "").replace("\"", "").trim();
                } else {
                    filename = "download.pdf";
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            }

            return filename;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("comeee.......","hiiiiiiiiii");

        }
    }

    private void displayFromUri(Uri uri) {
        isDocOpened = true;
        pdfView.recycle();
        if (pageNumber > pageCount)
            return;
        try {
            if (pdfView.isRecycled()) {
                pdfView.fromUri(uri)
                        .defaultPage(pageNumber)
                        .onPageChange(this)
                        .enableAnnotationRendering(true)
                        .onLoad(this)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(10)
                        .onPageError(this)
                        .swipeHorizontal(true)
                        .load();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }




}
