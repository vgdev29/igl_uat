package com.example.igl.Adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.igl.Activity.RFC_Connection_Listing;
import com.example.igl.Activity.RFC_StatusMastar_Page;
//import com.example.igl.Activity.TPI_Inspection_RFC_Activity;
import com.example.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.example.igl.Helper.CommonUtils;
import com.example.igl.Helper.SharedPrefs;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.R;

import java.util.ArrayList;
import java.util.List;

public class RFC_Adapter extends RecyclerView.Adapter<RFC_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private RFC_Adapter.ContactsAdapterListener listener;
    String Vender_Name,Vender_No;
    SharedPrefs sharedPrefs;
    static String log = "rfcapprovaladapter";
    public RFC_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array, RFC_Adapter.ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RFC_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.rfc_aproval_adapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        RFC_Adapter.ViewHolder viewHolder = new RFC_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RFC_Adapter.ViewHolder holder, final int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        holder.date_text.setText(Bp_No_array.getBp_date());
        holder.bp_no_text.setText(Bp_No_array.getBp_number());
        holder.user_name_text.setText(Bp_No_array.getFirst_name());
        holder.mobile_text.setText(Bp_No_array.getMobile_number());


        holder.mobile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ Bp_No_array.getMobile_number()));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getSociety()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getCity_region());


        if(Bp_No_array.getIgl_status().equals("null")){
            holder.status_text.setVisibility(View.GONE);
            Log.d(log,"igl status = "+Bp_No_array.getIgl_status());
        }else {
            if(Bp_No_array.getIgl_status().equals("2")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("RFC Pending");
                Log.d(log,"igl status = "+Bp_No_array.getIgl_status());
            }else {
                //holder.status_text.setVisibility(View.GONE);
            }
            if(Bp_No_array.getClaimFlag().equals("null")){
                holder.info_button.setVisibility(View.GONE);
                Log.d(log,"igl claim if = "+Bp_No_array.getClaimFlag());
            }else {
                holder.info_button.setVisibility(View.VISIBLE);
                Log.d(log,"igl claim else = "+Bp_No_array.getClaimFlag() + holder.info_button.getVisibility());
            }
            if(Bp_No_array.getJobFlag().equals("1")){
                holder.jobstart_button.setVisibility(View.VISIBLE);
                Log.d(log,"igl job if = "+Bp_No_array.getJobFlag());

            }else {
                holder.jobstart_button.setVisibility(View.GONE);
                Log.d(log,"igl job else = "+Bp_No_array.getJobFlag());
            }

        }

        holder.info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vender_Name = bp_no_list_array.get(position).getRfcvendorname();
                Vender_No = bp_no_list_array.get(position).getRFCMobileNo();
                Log.d(log,"Vender_Name= "+Vender_Name);
                Log.d(log,"Vender_No= "+Vender_No);
                BP_N0_DilogBox(Vender_Name,Vender_No);
            }
        });


        holder.jobstart_button.setOnClickListener(new View.OnClickListener() {
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
                String Bp_date = bp_no_list_array.get(position).getBp_date();
                String IGL_Status = bp_no_list_array.get(position).getIgl_status();
                String lpg_distributor = bp_no_list_array.get(position).getLpg_distributor();
                String lpg_conNo = bp_no_list_array.get(position).getLpg_conNo();
                String Unique_lpg_Id = bp_no_list_array.get(position).getUnique_lpg_Id();
                String lead_no = bp_no_list_array.get(position).getLead_no();
                String ownerName = bp_no_list_array.get(position).getOwnerName();
                String igl_code_group = bp_no_list_array.get(position).getIgl_code_group();
                String Fesibility_TPI_Name = bp_no_list_array.get(position).getFesabilityTpiName();
                String PipeLine_Length = bp_no_list_array.get(position).getPipeline_length();
                String PipeLine_Length_Id = bp_no_list_array.get(position).getPipeline_length_id();
                String RfcTpi = bp_no_list_array.get(position).getRfcTpi();
                String RfcVendor = bp_no_list_array.get(position).getRfcVendor();
                String JobFlag = bp_no_list_array.get(position).getJobFlag();
                String FesabilityTpimobileNo = bp_no_list_array.get(position).getFesabilityTpimobileNo();
                String VendorMobileNo = bp_no_list_array.get(position).getVendorMobileNo();
                String Rfcvendorname = bp_no_list_array.get(position).getRfcvendorname();
                String rfcAdmin = bp_no_list_array.get(position).getRfcAdmin();
                Log.e("JobFlag",JobFlag);
                if (JobFlag.equals("1")) {

                    Intent intent=new Intent(context, RFC_StatusMastar_Page.class);
                    intent.putExtra("First_name",First_name);
                    intent.putExtra("Middle_name",Middle_name);
                    intent.putExtra("Last_name",Last_name);
                    intent.putExtra("Mobile_number",Mobile_number);
                    intent.putExtra("Email_id",Email_id);
                    intent.putExtra("Aadhaar_number",Aadhaar_number);
                    intent.putExtra("City_region",City_region);
                    intent.putExtra("Area",Area);
                    intent.putExtra("Society",Society);
                    intent.putExtra("Landmark",Landmark);
                    intent.putExtra("House_type",House_type);
                    intent.putExtra("House_no",House_no);
                    intent.putExtra("Block_qtr_tower_wing",Block_qtr_tower_wing);
                    intent.putExtra("Floor",Floor);
                    intent.putExtra("Street_gali_road",Street_gali_road);
                    intent.putExtra("Pincode",Pincode);
                    intent.putExtra("Customer_type",Customer_type);
                    intent.putExtra("Lpg_company",Lpg_company);
                    intent.putExtra("Bp_number",Bp_number);
                    intent.putExtra("Bp_date",Bp_date);
                    intent.putExtra("IGL_Status",IGL_Status);
                    intent.putExtra("lpg_distributor",lpg_distributor);
                    intent.putExtra("lpg_conNo",lpg_conNo);
                    intent.putExtra("Unique_lpg_Id",Unique_lpg_Id);
                    intent.putExtra("lead_no",lead_no);
                    intent.putExtra("ownerName",ownerName);
                    intent.putExtra("igl_code_group",igl_code_group);
                    intent.putExtra("Feasibility_Type","1");
                    intent.putExtra("TPI_Status_Code","1");
                    intent.putExtra("Fesibility_TPI_Name",Fesibility_TPI_Name);
                    intent.putExtra("PipeLine_Length",PipeLine_Length);
                    intent.putExtra("PipeLine_Length_Id",PipeLine_Length_Id);
                    intent.putExtra("FesabilityTpimobileNo",FesabilityTpimobileNo);
                    intent.putExtra("VendorMobileNo",VendorMobileNo);
                    intent.putExtra("Rfcvendorname",Rfcvendorname);
                    intent.putExtra("rfcAdmin",rfcAdmin);
                    context.startActivity(intent);

                }else {
                    CommonUtils.toast_msg(context,"Pls get claimed to start the job");
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
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,mobile_text,descreption_text,cont_id;
        public CardView linearLayout;
        public Button info_button,jobstart_button;
        public ViewHolder(View itemView) {
            super(itemView);
            mobile_text=(TextView)itemView.findViewById(R.id.mobile_text);

            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            info_button= (Button) itemView.findViewById(R.id.info_button);
            jobstart_button = itemView.findViewById(R.id.job_start_button);
            cont_id = itemView.findViewById(R.id.contid_text);
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