package com.zocarro.myvideobook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.zocarro.myvideobook.Registeration.SignInWithEmailActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity
{
    Button resetPasswordButton, getOtpButton;
    EditText contactEditText;
    EditText confirmPasswordEditText, passwordEditText, otpEditText;
    TextInputLayout textInputPassword, textInputConfirmPassword, textInputEmail, textInputOtp;
    String OTP, newPassword;
    private static final String TAG = "ForgotPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        contactEditText = findViewById(R.id.contactEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        textInputEmail = findViewById(R.id.textInputEmail);
        getOtpButton = findViewById(R.id.getOtpButton);
        textInputOtp = findViewById(R.id.textInputOtp);
        otpEditText = findViewById(R.id.otpEditText);

        resetPasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newPassword = passwordEditText.getText().toString().trim();
                if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()))
                {
                    if (OTP.equals(otpEditText.getText().toString()))
                    {
                        ResetPassword(contactEditText.getText().toString().trim(), newPassword);
                    }
                    else{
                        otpEditText.setError("Account not found");
                    }
                }
                else {
                    passwordEditText.setError("Password and confirm password doesn't match");
                }
            }
        });

        getOtpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GetOtp(contactEditText.getText().toString().trim());
            }
        });
    }
    public void GetOtp(final String contactEmail)
    {
        String FORGOT_URL = Common.GetBaseURL() + "Authentication/forgot_password.php";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, FORGOT_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                pDialog.dismiss();
                Log.d(TAG, "onResponse: " + response);
                try
                {
                    JSONArray array = new JSONArray(response);
                    OTP = array.getJSONObject(0).getString("otp");
                    final String message = array.getJSONObject(1).getString("message");
                    if(message.equals("success"))
                    {
                        textInputConfirmPassword.setVisibility(View.VISIBLE);
                        textInputPassword.setVisibility(View.VISIBLE);
                        textInputOtp.setVisibility(View.VISIBLE);
                        contactEditText.setEnabled(false);
                        getOtpButton.setVisibility(View.GONE);
                        resetPasswordButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(ForgotPassword.this, "Enter Valid Input", Toast.LENGTH_SHORT).show();
                        //contactEditText.setEnabled(true);

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: " + error);
                pDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("contact", contactEmail);
                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };
        // Adding request to request queue
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ForgotPassword.this).add(request);
    }
    public void ResetPassword(final String contactEmail, final String newPassword)
    {
        String FORGOT_URL = Common.GetBaseURL() + "Authentication/reset_password.php";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, FORGOT_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                pDialog.dismiss();
                Log.d(TAG, "onResponse: " + response);
                try
                {
                    JSONArray array = new JSONArray(response);
                    final String message = array.getJSONObject(0).getString("message");
                    if (message.equals("success"))
                    {
                        textInputConfirmPassword.setVisibility(View.VISIBLE);
                        textInputPassword.setVisibility(View.VISIBLE);
                        startActivity(new Intent(ForgotPassword.this, SignInWithEmailActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: " + error);
                pDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("contact", contactEmail);
                params.put("new_password", newPassword);
                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };
        // Adding request to request queue
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ForgotPassword.this).add(request);
    }
}