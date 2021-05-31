package com.fieldmobility.igl.Adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fieldmobility.igl.Activity.NgSupUserDetailsActivity;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.Model.TpiDetailResponse;
import com.fieldmobility.igl.Model.TpiDetails;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.fieldmobility.igl.constants.Constant.JMR_NO;
import static com.fieldmobility.igl.constants.Constant.PREFS_JMR_NO;

public class NgUserListAdapter extends RecyclerView.Adapter<NgUserListAdapter.NguserListViewHolder> implements Filterable {

    private Context mctx;
    private List<NguserListModel> nguserList ;
    private List<NguserListModel> dataset;

    private ArrayList<TpiDetailResponse> mTpiDetailResponse;
    private TpiDetails tpiDetails;
    String log= "nguserlist";

    public NgUserListAdapter(Context context, List<NguserListModel> nguserList) {
        this.nguserList = nguserList;
        this.mctx= context;
        this.dataset = nguserList;
        mTpiDetailResponse = new ArrayList<TpiDetailResponse>();


    }

    @NonNull
    @Override
    public NguserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.ng_user_list_row_items,parent,false);
        return new NguserListViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NguserListViewHolder holder, final int position) {
          NguserListModel nguserListModel =nguserList.get(position);
        if (!TextUtils.isEmpty(nguserListModel.getBp_no())) {
            holder.tv_bpName.setText(nguserListModel.getBp_no());
        }
        if (!TextUtils.isEmpty(nguserListModel.getMobile_no())) {
            holder.tv_contactNo.setText(nguserListModel.getMobile_no());
        }
        holder.tv_control_room.setText("Control Room - "+nguserListModel.getControl_room());
        holder.tv_zone.setText(nguserListModel.getZone());
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
            holder.job_start_button.setVisibility(View.VISIBLE);
        }else {
            holder.job_start_button.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(nguserListModel.getConversion_date())){
            holder.tv_perferedTime.setText(nguserListModel.getConversion_date());
        }else {
            holder.tv_perferedTime.setText("- - -");
        }
        if ((nguserListModel.getClaim())){
            holder.btn_tpiDetails.setVisibility(View.VISIBLE);
        }else {
            holder.btn_tpiDetails.setVisibility(View.GONE);
        }
        holder.user_name_text.setText(nguserListModel.getCustomer_name());


        // priority - 2 is for high color is red
        //            1 is moderate color is blue
        //            0 is for low color is green
        if (!TextUtils.isEmpty(nguserListModel.getPriority())){
            if (nguserListModel.getPriority().equalsIgnoreCase("2")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#97FF7500"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("High Priority");

            }else if (nguserListModel.getPriority().equalsIgnoreCase("1")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#59FFEB3B"));
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Moderate Priority");
            }else if (nguserListModel.getPriority().equalsIgnoreCase("0")){
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFAFA"));//
                holder.tv_priority.setTextColor(Color.BLACK);
                holder.tv_priority.setText("Low Priority");
            }
        }


        holder.job_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mctx,"click" + position,Toast.LENGTH_SHORT).show();
                if (nguserListModel.getStart_job()) {
                    Intent intent = new Intent(mctx, NgSupUserDetailsActivity.class);
                    intent.putExtra("jmr_no", nguserListModel.getJmr_no());
                    intent.putExtra("mAssignDate", nguserListModel.getRfc_date());
                    intent.putExtra("startJob", nguserListModel.getStart_job());
                    intent.putExtra("ngmodel",   nguserList.get(position));
                    mctx.startActivity(intent);
                }else {
                    Utils.showToast(mctx,"Job Not Started");
                }


            }
        });


        holder.btn_tpiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadTpiDetails(nguserList.get(position).getTpi_id());
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
    private void loadTpiDetails(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_TPI_DETAILS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call< TpiDetailResponse> call = api.getTpiDetails(email);
        Log.d(log,"tpi details bp num = "+email);
        call.enqueue(new Callback< TpiDetailResponse>() {
            @Override
            public void onResponse(Call< TpiDetailResponse> call, Response< TpiDetailResponse> response) {

                TpiDetailResponse tpiDetailResponse1= response.body();
                tpiDetails = tpiDetailResponse1.getTpiDetails();
                Log.d(log,"tpi details response = "+mTpiDetailResponse.toString());


                    SharedPreferences sharedPreferences = mctx.getSharedPreferences("MySharedPrefContact", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("TPIName", tpiDetails.getFirstName());
                    myEdit.putString("TPI_CODE_GROUP", tpiDetails.getMobileNo());
                    myEdit.apply();

                BP_N0_DilogBox(tpiDetails);

            }
            @Override
            public void onFailure(Call< TpiDetailResponse> call, Throwable t) {
                Log.d(log,"tpi details error = "+ t.getLocalizedMessage());

            }
        });
    }
    private void BP_N0_DilogBox(TpiDetails tpiDetailResponse) {
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

        bp_no_text.setText("TPI Name: " + tpiDetailResponse.getFirstName());
        vendar_no.setText("TPI No: " + tpiDetailResponse.getMobileNo());
        final String tpiNo= tpiDetailResponse.getMobileNo();

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
    public long getItemId(int position) {
        Log.d(log,"getitem id  = "+position);
        return position;

    }

    @Override
    public int getItemViewType(int position) {
        Log.d(log,"getitemview id  = "+position);
        return position;
    }

    public class NguserListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bpName,tv_contactNo,tv_perferedTime,job_start_button,tv_address,status_text,user_name_text,tv_priority,tv_zone,tv_control_room;
        LinearLayout ll_bpNumber;
        Button btn_tpiDetails;
        RelativeLayout relativeLayout;
        public NguserListViewHolder(View itemView) {
            super(itemView);

            tv_bpName= itemView.findViewById(R.id.bp_no_text);
            tv_contactNo= itemView.findViewById(R.id.mobile_text);
            tv_perferedTime= itemView.findViewById(R.id.date_text);
            job_start_button= itemView.findViewById(R.id.job_start_button);
            tv_address= itemView.findViewById(R.id.address_text);
            status_text= itemView.findViewById(R.id.status_text);
            ll_bpNumber= itemView.findViewById(R.id.ll_bpNumber);
            btn_tpiDetails= itemView.findViewById(R.id.info_button);
            user_name_text= itemView.findViewById(R.id.user_name_text);
            tv_priority = itemView.findViewById(R.id.tv_priority);
            tv_zone = itemView.findViewById(R.id.zone);
            tv_control_room = itemView.findViewById(R.id.control_room);
            relativeLayout = itemView.findViewById(R.id.liner_layout);
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
                    Log.d(log,"filter = "+nguserList.size());
                } else {
                    List<NguserListModel> filteredList = new ArrayList<>();
                    for (NguserListModel row : dataset) {
                        if (row.getBp_no().toLowerCase().contains(charString.toLowerCase())
                                || row.getCustomer_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getJmr_no().toLowerCase().contains(charString.toLowerCase())
                                || row.getSociety().toLowerCase().contains(charString.toLowerCase())
                                || row.getArea().toLowerCase().contains(charString.toLowerCase())
                                || row.getContractor_id().toLowerCase().contains(charString.toLowerCase())
                                || row.getHouse_no().toLowerCase().contains(charString.toLowerCase())
                                || row.getCity().toLowerCase().contains(charString.toLowerCase())
                                || row.getFloor().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            Log.d(log,"filter loop = "+dataset.size());
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
                Log.d(log,"publish = "+nguserList.size());
                notifyDataSetChanged();
            }
        };
    }



}
