package com.zocarro.myvideobook.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.zocarro.myvideobook.BottomNavigation.BottomNavigationActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.HomeActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.checksum_testing;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultWithDataListener,ExternalWalletListener {

    ProgressDialog progressDialog;
    String API_ID = "rzp_test_0RHVnRvOPZwYaO";  //RazourPay API ID
    private AlertDialog.Builder alertDialogBuilder;
    Button btnPay;

    String courseTitle, transaction_id, message, bill_id, courseDiscount, sub_id, newPrice, videoid, videolink, mentorid, oldPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnPay = findViewById(R.id.payFees);

        progressDialog = new ProgressDialog(this);

        alertDialogBuilder = new AlertDialog.Builder(PaymentActivity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Payment Result");
        alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
            //do nothing
        });

        Intent intent = getIntent();
        courseTitle =  intent.getStringExtra("courseTitle");
        transaction_id =  intent.getStringExtra("transaction_id");
        message =  intent.getStringExtra("order_id");
        bill_id =  intent.getStringExtra("bill_id");
        courseDiscount =  intent.getStringExtra("courseDesc");
        sub_id =  intent.getStringExtra("subject_id");
        newPrice =  intent.getStringExtra("price");
        videoid =  intent.getStringExtra("v_id");
        videolink =  intent.getStringExtra("v_link");
        mentorid =  intent.getStringExtra("c_id");
        oldPrice =  intent.getStringExtra("oldprice");

//        btnPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                progressDialog.setMessage("Please wait...");
//                progressDialog.show();
                startPayment();
//            }
//        });

    }


    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        co.setKeyID(API_ID);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "GMIT ITCELL SANJAY PARMAR");
            options.put("description", "STUDENT REG. FEES");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://biochemical-damping.000webhostapp.com/GM%20Logo/gm.jpg");
            options.put("currency", "INR");
            options.put("amount", newPrice + "00");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "sanjayparmar7167@gmail.com");
            preFill.put("contact", "7435954425");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
            finish();
        }

    }

//    @Override
//    public void onExternalWalletSelected(String s, PaymentData paymentData) {
//
//       // try{
////            alertDialogBuilder.setMessage("External Wallet Selected:\nPayment Data: "+paymentData.getData());
////            alertDialogBuilder.show();
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//
//    }

//    @Override
//    public void onPaymentSuccess(String s, PaymentData paymentData) {
//
//    }

//    @Override
//    public void onPaymentError(int i, String s, PaymentData paymentData) {
//
//    }

    @Override
    public void onExternalWalletSelected(String s, PaymentData paymentData) {
        try{
            alertDialogBuilder.setMessage("External Wallet Selected:\nPayment Data: "+paymentData.getData());
            alertDialogBuilder.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        SharedPreferences sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        try{
//            alertDialogBuilder.setMessage("Payment Successful :\nPayment ID: "+s+"\nPayment Data: "+paymentData.getData());
//            alertDialogBuilder.show();

            progressDialog.setMessage("Transaction is Successful. Just a moment...");
            progressDialog.show();

            Toast.makeText(this, "Payment Successful :\nPayment ID: "+s+"\nPayment Data: "+paymentData.getData(), Toast.LENGTH_SHORT).show();

            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            setSuccess("Razour_Pay", message, "UNKNOWN", "Success", date, s
                    );

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                    edit.putString("PaymentStatus", "PaymentDone");
                    edit.apply();

                    startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                    finish();
                }
            }, 2000);

        }catch (Exception e){
            Common.progressDialogDismiss(PaymentActivity.this);
            progressDialog.dismiss();
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try{
            alertDialogBuilder.setMessage("Payment Failed:\nPayment Data: "+paymentData.getData());
            alertDialogBuilder.show();
            finish();
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    private void setSuccess(final String paymentMode, final String orderid, final String BankName, final String Status, final String time,final  String TXNIDPAYTM) {
        Common.progressDialogShow(PaymentActivity.this);
        String Webserviceurl=Common.GetWebServiceURL() + "purchased_courses.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST,Webserviceurl, response -> {
            Common.progressDialogDismiss(PaymentActivity.this);
            Log.d("response", response);
            try {
                JSONObject object=new JSONObject(response);
                String message=object.getString("message");
                if(message.equalsIgnoreCase("Course Enrolled"))
                {
                    Common.progressDialogDismiss(PaymentActivity.this);
                    startActivity(new Intent(PaymentActivity.this, BottomNavigationActivity.class));
                    finish();
                }

                else {
                    Common.progressDialogDismiss(PaymentActivity.this);
                    Toast.makeText(PaymentActivity.this, message , Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(PaymentActivity.this);
                Toast.makeText(PaymentActivity.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }, error -> {
            Common.progressDialogDismiss(PaymentActivity.this);
            Toast.makeText(PaymentActivity.this, "Norm Er Something went wrong" + error.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> data=new HashMap<>();
                data.put("payment_mode", paymentMode);
                data.put("tran_id", orderid);
                data.put("status", Status);
                data.put("time", time);
                data.put("BankName", BankName);
                data.put("TXNIDPAYTM", TXNIDPAYTM);
                data.put("user_id",uid);
                data.put("course_id", sub_id);
                data.put("price", newPrice);
                data.put("oldprice", "UNKNOWN");
                data.put("discount", "UNKNOWN");
                data.put("c_id", mentorid);
                Log.d("**********@@@", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PaymentActivity.this).add(request);
    }


}