package com.example.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igl.Activity.TPI_Connection_Activity;
import com.example.igl.Activity.TPI_RfcDone_Approval_Activity;
import com.example.igl.Activity.TPI_RfcHold_Approval_Activity;
import com.example.igl.Fragment.Feasibility_Tpi_Fragment;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.Model.BpDetail;
import com.example.igl.R;

import java.util.ArrayList;
import java.util.List;

public class TPI_Fesivility_Adapter extends RecyclerView.Adapter<TPI_Fesivility_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private ArrayList<BpDetail> bp_no_list_array;
    private ArrayList<BpDetail> New_bp_no_list_array;
    //private TPI_Fesivility_Adapter.ContactsAdapterListener listener;
    public TPI_Fesivility_Adapter(Context context, ArrayList<BpDetail> New_bp_no_list_array/*, TPI_Fesivility_Adapter.ContactsAdapterListener listener*/) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
     //   this.listener = listener;
        this.context = context;
    }

    @Override
    public TPI_Fesivility_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bp_no_listing_item, parent, false);
        TPI_Fesivility_Adapter.ViewHolder viewHolder = new TPI_Fesivility_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TPI_Fesivility_Adapter.ViewHolder holder, final int position) {
        final BpDetail Bp_No_array = bp_no_list_array.get(position);
        holder.fesibility_date_text.setVisibility(View.VISIBLE);
        holder.fesibility_date_text.setText("Fesibility Date: "+Bp_No_array.getFesabilityDate());
        holder.date_text.setText(Bp_No_array.getBpDate());
        holder.bp_no_text.setText(Bp_No_array.getBpNumber());
        holder.user_name_text.setText(Bp_No_array.getFirstName());
        holder.address_text.setText(Bp_No_array.getHouseNo()+" "+Bp_No_array.getSociety()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getCityRegion());

        if(Bp_No_array.getIglStatus().equals("null")){
            holder.status_text.setVisibility(View.GONE);
        }else {
            if(Bp_No_array.getIglStatus().equals("0")){
                if(Bp_No_array.getHoldStatus()!=null && Bp_No_array.getHoldStatus().equals("5")){
                    holder.status_text.setVisibility(View.VISIBLE);
                    holder.status_text.setText("Feasibility Check On Hold");
                }else {
                    holder.status_text.setVisibility(View.VISIBLE);
                    holder.status_text.setText("Feasibility Pending");
                }

            }else {
                //holder.status_text.setVisibility(View.GONE);
            }
            if(Bp_No_array.getIglStatus().equals("3")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Approval Pending \n RFC Done");
            }
            if(Bp_No_array.getIglStatus().equals("111")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Approval Pending \n RFC on Hold");
            }
        }

       holder.liner_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String First_name = bp_no_list_array.get(position).getFirstName();
               String Middle_name = bp_no_list_array.get(position).getMiddleName();
               String Last_name = bp_no_list_array.get(position).getLastName();
               String Mobile_number = bp_no_list_array.get(position).getMobileNumber();
               String Email_id = bp_no_list_array.get(position).getEmailId();
               String Aadhaar_number = bp_no_list_array.get(position).getAadhaarNumber();
               String City_region = bp_no_list_array.get(position).getCityRegion();
               String Area = bp_no_list_array.get(position).getArea();
               String Society = bp_no_list_array.get(position).getSociety();
               String Landmark = bp_no_list_array.get(position).getLandmark();
               String House_type = bp_no_list_array.get(position).getHouseType();
               String House_no = bp_no_list_array.get(position).getHouseNo();
               String Block_qtr_tower_wing = bp_no_list_array.get(position).getBlockQtrTowerWing();
               String Floor = bp_no_list_array.get(position).getFloor();
               String Street_gali_road = bp_no_list_array.get(position).getStreetGaliRoad();
               String Pincode = bp_no_list_array.get(position).getPincode();
               String Customer_type = bp_no_list_array.get(position).getCustomerType();
               String Lpg_company = bp_no_list_array.get(position).getLpgCompany();
               String Bp_number = bp_no_list_array.get(position).getBpNumber();
               String Bp_date = bp_no_list_array.get(position).getBpDate();
               String IGL_Status = bp_no_list_array.get(position).getIglStatus();

               String lpg_distributor = bp_no_list_array.get(position).getLpgDistributor();
               String lpg_conNo = bp_no_list_array.get(position).getLpgConNo();
               String Unique_lpg_Id = bp_no_list_array.get(position).getUniqueLpgId();
               String lead_no = bp_no_list_array.get(position).getLeadNo();
               String ownerName = bp_no_list_array.get(position).getOwnerName();
               String igl_code_group = bp_no_list_array.get(position).getIglCodeGroup();
               if (Bp_No_array.getIglStatus().equalsIgnoreCase("3")) {
                   Intent intent = new Intent(context, TPI_RfcDone_Approval_Activity.class);
                   intent.putExtra("bpno", Bp_number);
                   intent.putExtra("leadno", lead_no);
                   context.startActivity(intent);
               }
               else {
                   Intent intent = new Intent(context, TPI_RfcHold_Approval_Activity.class);
                   intent.putExtra("bpno", Bp_number);
                   intent.putExtra("leadno", lead_no);
                   context.startActivity(intent);
               }
           }
       });
    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,fesibility_date_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            fesibility_date_text = (TextView) itemView.findViewById(R.id.fesibility_date_text);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            liner_layout = (LinearLayout) itemView.findViewById(R.id.liner_layout);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bp_no_list_array = New_bp_no_list_array;
                } else {
                    ArrayList<BpDetail> filteredList = new ArrayList<>();
                    for (BpDetail row : New_bp_no_list_array) {
                        if (row.getBpNumber().toLowerCase().contains(charString.toLowerCase()) || row.getFirstName().toLowerCase().contains(charString.toLowerCase()) || row.getFesabilityDate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    bp_no_list_array = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bp_no_list_array;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bp_no_list_array = (ArrayList<BpDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }
}