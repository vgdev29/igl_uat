package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.fieldmobility.igl.Activity.TPI_Connection_Activity;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.BpDetail;
import com.fieldmobility.igl.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TPI_Fesivility_Adapter extends RecyclerView.Adapter<TPI_Fesivility_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private ArrayList<Bp_No_Item> bp_no_list_array;
    private ArrayList<Bp_No_Item> New_bp_no_list_array;
    //private TPI_Fesivility_Adapter.ContactsAdapterListener listener;
   /* public TPI_Fesivility_Adapter(Context context, ArrayList<BpDetail> New_bp_no_list_array*//*, TPI_Fesivility_Adapter.ContactsAdapterListener listener*//*) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        //   this.listener = listener;
        this.context = context;
    }*/
    public TPI_Fesivility_Adapter(Context context2, ArrayList<Bp_No_Item> list) {
        this.bp_no_list_array = list;
        this.New_bp_no_list_array = list;
        this.context = context2;
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
    public void onBindViewHolder(TPI_Fesivility_Adapter.ViewHolder holder,  int i) {
        final Bp_No_Item bp_No_Item = this.bp_no_list_array.get(i);
        holder.fesibility_date_text.setVisibility(View.VISIBLE);
        holder.fesibility_date_text.setText("Assign Date: "+bp_No_Item.getFesabilityDate());
        holder.date_text.setVisibility(View.GONE);
        holder.bp_no_text.setText(bp_No_Item.getBp_number());
        holder.user_name_text.setText(bp_No_Item.getFirst_name());
        holder.address_text.setText(bp_No_Item.getHouse_no()+" "+bp_No_Item.getFloor()+" "+bp_No_Item.getHouse_type()+" "+bp_No_Item.getArea()+" "+bp_No_Item.getSociety()+" \n"
                +bp_No_Item.getBlock_qtr_tower_wing()+" "+bp_No_Item.getStreet_gali_road()+" "+bp_No_Item.getLandmark()+" "+bp_No_Item.getCity_region()
                +"\nControl room - "+bp_No_Item.getControlRoom());
        holder.mobile_text.setText(bp_No_Item.getMobile_number());
        holder.mobile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" +bp_No_Item.getMobile_number() ));
                    context.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        if(bp_No_Item.getIgl_status().equals("null")){
            holder.status_text.setVisibility(View.GONE);
        }else {
            if(bp_No_Item.getIgl_status().equals("0")){

                    holder.status_text.setVisibility(View.VISIBLE);
                    holder.status_text.setText("Feasibility Pending");


            }else {
                //holder.status_text.setVisibility(View.GONE);
            }

        }

        holder.liner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name =  bp_no_list_array.get(i).getFirst_name();
                String middle_name = bp_no_list_array.get(i).getMiddle_name();
                String last_name = bp_no_list_array.get(i).getLast_name();
                String mobile_number = bp_no_list_array.get(i).getMobile_number();
                String email_id = bp_no_list_array.get(i).getEmail_id();
                String aadhaar_number = bp_no_list_array.get(i).getAadhaar_number();
                String city_region = bp_no_list_array.get(i).getCity_region();
                String area = bp_no_list_array.get(i).getArea();
                String society = bp_no_list_array.get(i).getSociety();
                String landmark = bp_no_list_array.get(i).getLandmark();
                String house_type = bp_no_list_array.get(i).getHouse_type();
                String house_no = bp_no_list_array.get(i).getHouse_no();
                String block_qtr_tower_wing = bp_no_list_array.get(i).getBlock_qtr_tower_wing();
                String floor = bp_no_list_array.get(i).getFloor();
                String street_gali_road = bp_no_list_array.get(i).getStreet_gali_road();
                String pincode = bp_no_list_array.get(i).getPincode();
                String customer_type = bp_no_list_array.get(i).getCustomer_type();
                String lpg_company = bp_no_list_array.get(i).getLpg_company();
                String bp_number = bp_no_list_array.get(i).getBp_number();
                String bp_date = bp_no_list_array.get(i).getBp_date();
                String igl_status = bp_no_list_array.get(i).getIgl_status();
                String lpg_distributor = bp_no_list_array.get(i).getLpg_distributor();
                String lpg_conNo = bp_no_list_array.get(i).getLpg_conNo();
                String unique_lpg_Id = bp_no_list_array.get(i).getUnique_lpg_Id();
                String lead_no = bp_no_list_array.get(i).getLead_no();
                String igl_code_group = bp_no_list_array.get(i).getIgl_code_group();
                String ownerName = bp_no_list_array.get(i).getOwnerName();
                Intent intent = new Intent(TPI_Fesivility_Adapter.this.context, TPI_Connection_Activity.class);
                intent.putExtra("First_name", first_name);
                intent.putExtra("Middle_name", middle_name);
                intent.putExtra("Last_name", last_name);
                intent.putExtra("Mobile_number", mobile_number);
                intent.putExtra("Email_id", email_id);
                intent.putExtra("Aadhaar_number", aadhaar_number);
                intent.putExtra("City_region", city_region);
                intent.putExtra("Area", area);
                intent.putExtra("Society", society);
                intent.putExtra("Landmark", landmark);
                intent.putExtra("House_type", house_type);
                intent.putExtra("House_no", house_no);
                intent.putExtra("Block_qtr_tower_wing", block_qtr_tower_wing);
                intent.putExtra("Floor", floor);
                intent.putExtra("Street_gali_road", street_gali_road);
                intent.putExtra("Pincode", pincode);
                intent.putExtra("Customer_type", customer_type);
                intent.putExtra("Lpg_company", lpg_company);
                intent.putExtra("Bp_number", bp_number);
                intent.putExtra("Bp_date", bp_date);
                intent.putExtra("IGL_Status", igl_status);
                intent.putExtra("lpg_distributor", lpg_distributor);
                intent.putExtra("lpg_conNo", lpg_conNo);
                intent.putExtra("Unique_lpg_Id", unique_lpg_Id);
                intent.putExtra("lead_no", lead_no);
                intent.putExtra("ownerName", ownerName );
                intent.putExtra("igl_code_group", igl_code_group);
                intent.putExtra("TPI_Status_Code", "0");
                intent.putExtra("Feasibility_Type", "0");
                  context.startActivity(intent);
            }
        });

        holder.refresh_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedetails( bp_No_Item,i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    private void updatedetails(Bp_No_Item bp_No_Item, int position)
    {
        CommonUtils.startProgressBar(context,"Loading...");
        Log.d("log", "updateNg = " + Constants.REFRESH_FEAS +bp_No_Item.getBp_number());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REFRESH_FEAS +bp_No_Item.getBp_number(), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CommonUtils.dismissProgressBar(context);
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d("log","response"+ response);
                    String status = jsonObject1.getString("status");
                    if (status.equals("200")) {
                        CommonUtils.toast_msg(context,jsonObject1.getString("message"));
                        JSONObject jsonObject= jsonObject1.getJSONObject("details");
                        Log.d("log","details"+ jsonObject.toString());
                        String name = jsonObject.getString("name");
                        String mob = jsonObject.getString("mob");

                        String society = jsonObject.getString("society");
                        String hno = jsonObject.getString("hno");
                        String block = jsonObject.getString("block");
                        String floor = jsonObject.getString("floor");
                        bp_No_Item.setFirst_name(name);
                        bp_No_Item.setMobile_number(mob);
                        bp_No_Item.setSociety(society);
                        bp_No_Item.setHouse_no(hno);
                        bp_No_Item.setBlock_qtr_tower_wing(block);
                        bp_No_Item.setFloor(floor);
                        bp_no_list_array.set(position,bp_No_Item);
                        notifyDataSetChanged();


                    }
                    else
                    {
                        CommonUtils.toast_msg( context,jsonObject1.getString("message"));
                    }
                } catch (JSONException e) {
                    CommonUtils.dismissProgressBar(context);
                    CommonUtils.toast_msg(context,"Oops..Error loading status!!");
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtils.dismissProgressBar(context);
                CommonUtils.toast_msg(context,"Oops..TimeOut!!");
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bp_no_text,user_name_text,address_text,date_text,status_text,fesibility_date_text,mobile_text;
        public LinearLayout liner_layout;
        public CardView linearLayout;
        ImageButton refresh_data;
        public ViewHolder(View itemView) {
            super(itemView);
            fesibility_date_text = (TextView) itemView.findViewById(R.id.fesibility_date_text);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            bp_no_text = (TextView) itemView.findViewById(R.id.bp_no_text);
            user_name_text = (TextView) itemView.findViewById(R.id.user_name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            status_text= (TextView) itemView.findViewById(R.id.status_text);
            mobile_text =itemView.findViewById(R.id.mobile_text);
            liner_layout = (LinearLayout) itemView.findViewById(R.id.liner_layout);
            refresh_data = itemView.findViewById(R.id.refresh_data);
            refresh_data.setVisibility(View.VISIBLE);
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
                    ArrayList<Bp_No_Item> filteredList = new ArrayList<>();
                    for (Bp_No_Item bp_No_Item : New_bp_no_list_array) {
                        if (bp_No_Item.getBp_number().toLowerCase().contains(charString.toLowerCase()) || bp_No_Item.getFirst_name().toLowerCase().contains(charString.toLowerCase()) || bp_No_Item.getFesabilityDate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(bp_No_Item);
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