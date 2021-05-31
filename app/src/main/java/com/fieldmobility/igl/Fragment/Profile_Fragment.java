package com.fieldmobility.igl.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fieldmobility.igl.Helper.AppController;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile_Fragment extends Fragment {

    EditText name_edit,email_edit,address_edit,strret_area_society_edit,state_edit,mobile_edit,aadhaar_edit;
    ImageView back_img;
    LinearLayout back_button;
    View root;
    MaterialDialog materialDialog;
    SharedPrefs sharedPrefs;
    public Profile_Fragment() {
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
        sharedPrefs=new SharedPrefs(getActivity());
        name_edit=root.findViewById(R.id.name_edit);
        email_edit=root.findViewById(R.id.email_edit);
        address_edit=root.findViewById(R.id.address_edit);
        strret_area_society_edit=root.findViewById(R.id.strret_area_society_edit);
        state_edit=root.findViewById(R.id.state_edit);
        mobile_edit=root.findViewById(R.id.mobile_edit);
        aadhaar_edit=root.findViewById(R.id.aadhaar_edit);

        User_Authorization();
        return root;
    }


    public void User_Authorization() {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String login_request = "login_request";
        StringRequest jr = new StringRequest(Request.Method.GET, Constants.Login_User,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        materialDialog.dismiss();
                        //User_Admin_Id="2";
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.e("Access token",json.toString());
                            // if (json.getString("success").equalsIgnoreCase("true")) {
                            name_edit.setText(json.getString("first_name"));
                            email_edit.setText(json.getString("userName"));
                            /*mobile_edit.setText(json.getString("first_name"));
                            address_edit.setText(json.getString("first_name"));
                            state_edit.setText(json.getString("first_name"));
                            // JSONObject json_paylode=json.getJSONObject("data");
                            sharedPrefs.setUUID(json.getString("id"));
                            sharedPrefs.setEmail(json.getString("userName"));
                            //sharedPrefs.setMobile(json_paylode.getString("mobile"));
                            sharedPrefs.setName(json.getString("first_name"));
                            //sharedPrefs.setState(json_paylode.getString("Status"));
*/



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                materialDialog.dismiss();
                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " +sharedPrefs.getToken());
                //headers.put("Cookie", "JSESSIONID=26A74DC8C72D234EFA902A6CE003B81B");

                return headers;
            }


        };
        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 10000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setTag(login_request);
        AppController.getInstance().addToRequestQueue(jr, login_request);
    }
}