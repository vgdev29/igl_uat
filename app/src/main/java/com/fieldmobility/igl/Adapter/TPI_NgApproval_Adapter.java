package com.fieldmobility.igl.Adapter;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.ViewNgDetaillsActivity;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TPI_NgApproval_Adapter extends RecyclerView.Adapter<TPI_NgApproval_Adapter.NgUserDoneDeclineListViewHolder> implements Filterable {
    private Context mctx;
    private List<NguserListModel> ngUserClaimList;
    private List<NguserListModel> new_ngUserClaimList;
    private List<NguserListModel> intent_ngUserClaimList;
    private String jmr_number;
    private MaterialDialog materialDialog;
    String log = "nguserapprovaladapter";

    public TPI_NgApproval_Adapter(Context context, List<NguserListModel> ngUserClaimList) {
        this.ngUserClaimList = ngUserClaimList;
        this.new_ngUserClaimList = ngUserClaimList;
        intent_ngUserClaimList = ngUserClaimList;
        this.mctx = context;
    }


    @NonNull
    @Override
    public NgUserDoneDeclineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mctx).inflate(R.layout.tpi_ng_approve_decline, parent, false);


        return new NgUserDoneDeclineListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NgUserDoneDeclineListViewHolder holder, final int position) {
        final NguserListModel ngUserClaimListModel = ngUserClaimList.get(position);
        holder.tv_dateTime.setText("NG Date- "+ngUserClaimListModel.getNg_update_date());
        holder.tv_bpName.setText(ngUserClaimListModel.getBp_no());
        holder.tv_address.setText(ngUserClaimListModel.getHouse_no() + " " + ngUserClaimListModel.getFloor() + " " + ngUserClaimListModel.getSociety() + " \n" + ngUserClaimListModel.getBlock_qtr() + " " + ngUserClaimListModel.getStreet() + " " + ngUserClaimListModel.getLandmark() + " " + ngUserClaimListModel.getCity()); holder.tv_dateTime.setText(ngUserClaimListModel.getConversion_date());
        holder.user_name_text.setText(ngUserClaimListModel.getCustomer_name());
        holder.tv_zone.setText(ngUserClaimListModel.getZone());
        holder.tv_mobile.setText(ngUserClaimListModel.getMobile_no());
        if (ngUserClaimListModel.getStatus().equalsIgnoreCase("DP")) {
            holder.status_text.setText("NG Done");
        } else if (ngUserClaimListModel.getStatus().equalsIgnoreCase("OP")) {
            holder.status_text.setText("NG on Hold");
        }
        if (ngUserClaimListModel.isMeter_status())
        {
            holder.tv_icml.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tv_icml.setVisibility(View.GONE);
        }
      /*  holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mctx,"click",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                if (ngUserClaimListModel.getStatus().equalsIgnoreCase("OP")) {
                    nguserListModel.setStatus("OH");
                } else if (ngUserClaimListModel.getStatus().equalsIgnoreCase("DP")) {
                    nguserListModel.setStatus("DN");
                }

                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                Claimed_API_POST(jmr_number, nguserListModel);

            }
        });*/
        holder.tv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + ngUserClaimListModel.getMobile_no()));
                    mctx.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.ngSupervisorinfo_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnected(mctx)) {
                    Utils.showProgressDialog(mctx);
                    loadNgAgentInfo(ngUserClaimListModel.getSupervisor_id(), ngUserClaimListModel.getContractor_id());
                }

            }
        });
        holder.btn_viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  //Toast.makeText(mctx,"click",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                jmr_number = ngUserClaimListModel.getJmrNo();
                nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                nguserListModel.setJmrNo(jmr_number);
                viewNGDetails(jmr_number);*/
                NguserListModel nguserListModel = new NguserListModel();
                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                Intent intent = new Intent(mctx, ViewNgDetaillsActivity.class);
                intent.putExtra("jmr_no", nguserListModel.getJmr_no());
                intent.putExtra("nglist",  ngUserClaimList.get(position));
                /*intent.putExtra("mAssignDate", nguserListModel.getRfC_Date());
                intent.putExtra("startJob",nguserListModel.getStart_job());*/
                mctx.startActivity(intent);

            }
        });
        /*holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguserListModel nguserListModel = new NguserListModel();
                //nguserListModel.setClaim(false);
                //nguserListModel.setStatus("DC");
                if (ngUserClaimListModel.getStatus().equalsIgnoreCase("OP")) {
                    nguserListModel.setStatus("PG");
                } else if (ngUserClaimListModel.getStatus().equalsIgnoreCase("DP")) {
                    nguserListModel.setStatus("PG");
                }
                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                UnClaimed_API_POST(jmr_number, nguserListModel);

            }
        });*/
    }



    @Override
    public long getItemId(int position) {
        return position;

    }
    public void setData(List<NguserListModel> ngUserClaimLists)
    {
        this.ngUserClaimList = ngUserClaimLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return ngUserClaimList.size();
    }

    private void loadNgAgentInfo(String supervisor_id, String contractor_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(mctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.CON_SUP_DETAILS + "Supervisoremailid=" + supervisor_id + "&Contractoremailid=" + contractor_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utils.hideProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject supObject = jsonObject.getJSONObject("SupervisorDetails");
                    JSONObject conObject = jsonObject.getJSONObject("ContractorDetails");


                    String conName = conObject.getString("firstName") ;
                    String conMob = conObject.getString("mobileNo");
                    String supName = supObject.getString("firstName") ;
                    String supMob = supObject.getString("mobileNo");
                    BP_N0_DilogBox(conName,conMob,supName,supMob);


                } catch (JSONException e) {
                    Utils.hideProgressDialog();
                    Log.d(log,"catch user details= "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void BP_N0_DilogBox(String conName, final String conMob, String supName, final String supMob  ) {
        final Dialog dialog = new Dialog(mctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rfc_info_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setTitle("Signature");
        TextView cont_name = dialog.findViewById(R.id.cont_name_text);
        TextView cont_mob = dialog.findViewById(R.id.cont_mob_no);
        TextView sup_name = dialog.findViewById(R.id.sup_name_text);
        TextView sup_mob = dialog.findViewById(R.id.sup_mob_no);
        cont_name.setText("Contractor:- " + conName);
        cont_mob.setText("MobNo :- " + conMob);
        sup_name.setText("Technician:- " + supName);
        sup_mob.setText("MobNo :- " + supMob);
        sup_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + supMob));
                    mctx.startActivity(callIntent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        cont_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" +  conMob));
                    mctx.startActivity(callIntent);
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

    class NgUserDoneDeclineListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName, tv_dateTime, tv_perferedTime, tv_address, user_name_text, status_text,tv_zone,tv_mobile,tv_icml;
        Button  btn_viewDetails,ngSupervisorinfo_text;
        LinearLayout ll_bpNumber;
        View itemView;

        NgUserDoneDeclineListViewHolder(View itemView) {
            super(itemView);
            tv_bpName = itemView.findViewById(R.id.bp_no_text);
            tv_dateTime = itemView.findViewById(R.id.date_text);
            tv_zone = itemView.findViewById(R.id.zone_text);
            tv_address = itemView.findViewById(R.id.address_text);
            tv_mobile = itemView.findViewById(R.id.mobile_text);
            ngSupervisorinfo_text = itemView.findViewById(R.id.ngSupervisorinfo_text);
            btn_viewDetails = itemView.findViewById(R.id.btn_viewDetails);
            tv_icml = itemView.findViewById(R.id.tv_incorrectmetercase);
            user_name_text = itemView.findViewById(R.id.user_name_text);
            status_text = itemView.findViewById(R.id.status_text);
            this.itemView = itemView;
        }

    }

    private void Claimed_API_POST(String jmr_number, NguserListModel nguserListModel) {
        materialDialog = new MaterialDialog.Builder(mctx)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number, nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", "weldone............");
                materialDialog.dismiss();

                Toast.makeText(mctx, "Approve Successfully", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", "error comes");
                materialDialog.dismiss();
                Toast.makeText(mctx, "Fail to success", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void UnClaimed_API_POST(String jmr_number, NguserListModel nguserListModel) {
        materialDialog = new MaterialDialog.Builder(mctx)
                .content("Please wait....")
                .progress(true, 0)
                .cancelable(false)
                .show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number, nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>", "weldone............");
                materialDialog.dismiss();

                Toast.makeText(mctx, "Decline Successfully ", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error", "error comes");
                materialDialog.dismiss();
                Toast.makeText(mctx, "Fail to success", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
public Filter    getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ngUserClaimList = new_ngUserClaimList;
                } else {
                    List<NguserListModel> filteredList = new ArrayList<>();
                    try {
                        for (NguserListModel row : new_ngUserClaimList) {
                            if (row.getBp_no().toLowerCase().contains(charString.toLowerCase())
                                    || row.getJmr_no().toLowerCase().contains(charString.toLowerCase())


                            ) {
                                filteredList.add(row);
                            }
                        }
                    }




                    catch (Exception e){
                        CommonUtils.toast_msg(mctx,"Some fields are null");
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

    public interface UpdateList {
        void onUpdatedList();
    }
}

