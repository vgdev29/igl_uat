package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import com.fieldmobility.igl.Activity.KYC_VerificationDetails;
import com.fieldmobility.igl.Activity.Kyc_Form_Activity;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.List;

public class KYC_Adapter  extends RecyclerView.Adapter<KYC_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;

    public KYC_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;

        this.context = context;
    }
    @Override
    public KYC_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bp_no_listing_item, parent, false);
        KYC_Adapter.ViewHolder viewHolder = new KYC_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KYC_Adapter.ViewHolder holder, final int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
            holder.date_text.setText(Bp_No_array.getBp_date());
            holder.bp_no_text.setText(Bp_No_array.getBp_number());
            holder.user_name_text.setText(Bp_No_array.getFirst_name());
            holder.address_text.setText(Bp_No_array.getHouse_no() + " " + Bp_No_array.getFloor() + " " + Bp_No_array.getHouse_type() + " " + Bp_No_array.getSociety() + " \n"
                    + Bp_No_array.getBlock_qtr_tower_wing() + " " + Bp_No_array.getStreet_gali_road() + " " + Bp_No_array.getLandmark() + " " + Bp_No_array.getCity_region()
                    + "\nControl room - " + Bp_No_array.getControlRoom());
            Log.e("KYC Status", Bp_No_array.getIgl_status());
            if (Bp_No_array.getIgl_status().equalsIgnoreCase("2")) {
                holder.status_text.setText("KYC Pending");
            }
            else {
                holder.status_text.setText("View KYC Details");
            }
            holder.mobile_text.setText(Bp_No_array.getMobile_number());
            holder.liner_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String First_name = bp_no_list_array.get(position).getFirst_name();
                    String Middle_name = bp_no_list_array.get(position).getMiddle_name();
                    String Last_name = bp_no_list_array.get(position).getLast_name();
                    String Mobile_number = bp_no_list_array.get(position).getMobile_number();
                    String Email_id = bp_no_list_array.get(position).getEmail_id();
                    String Aadhaar_number = bp_no_list_array.get(position).getAadhaar_number();
                    String City_region = bp_no_list_array.get(position).getCity_region();
                    String Area = bp_no_list_array.get(position).getArea();
                    String Society = bp_no_list_array.get(position).getSociety();
                    String Landmark = bp_no_list_array.get(position).getLandmark();
                    String House_type = bp_no_list_array.get(position).getHouse_type();
                    String House_no = bp_no_list_array.get(position).getHouse_no();
                    String Block_qtr_tower_wing = bp_no_list_array.get(position).getBlock_qtr_tower_wing();
                    String Floor = bp_no_list_array.get(position).getFloor();
                    String Street_gali_road = bp_no_list_array.get(position).getStreet_gali_road();
                    String Pincode = bp_no_list_array.get(position).getPincode();
                    String Customer_type = bp_no_list_array.get(position).getCustomer_type();
                    String Lpg_company = bp_no_list_array.get(position).getLpg_company();
                    String Bp_number = bp_no_list_array.get(position).getBp_number();
                    if (bp_no_list_array.get(position).getIgl_status().equalsIgnoreCase("2")) {
                        Intent intent = new Intent(context, Kyc_Form_Activity.class);
                        intent.putExtra("First_name", First_name);
                        intent.putExtra("Middle_name", Middle_name);
                        intent.putExtra("Last_name", Last_name);
                        intent.putExtra("Mobile_number", Mobile_number);
                        intent.putExtra("Email_id", Email_id);
                        intent.putExtra("Aadhaar_number", Aadhaar_number);
                        intent.putExtra("City_region", City_region);
                        intent.putExtra("Area", Area);
                        intent.putExtra("Society", Society);
                        intent.putExtra("Landmark", Landmark);
                        intent.putExtra("House_type", House_type);
                        intent.putExtra("House_no", House_no);
                        intent.putExtra("Block_qtr_tower_wing", Block_qtr_tower_wing);
                        intent.putExtra("Floor", Floor);
                        intent.putExtra("Street_gali_road", Street_gali_road);
                        intent.putExtra("Pincode", Pincode);
                        intent.putExtra("Customer_type", Customer_type);
                        intent.putExtra("Lpg_company", Lpg_company);
                        intent.putExtra("Bp_number", Bp_number);

                        intent.putExtra("address", bp_no_list_array.get(position).getAddress_image());
                        intent.putExtra("id", bp_no_list_array.get(position).getId_image());
                        intent.putExtra("custsig", bp_no_list_array.get(position).getCustomer_image());
                        intent.putExtra("ownsig", bp_no_list_array.get(position).getOwner_image());


                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, KYC_VerificationDetails.class);
                        intent.putExtra("kycdata",bp_no_list_array.get(position));
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
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,mobile_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            mobile_text = itemView.findViewById(R.id.mobile_text);
            liner_layout = (LinearLayout)itemView.findViewById(R.id.liner_layout);
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
                    List<Bp_No_Item> filteredList = new ArrayList<>();
                    for (Bp_No_Item row : New_bp_no_list_array) {
                        if (row.getBp_number().toLowerCase().contains(charString.toLowerCase()) || row.getFirst_name().toLowerCase().contains(charString.toLowerCase())) {
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
                bp_no_list_array = (ArrayList<Bp_No_Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }
}