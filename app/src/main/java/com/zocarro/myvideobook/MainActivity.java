package com.zocarro.myvideobook;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.DashboardMain;
import com.zocarro.myvideobook.Registeration.RegisterActivity;
import com.zocarro.myvideobook.Registeration.SignInWithEmailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button signInButton;
    TextView registerTextView;
    SignInButton googleSigin;
    TextView termsTextView;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;

    private String personName, personEmail, personId, personCity, personState;
    private Uri personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final boolean loginstatus = mPrefs.getBoolean("status", false);
        if (loginstatus == true) {
            loadDashboard();
        }

        signInButton = findViewById(R.id.signinButton);
        registerTextView = findViewById(R.id.registerTextView);
        termsTextView = findViewById(R.id.termsTextView);
        googleSigin = (SignInButton) findViewById(R.id.googlesigin);






        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignInWithEmailActivity.class);
                startActivity(i);
                finish();
            }
        });

        termsTextView.setOnClickListener(view -> {
            Common.progressDialogShow(MainActivity.this);
            String url = Common.GetWebServiceURL() + "terms_and_condition.php";
            StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Common.progressDialogDismiss(MainActivity.this);
                    try {
                        JSONArray array = new JSONArray(response);
                        String tnc = array.getJSONObject(0).getString("terms_and_conditions");

                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                        View mView = inflater.inflate(R.layout.terms_of_service_diaglog, null);
                        TextView txtdesc = mView.findViewById(R.id.descrText);
                        txtdesc.setText(tnc);
                        mBuilder.setView(mView);
                        final AlertDialog mDialog = mBuilder.create();
                        Button btn_close=mView.findViewById(R.id.exitButton);
                        btn_close.setOnClickListener(v -> mDialog.dismiss());
                        mDialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Common.progressDialogDismiss(MainActivity.this);
                    }
                }
            }, volleyError -> {
                volleyError.printStackTrace();
                Common.progressDialogDismiss(MainActivity.this);
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    return data;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
            AppController.getInstance().addToRequestQueue(sr);
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        googleSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
//                finish();
            }
        });
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardMain.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                personName = account.getDisplayName();
                personEmail = account.getEmail();
                personId = account.getId();
              /*  Log.d("DDD", number);
                Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();*/
            }
            userRegistration();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AAA", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, ""+e.getStatusCode(), Toast.LENGTH_LONG).show();
            //updateUI(null);
        }
    }

    public void userRegistration() {
        Common.progressDialogShow(MainActivity.this);

        String userRegUrl = Common.GetWebServiceURL()+ "signInWithGoogle.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(MainActivity.this);

                    Log.d("@@@", response);

                    JSONObject object = new JSONObject(response);
                    String message=object.getString("message");

                    if (message.equals("Success")) {
                        String u_id=object.getString("u_id");
                        String username=object.getString("username");
                        String u_email=object.getString("u_email");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("u_id",u_id);
                        editor.putString("username", username);
                        editor.putString("u_email", u_email);
                        editor.putBoolean("status",true);
                        editor.commit();
                        Toast.makeText(MainActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,
                                DashboardMain.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(message.equals("Already Register"))
                    {

                        String u_id=object.getString("u_id");
                        String username=object.getString("username");
                        String u_email=object.getString("u_email");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("u_id",u_id);
                        editor.putString("username", username);
                        editor.putString("u_email", u_email);

                        editor.putBoolean("status",true);
                        editor.commit();
                        Toast.makeText(MainActivity.this, u_id,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,
                                DashboardMain.class);
                        intent.putExtra("u_id", u_id);
                        startActivity(intent);
                        finish();
                    }






                } catch (Exception e) {
                    e.printStackTrace();
                    Common.progressDialogShow(MainActivity.this);
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogShow(MainActivity.this);
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("username",personName );
                data.put("u_conn", "Not Set");
                data.put("u_email", personEmail);
                data.put("u_city", "Not Set");
                data.put("u_state", "Not Set");
                data.put("u_pswd", personId);

                Log.d("parammmm", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(MainActivity.this).add(sr);

    }

}
