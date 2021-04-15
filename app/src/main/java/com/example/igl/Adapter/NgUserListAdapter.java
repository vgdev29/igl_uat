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
import com.example.rest.Api;

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

public class NgUserListAdapter extends RecyclerView.Adapter<NgUserListAdapter.NguserListViewHolder> implements Filterable  {

    private Context mctx;
    private List<NguserListModel> nguserList ;
    private List<NguserListModel> dataset;

    public NgUserListAdapter(Context context, List<NguserListModel> nguserList) {
        this.nguserList = nguserList;
        this.mctx= context;
        this.dataset = nguserList;

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
        if (!TextUtils.isEmpty(nguserListModel.getBpNo())) {
            holder.tv_bpName.setText(nguserListModel.getBpNo());
        }
        if (!TextUtils.isEmpty(nguserListModel.getAlt_Number())) {
            holder.tv_contactNo.setText(nguserListModel.getAlt_Number());
        }
        if (!TextUtils.isEmpty(nguserListModel.getHouseNo()) && !TextUtils.isEmpty(nguserListModel.getFloor())
                && !TextUtils.isEmpty(nguserListModel.getStreet_gali_Road())
                && !TextUtils.isEmpty(nguserListModel.getBlock_Qtr())
                && !TextUtils.isEmpty(nguserListModel.getArea())
                && !TextUtils.isEmpty(nguserListModel.getCity())){
            holder.tv_address.setText(nguserListModel.getHouseNo() + " " + nguserListModel.getCity());

        }
        holder.tv_perferedTime.setText("- - -");
        //nguserListModel.getConversionDate();
        if (!TextUtils.isEmpty(nguserListModel.getConversionDate())){
            holder.tv_perferedTime.setText(nguserListModel.getConversionDate());
        }else {
            holder.tv_perferedTime.setText("- - -");
        }
        if ((nguserListModel.getClaim())){
            holder.status_text.setText("Claimed");
        }else {
            holder.tv_perferedTime.setText("UnClaimed");
        }
        holder.user_name_text.setText(nguserListModel.getCustomer_Name());


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
                Intent intent = new Intent(mctx, NgUserDetailsActivity.class);
                intent.putExtra("jmr_no", nguserListModel.getJmrNo());
                intent.putExtra("mAssignDate", nguserListModel.getRfC_Date());
                intent.putExtra("startJob",nguserListModel.getStart_job());
                mctx.startActivity(intent);

            }
        });


        holder.btn_tpiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vender_Name = bp_no_list_array.get(position).getRfcvendorname();
                //Vender_No = bp_no_list_array.get(position).getRFCMobileNo();
                //Log.e("Vender_Name",Vender_Name);
                //Log.e("Vender_No",Vender_No);
                //String tpiName = nguserListModel.getCustomerName();
                //BP_N0_DilogBox("ishu","9897922586");

                loadTpiDetails();
            }
        });


        holder.tv_contactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num= nguserListModel.getAlt_Number();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + num));
                mctx.startActivity(intent);

            }
        });
        SharedPreferences.Editor editor = mctx.getSharedPreferences(PREFS_JMR_NO, MODE_PRIVATE).edit();
        editor.putString(JMR_NO, nguserListModel.getJmrNo());
        editor.apply();


    }
    private void loadTpiDetails(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_TPI_DETAILS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<TpiDetailResponse>> call = api.getTpiDetails("7000049237");
        call.enqueue(new Callback<ArrayList<TpiDetailResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<TpiDetailResponse>> call, Response<ArrayList<TpiDetailResponse>> response) {
                Log.e("MyError" , "error.............");

            }

            @Override
            public void onFailure(Call<ArrayList<TpiDetailResponse>> call, Throwable t) {
                Log.e("MyError" , "error.............");

            }

        });





    }
    private void BP_N0_DilogBox(String vender_Name, final String vender_No) {
        final Dialog dialog = new Dialog(mctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.bp_no_dilogbox);
        //dialog.setTitle("Signature");
        TextView bp_no_text=dialog.findViewById(R.id.bp_no_text);
        TextView  vendar_no=dialog.findViewById(R.id.vendar_no);
        bp_no_text.setText("TPI Name: "+vender_Name);
        vendar_no.setText("TPI No: "+vender_No);
        vendar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ vender_No));
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

    public class NguserListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName,tv_contactNo,tv_perferedTime,tv_priority,tv_address,status_text,user_name_text;
        LinearLayout ll_bpNumber;
        Button btn_tpiDetails;
        public NguserListViewHolder(View itemView) {
            super(itemView);

            tv_bpName= itemView.findViewById(R.id.tv_bpName);
            tv_contactNo= itemView.findViewById(R.id.tv_contactNo);
            tv_perferedTime= itemView.findViewById(R.id.tv_perferedTime);
            tv_priority= itemView.findViewById(R.id.tv_priority);
            tv_address= itemView.findViewById(R.id.tv_address);
            status_text= itemView.findViewById(R.id.status_text);
            ll_bpNumber= itemView.findViewById(R.id.ll_bpNumber);
            btn_tpiDetails= itemView.findViewById(R.id.btn_tpiDetails);
            user_name_text= itemView.findViewById(R.id.user_name_text);
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
                        if (row.getBpNo().toLowerCase().contains(charString.toLowerCase()) || row.getCustomer_Name().toLowerCase().contains(charString.toLowerCase())) {
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
