package com.zocarro.myvideobook.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import java.net.URLEncoder;

public class InvoiceWebActivity extends AppCompatActivity {

    private static final String TAG = "InvoiceWebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView webView = new WebView(this);
        final String bill_id = getIntent().getStringExtra("bill_id");
//        final String sh_id = getIntent().getStringExtra("sh_id");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);

        StringBuffer buffer=new StringBuffer(Common.GetWebServiceURL() +"invoice.php");
        buffer.append("?bill_id="+ URLEncoder.encode(bill_id));
//        buffer.append("&sh_id="+URLEncoder.encode(sh_id));
        webView.loadUrl(buffer.toString());
        Log.d(TAG, "onCreate: " + buffer);

        final ProgressDialog progressBar = new ProgressDialog(InvoiceWebActivity.this);
        progressBar.setMessage("Please wait...");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!progressBar.isShowing()) {
                    if(!(InvoiceWebActivity.this).isFinishing())
                    {
                        //show dialog
                        progressBar.show();
                    }
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                // do your stuff here
                if(webView!=null)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(webView, bill_id);
                    }else{
                        // Showing Toast message to user
                        Toast.makeText(InvoiceWebActivity.this, "Not available for device below Android LOLLIPOP",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(InvoiceWebActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // setting clickListener for Save Pdf Button
    }

    // object of print job
    PrintJob printJob;
    // a boolean to check the status of printing
    boolean printBtnPressed = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    private void PrintTheWebPage(WebView webView, final String orderid) {
        // set printBtnPressed true
        printBtnPressed=true;
        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);
        // setting the name of job
        String jobName = getString(R.string.app_name) + " INVOICE - "+ orderid;
        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,

                new PrintAttributes.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(printJob!=null && printBtnPressed) {
            if (printJob.isCompleted()) {
                Log.d(TAG, "onResume: Completed");
                finish();
            } else if (printJob.isStarted()) {
                Log.d(TAG, "onResume: isStarted");
            } else if (printJob.isBlocked()) {
                Log.d(TAG, "onResume: isBlocked");
            } else if (printJob.isCancelled()) {
                Log.d(TAG, "onResume: isCancelled");
                finish();
            } else if (printJob.isFailed()) {
                Log.d(TAG, "onResume: isFailed");
                finish();
            } else if (printJob.isQueued()) {
                Log.d(TAG, "onResume: isQueued");
            }
            // set printBtnPressed false
            printBtnPressed=false;
        }
    }
}