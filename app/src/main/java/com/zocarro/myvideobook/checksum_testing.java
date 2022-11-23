package com.zocarro.myvideobook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.zocarro.myvideobook.Dashboard.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class checksum_testing extends AppCompatActivity implements PaytmPaymentTransactionCallback
{
    String status;
    private static final String TAG = "checksum_testing";
    String total ="";
    String courseDesc, price, c_id, subject_id, order_id,oldprice;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        courseDesc = getIntent().getStringExtra("courseDesc");
        price = getIntent().getStringExtra("price");
        c_id = getIntent().getStringExtra("c_id");
        subject_id = getIntent().getStringExtra("subject_id");
        order_id = getIntent().getStringExtra("order_id");
//        oldprice = getIntent().getStringExtra("oldprice");
        oldprice = "20000";
        Log.d("oldprice",oldprice);

        total = getIntent().getStringExtra("price");

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        uid = mPrefs.getString("u_id", "none");

        total = total + ".00";
        Log.v("@@@", total);
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(checksum_testing.this);
        String url = Common.GetWebServiceURL() + "paytmallinone/generateChecksum.php";
        String CHECKSUMHASH ="";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum_testing.this);
            String param = "MID=" + PaymentUtils.MERCHANT_ID +
                    "&ORDER_ID=" + order_id +
                    "&CUST_ID=" + uid +
                    "&CHANNEL_ID=" + PaymentUtils.CHANNEL_ID +
                    "&TXN_AMOUNT=" + total +
                    "&WEBSITE=" + PaymentUtils.WEBSITE +
                    "&INDUSTRY_TYPE_ID=" + PaymentUtils.INDUSTRY_TYPE_ID +
                    "&CALLBACK_URL=" + PaymentUtils.CALLBACK_URL;

            Log.e("checksum_testing >>",param);
            Log.v("Total Amount >>>>",total);

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum_testing ke saht order id or status receive hoga..
            // Log.e("checksum_testing result >>",jsonObject.toString());
            if(jsonObject != null)
            {
                //Log.e("checksum_testing result >>",jsonObject.toString());
                try
                {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("checksum_testing >>",CHECKSUMHASH);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getStagingService();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("MID", PaymentUtils.MERCHANT_ID); //MID provided by paytm
            paramMap.put("ORDER_ID", order_id);
            paramMap.put("CUST_ID", uid);
            paramMap.put("CHANNEL_ID", PaymentUtils.CHANNEL_ID);
            paramMap.put("TXN_AMOUNT", total);
            paramMap.put("WEBSITE", PaymentUtils.WEBSITE);
            paramMap.put("CALLBACK_URL", PaymentUtils.CALLBACK_URL);
            paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", PaymentUtils.INDUSTRY_TYPE_ID);
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum_testing ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum_testing.this, true, true, checksum_testing.this  );
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum_testing ", " respon true " + bundle.toString());
        String payment_mode = bundle.getString("PAYMENTMODE");
        String Res_msg = bundle.getString("RESPMSG");
        String ORDERID=bundle.getString("ORDERID");
        String BANKNAME=bundle.getString("BANKNAME");
        String TXNDATE=bundle.getString("TXNDATE");
        String TXNIDPAYTM=bundle.getString("TXNID");
        String status=bundle.getString("STATUS");

        if(BANKNAME == null){
            BANKNAME = "UPI";
        }
        if (status.equalsIgnoreCase("TXN_SUCCESS")) {
            Log.d("PAYMENTMODE", payment_mode);
            Toast.makeText(checksum_testing.this, "Payment Successful", Toast.LENGTH_SHORT).show();
            status = "Success";
            setSuccess(payment_mode,ORDERID,BANKNAME,"Success",TXNDATE,TXNIDPAYTM);
        } else {
            Toast.makeText(checksum_testing.this, "Payment Failed", Toast.LENGTH_SHORT).show();
//            setSuccess(payment_mode,ORDERID,BANKNAME,"Failed",TXNDATE,TXNIDPAYTM);
        }
    }

    private void setSuccess(final String paymentMode, final String orderid, final String BankName, final String Status, final String time,final  String TXNIDPAYTM) {
        Common.progressDialogShow(checksum_testing.this);
        String Webserviceurl=Common.GetWebServiceURL() + "purchased_courses.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST,Webserviceurl, response -> {
            Common.progressDialogDismiss(checksum_testing.this);
            Log.d("response", response);
            try {
                JSONObject object=new JSONObject(response);
                String message=object.getString("message");
                if(message.equalsIgnoreCase("Course Enrolled"))
                {
                    startActivity(new Intent(checksum_testing.this, HomeActivity.class));
                    finish();
                }

                else {
                    Toast.makeText(checksum_testing.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(checksum_testing.this);
                Toast.makeText(checksum_testing.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Common.progressDialogDismiss(checksum_testing.this);
            Toast.makeText(checksum_testing.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
                data.put("course_id", subject_id);
                data.put("price", price);
                data.put("oldprice", oldprice);
                data.put("discount", courseDesc);
                data.put("c_id", c_id);
                Log.d("**********@@@", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(checksum_testing.this).add(request);
    }

    @Override
    public void networkNotAvailable()
    {
        Toast.makeText(getApplicationContext(),"Network is not Available",Toast.LENGTH_SHORT).show();
        status = "Pending";
//        startActivity(new Intent(checksum_testing.this, DashboardMain.class));
        finish();
    }
    @Override
    public void clientAuthenticationFailed(String s)
    {
    }
    @Override
    public void someUIErrorOccurred(String s)
    {
        Log.e("checksum_testing ", " ui fail respon  "+ s );
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1)
    {
        Log.e("checksum_testing ", " error loading pagerespon true "+ s + "  s1 " + s1);
        Toast.makeText(getApplicationContext(),"Transaction Failed",Toast.LENGTH_SHORT).show();
        status = "Failed";
//        startActivity(new Intent(checksum_testing.this, MainActivity.class));
        finish();
    }
    @Override
    public void onBackPressedCancelTransaction()
    {
        Log.e("checksum_testing ", " cancel call back respon  " );
        Toast.makeText(getApplicationContext(),"Transaction cancel",Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(checksum_testing.this, MainActivity.class));
        status = "Failed";
        finish();
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle)
    {
        Log.e("checksum_testing ", "  transaction cancel " );
        Toast.makeText(checksum_testing.this,"Transaction Cancel...",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(checksum_testing.this,CartActivity.class));
//        startActivity(new Intent(checksum_testing.this, MainActivity.class));
        status = "Failed";
        finish();
    }
}