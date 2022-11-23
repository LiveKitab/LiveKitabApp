package com.zocarro.myvideobook.Dashboard;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.BottomNavigation.BottomNavigationActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Change_Profile extends AppCompatActivity implements SingleUploadBroadcastReceiver.Delegate
{
    Button updateImg,updatepf;
    int flagimgupload;
    Context ctx=this;
    boolean flag;

    EditText txtName,txtEmail,txtNumber;
    TextView txtStream,txtProgram,txtBranch,txtTerm,txtMedium,txtUniversity;
    private int GALLERY = 1, CAMERA = 2;

    HttpURLConnection httpURLConnection;
    boolean check = true;
    URL url;
    String ImageName = "image_data";
    byte[] byteArray;
    ProgressDialog progressDialog;
    ByteArrayOutputStream byteArrayOutputStream;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    BufferedReader bufferedReader;
    Toolbar toolbar;
    StringBuilder stringBuilder;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    private Uri filePath;
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();
    ProgressDialog progressBar;


    //for dp
    String encodedImage;
    Bitmap bitmap;
    ImageView studentImg;
    ImageView updateDp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__profile);
        requestStoragePermission();
        allocateMemory();
        getProfileDetails();

        progressBar=new ProgressDialog(Change_Profile.this);
        updateImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showFileChooser();
            }
        });
        txtNumber.setEnabled(false);
        updatepf.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateprofile();
            }
        });
//        contactUpdate.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                final EditText editText_update;
//                final String[] number = new String[1];
//                LayoutInflater inflater = LayoutInflater.from(Change_Profile.this);
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Change_Profile.this);
//                View mView = inflater.inflate(R.layout.update_number, null);
//                editText_update = mView.findViewById(R.id.edt_update);
//                //  editText_update.setText(viewCartModel.getQty());
//                mBuilder.setView(mView);
//                number[0] = editText_update.getText().toString();
//                final AlertDialog mDialog = mBuilder.create();
//                Button btn_update = mView.findViewById(R.id.btn_update);
//                Button btn_close = mView.findViewById(R.id.btn_close);
//                btn_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        mDialog.dismiss();
//                    }
//                });
//                btn_update.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        String phonenumber = "(0/91)?[6-9][0-9]{9}";
//                        String mobile_number = editText_update.getText().toString();
//                        if(mobile_number.isEmpty()){
//                            editText_update.setError("Contact Number Field Can't be emapty");
//                        }
//                        else if (!editText_update.getText().toString().trim().matches(phonenumber))
//                        {
//                            editText_update.setError("Invalid Contact Number");
//                        }
//                        else {
//                            number[0] = editText_update.getText().toString();
//                            UpdateCon(number[0]);
//                            mDialog.dismiss();
//                        }
//                    }
//                });
//                mDialog.show();
//            }
//        });
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
    //    public void uploadMultipart()
//    {
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String u_id = mPrefs.getString("u_id", "none");
//        //getting name for the image
////        String name = editText.getText().toString().trim();
//        //getting the actual path of the image
//        String path = getPath(filePath);
//
//        //Uploading code
//        progressBar = ProgressDialog.show(this, "Image Updating",
//                "Please wait...", true,false);
//        try
//        {
//            String uploadId = UUID.randomUUID().toString();
//            uploadReceiver.setDelegate(this);
//            uploadReceiver.setUploadID(uploadId);
//
//            //Creating a multi part request
//            new MultipartUploadRequest(Change_Profile.this, uploadId, ImgUrlClass.UPLOAD_URL)
//                    .addFileToUpload(path, "image") //Adding file
//                    .addParameter("name", u_id) //Adding text parameter to the request
//                    .setMaxRetries(2)
//                    .startUpload(); //Starting the upload
//        }
//        catch (Exception exc)
//        {
//            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    protected void onResume()
    {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        uploadReceiver.unregister(this);
    }
    @Override
    public void onProgress(int progress)
    {
        //your implementation
        progressBar.show();

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes)
    {
        //your implementation
    }

    @Override
    public void onError(Exception exception)
    {
        //your implementation
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody)
    {
        //your implementation
        progressBar.dismiss();
        //Toast.makeText(Change_Profile.this, "Completed", Toast.LENGTH_LONG).show();
        getProfileDetails();
    }

    @Override
    public void onCancelled()
    {
        //your implementation
    }

    private void showFileChooser()
    {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent , "Browse Image" ),1);
    }

    //image receive
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri filepath = data.getData();
            try {


                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                encodeBitMapImage(bitmap);

            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //encode in base64
    private void encodeBitMapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , byteArrayOutputStream);
        studentImg.setImageBitmap(bitmap);
        byte [] bytesofimage = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(bytesofimage , Base64.DEFAULT);

    }

    //handling the image chooser activity result
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
//        {
//            filePath = data.getData();
//            try
//            {
//                //Aa bitmap ma final image aave che!!!!!!!!!!!!!!!!!!!!!
//
////
////                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
////                studentImg.setImageBitmap(bitmap);
////                uploadMultipart();
//
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
    //method to get the file path from uri
