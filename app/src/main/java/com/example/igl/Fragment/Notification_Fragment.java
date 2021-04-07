package com.example.igl.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.igl.Adapter.Notification_Adapter;
import com.example.igl.MataData.VideoListData;
import com.example.igl.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Notification_Fragment extends Fragment {

    TextView title_text;
    ImageView back_img;
    LinearLayout back_button;
    View root;

    public Notification_Fragment() {
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
        root = inflater.inflate(R.layout.notification_recyclerview, container, false);


        VideoListData[] myListData = new VideoListData[] {
                new VideoListData("RFC work pending", "18 Mar 2020"),
                new VideoListData("Non-LMC","13 Mar 2020"),
                new VideoListData("NG","18 Mar 2020"),
                new VideoListData("Feasibility", "11 Mar 2020"),
                new VideoListData("Non-LMC","17 Mar 2020"),
                new VideoListData("NG", "22 Mar 2020"),
                new VideoListData("Feasibility", "26 Mar 2020"),

        };

        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);
        Notification_Adapter adapter = new Notification_Adapter(getActivity(),myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return root;
    }
}