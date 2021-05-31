package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldmobility.igl.R;

public class Waveview_NewData extends Activity {


    private ProgressDialog prg;
    private WebView webView;
    private  int counter=0;
    String URL;
    TextView type;
      ImageView back;
   // TextView type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_new);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        type=findViewById(R.id.type);
        URL="http://"+getIntent().getStringExtra("pdf");

        type.setText(getIntent().getStringExtra("type"));
        webView =(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
       // webView.setWebViewClient(new WebViewClient());

       // getWebview(URL);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

        webView.requestFocus();
        webView.getSettings().setJavaScriptEnabled(true);
        String myPdfUrl = "http://49.50.118.112:8080/learning/IGL.pdf";
        String url ="https://drive.google.com/viewerng/viewer?embedded=true&url=" +myPdfUrl;
        Log.e("url++",url);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });

    }


    public void getWebview(String myurl)
    {

        webView.setWebViewClient(new WebViewClient()
        {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                prg.show();



                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                prg.dismiss();

                view.getSettings().setJavaScriptEnabled(true);




                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {



                super.onPageStarted(view, url, favicon);
            }





        });
        prg = ProgressDialog.show(Waveview_NewData.this, "", "Please Wait..", true);
        prg.setCancelable(true);
        webView.loadUrl(myurl);



    }



    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onBackPressed() {


        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }


}