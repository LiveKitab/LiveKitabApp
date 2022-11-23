package com.zocarro.myvideobook.MyPurchases;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class BillActivity extends AppCompatActivity
{
    ImageView courseImageView;
    Button downloadInvoiceButton, emailInvoiceButton;
    TextView courseNameTextView, subjectCodeTextView, universityNameTextView,timeTextView,coursePriceTextView, originalPriceTextView, discountTextView, transactionIdTextView, dateTextView, paymentModeTextView;
    String username, contactNumber, course_id, transaction_id, course_name, sub_code, course_bg, uni,payment_mode, amount, discount, date, transaction_time;
    int  price;
    int dis;
    int discountedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        course_id = getIntent().getStringExtra("course_id");

        allocateMemory();

        SendRequest();

        downloadInvoiceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createpdf1();
            }
        });
    }

    private void createpdf1()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int y = 420;

        Resources mResources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.v_logo);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(790, 700, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        final Canvas canvas = page.getCanvas();

        final Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 40, 50, null);
        canvas.drawText("Transaction ID:- " + transaction_id, 40, 220, paint);
        canvas.drawText("Customer Name:- " + username + "               " , 40, 240, paint);
        canvas.drawText("Mobile number:- " + contactNumber, 40, 260, paint);
        canvas.drawText("Course Name:- " + course_name, 40, 280, paint);
        canvas.drawText("University:- " + uni, 40, 300, paint);
        canvas.drawText("Subject code:- " + sub_code, 40, 320, paint);

        canvas.drawText("Date:- " + date, 40, 340, paint);

        canvas.drawText("", 40, 360, paint);
        canvas.drawText("Courses" + "                                                                                                                             "
                + "          " + "Price" + "          " + "Discount" + "          " + "Amount", 40, 380, paint);
        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, 400, paint);


        canvas.drawText(course_name, 40, y, paint);

        canvas.drawText(String.valueOf(amount) + " ₹", 490, y, paint);

        canvas.drawText(String.valueOf(dis) + " %", 560, y, paint);

        canvas.drawText(String.valueOf(discountedPrice) + " ₹", 630, y, paint);

        canvas.drawText("-------------------------------------------", 530, y+20, paint);

        canvas.drawText("Grand Total" + "             "+ discountedPrice + " ₹", 530, y+30, paint);
        //canvas.drawText("------------------------------------------------------------------------------------------", 40, 240, paint);
        y += 20;
        y += 20;

        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, y, paint);

//        canvas.drawText("Total:- " + (price + totalcharges), 590, y + 20, paint);
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BillVideoBook/";
        File file = new File(directory_path);

        if (!file.exists()) {
            file.mkdirs();
        }

        String targetPdf = directory_path + "Invoice" + transaction_id + ".pdf";
        File filePath = new File(targetPdf);

        try {

            if (!filePath.exists()) {
                document.writeTo(new FileOutputStream(filePath));
            }
            if (filePath.exists()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(directory_path);
                Log.v("@@@", "@@@ " + uri);
                intent.setDataAndType(uri, "text/pdf");

                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);


                Notification noti = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

                    noti = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Hello " + "Customer ")
                            .setContentText("Your Invoice is downloded !!").setSmallIcon(R.drawable.v_logo).
                                    setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.v_logo))
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .build();

                }
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                assert noti != null;
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, noti);

                Toast.makeText(BillActivity.this, "Invoice Downloaded", Toast.LENGTH_LONG).show();

                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(filePath), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent i = Intent.createChooser(target, "Open File");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();
    }
    private void SendRequest()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        Common.progressDialogShow(BillActivity.this);
        String Webserviceurl = Common.GetWebServiceURL() + "displayBill.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(BillActivity.this);
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        username = object.getString("username");
                        contactNumber = object.getString("u_cno");
                        transaction_id = object.getString("transaction_id");
                        course_name = object.getString("course_name");
                        sub_code = object.getString("sub_code");
                        course_bg = object.getString("course_bg" );
                        uni = object.getString("uni");
                        payment_mode = object.getString("payment_mode");
                        amount = object.getString("amount");
                        discount = object.getString("discount");
                        date = object.getString("date");
                        transaction_time = object.getString("transaction_time");
                    }

                    // original price is amount after discount
                    price = parseInt(amount);
                    dis = parseInt(discount);
                    discountedPrice = price-((price*dis)/100);
                    transactionIdTextView.setText(transaction_id);
                    courseNameTextView.setText(course_name);
                    subjectCodeTextView.setText(sub_code);
                    universityNameTextView.setText(uni);
                    paymentModeTextView.setText(payment_mode);
                    coursePriceTextView.setText(amount + " ₹");
                    discountTextView.setText(discount + " % off");
                    dateTextView.setText(date);
                    timeTextView.setText(transaction_time);
                    originalPriceTextView.setText(String.valueOf(discountedPrice) + " ₹");

                    String IMG_URL = Common.GetBaseImageURL()+"course/" + course_bg;
                    Log.d("IMG", IMG_URL);
                    Glide.with(BillActivity.this).load(IMG_URL).into(courseImageView);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(BillActivity.this);
                    Toast.makeText(BillActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BillActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                Common.progressDialogDismiss(BillActivity.this);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("course_id", course_id);
                data.put("user_id", uid);
                Log.d("^_^", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(BillActivity.this).add(request);
    }



    public void allocateMemory ()
    {
        courseImageView = findViewById(R.id.courseImageView);
        courseNameTextView = findViewById(R.id.courseNameTextView);
        subjectCodeTextView = findViewById(R.id.subjectCodeTextView);
        universityNameTextView = findViewById(R.id.universityNameTextView);
        coursePriceTextView = findViewById(R.id.coursePriceTextView);
        discountTextView = findViewById(R.id.discountTextView);
        originalPriceTextView = findViewById(R.id.originalPriceTextView);
        transactionIdTextView = findViewById(R.id.transactionIdTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        paymentModeTextView = findViewById(R.id.paymentModeTextView);
        downloadInvoiceButton = findViewById(R.id.downloadInvoiceButton);
        //emailInvoiceButton = findViewById(R.id.emailInvoiceButton);
    }
}