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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Service_Adapter extends RecyclerView.Adapter<Service_Adapter.ViewHolder>   {
    Context context;
    // RecyclerView recyclerView;
    public ArrayList<CompServMaster> compservlist;
    static String log = "serviceadapter";
    public Service_Adapter(Context context, ArrayList<CompServMaster> comlist) {
        this.compservlist = comlist;

        this.context = context;
    }

    @Override
    public Service_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.servicelist_adapter, parent, false);

        Service_Adapter.ViewHolder viewHolder = new Service_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Service_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CompServMaster compm = compservlist.get(position);
        holder.servdescr.setText(compm.getServiceDescription());
        holder.serviceno.setText(""+compm.getServiceNo());
        holder.unit.setText(compm.getUnit());
        holder.rate.setText(""+compm.getRate());
        holder.quantity.setText(""+compm.getQty());
        holder.amount.setText(""+compm.getAmt());
        holder.remarks.setText(compm.getRemarks());

    }
    @Override
    public int getItemCount() {
        return compservlist.size();
    }



    public void setData(ArrayList<CompServMaster> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.compservlist = filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView amount, serviceno,servdescr,unit,rate,quantity,remarks;



        public ViewHolder(View itemView) {
            super(itemView);
            serviceno = itemView.findViewById(R.id.serviceno);
            servdescr = itemView.findViewById(R.id.servicedesc);
            unit = itemView.findViewById(R.id.unit);
            rate = itemView.findViewById(R.id.rate);
            amount = itemView.findViewById(R.id.amount);
            quantity = itemView.findViewById(R.id.quantity);
            remarks = itemView.findViewById(R.id.remarks);





        }
    }






}