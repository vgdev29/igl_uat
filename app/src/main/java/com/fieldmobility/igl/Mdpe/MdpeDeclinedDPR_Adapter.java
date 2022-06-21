package com.fieldmobility.igl.Mdpe;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.List;

public class MdpeDeclinedDPR_Adapter extends RecyclerView.Adapter<MdpeDeclinedDPR_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    public List<DprDetails_Model> dprlist;
    public List<DprDetails_Model> new_dprlist;
    SharedPrefs sharedPrefs;
    static String log = "mdpeadapter";
    public MdpeDeclinedDPR_Adapter(Context context, List<DprDetails_Model> mdpelist) {
        this.dprlist = mdpelist;
        this.new_dprlist = mdpelist;
        this.context = context;
    }

    @Override
    public MdpeDeclinedDPR_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mdpedeclined_dpr, parent, false);
        sharedPrefs=new SharedPrefs(context);
        MdpeDeclinedDPR_Adapter.ViewHolder viewHolder = new MdpeDeclinedDPR_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MdpeDeclinedDPR_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final DprDetails_Model dpr = dprlist.get(position);
        holder.tv_allo.setText(dpr.getAllocation_no());
        holder.tv_suballo.setText(dpr.getSub_allocation());
        holder.tv_dpr.setText(dpr.getDpr_no());
        holder.tv_submit_date.setText(dpr.getCreation_date());
        holder.tv_tpiaction.setText(dpr.tpiaction);
        holder.tv_tpiremarks.setText(dpr.tpiremarks);

        holder.mdpe_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Please fill the DPR Again with correction",Toast.LENGTH_LONG).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return dprlist.size();
    }


    public void setData(List<DprDetails_Model> filterList)
    {
        Log.d("mdpelist","setData = "+filterList.size());
       this.dprlist = filterList;
       notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_allo, tv_dpr, tv_submit_date,tv_suballo,tv_tpiaction,tv_tpiremarks;
        LinearLayout mdpe_card;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_allo =  itemView.findViewById(R.id.tv_allo_num);
            tv_suballo =  itemView.findViewById(R.id.tv_suballo);
            tv_dpr =  itemView.findViewById(R.id.tv_dpr);
            tv_submit_date =  itemView.findViewById(R.id.tv_submit_date);
            mdpe_card = itemView.findViewById(R.id.mdpe_card);
            tv_tpiaction = itemView.findViewById(R.id.tv_tpiaction);
            tv_tpiremarks = itemView.findViewById(R.id.tv_tpiremarks);


        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dprlist = new_dprlist;
                } else {
                    List<DprDetails_Model> filteredList = new ArrayList<>();
                    for (DprDetails_Model row : new_dprlist) {
                        if (row.getAllocation_no().toLowerCase().contains(charString.toLowerCase())
                                || row.getSub_allocation().toLowerCase().contains(charString.toLowerCase())
                                ||row.getDpr_no().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }
                    dprlist = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dprlist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dprlist = (ArrayList<DprDetails_Model>) filterResults.values;
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