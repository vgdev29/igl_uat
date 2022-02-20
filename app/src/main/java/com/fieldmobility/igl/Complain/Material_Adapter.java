package com.fieldmobility.igl.Complain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.R;

import java.util.ArrayList;

public class Material_Adapter extends RecyclerView.Adapter<Material_Adapter.ViewHolder>   {
    Context context;
    // RecyclerView recyclerView;
    public ArrayList<CompMatMaster> compmatlist;
    static String log = "serviceadapter";
    public Material_Adapter(Context context, ArrayList<CompMatMaster> comlist) {
        this.compmatlist = comlist;
        this.context = context;
    }

    @Override
    public Material_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.servicelist_adapter, parent, false);

        Material_Adapter.ViewHolder viewHolder = new Material_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Material_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CompMatMaster compm = compmatlist.get(position);
        holder.servdescr.setText(compm.getMaterialDescription());
        holder.serviceno.setText(""+compm.getMaterialNo());
        holder.unit.setText(compm.getUnit());
        holder.rate.setText(""+compm.getRate());
        holder.quantity.setText(""+compm.getQty());
        holder.amount.setText(""+compm.getAmt());
        holder.remarks.setText(compm.getRemarks());




    }
    @Override
    public int getItemCount() {
        return compmatlist.size();
    }



    public void setData(ArrayList<CompMatMaster> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.compmatlist = filterList;
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