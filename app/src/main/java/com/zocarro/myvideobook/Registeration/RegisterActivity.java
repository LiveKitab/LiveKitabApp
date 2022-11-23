package com.zocarro.myvideobook.Registeration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.zocarro.myvideobook.Category.Department;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.DashboardMain;
import com.zocarro.myvideobook.Dashboard.HomeActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.SelectType;
import com.zocarro.myvideobook.courses.course_semester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{
     EditText edtName,edtContact,edtEmail,edtPassword,edtState,edtCity;
     MaterialButton btnRegister;
     String email,password,contact,name,state,city;
    private Button verifyButton;
    EditText otpEditText;
    ImageButton closeImageview;
    TextView logintextview;
    String number;
    AlertDialog alertDialog;
    String serverOtp;
    private static final String TAG = "RegisterAc`tivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        allocateMemory();
        logintextview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RegisterActivity.this,SignInWithEmailActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edtTextToString();
                if (validateInput())
                {
//                    userRegistration();
                    generateOtp();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.layout_otp_dialog, viewGroup, false);
                    builder.setView(dialogView);
                    alertDialog = builder.create();
                    alertDialog.setCancelable(true);
                    verifyButton = dialogView.findViewById(R.id.verifyButton);
                    closeImageview = dialogView.findViewById(R.id.closeImageview);
                    otpEditText = dialogView.findViewById(R.id.otpEditText);
                    verifyButton.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String otp = otpEditText.getText().toString();
                            Log.d(TAG, "onClick: " + otp);
                            if (otp.equals(serverOtp))
                            {
                                userRegistration();

                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Incorrect Otp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    closeImageview.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Log.d("fwewef","csc");
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
            }
        });
    }
    private boolean validateInput()
    {
        if (name.isEmpty())
        {
           edtName.setError("Full name cannot be empty");
            return false;
        }
        String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        if (!edtContact.getText().toString().trim().matches(pattern))
        {
            edtContact.setError("Contact number can not be empty");
            return false;
        }
        //language=AIDL
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty())
        {
            edtEmail.setError("Email can't be emapty");
            return false;
        }
        if (!edtEmail.getText().toString().trim().matches(emailPattern))
        {
            edtEmail.setError("Invalid email Address");
            return false;
        }
        if(city.isEmpty())
        {
            edtCity.setError("city can not be emapty");
            return false;
        }
        if (state.isEmpty()){
            edtState.setError("city can not be emapty");
            return false;
        }
        return true;
    }
    public void userRegistration()
    {
        Common.progressDialogShow(RegisterActivity.this);

        String userRegUrl = Common.GetWebServiceURL()+ "userRegistration.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(RegisterActivity.this);
                    JSONArray res = new JSONArray(response);
                    String message = res.getJSONObject(0).getString("message");
                    String user_id = res.getJSONObject(1).getString("user_id");
                    Log.d("registerlog", response);

                    for(int i=2;i<res.length();i++)
                    {
                        JSONObject obj=res.getJSONObject(i);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                       String u_id =  obj.getString("u_id");
                        name = obj.getString("username");
                        String userEmail = obj.getString("u_email");
                        String u_cno=obj.getString("u_cno");
                        editor.putString("u_id",u_id);
                        editor.putString("username",name);
                        editor.putString("u_email", userEmail);
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
                        editor.apply();
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        intent.putExtra("stream_id", stream);
                        intent.putExtra("pr_id", department);
                        intent.putExtra("br_id", program);
                        intent.putExtra("term_id", term_id);
                        intent.putExtra("med", med);
                        startActivity(intent);
                        finish();
                    }
                    if(message.equals("Success") == true)
                    {
                        Intent intent = new Intent(RegisterActivity.this, SelectType.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Common.progressDialogShow(RegisterActivity.this);
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogShow(RegisterActivity.this);
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                contact = edtContact.getText().toString();
                name = edtName.getText().toString();
                state = edtState.getText().toString();
                city = edtCity.getText().toString();
                data.put("username",name );
                data.put("u_conn", contact);
                data.put("u_email", email);
                data.put("u_city", city);
                data.put("u_state", state);
                data.put("u_pswd", password);
                Log.d("parammmm", data.toString());
                return data;


            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(RegisterActivity.this).add(sr);

    }

    private void edtTextToString()
    {
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        contact = edtContact.getText().toString().trim();
        name = edtName.getText().toString().trim();
        state = edtState.getText().toString().trim();
        city = edtCity.getText().toString().trim();
    }

    private void allocateMemory()
    {
            btnRegister = findViewById(R.id.btnRegister);
            edtName = findViewById(R.id.nameEditText);
            edtContact = findViewById(R.id.numberEditText);
            edtEmail = findViewById(R.id.emailEditText);
            edtPassword = findViewById(R.id.passwordEditText);
            logintextview = findViewById(R.id.logintextview);
            edtState = findViewById(R.id.stateEditText);
            edtCity = findViewById(R.id.cityEditText);
    }
    private void generateOtp()
    {
        String userRegUrl = Common.GetBaseURL() +"AuthMain/generateOtp.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                try
                {
                    Log.d(TAG, "onResponse: "+ response);
                    JSONArray array = new JSONArray(response);

                    serverOtp = array.getJSONObject(0).getString("otp");
                    Log.d(TAG, "onResponse: " + serverOtp);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: "+ error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                number = edtContact.getText().toString().trim();
                data.put("customercontact",number);
                Log.d(TAG, "getParams: "+ data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(RegisterActivity.this).add(sr);
    }

}

