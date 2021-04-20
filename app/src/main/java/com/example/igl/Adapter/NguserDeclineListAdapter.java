package com.example.igl.Adapter;

import android.content.Context;
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

import com.example.igl.Model.NguserListModel;
import com.example.igl.R;
import com.example.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NguserDeclineListAdapter extends RecyclerView.Adapter<NguserDeclineListAdapter.NgUserDeclineListViewHolder> implements Filterable {
    private Context mctx;
    private List<NguserListModel> ngUserClaimList;
    private List<NguserListModel> new_ngUserClaimList;
    private String jmr_number;

    public NguserDeclineListAdapter(Context context, List<NguserListModel> ngUserClaimList) {
        this.ngUserClaimList = ngUserClaimList;
        this.new_ngUserClaimList = ngUserClaimList;
        this.mctx = context;
    }


    @NonNull
    @Override
    public NgUserDeclineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mctx).inflate(R.layout.tpi_ng_decline_list, parent, false);



        return new NgUserDeclineListViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull NgUserDeclineListViewHolder holder, int position) {
        final NguserListModel ngUserClaimListModel = ngUserClaimList.get(position);
        //holder.bind(ngUserClaimList.get(position));


        //nguserListModel.getBpNo();
        holder.tv_bpName.setText(ngUserClaimListModel.getBp_no());
        holder.tv_address.setText(ngUserClaimListModel.getHouse_no() + ngUserClaimListModel.getCity());
        holder.tv_dateTime.setText(ngUserClaimListModel.getConversion_date());
        holder.user_name_text.setText(ngUserClaimListModel.getCustomer_name());
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

        /*holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mctx,"click",Toast.LENGTH_SHORT).show();
                NguserListModel nguserListModel = new NguserListModel();
                //nguserListModel.setClaim(true);
                nguserListModel.setStatus("DN");
                jmr_number = ngUserClaimListModel.getJmrNo();
                nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                nguserListModel.setJmrNo(jmr_number);
                Claimed_API_POST(jmr_number,nguserListModel);

            }
        });*/
       /* holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguserListModel nguserListModel = new NguserListModel();
                //nguserListModel.setClaim(false);
                nguserListModel.setStatus("DC");
                jmr_number = ngUserClaimListModel.getJmrNo();
                nguserListModel.setBpNo(ngUserClaimListModel.getBpNo());
                nguserListModel.setJmrNo(jmr_number);
                UnClaimed_API_POST(jmr_number,nguserListModel);

            }
        });*/
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






    class NgUserDeclineListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName, tv_dateTime, tv_perferedTime, tv_address,user_name_text,status_text;
        //Button btn_approve,btn_decline;
        LinearLayout ll_bpNumber;
        View itemView;
        NgUserDeclineListViewHolder(View itemView) {
            super(itemView);
            tv_bpName = itemView.findViewById(R.id.tv_bpNumber);
            tv_dateTime = itemView.findViewById(R.id.tv_perferedTime);
            tv_address = itemView.findViewById(R.id.tv_address);
           // btn_approve = itemView.findViewById(R.id.btn_btn_tpiApprove);
           // btn_decline = itemView.findViewById(R.id.btn_tpidecline);

            user_name_text = itemView.findViewById(R.id.user_name_text);
            status_text = itemView.findViewById(R.id.status_text);
            this.itemView = itemView;
        }

    }
    private void Claimed_API_POST(String jmr_number, NguserListModel nguserListModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NguserListModel>> call = api.getUpdateNgUserField1(jmr_number ,nguserListModel);
        //Call<List<NguserListModel>> call =  api.getUpdateNgUserField(preferences.getString("jmr_no" , ""),nguserListModel);

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
    }
    private void UnClaimed_API_POST(String jmr_number,NguserListModel nguserListModel) {
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
    }
    private void startJob(String jmr_number,NguserListModel nguserListModel) {
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

