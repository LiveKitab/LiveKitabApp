package com.zocarro.myvideobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class SupportActivity extends AppCompatActivity
{
WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        webview = findViewById(R.id.webview);

    }
}