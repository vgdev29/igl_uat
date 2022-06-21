package com.fieldmobility.igl.database;

import android.content.Context;
import android.os.AsyncTask;

import com.fieldmobility.igl.Model.NguserListModel;

import java.util.List;

public  class DatabaseUtil {

    public static void saveData(Context context,NguserListModel user,DatabaseSubmitListener listener){

        class InsertUser extends AsyncTask<Void, Void, Void> {

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

        InsertUser st = new InsertUser();
        st.execute();

    }
    public static void fetchNgUsersFromDb(Context context,String fetchType,ReadNgUsersListener listener){

        class FetchNgList extends AsyncTask<Void, Void, List<NguserListModel>> {

            @Override
            protected List<NguserListModel> doInBackground(Void... voids) {

                //adding to database
                List<NguserListModel> users = null;
                if (fetchType.equals("DP")) {
                    users = MyDatabseClient.getInstance(context).getAppDatabase()
                            .ngUserDao()
                            .getDPUsers("DP");
                }
                else if (fetchType.equals("OP")) {
                    users = MyDatabseClient.getInstance(context).getAppDatabase()
                            .ngUserDao()
                            .getOPUsers("OP");
                }
                else {
                    users = MyDatabseClient.getInstance(context).getAppDatabase()
                            .ngUserDao()
                            .getAllUsers();
                }
                return users;
            }

            @Override
            protected void onPostExecute(List<NguserListModel> list) {
                super.onPostExecute(list);
                if (list!=null)
                listener.onSuccess(list);
                else
                    listener.onFailure();
            }
        }

        FetchNgList st = new FetchNgList();
        st.execute();

    }
    public static void deleteUserByJmrNo(Context context,List<String> jmr_nos,DatabseDeleteListener listener){

        class InsertUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                MyDatabseClient.getInstance(context).getAppDatabase()
                        .ngUserDao()
                        .deleteUserByJmrNos(jmr_nos);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onDataDeleted();
            }
        }

        InsertUser st = new InsertUser();
        st.execute();

    }

}
