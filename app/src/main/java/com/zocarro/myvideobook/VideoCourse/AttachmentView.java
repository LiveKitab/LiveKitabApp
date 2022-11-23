package com.zocarro.myvideobook.VideoCourse;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

public class AttachmentView extends AppCompatActivity {

    WebView webView;
    ProgressDialog pDialog;
    String doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment_view);
        doc=getIntent().getExtras().getString("doc");
        webView=findViewById(R.id.webview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Attachment File");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        webView.getSettings().setJavaScriptEnabled(true);
        pDialog = new ProgressDialog(AttachmentView.this);
        pDialog.setTitle("Video Attachment view");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        String url = "http://docs.google.com/gview?embedded=true&url="+ Common.GetBaseImageURL()+ "editor/video/"+doc;

        Log.d("UUU",url);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });

    }
}
