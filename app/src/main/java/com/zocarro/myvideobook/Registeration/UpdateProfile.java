package com.zocarro.myvideobook.Registeration;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {

    TextView txtName,txtEmail,txtContact,txtUpdateImg;
    ImageView studentImg;
    ImageView updateNumber;
    Bitmap Fixbitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    HttpURLConnection httpURLConnection;
    URL url;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    boolean flag;
    String ImageName = "image_data";
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    boolean check = true;
    private int GALLERY = 1, CAMERA = 2;
    ProgressDialog progressDialog;
    String s_id, sc_id;
    int flagimgupload;
    Context ctx=this;
    CircleImageView imgStudent;
    String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile2);

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Update Profile");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);*/
        allocateMemory();
        getProfileDetails();
       // Fixbitmap=getDrawable(R.drawable.v_lolgo);
       /* Drawable myDrawable = getResources().getDrawable(R.drawable.v_lolgo);
        Fixbitmap = ((BitmapDrawable) myDrawable).getBitmap();*/



        txtUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                flagimgupload = 1;

            }
        });
        updateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText_update;
                final String[] number = new String[1];
                LayoutInflater inflater = LayoutInflater.from(UpdateProfile.this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateProfile.this);
                View mView = inflater.inflate(R.layout.update_number, null);
                editText_update = mView.findViewById(R.id.edt_update);
                //  editText_update.setText(viewCartModel.getQty());
                mBuilder.setView(mView);
                number[0] = editText_update.getText().toString();
                final AlertDialog mDialog = mBuilder.create();
                Button btn_update = mView.findViewById(R.id.btn_update);
                Button btn_close = mView.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phonenumber = "(0/91)?[6-9][0-9]{9}";
                        String mobile_number = editText_update.getText().toString();
                        if(mobile_number.isEmpty()){
                            editText_update.setError("Contact Number Field Can't be emapty");
                        }
                        else if (!editText_update.getText().toString().trim().matches(phonenumber)){
                            editText_update.setError("Invalid Contact Number");

                        }
                        else {
                            number[0] = editText_update.getText().toString();
                            UpdateCon(number[0]);
                            mDialog.dismiss();
                        }
                    }
                });
                mDialog.show();
            }
        });

