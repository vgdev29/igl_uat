package com.fieldmobility.igl.Mdpe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MITDtoRFC.MITD_Done;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.Riser.activity.RiserFormActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MdpeList_Adapter extends RecyclerView.Adapter<MdpeList_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    public List<MdpeSubAllocation> mdpesublist;
    public List<MdpeSubAllocation> new_mdpesublist;
    String Vender_Name,Vender_No;
    SharedPrefs sharedPrefs;
    static String log = "mdpeadapter";
    public MdpeList_Adapter(Context context, List<MdpeSubAllocation> mdpelist) {
        this.mdpesublist = mdpelist;
        this.new_mdpesublist = mdpelist;
        this.context = context;
    }

    @Override
    public MdpeList_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mdpelist_adapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        MdpeList_Adapter.ViewHolder viewHolder = new MdpeList_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MdpeList_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MdpeSubAllocation mdpesuball = mdpesublist.get(position);
        holder.tv_allo.setText(mdpesuball.getAllocationNumber());
        holder.tv_suballo.setText(mdpesuball.getSuballocationNumber());
        holder.tv_wbs.setText(mdpesuball.getWbsNumber());
        holder.tv_address.setText(mdpesuball.getCity()+" "+mdpesuball.getArea()+" "+mdpesuball.getSociety()+" "+mdpesuball.getZone());
        holder.tv_assign_date.setText(mdpesuball.getAgentAssignDate());
        holder.tv_assignment.setText(
        mdpesuball.getMethod()+" "+mdpesuball.getAreaType()+" "+mdpesuball.getSize()+" "+mdpesuball.getLength()+" "+mdpesuball.getTrenchlessMethod());

        try {
            holder.tv_tpi.setText(mdpesuball.getUserName()+"\n"+mdpesuball.getUserMob());
            holder.tv_tpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+ mdpesuball.getTpiClaim()));
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
        return mdpesublist.size();
    }



    public void setData(List<MdpeSubAllocation> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.mdpesublist = filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_allo, tv_assignment, tv_assign_date, tv_address,tv_suballo,tv_tpi,tv_wbs;
        LinearLayout mdpe_card;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_allo =  itemView.findViewById(R.id.tv_allo_num);
            tv_suballo =  itemView.findViewById(R.id.tv_suballo);
            tv_wbs =  itemView.findViewById(R.id.tv_wbs);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_assign_date =  itemView.findViewById(R.id.tv_assign_date);
            tv_tpi = itemView.findViewById(R.id.tv_tpi);
            tv_assignment = itemView.findViewById(R.id.tv_assignment);
            mdpe_card = itemView.findViewById(R.id.mdpe_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MdpeSubAllocation mdpeSubAllocation = mdpesublist.get(getAdapterPosition());
                    if(mdpeSubAllocation.getTpiClaim()!=0 ) {
                        Intent intent = new Intent(context, MdpeTiles.class);
                        intent.putExtra("data", mdpeSubAllocation);
                        context.startActivity(intent);
                    }
                    else
                    {
                        CommonUtils.toast_msg(context,"Tpi not Calimed.... ");
                    }
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
                    mdpesublist = new_mdpesublist;
                } else {
                    List<MdpeSubAllocation> filteredList = new ArrayList<>();
                    for (MdpeSubAllocation row : new_mdpesublist) {
                        if (row.getAllocationNumber().toLowerCase().contains(charString.toLowerCase())
                                || row.getSuballocationNumber().toLowerCase().contains(charString.toLowerCase())
                                ||row.getWbsNumber().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }
                    mdpesublist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mdpesublist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mdpesublist = (ArrayList<MdpeSubAllocation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    private void BP_N0_DilogBox(String vender_Name, final String vender_No) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bp_no_dilogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
        TextView  vendar_no=dialog.findViewById(R.id.vendar_no);
        bp_no_text.setText("TPI Name :- "+vender_Name);
        vendar_no.setText("TPI MobNo :- "+vender_No);
        vendar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ vender_No));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
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