package com.fieldmobility.igl.Complain;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Activity.NgSupUserDetailsActivity;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Mdpe.MdpeTiles;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ComSupList_Adapter extends RecyclerView.Adapter<ComSupList_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    public List<ComplainModel> complist;
    public List<ComplainModel> new_complist;
    String Vender_Name,Vender_No;
    SharedPrefs sharedPrefs;
    static String log = "compsupadapter";
    public ComSupList_Adapter(Context context, List<ComplainModel> comlist) {
        this.complist = comlist;
        this.new_complist = comlist;
        this.context = context;
    }

    @Override
    public ComSupList_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.comsuplist_adapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        ComSupList_Adapter.ViewHolder viewHolder = new ComSupList_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComSupList_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ComplainModel compm = complist.get(position);
        holder.tv_ticketnum.setText(compm.getTicketType());
        holder.tv_control.setText(compm.getControlRoom());
        holder.tv_bpnum.setText(""+compm.getBpNumber());
        holder.tv_comptype.setText(compm.getCompType());
        holder.tv_assign_date.setText(compm.getSupAssignDate());
        holder.tv_meter.setText(compm.getMeterNo());
        holder.schedule_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP_N0_DilogBox(compm.getTicketType());
            }
        });

        try {
            holder.tv_tpi.setText(compm.getTpiName()+"\n"+compm.getTpiMob());
            holder.tv_tpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+ compm.getTpiMob()));
                        context.startActivity(callIntent);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){}





    }
    @Override
    public int getItemCount() {
        return complist.size();
    }



    public void setData(List<ComplainModel> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.complist = filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ticketnum, tv_control, tv_assign_date, tv_bpnum,tv_comptype,tv_tpi,tv_meter;
        LinearLayout comp_card;
        Button schedule_appt;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_ticketnum =  itemView.findViewById(R.id.tv_ticket_num);
            tv_control =  itemView.findViewById(R.id.tv_control);
            tv_bpnum =  itemView.findViewById(R.id.tv_bpnum);
            tv_comptype =  itemView.findViewById(R.id.tv_complaintype);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            tv_tpi = itemView.findViewById(R.id.tv_tpi);
            tv_meter = itemView.findViewById(R.id.tv_meterno);
            comp_card = itemView.findViewById(R.id.comp_card);
            schedule_appt = itemView.findViewById(R.id.schedule_appt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComplainModel comp = complist.get(getAdapterPosition());

                        Intent intent = new Intent(context, CompSupStatus.class);
                        intent.putExtra("data", comp);
                        context.startActivity(intent);


                }
            });

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    complist = new_complist;
                } else {
                    List<ComplainModel> filteredList = new ArrayList<>();
                    for (ComplainModel row : new_complist) {
                        if (row.getTicketType().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }
                    complist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = complist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                complist = (ArrayList<ComplainModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    private void BP_N0_DilogBox(String ticket) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comp_schedule_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView ticket_text=dialog.findViewById(R.id.ticket_text);
        TextView  date=dialog.findViewById(R.id.date);
        TextView  time =dialog.findViewById(R.id.time);
        ticket_text.setText("Ticket :- "+ticket);
        date.setText("Date :- ");
        time.setText("Time :- ");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.DATE, 0);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                DatePickerDialog   pickerDialog_Date = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                Log.e("Date", year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);

                                date.setText("Date :- "+year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                pickerDialog_Date.getDatePicker().setMinDate(cldr.getTimeInMillis());
                //pickerDialog_Date.getDatePicker().setMaxDate(System.currentTimeMillis());
                pickerDialog_Date.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( "Time :- "+selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

        }});
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // finish();
            }
        });


        dialog.show();
    }

}