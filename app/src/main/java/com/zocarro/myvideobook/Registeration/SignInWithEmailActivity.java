package com.zocarro.myvideobook.Registeration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.BottomNavigation.BottomNavigationActivity;
import com.zocarro.myvideobook.CheckNetwork;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.ForgotPassword;
import com.zocarro.myvideobook.NoInternetActivity;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInWithEmailActivity extends AppCompatActivity
{
    Button loginButton;
    private static final String KEY_STATUS = "loginstatus";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "u_email";
    private static final String KEY_PASSWORD = "u_pwd";
    private static final String KEY_EMPTY = "";
    EditText nameEditText,passwordEditText;
    TextView forgotPasswordTextView;
    String username,password,userEmail,name;
    ImageView closeImageview;
    TextView registerTextView;
    String u_id;
    private Button verifyButton;
    EditText otpEditText;
    String number;
    private static final String TAG = "SignInWithEmailActivity";
    String serverOtp;
    String regid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_email);
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            final boolean loginstatus = preferences.getBoolean("status",false);
            if (loginstatus==true)
            {
//                startActivity(new Intent(SignInWithEmailActivity.this, HomeActivity.class));
                startActivity(new Intent(SignInWithEmailActivity.this, BottomNavigationActivity.class));
                finish();
            }
            nameEditText = findViewById(R.id.nameEditText);
            forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
            passwordEditText = findViewById(R.id.passwordEditText);
            loginButton = findViewById(R.id.loginButton);
            registerTextView = findViewById(R.id.registerTextView);
            forgotPasswordTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity( new Intent(SignInWithEmailActivity.this, ForgotPassword.class));
                }
            });
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            regid = mPrefs.getString("regid", "none");
            Log.d("reg_id",regid);
            loginButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    username = nameEditText.getText().toString();
                    Log.v("&&&&",username);
                    password = passwordEditText.getText().toString();
                    Log.v("***",password);
                    if(validateInputs())
                    {
                        login();
//                        generateOtp();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignInWithEmailActivity.this);
//                        ViewGroup viewGroup = findViewById(android.R.id.content);
//                        View dialogView = LayoutInflater.from(SignInWithEmailActivity.this).inflate(R.layout.layout_otp_dialog, viewGroup, false);
//                        builder.setView(dialogView);
//                        final AlertDialog alertDialog = builder.create();
//                        verifyButton = dialogView.findViewById(R.id.verifyButton);
//                        closeImageview = dialogView.findViewById(R.id.closeImageview);
//                        otpEditText = dialogView.findViewById(R.id.otpEditText);
//                        verifyButton.setOnClickListener(new View.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(View view)
//                            {
//                                String otp = otpEditText.getText().toString();
//                                Log.d(TAG, "onClick: " + otp);
//                                if (otp.equals(serverOtp))
//                                {
//                                    login();
//                                    alertDialog.dismiss();
//                                }
//                                else {
//                                    Toast.makeText(SignInWithEmailActivity.this, "Incorrect Otp", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                        closeImageview.setOnClickListener(new View.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(View v)
//                            {
//                                alertDialog.dismiss();
//                            }
//                        });
//
//                        alertDialog.show();
//                        alertDialog.setCancelable(true);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "no interntet", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignInWithEmailActivity.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }
        registerTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SignInWithEmailActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    private boolean validateInputs()
    {
        if (KEY_EMPTY.equals(username))
        {
            nameEditText.setError("Number cannot be empty");
            nameEditText.requestFocus();
            return false;
        }
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        if (!nameEditText.getText().toString().trim().matches(emailPattern)){
//            nameEditText.setError("Invalid email Address");
//            return false;
//        }
        if (KEY_EMPTY.equals(password))
        {
            passwordEditText.setError("Password cannot be empty");
            passwordEditText.requestFocus();
            return false;
        }
        return true;
    }
    private void login()
    {
        Common.progressDialogShow(SignInWithEmailActivity.this);
        String LOGIN_URL = Common.GetWebServiceURL()+"login.php";
        Log.d("url",LOGIN_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(SignInWithEmailActivity.this, response, Toast.LENGTH_SHORT).show();



                try
                {
                    Common.progressDialogDismiss(SignInWithEmailActivity.this);
                    JSONObject obj = new JSONObject(response);
                    Log.d("loginstatus",""+obj.getInt(KEY_STATUS));
                    if (obj.getInt(KEY_STATUS)==0)
                    {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignInWithEmailActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        u_id=obj.getString("u_id");
                        name = obj.getString("username");
                        userEmail = obj.getString("u_email");
                        String u_cno=obj.getString("u_cno");
                        editor.putString("u_id",u_id);
                        editor.putString("username", name);
                        editor.putString("u_email", userEmail);
                        editor.putString("u_cno",u_cno);
                        String stream = obj.getString("stream");
                        String department = obj.getString("department");
                        String program = obj.getString("program");
                        String term_id = obj.getString("term_id");
                        String med = obj.getString("med");
                        editor.putString("stream_id", stream);
                        editor.putString("br_id", department);
                        editor.putString("pr_id", program);
                        editor.putString("term_id", term_id);
                        editor.putString("med", med);
                        editor.putBoolean("status",true);
                        editor.commit();
                        Toast.makeText(SignInWithEmailActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(SignInWithEmailActivity.this, HomeActivity.class);
                        Intent i=new Intent(SignInWithEmailActivity.this, BottomNavigationActivity.class);
                        i.putExtra("stream_id", stream);
                        i.putExtra("pr_id", department);
                        i.putExtra("br_id", program);
                        i.putExtra("term_id", term_id);
                        i.putExtra("med", med);
                        startActivity(i);
                        finish();

                    }
                    else {
                        Toast.makeText(SignInWithEmailActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SignInWithEmailActivity.this);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SignInWithEmailActivity.this);
                Toast.makeText(SignInWithEmailActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put("reg_id", regid);
                Log.d("aaa",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(SignInWithEmailActivity.this).add(stringRequest);
    }
    private void generateOtp()
    {
        String userRegUrl = Common.GetBaseURL() +"Authentication/generateOtp.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    Log.d(TAG, "onResponse: "+ response);
                    JSONArray array = new JSONArray(response);

                    serverOtp = array.getJSONObject(0).getString("otp");
                    Log.d(TAG, "onResponse: " + serverOtp);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: "+ error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                number = nameEditText.getText().toString().trim();
                data.put("customercontact",number);
                Log.d(TAG, "getParams: "+ data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SignInWithEmailActivity.this).add(sr);
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}

