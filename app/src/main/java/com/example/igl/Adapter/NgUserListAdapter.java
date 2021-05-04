package com.example.igl.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.igl.Activity.NgUserDetailsActivity;
import com.example.igl.Model.BpDetail;
import com.example.igl.Model.NguserListModel;
import com.example.igl.Model.TpiDetailResponse;
import com.example.igl.R;
import com.example.igl.interfaces.ListDataPasser;
import com.example.igl.utils.Utils;
import com.example.rest.Api;
import com.example.rest.DBManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.constants.Constant.JMR_NO;
import static com.example.constants.Constant.PREFS_JMR_NO;

public class NgUserListAdapter extends RecyclerView.Adapter<NgUserListAdapter.NguserListViewHolder> implements Filterable, ListDataPasser {

    private Context mctx;
    private List<NguserListModel> nguserList ;
    private List<NguserListModel> dataset;

    private ArrayList<TpiDetailResponse> mTpiDetailResponse;
    private TpiDetailResponse tpiDetailResponse;

    public NgUserListAdapter(Context context, List<NguserListModel> nguserList) {
        this.nguserList = nguserList;
        this.mctx= context;
        this.dataset = nguserList;
        mTpiDetailResponse = new ArrayList<TpiDetailResponse>();
        tpiDetailResponse= new TpiDetailResponse();

    }

