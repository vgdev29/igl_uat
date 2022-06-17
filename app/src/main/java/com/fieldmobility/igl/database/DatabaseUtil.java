package com.fieldmobility.igl.database;

import android.content.Context;
import android.os.AsyncTask;

import com.fieldmobility.igl.Model.NguserListModel;

public  class DatabaseUtil {

    public static void saveData(Context context,NguserListModel user,DatabaseSubmitListener listener){

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                MyDatabseClient.getInstance(context).getAppDatabase()
                        .ngUserDao()
                        .insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onDataSaved(); // if saved successfull otherwise call onFailure
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }
}
