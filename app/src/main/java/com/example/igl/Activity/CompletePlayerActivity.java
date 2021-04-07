/*
package com.example.igl.Activity;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.igl.R;

import com.veer.exvidplayer.Player.Constants;
import com.veer.exvidplayer.VideoPlayer.ExVpCompleteFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CompletePlayerActivity extends AppCompatActivity {
  String[] type = new String[] {
      Constants.MEDIA_TYPE_OTHERS
  };
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
              WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
              WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
              WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.video_layout);
        Activity_Mathod();

  }


    private void Activity_Mathod() {
        Intent intent = getIntent();
        String Video_Url = intent.getStringExtra("video");
        System.out.println("Video_url++++"+ Video_Url);
        String[] url = new String[] {Video_Url};
        ArrayList video_url=new ArrayList(Arrays.asList(url));
        ArrayList video_type=new ArrayList(Arrays.asList(type));
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ExVpCompleteFragment exVpCompleteFragment =new ExVpCompleteFragment();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("urls",video_url);
        bundle.putStringArrayList("type",video_type);
        bundle.putInt("currentIndex",0);
        exVpCompleteFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.parent, exVpCompleteFragment);

        fragmentTransaction.commit();
    }
}
*/
