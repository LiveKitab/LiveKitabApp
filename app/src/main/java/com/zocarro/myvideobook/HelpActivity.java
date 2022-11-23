package com.zocarro.myvideobook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zocarro.myvideobook.Dashboard.DashboardMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HelpActivity extends AppCompatActivity
{
    Toolbar toolbar;
    ProgressDialog progressDialog;
    byte[] byteArray;
    String ConvertImage;
    int RC;
    Button submit;
    private int GALLERY = 1, CAMERA = 2;
    boolean check = true;

    ByteArrayOutputStream byteArrayOutputStream;

    BufferedWriter bufferedWriter;
    boolean flag;
    OutputStream outputStream;
    HttpURLConnection httpURLConnection;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    java.net.URL url;
    EditText subjectedt,messageedt;
    ImageView imgupload,txtdoc;
    String subject,msg,Timeline;
    Bitmap Fixbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Help & Support");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        allocatememory();
        imgupload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showPictureDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UploadImageToServer();
            }
        });
        Fixbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_gray_button);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    public void allocatememory()
    {
        subjectedt = findViewById(R.id.subjectedt);
        messageedt = findViewById(R.id.messageedt);
        imgupload = findViewById(R.id.imgupload);
        submit = findViewById(R.id.btnContinue);
        txtdoc = findViewById(R.id.txtdoc);
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
    private void showPictureDialog() {
        flag = true;
        //Fixbitmap=null;
        android.app.AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera()
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                Uri contentURI = data.getData();
                try
                {
                    Fixbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    txtdoc.setImageBitmap(Fixbitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(HelpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA)
        {
            Fixbitmap = (Bitmap) data.getExtras().get("data");
            txtdoc.setImageBitmap(Fixbitmap);
        }
    }
    public void UploadImageToServer()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int width = Fixbitmap.getWidth();
        int height = Fixbitmap.getHeight();
        Log.v("HHH",String.valueOf(width));
        Log.v("HHH",String.valueOf(height));
        subject= TextUtils.htmlEncode(subjectedt.getText().toString());
        msg= TextUtils.htmlEncode(messageedt.getText().toString());
        Log.d("@@@",subject);
        Log.d("@@@",msg);
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 487, 379, false);
        Log.v("GGG",String.valueOf(b2.getWidth()));
        Log.v("GGG",String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        //ConvertImage=null;
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getApplicationContext(), "Sending Problem","Please Wait",false,false);
            }
            @Override
            protected void onPostExecute(String string1)
            {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(HelpActivity.this,"Added Sucessfully ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HelpActivity.this, DashboardMain.class);
                startActivity(intent);
                finish();
                Log.d("@@@@@",string1);
            }
            @Override
            protected String doInBackground(Void... params)
            {
                HelpActivity.ImageProcessClass imageProcessClass = new HelpActivity.ImageProcessClass();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String username = mPrefs.getString("username", "none");
                String u_id = mPrefs.getString("u_id", "none");
                String u_email = mPrefs.getString("u_email", "none");
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("image_data", ConvertImage);
                HashMapParams.put("user_name", username);
                HashMapParams.put("title",subject);
                HashMapParams.put("u_id", u_id);
                HashMapParams.put("u_email", u_email);
                Log.v("VVVVV", String.valueOf(HashMapParams));
                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"insertsupport.php", HashMapParams);
                return FinalData;
            }

        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }
    public class ImageProcessClass
    {
        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData)
        {
            StringBuilder stringBuilder = new StringBuilder();
            try
            {
                url = new URL(requestURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                RC = httpURLConnection.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK)
                {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(RC2);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilder.toString();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                Toast.makeText(HelpActivity.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();
            }
        }
    }





}
