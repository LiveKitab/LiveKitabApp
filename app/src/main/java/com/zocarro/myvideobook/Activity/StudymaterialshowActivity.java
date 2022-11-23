package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.github.barteksc.pdfviewer.PDFView;
import com.zocarro.myvideobook.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class StudymaterialshowActivity extends AppCompatActivity
{
PDFView studymaterialwebview;
String subjectname,file;
TextView nostudyfound;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studymaterialshow);
        toolbar = findViewById(R.id.toolbar);
        nostudyfound = findViewById(R.id.nostudyfound);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Pdf Viewer");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        studymaterialwebview = findViewById(R.id.pdf);
        Intent intent =getIntent();
        file = intent.getStringExtra("file");
        Log.d("file",file);
        if(file.equalsIgnoreCase("File not found"))
        {
            Log.d("vledvne","b");
            nostudyfound.setVisibility(View.VISIBLE);
        }
        else
        {
            studymaterialwebview.setVisibility(View.VISIBLE);

            new StudymaterialshowActivity.RetrivePDFfromUrl().execute(file);
        }

        subjectname = intent.getStringExtra("chap_name");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            finish();// close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
//    private void pdfOpen(String fileUrl)
//    {
//        studymaterialwebview.getSettings().setJavaScriptEnabled(true);
//        studymaterialwebview.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//        //---you need this to prevent the webview from
//        // launching another browser when a url
//        // redirection occurs---
//        studymaterialwebview.setWebViewClient(new Callback());
//        studymaterialwebview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+fileUrl);
//
//    }
class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
    @Override
    protected InputStream doInBackground(String... strings) {
        // we are using inputstream
        // for getting out PDF.
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            // below is the step where we are
            // creating our connection.
            HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                // response is success.
                // we are getting input stream from url
                // and storing it in our variable.
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            // this is the method
            // to handle errors.
            e.printStackTrace();
            return null;
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        // after the execution of our async
        // task we are loading our pdf in our pdf view.
        studymaterialwebview.fromStream(inputStream).load();
    }
}

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}