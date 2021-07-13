package com.fieldmobility.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fieldmobility.igl.Adapter.NgUserListAdapter;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.Model.NguserListModel;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.interfaces.ListDataPasser;
import com.fieldmobility.igl.utils.RetrofitCancelOberver;
import com.fieldmobility.igl.utils.Utils;
import com.fieldmobility.igl.rest.DBManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.fieldmobility.igl.utils.Utils.hideProgressDialog;
import static com.fieldmobility.igl.utils.Utils.showProgressDialog;

public class NgSupListActivity extends AppCompatActivity implements ListDataPasser {

    private RecyclerView recyclerView_ngAssignment;
    private NgUserListAdapter adapter;
    private ImageView back;
    private ImageView ll_sort;
    private RelativeLayout rel_noNgUserListingData;
    private ArrayList<NguserListModel> ngUserList=new ArrayList<>();
    private Button btnTryAgain;
    private TextView tv_ngUserListdata, count_list;
    private EditText editTextSearch;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RetrofitCancelOberver retrofitCancelOberver;
    private SharedPrefs sharedPrefs;
    String log = "nguserlist";
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_user_list);
        retrofitCancelOberver = new RetrofitCancelOberver();
        getLifecycle().addObserver(retrofitCancelOberver);
        sharedPrefs = new SharedPrefs(getApplicationContext());
        btnTryAgain = findViewById(R.id.btnTryAgain);
        back = findViewById(R.id.back);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        recyclerView_ngAssignment = findViewById(R.id.recyclerView_ngAssignment);
        recyclerView_ngAssignment.setHasFixedSize(true);
        recyclerView_ngAssignment.setLayoutManager(new LinearLayoutManager(this));
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        editTextSearch = findViewById(R.id.editTextSearch);
        count_list = findViewById(R.id.list_count);
        ll_sort = findViewById(R.id.ll_sort);
        ll_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(), " Sort", Toast.LENGTH_SHORT).show();

                    showFiltersDialog();

            }
        });
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNgUserList();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  bookadapter.getFilter().filter(s.toString());
                if (adapter != null)
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    //bp_no_array.clear();
                    //Feasivility_List();
                    if (Utils.isNetworkConnected(NgSupListActivity.this)) {
                        loadNgUserList();

                    } else {
                        Utils.showToast(NgSupListActivity.this, "Please check your connectivity");
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                        tv_ngUserListdata.setText("Please check your connectivity!!");
                        recyclerView_ngAssignment.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        loadNgUserList();



    }
    private void loadNgUserList(){
        showProgressDialog(this);
        String supervisor_id = sharedPrefs.getEmail();
        Log.d(log,"sup id = "+supervisor_id);
        Call call = DBManager.loadNgUserList(NgSupListActivity.this, "AS" ,supervisor_id);
        retrofitCancelOberver.addlist(call);
    }
    private void setListData(final List<NguserListModel> ngUserList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                if (ngUserList.size() > 0) {
                    count_list.setText("Count\n"+String.valueOf(ngUserList.size()));
                    recyclerView_ngAssignment.setVisibility(View.VISIBLE);
                    rel_noNgUserListingData.setVisibility(View.GONE);
                    adapter = new NgUserListAdapter(NgSupListActivity.this, ngUserList);
                    recyclerView_ngAssignment.setAdapter(adapter);

                } else {
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                }

                /*if (adapter != null && updateListFlag) {
                    adapter.updateList(activity, prodLineItems, retailer, actionId, dialog2);
                }*/
            }
        });
    }

    private Dialog mFilterDialog;




    public void showFiltersDialog() {
        //Toast.makeText(getApplicationContext(),"Filter icon",Toast.LENGTH_SHORT).show();
        mFilterDialog = new Dialog(NgSupListActivity.this);
        mFilterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mFilterDialog.setCanceledOnTouchOutside(false);
        mFilterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFilterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFilterDialog.setContentView(R.layout.dialog_filter_ng_user_list);
      //  mFilterDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rl_dialog_title = (RelativeLayout) mFilterDialog.findViewById(R.id.rl_dialog_title);
        Button btn_applyFilters = (Button) mFilterDialog.findViewById(R.id.btn_applyFilters);
        Button btn_clearAllFilters = (Button) mFilterDialog.findViewById(R.id.btn_clearAllFilters);
        ImageButton ibCancel = (ImageButton) mFilterDialog.findViewById(R.id.ib_create_cancel);
        radioGroup = mFilterDialog.findViewById(R.id.radioGroup);

        rl_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterDialog.dismiss();

            }
        });
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterDialog.dismiss();
            }
        });

        btn_clearAllFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDataset(ngUserList);
                adapter.notifyDataSetChanged();
                mFilterDialog.dismiss();
                count_list.setText("Count\n"+ ngUserList.size());
            }
        });

        btn_applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final ProgressDialog dialog2 = ProgressDialog.show(NgUserListActivity.this, null, "Loading... ", true, false);
                int checkedId = radioGroup.getCheckedRadioButtonId();
                Log.d(log,"checked id = "+checkedId);

                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.radioButton_ngWithcalim:
                        refreshRecyclerClaim();
                        break;
                    case R.id.radioButton_ngWithUncalim:
                        refreshRecyclerUNClaim();
                        break;
                    case R.id.radioButton_ngJstart:
                        refreshRecyclerJObStarted();
                        break;
                    case R.id.radioButton_ngWithPriority:
                        showPeriority();
                        break;
                }
                mFilterDialog.dismiss();

            }
        });

        mFilterDialog.show();
    }

    public void refreshRecyclerClaim()
    {
        List<NguserListModel> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (NguserListModel nguserListModel : ngUserList)
        {
            if (nguserListModel.getStatus().equalsIgnoreCase("AS") && nguserListModel.getClaim())
            {
                Log.d(log,"claim if = "+nguserListModel.getClaim()+nguserListModel.getStatus());
                filterList.add(nguserListModel);
            }
        }
        count_list.setText("Count\n"+ filterList.size());
        adapter.setDataset(filterList);
        adapter.notifyDataSetChanged();

    }

    public void refreshRecyclerJObStarted()
    {
        List<NguserListModel> filterList = new ArrayList<>();
        Log.d(log,"refresh claim");
        for (NguserListModel nguserListModel : ngUserList)
        {
            if (nguserListModel.getStatus().equalsIgnoreCase("AS") && nguserListModel.getClaim() && nguserListModel.getStart_job())
            {
                Log.d(log,"claim if = "+nguserListModel.getClaim()+nguserListModel.getStatus());
                filterList.add(nguserListModel);
            }
        }
        count_list.setText("Count\n"+ filterList.size());
        adapter.setDataset(filterList);
        adapter.notifyDataSetChanged();

    }
    public void refreshRecyclerUNClaim()
    {
        List<NguserListModel> filterList = new ArrayList<>();
        Log.d(log,"refresh unclaim");
        for (NguserListModel nguserListModel : ngUserList)
        {
            if (nguserListModel.getStatus().equalsIgnoreCase("AS") && !nguserListModel.getClaim())
            {
                Log.d(log,"claim if = "+nguserListModel.getClaim()+nguserListModel.getStatus());
                filterList.add(nguserListModel);
            }
        }
        count_list.setText("Count\n"+ filterList.size());
        adapter.setDataset(filterList);
        adapter.notifyDataSetChanged();

    }
    private void showPeriority(){
        // priority - 2 is for high color is red
        //            1 is moderate color is blue
        //            0 is for low color is green
        ArrayList<NguserListModel> highPriorityList= new ArrayList<>();
        ArrayList<NguserListModel> moderatePriorityList= new ArrayList<>();
        ArrayList<NguserListModel> lowPriorityList= new ArrayList<>();
        ArrayList<NguserListModel> filterList= new ArrayList<>();
        for (NguserListModel nguserListModel : ngUserList){
            if (Integer.parseInt(nguserListModel.getPriority())==2){
                highPriorityList.add(nguserListModel);
            }if (Integer.parseInt(nguserListModel.getPriority())==1){
                moderatePriorityList.add(nguserListModel);
            }
            if (Integer.parseInt(nguserListModel.getPriority())==0){
                lowPriorityList.add(nguserListModel);
            }
        }
        filterList.addAll(highPriorityList);
        filterList.addAll(moderatePriorityList);
        filterList.addAll(lowPriorityList);
        setListData(filterList);
    }

    @Override
    public void notifyDataPasser(ArrayList list) {
        ngUserList=list;
        setListData(ngUserList);

    }

    @Override
    public void notifyFailurePasser(ArrayList list) {
        ngUserList=list;
        setListData(ngUserList);

    }

    @Override
    public void notifyLowNetworkPasser(ArrayList list) {
        ngUserList=list;
        setListData(ngUserList);

    }
}
