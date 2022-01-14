package com.fieldmobility.igl.MITDtoRFC;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.RFC_StatusMastar_Page;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MITDRFC_Adapter extends RecyclerView.Adapter<MITDRFC_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    String Vender_Name,Vender_No;
    SharedPrefs sharedPrefs;
    static String log = "rfcapprovaladapter";
    public MITDRFC_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.context = context;
    }

    @Override
    public MITDRFC_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mitdrfcadapter, parent, false);
        sharedPrefs=new SharedPrefs(context);
        MITDRFC_Adapter.ViewHolder viewHolder = new MITDRFC_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MITDRFC_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
        if (Bp_No_array.getIgl_rfcvendor_assigndate().equalsIgnoreCase("null")||Bp_No_array.getIgl_rfcvendor_assigndate().equals(null))
        {
            holder.date_text.setText("NA");
        }
        else {
            holder.date_text.setText("Assign Date - " +Bp_No_array.getIgl_rfcvendor_assigndate());
        }

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
        holder.address_text.setText(Bp_No_array.getHouse_no()+" "+Bp_No_array.getFloor()+" "+Bp_No_array.getHouse_type()+" "+Bp_No_array.getArea()+" "+Bp_No_array.getSociety()+" \n"
                +Bp_No_array.getBlock_qtr_tower_wing()+" "+Bp_No_array.getStreet_gali_road()+" "+Bp_No_array.getLandmark()+" "+Bp_No_array.getCity_region()
        +"\nControl Room- "+Bp_No_array.getControlRoom());


        if(Bp_No_array.getIgl_status().equals("null")){
            holder.status_text.setVisibility(View.GONE);
            Log.d(log,"igl status = "+Bp_No_array.getIgl_status());
        }else {
            if(Bp_No_array.getIgl_status().equals("13")){
                holder.status_text.setVisibility(View.VISIBLE);
                holder.status_text.setText("Testing & Commissioning Pending");
                Log.d(log,"igl status = "+Bp_No_array.getIgl_status());
            }else {
                holder.status_text.setVisibility(View.GONE);
            }

        }

        if(Bp_No_array.getRfcTpi().equals("null")){
            holder.info_button.setVisibility(View.GONE);
            holder.jobstart_button.setVisibility(View.GONE);

            Log.d(log,"igl claim if = "+Bp_No_array.getClaimFlag());
        }else {
            holder.info_button.setVisibility(View.VISIBLE);
            holder.jobstart_button.setVisibility(View.VISIBLE);
            Log.d(log,"igl claim else = "+Bp_No_array.getClaimFlag() + holder.info_button.getVisibility());
        }

        if (!TextUtils.isEmpty(Bp_No_array.getAmount())){
            if (Bp_No_array.getAmount().equalsIgnoreCase("2")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#90EE90"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Intrested Customer");

            }else if (Bp_No_array.getAmount().equalsIgnoreCase("0")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Normal Customer");
            }
        }

        holder.info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vender_Name = bp_no_list_array.get(position).getRfcTpi();
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
                String lead_no = bp_no_list_array.get(position).getLead_no();

                String Last_name = bp_no_list_array.get(position).getLast_name();
                String Mobile_number = bp_no_list_array.get(position).getMobile_number();

                String Bp_number = bp_no_list_array.get(position).getBp_number();
                String codeGroup = bp_no_list_array.get(position).getIgl_code_group();
                String custName = First_name+" "+Last_name;

                    Intent intent=new Intent(context, MITD_Done.class);
                    intent.putExtra("custname",custName);
                    intent.putExtra("mobile",Mobile_number);
                    intent.putExtra("leadno",lead_no);
                    intent.putExtra("Bp_number",Bp_number);
                    intent.putExtra("codeGroup",codeGroup);

                    context.startActivity(intent);


            }

        });





    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }



    public void setData(List<Bp_No_Item> filterList)
    {
        Log.d("rfcLi","setData = "+filterList.size());
       // this.bp_no_list_array.clear();
       this.bp_no_list_array = filterList;
       notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,mobile_text,descreption_text,cont_id,tv_priority;
        public CardView linearLayout;
        public Button info_button,jobstart_button;
        RelativeLayout relativeLayout;
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

            tv_priority = itemView.findViewById(R.id.tv_priority);
            relativeLayout = itemView.findViewById(R.id.rl_rfclist);

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
                        if (row.getBp_number().toLowerCase().contains(charString.toLowerCase()) || row.getFirst_name().toLowerCase().contains(charString.toLowerCase())
                                ||row.getHouse_no().toLowerCase().contains(charString.toLowerCase())||row.getFloor().toLowerCase().contains(charString.toLowerCase())
                                ||row.getArea().toLowerCase().contains(charString.toLowerCase())||row.getSociety().toLowerCase().contains(charString.toLowerCase())
                                ||row.getBlock_qtr_tower_wing().toLowerCase().contains(charString.toLowerCase())) {
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