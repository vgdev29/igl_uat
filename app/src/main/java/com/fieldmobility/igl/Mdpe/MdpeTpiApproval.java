package com.fieldmobility.igl.Mdpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fieldmobility.igl.Activity.TPI_RfcDone_Approval_Activity;
import com.fieldmobility.igl.Helper.CommonUtils;
import com.fieldmobility.igl.Helper.Constants;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.databinding.ActivityMdpeApprovalBinding;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MdpeTpiApproval extends AppCompatActivity {

    DprDetails_Model dpr = new DprDetails_Model();
    ActivityMdpeApprovalBinding binding;
    SignatureView signatureView;
    Bitmap bitmap;
    String signature_path;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    String log= "mdpeapproval";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mdpe_approval);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent()!=null)
        {
            dpr = (DprDetails_Model) getIntent().getSerializableExtra("dpr");
        }
        binding.tvLati.setText(dpr.getLatitude());
        binding.tvLongi.setText(dpr.getLongitude());
        binding.tvDpr.setText(dpr.getDpr_no());
        binding.tvSubmit.setText(dpr.getCreation_date());
        binding.tvInput.setText(dpr.getInput()+" "+dpr.getInput_unit());
        binding.tvCategory.setText(dpr.getCategory());
        binding.tvSection.setText(dpr.getSection()+" - "+dpr.getSub_Section());
        binding.tvSectionid.setText(dpr.getSection_id());
        binding.tvCategory.setText(dpr.getCategory());
        binding.tvAlloNum.setText(dpr.getAllocation_no());
        binding.tvSuballo.setText(dpr.getSub_allocation());
        binding.tvWbs.setText(dpr.getWbs_number());
        String file_path_image = dpr.getFiles_path();
        int x = file_path_image.indexOf("mdpedocapp");
        String strname = file_path_image.substring(x,file_path_image.length());
        strname = Constants.BASE_URL+strname;
        strname.replace("\\", "/");
        Picasso.with(MdpeTpiApproval.this)
                .load(strname)
                .placeholder(R.color.red_light)
                .into(binding.image);

        String finalStrname = strname;
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showZoomImageView(MdpeTpiApproval.this, finalStrname);
            }
        });
        binding.tvAssignDate.setText(dpr.getAgent_assign_date());
        binding.tvTpi.setText(dpr.getAgent()+"\n"+dpr.getAgent_mob());
        binding.tvAssignment.setText(dpr.getMethod()+"\n"+dpr.getSize()+"\n"+dpr.getArea_type()+"\n"+dpr.getTrenchless_method()+"\n"+dpr.getLength());
        binding.tvAddress.setText(dpr.getArea()+","+dpr.getSociety()+","+dpr.getCity()+","+dpr.getZone());
        binding.signatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpi_signature();
            }
        });
        binding.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signature_path==null ||signature_path.isEmpty())
                {
                    CommonUtils.toast_msg(MdpeTpiApproval.this,"Select Signature");
                }
                else
                {
                    MdpeDpr_Model dpr_mod = new MdpeDpr_Model(dpr.getIdDpr(),dpr.getAllocation_no(),dpr.getSub_allocation(),dpr.getDpr_no(),
                            dpr.section_id,dpr.getInput(),dpr.getLatitude(),dpr.getLongitude(),
                            dpr.getFiles_path(),dpr.getTpi_id(),dpr.getCreation_date(),2,binding.etDeclineRemarks.getText().toString().trim(),
                            signature_path,"");
                    approve_decline(dpr_mod);
                }
            }
        });
        binding.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    MdpeDpr_Model dpr_mod = new MdpeDpr_Model(dpr.getIdDpr(),dpr.getAllocation_no(),dpr.getSub_allocation(),dpr.getDpr_no(),
                            dpr.section_id,dpr.getInput(),dpr.getLatitude(),dpr.getLongitude(),
                            dpr.getFiles_path(),dpr.getTpi_id(),dpr.getCreation_date(),1,binding.etDeclineRemarks.getText().toString().trim(),
                            signature_path,"");
                    approve_decline(dpr_mod);
                }

            }
        });




    }
    public boolean validate()
    {
        boolean isValid = true;
        if (signature_path==null || signature_path.isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(MdpeTpiApproval.this, "Please Select Signature");
        } else if ( binding.etDeclineRemarks.getText().toString().trim().isEmpty()) {
            isValid = false;
            CommonUtils.toast_msg(this, "Pls Mention Decline Reason");
        }

        else {
            isValid = true;
        }

        return isValid;
    }

    private void tpi_signature() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        getWindow().setLayout(-1, -1);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle(SecurityConstants.Signature);
        dialog.setCancelable(true);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        Button clear =   dialog.findViewById(R.id.clear);
        Button save =  dialog.findViewById(R.id.save);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signatureView.clearCanvas();
            }
        });
        ((ImageView) dialog.findViewById(R.id.crose_img)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = signatureView.getSignatureBitmap();
                String customer_image_select = saveImage(signatureBitmap);
                if (signatureBitmap != null) {
                    signature_path = change_to_binary(signatureBitmap);
                    binding.signatureImage.setImageBitmap(signatureBitmap);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getFilesDir() + IMAGE_DIRECTORY /*iDyme folder*/);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("Signature_Page++", wallpaperDirectory.toString());
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String change_to_binary(Bitmap bitmapOrg) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        return ba1;
    }

    private void approve_decline(MdpeDpr_Model dpr ) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .content("Please wait....")
                .progress(true, 0)
                .show();
        String jsonInString = new Gson().toJson(dpr);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Constants.MDPEDPR_APPROVALUPDATE,mJSONObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                materialDialog.dismiss();
                try {

                    Log.d(log,response.toString());
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        JSONObject data_object = response.getJSONObject("data");
                        CommonUtils.toast_msg(MdpeTpiApproval.this,response.getString("message"));
                        onBackPressed();
                    }
                    else
                    {
                        CommonUtils.toast_msg(MdpeTpiApproval.this,response.getString("message"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }
}