    @NonNull
    @Override
    public NguserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.ng_user_list_row_items,parent,false);

        return new NguserListViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NguserListViewHolder holder, final int position) {
        final NguserListModel nguserListModel =nguserList.get(position);
        if (!TextUtils.isEmpty(nguserListModel.getBp_no())) {
            holder.tv_bpName.setText(nguserListModel.getBp_no());
        }
        if (!TextUtils.isEmpty(nguserListModel.getMobile_no())) {
            holder.tv_contactNo.setText(nguserListModel.getMobile_no());
        }
        /*if (!TextUtils.isEmpty(nguserListModel.getHouse_no()) && !TextUtils.isEmpty(nguserListModel.getFloor())
                && !TextUtils.isEmpty(nguserListModel.getStreet())
                && !TextUtils.isEmpty(nguserListModel.getBlock_qtr())
                && !TextUtils.isEmpty(nguserListModel.getArea())
                && !TextUtils.isEmpty(nguserListModel.getCity())){
            holder.tv_address.setText(nguserListModel.getHouse_no() + " " + nguserListModel.getCity());

        }*/
        holder.tv_address.setText(nguserListModel.getHouse_no()+" "+nguserListModel.getFloor()+" "
                +nguserListModel.getSociety()+" \n"
                +nguserListModel.getBlock_qtr()+" "+nguserListModel.getStreet()+" "+nguserListModel.getLandmark()+" "
                +nguserListModel.getCity()
               );
        if (nguserListModel.getStart_job()){
            holder.job_status.setText("Job Started");
        }else {
            holder.job_status.setText("Job Not Started");
        }
        holder.tv_perferedTime.setText("- - -");
        //nguserListModel.getConversionDate();
        if (!TextUtils.isEmpty(nguserListModel.getConversion_date())){
            holder.tv_perferedTime.setText(nguserListModel.getConversion_date());
        }else {
            holder.tv_perferedTime.setText("- - -");
        }
        if ((nguserListModel.getClaim())){
            holder.status_text.setText("Claimed");
        }else {
            holder.status_text.setText("UnClaimed");
        }
        holder.user_name_text.setText(nguserListModel.getCustomer_name());


        // priority - 2 is for high color is red
        //            1 is moderate color is blue
        //            0 is for low color is green
        if (!TextUtils.isEmpty(nguserListModel.getPriority())){
            if (nguserListModel.getPriority().equalsIgnoreCase("2")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#EC4134"));
                holder.tv_priority.setTextColor(Color.WHITE);
                holder.tv_priority.setText("High Priority");

            }else if (nguserListModel.getPriority().equalsIgnoreCase("1")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#2274BE"));
                holder.tv_priority.setTextColor(Color.WHITE);
                holder.tv_priority.setText("Moderate Priority");
            }else if (nguserListModel.getPriority().equalsIgnoreCase("0")){
                holder.tv_priority.setBackgroundColor(Color.parseColor("#1BA25A"));
                holder.tv_priority.setTextColor(Color.WHITE);
                holder.tv_priority.setText("Low Priority");
            }
        }


        holder.ll_bpNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mctx,"click" + position,Toast.LENGTH_SHORT).show();
                if (nguserListModel.getStart_job()) {
                    Intent intent = new Intent(mctx, NgUserDetailsActivity.class);
                    intent.putExtra("jmr_no", nguserListModel.getJmr_no());
                    intent.putExtra("mAssignDate", nguserListModel.getRfc_date());
                    intent.putExtra("startJob", nguserListModel.getStart_job());
                    mctx.startActivity(intent);
                }else {
                    Utils.showToast(mctx,"Job Not Started");
                }


            }
        });


        holder.btn_tpiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTpiDetails(nguserListModel.getBp_no());
            }
        });


        holder.tv_contactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num= nguserListModel.getMobile_no();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + nguserListModel.getMobile_no()));
                mctx.startActivity(intent);

            }
        });
        SharedPreferences.Editor editor = mctx.getSharedPreferences(PREFS_JMR_NO, MODE_PRIVATE).edit();
        editor.putString(JMR_NO, nguserListModel.getJmr_no());
        editor.apply();


    }
    private void loadTpiDetails(String bp_no){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_TPI_DETAILS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<TpiDetailResponse>> call = api.getTpiDetails(bp_no);
        call.enqueue(new Callback<ArrayList<TpiDetailResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<TpiDetailResponse>> call, Response<ArrayList<TpiDetailResponse>> response) {
                Log.e("MyError" , "error.............");
                mTpiDetailResponse= response.body();
                for (TpiDetailResponse tpiDetailResponse1: mTpiDetailResponse){
                    tpiDetailResponse.setTpiName(tpiDetailResponse1.getTpiName());
                    tpiDetailResponse.setCodeGroup(tpiDetailResponse1.getCodeGroup());

                    SharedPreferences sharedPreferences = mctx.getSharedPreferences("MySharedPrefContact", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("TPIName", tpiDetailResponse1.getTpiName());
                    myEdit.putString("TPI_CODE_GROUP", tpiDetailResponse1.getCodeGroup());
                    myEdit.apply();
                }
                BP_N0_DilogBox(tpiDetailResponse);

            }
            @Override
            public void onFailure(Call<ArrayList<TpiDetailResponse>> call, Throwable t) {
                Log.e("MyError" , "error.............");

            }
        });
    }
    private void BP_N0_DilogBox(TpiDetailResponse tpiDetailResponse) {
        final Dialog dialog = new Dialog(mctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
        TextView  vendar_no=dialog.findViewById(R.id.vendar_no);
        /*for (TpiDetailResponse tpiDetailResponse1: tpiDetailResponse) {
            bp_no_text.setText("TPI Name: " + tpiDetailResponse1.getTpiName());
            vendar_no.setText("TPI No: " + tpiDetailResponse1.get);
        }*/

        bp_no_text.setText("TPI Name: " + tpiDetailResponse.getTpiName());
        vendar_no.setText("TPI No: " + tpiDetailResponse.getBpNumber());
        final String tpiNo= tpiDetailResponse.getBpNumber();

        vendar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ tpiNo));
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
    public int getItemCount() {
        return nguserList.size();
    }

    @Override
    public void notifyDataPasser(ArrayList list) {

        //for (TpiDetailResponse tpiDetailResponse : list){
        //}

    }

    @Override
    public void notifyFailurePasser(ArrayList list) {

    }

    @Override
    public void notifyLowNetworkPasser(ArrayList list) {

    }

    public class NguserListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName,tv_contactNo,tv_perferedTime,tv_priority,tv_address,status_text,user_name_text,job_status;
        LinearLayout ll_bpNumber;
        Button btn_tpiDetails;
        public NguserListViewHolder(View itemView) {
            super(itemView);

            tv_bpName= itemView.findViewById(R.id.bp_no_text);
            tv_contactNo= itemView.findViewById(R.id.mobile_text);
            tv_perferedTime= itemView.findViewById(R.id.date_text);
            tv_priority= itemView.findViewById(R.id.priority_button);
            tv_address= itemView.findViewById(R.id.address_text);
            status_text= itemView.findViewById(R.id.status_text);
            ll_bpNumber= itemView.findViewById(R.id.ll_bpNumber);
            btn_tpiDetails= itemView.findViewById(R.id.info_button);
            user_name_text= itemView.findViewById(R.id.user_name_text);
            job_status= itemView.findViewById(R.id.job_status);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    nguserList = dataset;
                } else {
                    List<NguserListModel> filteredList = new ArrayList<>();
                    for (NguserListModel row : dataset) {
                        if (row.getBp_no().toLowerCase().contains(charString.toLowerCase()) || row.getCustomer_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    nguserList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = nguserList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                nguserList = (ArrayList<NguserListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



}
