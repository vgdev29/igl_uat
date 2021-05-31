package com.fieldmobility.igl.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fieldmobility.igl.R;


public class BePartnerStepTwoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "fullname";
    private static final String ARG_PARAM2 = "father_husbandname";

    // TODO: Rename and change types of parameters
     String father_husbandname;
     String fullname;

    private OnStepTwoListener mListener;

    public BePartnerStepTwoFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BePartnerStepOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BePartnerStepTwoFragment newInstance(String fullname, String father_husbandname) {
        BePartnerStepTwoFragment fragment = new BePartnerStepTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fullname);
        args.putString(ARG_PARAM2, father_husbandname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullname = getArguments().getString(ARG_PARAM1);
            father_husbandname = getArguments().getString(ARG_PARAM2);
            Log.e("mParam22",fullname);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_be_partner_step_two, container, false);
    }

    private Button backBT;
    private Button nextBT;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullname = getArguments().getString("fullname");
        father_husbandname = getArguments().getString("father_husbandname");
        Log.e("mParam22",fullname);

        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
    }

    @Override
    public void onResume() {
        super.onResume();
        backBT.setOnClickListener(this);
        nextBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;

            case R.id.nextBT:
                if (mListener != null)
                    mListener.onNextPressed(this);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepTwoListener) {
            mListener = (OnStepTwoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        backBT=null;
        nextBT=null;
    }

    public interface OnStepTwoListener {
        void onBackPressed(Fragment fragment);
        void onNextPressed(Fragment fragment);

    }
}