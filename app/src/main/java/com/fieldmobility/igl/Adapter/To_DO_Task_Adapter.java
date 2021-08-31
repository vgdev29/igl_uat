package com.fieldmobility.igl.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Activity.To_DoList_Activity;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.To_Do_Item;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class To_DO_Task_Adapter extends RecyclerView.Adapter<To_DO_Task_Adapter.ViewHolder>{

    Context context;
    DatePickerDialog picker;
    // RecyclerView recyclerView;
    private List<To_Do_Item> bp_no_list_array;
    String bp_no,todo_catagory_id;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    Button date_button;
     TextView date_text,time_text;
     EditText descreption_edit;
     TimePickerDialog pickerDialog_Time;
     String am_pm1;
    public To_DO_Task_Adapter(Context context,List<To_Do_Item> bp_no_list_array) {
        this.bp_no_list_array = bp_no_list_array;
        this.context = context;
    }
    @Override
    public To_DO_Task_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.to_do_task_item, parent, false);
        sharedPrefs=new SharedPrefs(context);
        To_DO_Task_Adapter.ViewHolder viewHolder = new To_DO_Task_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(To_DO_Task_Adapter.ViewHolder holder, final int position) {
        sharedPrefs=new SharedPrefs(context);
        final To_Do_Item bp_no_item = bp_no_list_array.get(position);
        holder.date_text.setText(bp_no_item.getDate());
        holder.title_text.setText(bp_no_item.getCategory_name());
        holder.descreption.setText("Description :- "+bp_no_item.getDescription());
        holder.bp_no_text.setText(bp_no_item.getBp());
        holder.address_text.setText("Address:- "+bp_no_item.getIgl_address());

        holder.scheduletask_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 bp_no=bp_no_list_array.get(position).getBp();
                 todo_catagory_id=bp_no_list_array.get(position).getTodo_id();
                Dilogbox_Session();

            }
        });
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo_catagory_id=bp_no_list_array.get(position).getTodo_id();
               // To_DO_Delete();
                BP_N0_DilogBox();
            }
        });
    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView video_image;
        public TextView title_text,date_text,descreption,bp_no_text,address_text;
        public LinearLayout video_layout;
        Button scheduletask_text,delete_button;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title_text = (TextView) itemView.findViewById(R.id.title_text);
            this.bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            this.address_text = (TextView) itemView.findViewById(R.id.address_text);
            this.descreption = (TextView) itemView.findViewById(R.id.descreption);
            this.date_text = (TextView) itemView.findViewById(R.id.date_text);
            this.scheduletask_text = (Button) itemView.findViewById(R.id.scheduletask_text);
            this.delete_button = (Button) itemView.findViewById(R.id.delete_button);

        }
    }
    private void Dilogbox_Session() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dilogbox_schedule_date);
        dialog.setCancelable(false);

        Button scheduletask_text = (Button) dialog.findViewById(R.id.scheduletask_text);
        Button date_button = (Button) dialog.findViewById(R.id.date_button);
        Button time_button = (Button) dialog.findViewById(R.id.time_button);
        date_text = (TextView) dialog.findViewById(R.id.date_text);
        time_text = (TextView) dialog.findViewById(R.id.time_text);
        descreption_edit= (EditText) dialog.findViewById(R.id.descreption_edit);
        ImageView crosse_image=dialog.findViewById(R.id.crose_img);
        crosse_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;
                                if(month < 10){
                                    formattedMonth = "0" + month;
                                }
                                if(dayOfMonth < 10){
                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date",year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                               date_text.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
                //dialog.dismiss();
            }
        });
        scheduletask_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To_DO_List();
                dialog.dismiss();
            }
        });
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerDialog_Time = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hour, int minutes) {
                                if(hour > 12) {
                                    am_pm1 = "PM";
                                    hour = hour - 12;
                                }
                                else
                                {
                                    am_pm1="AM";
                                }
                                time_text.setText(hour + ":" + minutes+" "+am_pm1);
                            }
                        }, hour, minutes, true);

                pickerDialog_Time.show();
            }
        });

        dialog.show();
    }
    public void To_DO_List() {

        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.TODO_UPDATE+sharedPrefs.getUUID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());


                            Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                            ((To_DoList_Activity)context).TO_DO_LIST();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    //params.put("id", sharedPrefs.getUUID());
                    //params.put("bpno",bp_no);
                    params.put("date", date_text.getText().toString());
                    params.put("time", time_text.getText().toString());
                    params.put("todo_id", todo_catagory_id);
                    params.put("description", descreption_edit.getText().toString());
                } catch (Exception e) {
                }
                return params;
            }
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }


    private void BP_N0_DilogBox() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        dialog.setCancelable(true);
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
        bp_no_text.setText("Delete Task");
        TextView vendar_no=dialog.findViewById(R.id.vendar_no);
        vendar_no.setText("Do You Want to Delete Task");
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        Button cancle_button= (Button) dialog.findViewById(R.id.cancle_button);
        cancle_button.setVisibility(View.VISIBLE);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                To_DO_Delete();
            }
        });

        cancle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void To_DO_Delete() {

        materialDialog = new MaterialDialog.Builder(context)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.POST, Constants.DeleteTODO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();

                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Response", json.toString());
                            if(json.getString("Sucess").equals("true")){
                                Toast.makeText(context, "" + "Successfully Updated", Toast.LENGTH_SHORT).show();
                                ((To_DoList_Activity)context).TO_DO_LIST();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("object",obj.toString());
                        JSONObject error1=obj.getJSONObject("error");
                        String error_msg=error1.getString("message");
                        //  Toast.makeText(Forgot_Password_Activity.this, "" + error_msg, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    //params.put("id", sharedPrefs.getUUID());
                    //params.put("bpno",bp_no);

                    params.put("todo_id", todo_catagory_id);
                } catch (Exception e) {
                }
                return params;
            }
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                //headers.put(" Content-Type", "text/html");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " +sharedPrefs.getToken());
                return headers;
            }*/
        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
}