//    public String getPath(Uri uri)
//    {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//
//        cursor = getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }

    //    private void UpdateCon(final String s)
//    {
//        Common.progressDialogShow(this);
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String u_id = mPrefs.getString("u_id", "none");
//        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateContact.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Common.progressDialogDismiss(Change_Profile.this);
//                    JSONObject object = new JSONObject(response);
//                    if (object.getString("message").equals("success"))
//                    {
//                        Toast.makeText(Change_Profile.this, "Mobile Number updated successfully", Toast.LENGTH_SHORT).show();
//                        ctx.startActivity(new Intent(ctx, Update_profile.class));
//                        ((Activity) ctx).finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("u_id",u_id);
//                params.put("u_cno",s);
//                Log.d("PPP",params.toString());
//                return params;
//            }
//        };
//        Volley.newRequestQueue(Change_Profile.this).add(stringRequest);
//
//    }
    private void allocateMemory()
    {
        updateImg = findViewById(R.id.updateStudentImg);
        txtStream = findViewById(R.id.txtStream);
        txtBranch = findViewById(R.id.txtBranch);
        txtProgram = findViewById(R.id.txtProgram);
        txtTerm = findViewById(R.id.txtTerm);
        txtUniversity = findViewById(R.id.txtUniversity);
        txtMedium = findViewById(R.id.txtMedium);
        txtName=findViewById(R.id.studentName);
        toolbar = findViewById(R.id.toolbar);
        txtEmail = findViewById(R.id.txtEmail);
        txtNumber = findViewById(R.id.txtNumber);
        studentImg = findViewById(R.id.imgStudent);
        updatepf = findViewById(R.id.updatepf);
    }
    private void getProfileDetails()
    {
        Common.progressDialogShow(this);
        String Webserviceurl = Common.GetWebServiceURL() + "studentProfile.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        Log.d("!!!@@", u_id);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("ksvbhsd",response);
                try
                {
                    Common.progressDialogDismiss(Change_Profile.this);
                    JSONArray array = new JSONArray(response);

                    String error = array.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false)
                    {

                    } else
                    {
                        int total = Integer.parseInt(array.getJSONObject(1).getString("total"));
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++)
                            {
                                JSONObject current = array.getJSONObject(i);
                                txtName.setText(current.getString("username"));
                                txtEmail.setText(current.getString("u_email"));
                                txtNumber.setText(current.getString("u_cno"));
                                String image = current.getString("u_img");
                                txtTerm.setText(current.getString("term_name"));
                                txtStream.setText(current.getString("stream_name"));
                                txtProgram.setText(current.getString("program_name"));
                                txtBranch.setText(current.getString("b_name"));
                                txtMedium.setText(current.getString("med"));
                                txtUniversity.setText(current.getString("uni_name"));
                                if(image.equals("NOT SET"))
                                {

                                }
                                else {
                                    String URL_IMAGE = Common.GetBaseImageURL() + "src/user/" + image;
                                    Glide.with(Change_Profile.this).load(URL_IMAGE).into(studentImg);
                                }
                            }
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(Change_Profile.this);
                    Toast.makeText(Change_Profile.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(Change_Profile.this);
                Toast.makeText(Change_Profile.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("u_id",u_id);
                return data;
            }
        };
        Volley.newRequestQueue(Change_Profile.this).add(request);
    }
    private void updateprofile()
    {
        Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateprofile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(Change_Profile.this);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success"))
                    {
                        Toast.makeText(Change_Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        ctx.startActivity(new Intent(ctx, BottomNavigationActivity.class));

                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("u_id",u_id);
                params.put("user_name",txtName.getText().toString());
                params.put("user_mail",txtEmail.getText().toString());
                params.put("encodedImage",encodedImage);
                Log.d("PPP",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(Change_Profile.this).add(stringRequest);

    }


    //    public class ImageProcessClass
//    {
//        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData)
//        {
//            StringBuilder stringBuilder = new StringBuilder();
//            try {
//                url = new URL(requestURL);
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setReadTimeout(20000);
//                httpURLConnection.setConnectTimeout(20000);
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoOutput(true);
//                outputStream = httpURLConnection.getOutputStream();
//                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                bufferedWriter.write(bufferedWriterDataFN(PData));
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                RC = httpURLConnection.getResponseCode();
//                if (RC == HttpsURLConnection.HTTP_OK) {
//                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                    stringBuilder = new StringBuilder();
//                    String RC2;
//                    while ((RC2 = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(RC2);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return stringBuilder.toString();
//        }
//
//        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException
//        {
//            stringBuilder = new StringBuilder();
//            for (Map.Entry<String, String> KEY : HashMapParams.entrySet())
//            {
//                if (check)
//                    check = false;
//                else
//                    stringBuilder.append("&");
//                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
//                stringBuilder.append("=");
//                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
//            }
//            return stringBuilder.toString();
//        }
//    }
    private void requestStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE)
        {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            }
            else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
