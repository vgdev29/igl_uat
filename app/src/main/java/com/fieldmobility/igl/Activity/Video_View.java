package com.fieldmobility.igl.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldmobility.igl.Helper.SharedPrefs;
import com.fieldmobility.igl.R;

public class Video_View extends Activity {

    private ProgressDialog prg;
    private WebView webView;
    private  int counter=0;
    String URL;
    ImageView back;
    TextView type;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_new);
        sharedPrefs=new SharedPrefs(this);
        type=findViewById(R.id.type);
        URL="http://"+getIntent().getStringExtra("video").trim();
        Log.e("URL",URL);
        type.setText(getIntent().getStringExtra("type"));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView =(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
    //    webView.getSettings().setAppCacheEnabled(true);
     //   webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        getWebview(URL);
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
        prg = ProgressDialog.show(Video_View.this, "", "Please Wait..", false);
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