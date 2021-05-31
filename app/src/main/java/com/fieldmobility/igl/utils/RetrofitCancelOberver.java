package com.fieldmobility.igl.utils;


import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;

public class RetrofitCancelOberver implements LifecycleObserver {

    ArrayList<Call> arrayList_retrofitCalls = new ArrayList<>();

    Set<Call> set;

    public void addlist(Call call) {
        if (call != null) {
            arrayList_retrofitCalls.add(call);
            removeduplicates(arrayList_retrofitCalls);
        }
    }

    public void removeCall(Call call) {
        try {
            arrayList_retrofitCalls.remove(call);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void removeduplicates(ArrayList<Call> arrayList) {
        try {
            set = new HashSet<>(arrayList);
            arrayList_retrofitCalls.clear();
            arrayList_retrofitCalls.addAll(set);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cancelrequest() {
        Log.e("checkpoint", "ondestroy OnLifecycleEvent");

        try {

            for (Iterator<Call> iterator = arrayList_retrofitCalls.iterator(); iterator.hasNext(); ) {
                Call call = iterator.next();
                {
                    if (call != null) {
                        Log.e("checkpoint", "call LOOP----" + call.request().url().toString());
                        call.cancel();
                        Log.e("checkpoint", "call cancel----" + call.request().url().toString());
                        iterator.remove();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
