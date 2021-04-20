package com.example.igl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igl.Adapter.NgUserListAdapter;
import com.example.igl.Model.NguserListModel;
import com.example.igl.R;
import com.example.igl.interfaces.ListDataPasser;
import com.example.igl.utils.RetrofitCancelOberver;
import com.example.igl.utils.Utils;
import com.example.igl.widgets.CircularTextView;
import com.example.rest.DBManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class NgUserListActivity extends AppCompatActivity implements ListDataPasser {

    private RecyclerView recyclerView_ngAssignment;
    private NgUserListAdapter adapter;
    private ImageView back;
    private RelativeLayout ll_sort;
    private RelativeLayout rel_noNgUserListingData;

    private ArrayList<NguserListModel> ngUserList=new ArrayList<>();
    private Button btnTryAgain;
    private TextView tv_ngUserListdata;
    private EditText editTextSearch;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RetrofitCancelOberver retrofitCancelOberver;
    private CircularTextView tv_FiltersIndicator;
    private  void runAnimation(CircularTextView tv_FiltersIndicator) {
        PropertyValuesHolder pvhx = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.5f, .5f);
        PropertyValuesHolder pvhy = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.5f, .5f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(tv_FiltersIndicator, pvhx, pvhy);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setDuration(2000);
        anim.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ng_user_list);
        retrofitCancelOberver = new RetrofitCancelOberver();
        getLifecycle().addObserver(retrofitCancelOberver);

        tv_FiltersIndicator = findViewById(R.id.tv_FiltersIndicator);
        tv_FiltersIndicator.setSolidColor("#FF0000");
        tv_FiltersIndicator.setVisibility(View.GONE);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        back = findViewById(R.id.back);
        tv_ngUserListdata = findViewById(R.id.tv_ngUserListdata);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        recyclerView_ngAssignment = findViewById(R.id.recyclerView_ngAssignment);
        recyclerView_ngAssignment.setHasFixedSize(true);
        recyclerView_ngAssignment.setLayoutManager(new LinearLayoutManager(this));
        rel_noNgUserListingData = findViewById(R.id.rel_noNgUserListingData);
        editTextSearch = findViewById(R.id.editTextSearch);
        ll_sort = findViewById(R.id.ll_sort);
        ll_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Sort", Toast.LENGTH_SHORT).show();
                if (filtersDialogOpenCount == 0) {
                    showFiltersDialog();
                }
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
                    if (Utils.isNetworkConnected(NgUserListActivity.this)) {
                        loadNgUserList();

                    } else {
                        Utils.showToast(NgUserListActivity.this, "Please check your connectivity");
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
        Call call = DBManager.loadNgUserList(NgUserListActivity.this, "AS");
        retrofitCancelOberver.addlist(call);
    }


    /*private void loadNgUserList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<NguserListModel>> call = api.getClaimUnclaimList("AS");


        call.enqueue(new Callback<ArrayList<NguserListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguserListModel>> call, Response<ArrayList<NguserListModel>> response) {

                //finally we are setting the list to our MutableLiveData
                //ngUserList.setValue(response.body());
                if (response.body() != null) {
                    ngUserList = response.body();
                    if (ngUserList.size() > 0) {
                        setListData(ngUserList);
                    } else {

                        recyclerView_ngAssignment.setVisibility(View.GONE);
                        rel_noNgUserListingData.setVisibility(View.VISIBLE);
                        tv_ngUserListdata.setText("No data found!!");
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView_ngAssignment.setVisibility(View.GONE);
                            rel_noNgUserListingData.setVisibility(View.VISIBLE);
                            tv_ngUserListdata.setText("Failed o fetch data due to some problem please try after some time!!");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NguserListModel>> call, Throwable t) {
                Log.e("MyError", "error.............");

                rel_noNgUserListingData.setVisibility(View.VISIBLE);
                recyclerView_ngAssignment.setVisibility(View.GONE);
            }
        });
    }*/

    private void setListData(final List<NguserListModel> ngUserList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ngUserList.size() > 0) {
                    recyclerView_ngAssignment.setVisibility(View.VISIBLE);
                    rel_noNgUserListingData.setVisibility(View.GONE);
                    adapter = new NgUserListAdapter(NgUserListActivity.this, ngUserList);
                    recyclerView_ngAssignment.setAdapter(adapter);

                } else {
                    rel_noNgUserListingData.setVisibility(View.VISIBLE);
                }

                if ( mSelectedId == CLAIM || mSelectedId == UNCLAIM || mSelectedId == PRIORITY) {
                    tv_FiltersIndicator.setVisibility(View.VISIBLE);
                    //scheduleAnimation(true);
                    runAnimation(tv_FiltersIndicator);

                } else {
                    tv_FiltersIndicator.setVisibility(View.GONE);

                }

                if ( mSelectedId == CLAIM || mSelectedId == UNCLAIM|| mSelectedId == UNCLAIM) {
                    tv_FiltersIndicator.setVisibility(View.VISIBLE);
                } else {
                    tv_FiltersIndicator.setVisibility(View.GONE);
                }
                /*if (adapter != null && updateListFlag) {
                    adapter.updateList(activity, prodLineItems, retailer, actionId, dialog2);
                }*/
            }
        });
    }

    private Dialog mFilterDialog;
    private RadioButton radioButton_ngWithcalim, radioButton_ngWithUncalim, radioButton_ngWithPriority;
    private boolean flagRadioButtonCheck1 = false;
    private boolean flagRadioButtonCheck2 = false;
    private boolean flagRadioButtonCheck3 = false;
    private int filtersDialogOpenCount = 0;
    public static final int CLAIM = 2;
    public static final int UNCLAIM = 3;
    public static final int PRIORITY = 4;
    public static final int ALL_NG = 5;
    private int mSelectedId;

    public void showFiltersDialog() {
        //Toast.makeText(getApplicationContext(),"Filter icon",Toast.LENGTH_SHORT).show();
        mFilterDialog = new Dialog(NgUserListActivity.this);
        mFilterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mFilterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFilterDialog.setContentView(R.layout.dialog_filter_ng_user_list);
        mFilterDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rl_dialog_title = (RelativeLayout) mFilterDialog.findViewById(R.id.rl_dialog_title);
        Button btn_applyFilters = (Button) mFilterDialog.findViewById(R.id.btn_applyFilters);
        Button btn_clearAllFilters = (Button) mFilterDialog.findViewById(R.id.btn_clearAllFilters);
        ImageButton ibCancel = (ImageButton) mFilterDialog.findViewById(R.id.ib_create_cancel);
        radioButton_ngWithPriority = (RadioButton) mFilterDialog.findViewById(R.id.radioButton_ngWithPriority);
        radioButton_ngWithUncalim = (RadioButton) mFilterDialog.findViewById(R.id.radioButton_ngWithUncalim);
        radioButton_ngWithcalim = (RadioButton) mFilterDialog.findViewById(R.id.radioButton_ngWithcalim);
        if (mSelectedId == CLAIM)
            radioButton_ngWithcalim.setChecked(true);
        else {
            {
                if (flagRadioButtonCheck2)
                    flagRadioButtonCheck2 = false;
            }
            if (flagRadioButtonCheck1)
                flagRadioButtonCheck1 = false;
        }
        rl_dialog_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDialogOpenCount = 0;
                mFilterDialog.dismiss();

            }
        });
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersDialogOpenCount = 0;
                mFilterDialog.dismiss();
            }
        });

        btn_clearAllFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton_ngWithPriority.setChecked(false);
                radioButton_ngWithUncalim.setChecked(false);
                radioButton_ngWithcalim.setChecked(false);

                if (mSelectedId == CLAIM || mSelectedId == UNCLAIM|| mSelectedId == PRIORITY) {
                    tv_FiltersIndicator.setVisibility(View.VISIBLE);
                    runAnimation(tv_FiltersIndicator);

                } else {
                    tv_FiltersIndicator.setVisibility(View.GONE);
                }
                /*if (r != null)
                    baseRecyclerAdapter.notifyDataSetChanged();*/
            }
        });
        radioButton_ngWithcalim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_ngWithcalim.isChecked()) {
                    if (!flagRadioButtonCheck1) {
                        radioButton_ngWithcalim.setChecked(true);
                        radioButton_ngWithUncalim.setChecked(false);
                        radioButton_ngWithPriority.setChecked(false);
                        flagRadioButtonCheck1 = true;
                        flagRadioButtonCheck2 = false;
                        flagRadioButtonCheck3 = false;
                        Utils.showToast(NgUserListActivity.this, "claim click");


                    } else {
                        flagRadioButtonCheck1 = false;
                        radioButton_ngWithcalim.setChecked(false);
                        radioButton_ngWithUncalim.setChecked(false);
                        radioButton_ngWithPriority.setChecked(false);
                    }
                }
            }
        });
        radioButton_ngWithUncalim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_ngWithUncalim.isChecked()) {
                    if (!flagRadioButtonCheck2) {
                        radioButton_ngWithUncalim.setChecked(true);
                        radioButton_ngWithcalim.setChecked(false);
                        radioButton_ngWithPriority.setChecked(false);
                        flagRadioButtonCheck2 = true;
                        flagRadioButtonCheck1 = false;
                        flagRadioButtonCheck3 = false;

                    } else {
                        flagRadioButtonCheck2 = false;
                        radioButton_ngWithPriority.setChecked(false);
                        radioButton_ngWithUncalim.setChecked(false);
                        radioButton_ngWithcalim.setChecked(false);
                    }
                }
            }
        });
        radioButton_ngWithPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_ngWithPriority.isChecked()) {
                    if (!flagRadioButtonCheck3) {
                        radioButton_ngWithPriority.setChecked(true);
                        radioButton_ngWithcalim.setChecked(false);
                        radioButton_ngWithUncalim.setChecked(false);
                        flagRadioButtonCheck3 = true;
                        flagRadioButtonCheck2 = false;
                        flagRadioButtonCheck1 = false;

                    } else {
                        flagRadioButtonCheck3 = false;
                        radioButton_ngWithcalim.setChecked(false);
                        radioButton_ngWithUncalim.setChecked(false);
                        radioButton_ngWithPriority.setChecked(false);
                    }
                }
            }
        });
        btn_applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //final ProgressDialog dialog2 = ProgressDialog.show(NgUserListActivity.this, null, "Loading... ", true, false);

                if (radioButton_ngWithcalim.isChecked()) {
                    mSelectedId = CLAIM;
                } else if (radioButton_ngWithUncalim.isChecked()) {
                    mSelectedId = UNCLAIM;
                } else if (radioButton_ngWithPriority.isChecked()) {
                    mSelectedId = PRIORITY;
                } else {
                    mSelectedId = ALL_NG;
                }
                showItems(mSelectedId);

                mFilterDialog.dismiss();
                filtersDialogOpenCount = 0;
            }
        });


        mFilterDialog.show();
        filtersDialogOpenCount = 1;
    }

    private void showItems(final int actionId) {
        boolean updateList = true;
        switch (actionId) {
            case CLAIM:

                Utils.showToast(NgUserListActivity.this,"show claim");
                Call call_calimed = DBManager.getClaimedList(NgUserListActivity.this, "AS","1");
                retrofitCancelOberver.addlist(call_calimed);
                break;

            case UNCLAIM:
                updateList = true;
                Utils.showToast(NgUserListActivity.this,"show un-claim");
                Call call_unclaimed = DBManager.getClaimedList(NgUserListActivity.this, "AS","0");
                retrofitCancelOberver.addlist(call_unclaimed);
                break;
            case PRIORITY:
                updateList = true;
                Utils.showToast(NgUserListActivity.this,"priority");
                showPeriority();
                break;
            case ALL_NG:
                updateList = true;
                Utils.showToast(NgUserListActivity.this,"all ng list");

                break;
        }
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
