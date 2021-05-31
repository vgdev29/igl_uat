package com.fieldmobility.igl.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fieldmobility.igl.R;


public class BePartnerStepOneFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "fullname";
    private static final String ARG_PARAM2 = "father_husbandname";
    private static final String ARG_PARAM3 = "mobile_no";
    private static final String ARG_PARAM4 = "contect_no";
    private static final String ARG_PARAM5 = "aadhaar_no";
    private static final String ARG_PARAM6 = "email_id";
    // TODO: Rename and change types of parameters
    public static String mParam1;
    public static String mParam3="";
    public static String fullname_edit;
    public static String father_husband_name_edit,mobile_no_edit,contect_no_edit,aadhaar_no_edit,email_id_edit;
    public static String type_ownership,proof_of_residence;
    private OnStepOneListener mListener;

    public BePartnerStepOneFragment() {
    }

    public static BePartnerStepOneFragment newInstance(String mParam1, String mParam2) {
        BePartnerStepOneFragment fragment = new BePartnerStepOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParam1);
        args.putString(ARG_PARAM2, mParam2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_be_partner_step_one, container, false);

    }

    private Button nextBT;
    private CheckBox cb1, cb2, cb3, cb4,cb5,cb6;
    private String checkBoxChoices = "";
    public   EditText fullname;
    public  EditText father_husbandname;
    private EditText mobile_no;
    private EditText contect_no;
    private EditText aadhaar_no;
    private EditText email_id;
    private RadioGroup radioGroup;
    RadioButton genderradioButton;
    private String owner_rentes;
    String name;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nextBT = view.findViewById(R.id.nextBT);
        fullname=view.findViewById(R.id.fullname);
        father_husbandname=view.findViewById(R.id.father_husbandname);
        mobile_no=view.findViewById(R.id.mobile_no);
        contect_no=view.findViewById(R.id.contect_no);
        aadhaar_no=view.findViewById(R.id.aadhaar_no);
        email_id=view.findViewById(R.id.email_id);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) view.findViewById(selectedId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.owner:

                        owner_rentes="Owner";
                        break;
                    case R.id.rents:
                        owner_rentes="Rents";
                        break;


                }
            }
        });
        cb1 = (CheckBox) view.findViewById(R.id.cb1);
        cb2 = (CheckBox) view.findViewById(R.id.cb2);
        cb3 = (CheckBox) view.findViewById(R.id.cb3);
        cb4 = (CheckBox) view.findViewById(R.id.cb4);
        cb5 = (CheckBox) view.findViewById(R.id.cb5);
        cb6 = (CheckBox) view.findViewById(R.id.cb6);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    checkBoxChoices= cb1.getText().toString() ;
                    Toast.makeText(getActivity(), "Selected : " + checkBoxChoices , Toast.LENGTH_LONG).show();
                    mParam3=checkBoxChoices;

                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb1.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    checkBoxChoices= cb2.getText().toString();
                    Toast.makeText(getActivity(), "Selected : " + checkBoxChoices , Toast.LENGTH_LONG).show();
                }
            }
        });
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    checkBoxChoices = cb3.getText().toString() ;

                }
            }
        });
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);
                    checkBoxChoices = cb4.getText().toString() ;
                }
            }
        });
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb4.setChecked(false);
                    cb3.setChecked(false);
                    cb6.setChecked(false);
                    checkBoxChoices = cb5.getText().toString() ;
                }
            }
        });
        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb3.setChecked(false);
                    checkBoxChoices = cb6.getText().toString() ;
                }
            }
        });


        New_Layout();

    }

    public void New_Layout() {
        fullname_edit=fullname.getText().toString();
        Log.e("fullname",fullname_edit);
        father_husband_name_edit=father_husbandname.getText().toString();
        mobile_no_edit=mobile_no.getText().toString();
        contect_no_edit=contect_no.getText().toString();
        aadhaar_no_edit=aadhaar_no.getText().toString();
        email_id_edit=email_id.getText().toString();
        proof_of_residence=checkBoxChoices;
        type_ownership=owner_rentes;

    }


    @Override
    public void onResume() {
        super.onResume();
        nextBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.nextBT:
                New_Layout();

                if (mListener != null)
                    mListener.onNextPressed(this);
                    //mListener.onNextPressed1(mParam1);

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepOneListener) {
            mListener = (OnStepOneListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        nextBT = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepOneListener {
        //void onFragmentInteraction(Uri uri);
        void onNextPressed(Fragment fragment);

        //void onNextPressed1(String name);

    }
}