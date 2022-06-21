package com.fieldmobility.igl.database;

import com.fieldmobility.igl.Model.NguserListModel;

import java.util.List;

public interface ReadNgUsersListener {
     void onSuccess(List<NguserListModel> users);
    void onFailure();
}
