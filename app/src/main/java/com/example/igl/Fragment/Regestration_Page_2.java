package com.example.igl.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.igl.R;

import androidx.fragment.app.Fragment;

public class Regestration_Page_2 extends Fragment {

    TextView title_text;
    ImageView back_img;
    LinearLayout back_button;
    View root;

    public Regestration_Page_2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.regestration_page_1, container, false);
        return root;
    }
}