/*
        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText_update;
                final String[] add = {new String()};
                LayoutInflater inflater = LayoutInflater.from(UpdateProfile.this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateProfile.this);
                View mView = inflater.inflate(R.layout.update_email_dialog, null);
                editText_update = mView.findViewById(R.id.edt_update);
                editText_update.setHint("Enter the Email");
                editText_update.setInputType(InputType.TYPE_CLASS_TEXT);
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button btn_update = mView.findViewById(R.id.btn_update);
                Button btn_close = mView.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String address = editText_update.getText().toString();
                        if(address.isEmpty()){
                            editText_update.setError("Email Address Field Can't be emapty");
                        }
                         else if (!editText_update.getText().toString().trim().matches(emailPattern)){
                            editText_update.setError("Invalid email Address");

                        }
                        else {
                            add[0] = editText_update.getText().toString();
                            updateEmailAdd(add[0]);
                            mDialog.dismiss();
                        }
                    }
                });
                mDialog.show();

            }
        });
*/

    }

    private void showPictureDialog() {
        flag = true;
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

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    /*final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Fixbitmap = BitmapFactory.decodeStream(imageStream);*/
                    Fixbitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                       //Fixbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        imgStudent.setImageBitmap(Fixbitmap);
                        //UploadImageStudentToServer();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateProfile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {

            //    ShowSelectedImage.setImageBitmap(FixBitmap);
            //  img_update.setVisibility(View.VISIBLE);

                Fixbitmap = (Bitmap) data.getExtras().get("data");
                imgStudent.setImageBitmap(Fixbitmap);
                UploadImageStudentToServer();

        }
    }
    public void UploadImageStudentToServer() {
        int width = Fixbitmap.getWidth();
        int height = Fixbitmap.getHeight();
        Log.v("HHH", String.valueOf(width));
        Log.v("HHH", String.valueOf(height));
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 487, 379, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = null;
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UpdateProfile.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(UpdateProfile.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("st_dp");
                editor.commit();

                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
               ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "src/student/");
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                u_id = mPrefs.getString("u_id", "none");
                HashMapParams.put(ImageName, ConvertImage);


                HashMapParams.put(ImageName, ConvertImage);

                HashMapParams.put("u_id",u_id);
                Log.v("TTTT", "" + ImageName);
                Log.v("TTTT", "" + Common.GetBaseImageURL() + "src/student/");
                Log.v("VVVVV", String.valueOf(HashMapParams));
                Log.v("TTTT", "" + Common.GetBaseImageURL() + "src/student/");
                Log.v("VVVVV", String.valueOf(HashMapParams));
                //     HashMapParams.put(Date,GetdatefromEditText);
/*                cid.setText("");
                sc_id.setText("");
                ac_year.setText("");
                ac_expdate.setText("");
                date.setText("");
                imageName.setText("");*/
                String FinalData = imageProcessClass.ImageHttpRequest("http://videobooks.zocarro.com/videobook/App/ws/update_profile.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }
    public class ImageProcessClass {
        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
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
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReader.readLine()) != null) {
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(UpdateProfile.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void UpdateCon(final String s) {
        Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateContact.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(UpdateProfile.this);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success")) {
                        Toast.makeText(UpdateProfile.this, "Mobile Number updated Succeessfully", Toast.LENGTH_SHORT).show();
                        ctx.startActivity(new Intent(ctx, UpdateProfile.class));
                        ((Activity) ctx).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id",u_id);
                params.put("u_cno",s);
                Log.d("PPP",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(UpdateProfile.this).add(stringRequest);

    }

    private void updateEmailAdd(final String emailAdd){
        Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateEmailAdd.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Common.progressDialogDismiss(UpdateProfile.this);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success")) {
                        Toast.makeText(UpdateProfile.this, "Email updated Succeessfully",
                                Toast.LENGTH_SHORT).show();
                        ctx.startActivity(new Intent(ctx, UpdateProfile.class));
                        ((Activity) ctx).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("u_id",u_id);
                params.put("u_email",emailAdd);
                Log.d("paraaa",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(UpdateProfile.this).add(stringRequest);
    }


    private void getProfileDetails() {
        Common.progressDialogShow(this);
        String Webserviceurl = Common.GetWebServiceURL() + "studentProfile.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        Log.d("!!!@@", u_id);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(UpdateProfile.this);
                    JSONArray array = new JSONArray(response);
                    String error = array.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false)
                    {

                    }
                    else {
                        int total = Integer.parseInt(array.getJSONObject(1).getString("total"));
                        if (total != 0)
                        {
                            for (int i = 2; i < array.length(); i++)
                            {
                                JSONObject current = array.getJSONObject(i);
                               /* {
                                    "t_img": "K.png",
                                        "t_fname": "Shubhnagiiii",
                                        "t_lname": "Shukla",
                                        "t_cont": "9033532883",
                                        "t_email": "kkaushal8511@gmail.com",
                                        "t_study": "be",
                                        "t_spsubject": "Maths",
                                        "t_dob": "12/07/1999"
                                }*/
                                txtName.setText(current.getString("username"));
                                txtEmail.setText(current.getString("u_email"));
                                txtContact.setText(current.getString("u_cno"));
                                String image = current.getString("u_img");
                                if(image.equals("NOT SET"))
                                {

                                }
                                else {
                                    String URL_IMAGE = Common.GetBaseImageURL() + "src/user/" + image;
                                    Glide.with(UpdateProfile.this).load(URL_IMAGE).into(studentImg);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(UpdateProfile.this);
                    Toast.makeText(UpdateProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(UpdateProfile.this);
                Toast.makeText(UpdateProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("u_id",u_id);
                return data;
            }
        };
        Volley.newRequestQueue(UpdateProfile.this).add(request);
    }


    private void allocateMemory() {

        txtName = findViewById(R.id.studentName);
        txtEmail = findViewById(R.id.txtEmail);
        txtContact = findViewById(R.id.txtNumber);
        studentImg = findViewById(R.id.imgStudent);
//        updateEmail = findViewById(R.id.emailUpdate);
        updateNumber = findViewById(R.id.contactUpdate);
        txtUpdateImg = findViewById(R.id.txtImg);
        imgStudent = findViewById(R.id.imgStudent);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Fixbitmap = ((BitmapDrawable) imgStudent.getDrawable()).getBitmap();

    }


    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
