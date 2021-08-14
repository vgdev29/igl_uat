package com.fieldmobility.igl.Listeners;

import org.json.JSONException;
import org.json.JSONObject;

public interface PlainTextListItemSelectListener {

    void onPlainTextItemSelect(String item, int itemPosition) throws JSONException;
}
