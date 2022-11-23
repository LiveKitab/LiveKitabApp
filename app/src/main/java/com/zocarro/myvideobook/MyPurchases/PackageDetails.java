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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class PackageDetails extends AppCompatActivity {

    RecyclerView packageCourseRecyclerView;
    TextView packageNameTextView, packagePriceTextView,packageDiscountTextView, transactionIdTextView, transactionDateTextView;
    ArrayList<PackageModel> list = new ArrayList<>();
    ImageView packageImageView;
    String packageId, packageName, discount, transactionId, transactionDate, packagePrice, packageImage, university, sub_code,packoldprice;
    Button downloadInvoiceButton;
    int price,amount,dis, discountedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        packageId =getIntent().getStringExtra("pk_id");
        packageName =getIntent().getStringExtra("pk_title");
        discount =getIntent().getStringExtra("pk_dis");
        packagePrice =getIntent().getStringExtra("pk_Price");
        packoldprice=getIntent().getStringExtra("pk_oldPrice");
        packageImage =getIntent().getStringExtra("pk_img");

        allocateMemory();

        getPackages();

        packageNameTextView.setText(packageName);


        packagePriceTextView.setText(packagePrice);
        packageDiscountTextView.setText(discount + " % Discount");

        String IMG_URL = Common.GetBaseImageURL() + "package/" + packageImage;


        Glide.with(this).load(IMG_URL).into(packageImageView);

        downloadInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpdf1();
            }
        });
    }


    private void createpdf1() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String phoneNumber = preferences.getString("u_cno","none");
        String username = preferences.getString("username","none");
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
        canvas.drawText("Transaction ID:- " + transactionId, 40, 220, paint);
        canvas.drawText("Customer Name:- " + username + "               " , 40, 240, paint);
        canvas.drawText("Mobile number:- " + phoneNumber, 40, 260, paint);
        canvas.drawText("Course Name:- " + packageName, 40, 280, paint);
        canvas.drawText("University:- " + university, 40, 300, paint);
        canvas.drawText("Subject code:- " + sub_code, 40, 320, paint);

        canvas.drawText("Date:- " + transactionDate, 40, 340, paint);

        canvas.drawText("", 40, 360, paint);
        canvas.drawText("Courses" + "                                                                                                                             "
                + "          " + "Price" + "          " + "Discount" + "          " + "Amount", 40, 380, paint);
        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, 400, paint);



        canvas.drawText(packageName, 40, y, paint);

        canvas.drawText(String.valueOf(packoldprice) , 490, y, paint);

        canvas.drawText(String.valueOf(discount) + " %", 560, y, paint);

        canvas.drawText(String.valueOf(""+packagePrice) , 630, y, paint);

        canvas.drawText("-------------------------------------------", 530, y+20, paint);

        canvas.drawText("Grand Total" + "             "+ packagePrice , 530, y+30, paint);
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

        String targetPdf = directory_path + "Invoice" + transactionId + ".pdf";
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

                Toast.makeText(PackageDetails.this, "Invoice Downloaded", Toast.LENGTH_LONG).show();

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


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        document.close();
    }


    private void getPackages() {
        Common.progressDialogShow(PackageDetails.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "packageBuyDetails.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(PackageDetails.this);
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);

                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);

                        list.add(new PackageModel(object.getString("pkg_img"),
                                object.getString("c_id"),
                                object.getString("course_id"),
                                object.getString("course_name"),
                                object.getString("course_des"),
                                object.getString("course_bg"),
                                object.getString("uni"),
                                object.getString("pkg_id"),
                                object.getString("pkg_title"),
                                object.getString("price"),
                                object.getString("dis"),
                                object.getString("course_plan_duration"),
                                object.getString("sub_code"),
                                object.getString("credit"),
                                object.getString("rating"),
                                object.getString("enrolled"),
                                object.getString("purchased"),
                                object.getString("transaction_id")));

                        transactionId = object.getString("transaction_id");
                        transactionDate = object.getString("transaction_date");
                        sub_code = object.getString("sub_code");
                        university = object.getString("uni");

                    }

                    transactionDateTextView.setText("Transaction Date : -" + transactionDate);
                    transactionIdTextView.setText("Transaction ID : -" + transactionId);

                    Collections.reverse(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(PackageDetails.this, LinearLayoutManager.VERTICAL, false);
                    packageCourseRecyclerView.setLayoutManager(layoutManager);
                    PackageDetailsAdapter adapter=new PackageDetailsAdapter(PackageDetails.this,list);
                    packageCourseRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(PackageDetails.this);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Common.progressDialogDismiss(PackageDetails.this);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("user_id",uid);
                data.put("pkg_id",packageId);
                Log.d("LLL",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PackageDetails.this).add(sr);
    }

    private void allocateMemory() {
        packageImageView = findViewById(R.id.packageImageView);
        packageCourseRecyclerView = findViewById(R.id.packageCourseRecyclerView);
        packageNameTextView = findViewById(R.id.packageNameTextView);
        packagePriceTextView = findViewById(R.id.packagePriceTextView);
        packageDiscountTextView = findViewById(R.id.packageDiscountTextView);
        transactionIdTextView = findViewById(R.id.transactionIdTextView);
        transactionDateTextView = findViewById(R.id.transactionDateTextView);
        downloadInvoiceButton = findViewById(R.id.downloadInvoiceButton);
    }
}