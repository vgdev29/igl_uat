package com.example.igl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.constants.Constant;
import com.example.igl.Activity.New_Regestration_Form;
import com.example.igl.Activity.NgUserDetailsActivity;
import com.example.igl.Fragment.NgClaim_Tpi_Fragment;
import com.example.igl.Fragment.Ng_Pending_Fragment;
import com.example.igl.Fragment.Ready_Inspection_Tpi_Fragment;
import com.example.igl.Helper.AppController;
import com.example.igl.Helper.Constants;
import com.example.igl.MataData.Bp_No_Item;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;
import com.example.igl.utils.Utils;
import com.example.rest.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.constants.Constant.JMR_NO;


public class NgUserClaimListAdapter extends RecyclerView.Adapter<NgUserClaimListAdapter.NgUserClaimListViewHolder> implements Filterable {
    private Context mctx;
    private List<NguserListModel> ngUserClaimList;
    private List<NguserListModel> new_ngUserClaimList;
    private String jmr_number;

    public NgUserClaimListAdapter(Context context, List<NguserListModel> ngUserClaimList) {
        this.ngUserClaimList = ngUserClaimList;
        this.new_ngUserClaimList = ngUserClaimList;
        this.mctx = context;
    }

    @NonNull
    @Override
    public NgUserClaimListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mctx).inflate(R.layout.tpi_claim_row_item, parent, false);



        return new NgUserClaimListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NgUserClaimListViewHolder holder, int position) {
        final NguserListModel ngUserClaimListModel = ngUserClaimList.get(position);
        //holder.bind(ngUserClaimList.get(position));


        //nguserListModel.getBpNo();
        holder.tv_bpName.setText(ngUserClaimListModel.getBpNo());
        holder.tv_address.setText(ngUserClaimListModel.getHouseNo() + ngUserClaimListModel.getCity());
        holder.tv_dateTime.setText(ngUserClaimListModel.getConversionDate());
        holder.user_name_text.setText(ngUserClaimListModel.getCustomer_Name());
        /*if((ngUserClaimListModel.getStart_job())){
            holder.status_text.setText("Job Started");
        }else {
            holder.status_text.setText("Job not Started");
        }*/
        if(!TextUtils.isEmpty((ngUserClaimListModel.getStatus()))){
            holder.status_text.setText(ngUserClaimListModel.getStatus());
        }else {
            holder.status_text.setText("--");
        }



        /*if (ngUserClaimListModel.getClaim()){
            holder.btn_claim.setClickable(false);
            holder.btn_claim.setEnabled(false);

            holder.btn_claim.setVisibility(View.GONE);
            holder.btn_unclaim.setVisibility(View.VISIBLE);

        }else {
            holder.btn_unclaim.setClickable(false);
            holder.btn_unclaim.setEnabled(false);
            holder.btn_unclaim.setVisibility(View.GONE);

        }*/

        if (ngUserClaimListModel.getClaim()){
            holder.btn_unclaim.setVisibility(View.VISIBLE);
            holder.btn_claim.setVisibility(View.GONE);
        }else {
            holder.btn_unclaim.setVisibility(View.GONE);
            holder.btn_claim.setVisibility(View.VISIBLE);
        }


        holder.btn_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mctx,"click",Toast.LENGTH_SHORT).show();
                //Toast.makeText(mctx,"claim ",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                nguserListModel.setClaim(true);
                jmr_number = ngUserClaimListModel.getJmrNo();
                nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                nguserListModel.setJmrNo(jmr_number);
                ((NgClaim_Tpi_Fragment)mctx).Claimed_API_POST(jmr_number,nguserListModel);

            }
        });
        holder.btn_unclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mctx,"unclaim ",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                nguserListModel.setClaim(false);
                jmr_number = ngUserClaimListModel.getJmrNo();
                nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                nguserListModel.setJmrNo(jmr_number);
                nguserListModel.setStart_job(false);
                //UnClaimed_API_POST(jmr_number,nguserListModel);
                ((NgClaim_Tpi_Fragment)mctx).UnClaimed_API_POST(jmr_number,nguserListModel);

            }
        });

        holder.btn_startJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ngUserClaimListModel.getClaim()) {
                    if((ngUserClaimListModel.getStart_job())){
                        Utils.showToast(mctx,"Job Already started , Unclaim first");
                    }else {
                        NguserListModel nguserListModel = new NguserListModel();
                        //nguserListModel.setClaim(false);
                        jmr_number = ngUserClaimListModel.getJmrNo();
                        nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                        nguserListModel.setJmrNo(jmr_number);
                        nguserListModel.setStart_job(true);
                        //startJob(jmr_number, nguserListModel);
                        ((NgClaim_Tpi_Fragment)mctx).startJob(jmr_number, nguserListModel);
                    }
                }else {
                    Utils.showToast(mctx,"Claimed first");
                }
            }
        });

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



    class NgUserClaimListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName, tv_dateTime, tv_perferedTime, tv_address,user_name_text,status_text;
        Button btn_claim,btn_unclaim,btn_startJob;
        LinearLayout ll_bpNumber;
        View itemView;
        NgUserClaimListViewHolder(View itemView) {
            super(itemView);
            tv_bpName = itemView.findViewById(R.id.tv_bpNumber);
            tv_dateTime = itemView.findViewById(R.id.tv_perferedTime);
            tv_address = itemView.findViewById(R.id.tv_address);
            btn_claim = itemView.findViewById(R.id.btn_tpiClaim);
            btn_unclaim = itemView.findViewById(R.id.btn_tpiUnClaim);
            btn_startJob = itemView.findViewById(R.id.btn_startJob);
            user_name_text = itemView.findViewById(R.id.user_name_text);
            status_text = itemView.findViewById(R.id.status_text);
            this.itemView = itemView;
        }

    }
    /*private void Claimed_API_POST(String jmr_number, NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number ,nguserListModel);
        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");

                Toast.makeText(mctx,"Claim Successfully",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(mctx,"Fail to success",Toast.LENGTH_SHORT).show();

            }
        });
    }*/
    /*private void UnClaimed_API_POST(String jmr_number,NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number,nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");

                Toast.makeText(mctx,"UnClaim Successfully ",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(mctx,"Fail to success",Toast.LENGTH_SHORT).show();

            }
        });
    }*/
    /*private void startJob(String jmr_number,NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number,nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

        call.enqueue(new Callback<List<NguserListModel>>() {


            @Override
            public void onResponse(Call<List<NguserListModel>> call, retrofit2.Response<List<NguserListModel>> response) {
                Log.e("Mysucess>>>>>>>>>>" , "weldone............");

                Toast.makeText(mctx,"Start Job Successfully ",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<List<NguserListModel>> call, Throwable t) {
                Log.e("My error" , "error comes");
                Toast.makeText(mctx,"Fail to success",Toast.LENGTH_SHORT).show();

            }
        });
    }*/

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
                        if (row.getBpNo().toLowerCase().contains(charString.toLowerCase()) || row.getCustomer_Name().toLowerCase().contains(charString.toLowerCase())) {
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

    public interface ListParser{
        void listParser();
    }
}
