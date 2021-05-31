package com.fieldmobility.igl.Adapter;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.TPI_NgPending_Activity;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TPI_NgPending_Adapter extends RecyclerView.Adapter<TPI_NgPending_Adapter.NgUserClaimListViewHolder> implements Filterable {
    private Context mctx;
    private List<NguserListModel> ngUserClaimList;
    private List<NguserListModel> new_ngUserClaimList;
    private String jmr_number;
    Activity activity;
    SharedPrefs sharedPrefs;
    String log = "nguser";

    public TPI_NgPending_Adapter(Context context, List<NguserListModel> ngUserClaimList, Activity activity) {
        this.ngUserClaimList = ngUserClaimList;
        this.new_ngUserClaimList = ngUserClaimList;
        this.mctx = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NgUserClaimListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mctx).inflate(R.layout.tpi_claim_row_item, parent, false);
        sharedPrefs = new SharedPrefs(mctx);
        return new NgUserClaimListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NgUserClaimListViewHolder holder, int position) {
        final NguserListModel ngUserClaimListModel = ngUserClaimList.get(position);
        //holder.bind(ngUserClaimList.get(position));


        //nguserListModel.getBpNo();
        holder.tv_bpName.setText(ngUserClaimListModel.getBp_no());
        //holder.tv_address.setText(ngUserClaimListModel.getHouse_no() + ngUserClaimListModel.getCity());
        holder.tv_address.setText(ngUserClaimListModel.getHouse_no() + " " + ngUserClaimListModel.getFloor() + " " + ngUserClaimListModel.getSociety() + " \n" + ngUserClaimListModel.getBlock_qtr() + " " + ngUserClaimListModel.getStreet() + " " + ngUserClaimListModel.getLandmark() + " " + ngUserClaimListModel.getCity()
        );
       // holder.tv_dateTime.setText(ngUserClaimListModel.getConversion_date());
        holder.user_name_text.setText(ngUserClaimListModel.getCustomer_name());
        holder.control_room.setText("Control Room - "+ngUserClaimListModel.getControl_room());

        if (!TextUtils.isEmpty(ngUserClaimListModel.getZone())) {
            holder.zone_text.setText(ngUserClaimListModel.getZone());
        }
        if (!TextUtils.isEmpty(ngUserClaimListModel.getMobile_no())) {
            holder.mobile_text.setText(ngUserClaimListModel.getMobile_no());
        }

        if (ngUserClaimListModel.getClaim()) {
            if (sharedPrefs.getEmail().equals(ngUserClaimListModel.getTpi_id())) {
                holder.btn_unclaim.setVisibility(View.VISIBLE);
                holder.btn_claim.setVisibility(View.GONE);
                if ((ngUserClaimListModel.getStart_job())) {
                    holder.status_text.setText("Job Started");
                    holder.btn_startJob.setVisibility(View.GONE);
                } else {
                    holder.status_text.setText("Job not Started");
                    holder.btn_startJob.setVisibility(View.VISIBLE);
                }
            } else {
                holder.status_text.setText("NG Claimed by other TPI");
                holder.btn_unclaim.setVisibility(View.GONE);
                holder.btn_claim.setVisibility(View.GONE);
                holder.btn_startJob.setVisibility(View.GONE);
            }


        } else {
            holder.status_text.setText("NG Pending");
            holder.btn_unclaim.setVisibility(View.GONE);
            holder.btn_claim.setVisibility(View.VISIBLE);
            holder.btn_startJob.setVisibility(View.GONE);
        }

        holder.mobile_text.setOnClickListener(new View.OnClickListener() {
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
        holder.btn_claim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(mctx,"click",Toast.LENGTH_SHORT).show();
                //Toast.makeText(mctx,"claim ",Toast.LENGTH_SHORT).show();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Log.d(log, " date =" + date);
                SharedPrefs sharedPrefs = new SharedPrefs(mctx);
                String tpi_id = sharedPrefs.getEmail();
                NguserListModel nguserListModel = new NguserListModel();
                nguserListModel.setClaim(true);
                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                nguserListModel.setTpi_id(tpi_id);
                nguserListModel.setClaim_date(date);

                ((TPI_NgPending_Activity) mctx).Claimed_API_POST(jmr_number, nguserListModel);

            }
        });
        holder.btn_unclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mctx,"unclaim ",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                nguserListModel.setClaim(false);
                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                nguserListModel.setStart_job(false);
                nguserListModel.setTpi_id("");
                nguserListModel.setConversion_date("null");
                nguserListModel.setClaim_date("null");
                //UnClaimed_API_POST(jmr_number,nguserListModel);
                ((TPI_NgPending_Activity) mctx).UnClaimed_API_POST(jmr_number, nguserListModel);

            }
        });

        holder.btn_startJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Log.d(log, " date =" + date);
                NguserListModel nguserListModel = new NguserListModel();
                //nguserListModel.setClaim(false);
                jmr_number = ngUserClaimListModel.getJmr_no();
                nguserListModel.setBp_no(ngUserClaimListModel.getBp_no());
                nguserListModel.setJmr_no(jmr_number);
                nguserListModel.setStart_job(true);
                nguserListModel.setConversion_date(date);
                //startJob(jmr_number, nguserListModel);
                ((TPI_NgPending_Activity) mctx).startJob(jmr_number, nguserListModel);

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


                    String conName = conObject.getString("firstName");
                    String conMob = conObject.getString("mobileNo");
                    String supName = supObject.getString("firstName");
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

    private void BP_N0_DilogBox(String conName,String conMob,String supName,String supMob  ) {
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
        cont_name.setText("Cont. :- " + conName);
        cont_mob.setText("MobNo :- " + conMob);
        sup_name.setText("Sup. :- " + supName);
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

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return ngUserClaimList.size();
    }


class NgUserClaimListViewHolder extends RecyclerView.ViewHolder {
    TextView tv_bpName, tv_dateTime, tv_perferedTime, tv_address, user_name_text, status_text, mobile_text, zone_text,control_room;
    Button btn_claim, btn_unclaim, btn_startJob, ngSupervisorinfo_text;
    LinearLayout ll_bpNumber;
    View itemView;

    NgUserClaimListViewHolder(View itemView) {
        super(itemView);
        tv_bpName = itemView.findViewById(R.id.bp_no_text);
        tv_dateTime = itemView.findViewById(R.id.date_text);
        tv_address = itemView.findViewById(R.id.address_text);
        btn_claim = itemView.findViewById(R.id.claimed_button);
        btn_unclaim = itemView.findViewById(R.id.unclaimed_button);
        btn_startJob = itemView.findViewById(R.id.job_start_button);
        user_name_text = itemView.findViewById(R.id.user_name_text);
        mobile_text = itemView.findViewById(R.id.mobile_text);
        status_text = itemView.findViewById(R.id.status_text);
        control_room = itemView.findViewById(R.id.control_room);
        zone_text = itemView.findViewById(R.id.zone_text);
        ngSupervisorinfo_text = itemView.findViewById(R.id.ngSupervisorinfo_text);
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
                        if (row.getBp_no().toLowerCase().contains(charString.toLowerCase()) || row.getCustomer_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getHouse_no().toLowerCase().contains(charString.toLowerCase()) || row.getFloor().toLowerCase().contains(charString.toLowerCase())
                                || row.getArea().toLowerCase().contains(charString.toLowerCase()) || row.getSociety().toLowerCase().contains(charString.toLowerCase())
                                || row.getBlock_qtr().toLowerCase().contains(charString.toLowerCase())) {
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

public interface ListParser {
    void listParser();
}


}
