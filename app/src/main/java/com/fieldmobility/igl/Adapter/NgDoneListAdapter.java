package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.List;

public class NgDoneListAdapter  extends RecyclerView.Adapter<NgDoneListAdapter.NgDoneListViewHolder> implements Filterable {
    private Context mctx;
    private List<NguserListModel> ngUserClaimList;
    private List<NguserListModel> new_ngUserClaimList;
    private String jmr_number;

    public NgDoneListAdapter(Context context, List<NguserListModel> ngUserClaimList) {
        this.ngUserClaimList = ngUserClaimList;
        this.new_ngUserClaimList = ngUserClaimList;
        this.mctx = context;
    }

    @NonNull
    @Override
    public NgDoneListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mctx).inflate(R.layout.ng_done_row_item, parent, false);



        return new NgDoneListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NgDoneListViewHolder holder, int position) {
        final NguserListModel ngUserClaimListModel = ngUserClaimList.get(position);
        holder.tv_bpName.setText(ngUserClaimListModel.getBp_no());
        holder.tv_address.setText(ngUserClaimListModel.getHouse_no() + ngUserClaimListModel.getCity());
        holder.tv_dateTime.setText(ngUserClaimListModel.getConversion_date());
        holder.user_name_text.setText(ngUserClaimListModel.getCustomer_name());
        if(!TextUtils.isEmpty((ngUserClaimListModel.getStatus()))){
            holder.status_text.setText(ngUserClaimListModel.getStatus());
        }else {
            holder.status_text.setText("--");
        }

    }

    @Override
    public long getItemId(int position){
        return position;

    }
    @Override
    public int getItemViewType(int position){
        return position;
    }



    @Override
    public int getItemCount() {
        return ngUserClaimList.size();
    }






    class NgDoneListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName, tv_dateTime, tv_perferedTime, tv_address,user_name_text,status_text;
        LinearLayout ll_bpNumber;
        View itemView;
        NgDoneListViewHolder(View itemView) {
            super(itemView);
            tv_bpName = itemView.findViewById(R.id.tv_bpNumber);
            tv_dateTime = itemView.findViewById(R.id.tv_perferedTime);
            tv_address = itemView.findViewById(R.id.tv_address);
            user_name_text = itemView.findViewById(R.id.user_name_text);
            status_text = itemView.findViewById(R.id.status_text);
            this.itemView = itemView;
        }

    }




    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ngUserClaimList = new_ngUserClaimList;
                } else {
                    List<NguserListModel> filteredList = new ArrayList<>();
                    for (NguserListModel row : new_ngUserClaimList) {
                        if (row.getBp_no().toLowerCase().contains(charString.toLowerCase()) || row.getCustomer_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    ngUserClaimList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = ngUserClaimList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ngUserClaimList = (ArrayList<NguserListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

