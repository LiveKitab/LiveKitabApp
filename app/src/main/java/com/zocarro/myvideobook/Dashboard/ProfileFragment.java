package com.zocarro.myvideobook.Dashboard;

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
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.SelectType;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment
{

    private TextView txtName,txtEmail,txtContact,txtUpdateImg,txtStream,txtProgram,txtBranch,txtTerm;
    private ImageView studentImg,settingImg;
    private ImageView updateNumber;
    private Bitmap Fixbitmap, Fixbitmap1, Fixbitmap2;
    private ByteArrayOutputStream byteArrayOutputStream, byteArrayOutputStream1, byteArrayOutputStream2;
    private byte[] byteArray;
    private String ConvertImage, Convertfname, Convertmname;
    private HttpURLConnection httpURLConnection;
    private URL url;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private int RC;
    private boolean flag;
    private String ImageName = "image_data";
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private boolean check = true;
    private int GALLERY = 1, CAMERA = 2;
    private ProgressDialog progressDialog;
    private String u_id;
    private int flagimgupload;
    private CircleImageView imgStudent;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_update_profile2, container, false);
        txtName = view.findViewById(R.id.studentName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtContact = view.findViewById(R.id.txtNumber);
        studentImg = view.findViewById(R.id.imgStudent);
//        updateEmail = view.findViewById(R.id.emailUpdate);
        updateNumber = view.findViewById(R.id.contactUpdate);
        txtUpdateImg = view.findViewById(R.id.txtImg);
        imgStudent = view.findViewById(R.id.imgStudent);
        txtStream = view.findViewById(R.id.txtStream);
        txtBranch = view.findViewById(R.id.txtBranch);
        txtProgram = view.findViewById(R.id.txtProgram);
        txtTerm = view.findViewById(R.id.txtTerm);
        settingImg = view.findViewById(R.id.settingStream);

        Fixbitmap = ((BitmapDrawable) imgStudent.getDrawable()).getBitmap();
        byteArrayOutputStream = new ByteArrayOutputStream();

        getProfileDetails();


        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectType.class);
                startActivity(intent);
//                finish();
            }
        });
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
                LayoutInflater inflater = LayoutInflater.from(getContext());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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
//                            UpdateCon(number[0]);
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
                LayoutInflater inflater = LayoutInflater.from(getContext());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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

        return view;
    }

//    private void UpdateCon(final String s) {
//        Common.progressDialogShow(getContext());
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        final String u_id = mPrefs.getString("u_id", "none");
//        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateContact.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Common.progressDialogDismiss(getContext());
//                    JSONObject object = new JSONObject(response);
//                    if (object.getString("message").equals("success")) {
//                        Toast.makeText(getContext(), "Mobile Number updated Succeessfully", Toast.LENGTH_SHORT).show();
//                        getContext().startActivity(new Intent(getContext(), Update_profile.class));
//                        ((Activity) getContext()).finish();
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
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

//    private void updateEmailAdd(final String emailAdd){
//        Common.progressDialogShow(getContext());
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        final String u_id = mPrefs.getString("u_id", "none");
//        String UPDATE_CON2 = Common.GetWebServiceURL() + "updateEmailAdd.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_CON2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    Common.progressDialogDismiss(getContext());
//                    JSONObject object = new JSONObject(response);
//                    if (object.getString("message").equals("success")) {
//                        Toast.makeText(getContext(), "Email updated Succeessfully",
//                                Toast.LENGTH_SHORT).show();
//                        getContext().startActivity(new Intent(getContext(), Update_profile.class));
//                        ((Activity) getContext()).finish();
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
//                params.put("u_email",emailAdd);
//                Log.d("paraaa",params.toString());
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//    }

    
    
    private void showPictureDialog()
    {
        flag = true;
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    if (flagimgupload == 1) {
                        Fixbitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                        UploadImageStudentToServer();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {


            if (flagimgupload == 1) {
                Fixbitmap = (Bitmap) data.getExtras().get("data");
                UploadImageStudentToServer();
            }
        }
    }

    public void UploadImageStudentToServer()
    {
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
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(), "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(getContext(), "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                getProfileDetails();



                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                u_id = mPrefs.getString("u_id", "none");

                HashMapParams.put(ImageName, ConvertImage);

                HashMapParams.put("u_id",u_id);
                Log.v("TTTT", "" + ImageName);
                Log.v("TTTT", "" + Common.GetBaseImageURL() + "src/student/");
                Log.v("VVVVV", String.valueOf(HashMapParams));

                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"update_profile.php", HashMapParams);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }
            else {
                Toast.makeText(getContext(), "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
           Log.d("RRR","error");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getProfileDetails()
    {
        Common.progressDialogShow(getContext());
        String Webserviceurl = Common.GetWebServiceURL() + "studentProfile.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String u_id = mPrefs.getString("u_id", "none");
        Log.d("!!!@@", u_id);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(getContext());
                    JSONArray array = new JSONArray(response);
                    String error = array.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {

                    } else {
                        int total = Integer.parseInt(array.getJSONObject(1).getString("total"));
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
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
                                txtTerm.setText(current.getString("term_name"));
                                txtStream.setText(current.getString("stream_name"));
                                txtProgram.setText(current.getString("program_name"));
                                txtBranch.setText(current.getString("b_name"));
                                String image = current.getString("u_img");
                                if(image.equals("NOT SET"))
                                {
                                }
                                else{
                                    String URL_IMAGE = Common.GetBaseImageURL() + "user/" + image;
                                    Glide.with(getContext()).load(URL_IMAGE).into(studentImg);
                                }
                            }
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(getContext());
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(getContext());
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("u_id",u_id);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }
}
