package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.HomeActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.Registeration.SignInWithEmailActivity;
import com.zocarro.myvideobook.SelectType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SettingActivity extends AppCompatActivity
{
    Toolbar toolbar;
    RelativeLayout resetpassword;
    LinearLayout lnr3,lnr2,lnr4,lnr12,lnr5,changeclass;
    TextView facebooktxt;
    String delBoy_mobile_number = "9099535481";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.toolbar);
        changeclass = findViewById(R.id.changeclass);
        allocatedmemory();
//        toolbar.setTitle("Settings");
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        changeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, SelectType.class));
            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingActivity.this,UpdatePassword.class);
                startActivity(intent);
            }
        });
        lnr3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                getOpenFacebookIntent(getApplicationContext());
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/426253597411506"));
//                    startActivity(intent);
//                } catch(Exception e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/livekitabapp")));
//                }
//                Toast.makeText(SettingActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                try {
//                    getApplicationContext().getPackageManager()
//                            .getPackageInfo("com.facebook.katana", 0);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/livekitabapp"));
//                    startActivity(intent);
//                } catch(Exception e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/livekitabapp")));
//                }
                Uri uri = Uri.parse("https://www.facebook.com/livekitabapp");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/livekitabapp")));
                }
            }
        });
        lnr2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://www.instagram.com/livekitabapp/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try
                {
                    startActivity(likeIng);
                }

                catch (ActivityNotFoundException e)
                {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/livekitabapp/")));
                }
            }
        });
        lnr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+Uri.encode(delBoy_mobile_number.trim())));
                    Log.d("TAG", "onClick:Number" + delBoy_mobile_number);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
lnr12.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+"livekitabapp"));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+"livekitabapp"));
            startActivity(intent);
        }
    }
});
        lnr5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                LogoutServer();
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("u_id");
                                editor.remove("status");
                                editor.remove("stream_id");
                                editor.remove("br_id");
                                editor.remove("pr_id");
                                editor.remove("term_id");
                                editor.remove("med");
                                editor.putBoolean("status",false);
                                //editor.apply();
                                editor.commit();
                                Intent intent = new Intent(SettingActivity.this,SignInWithEmailActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
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
    private void allocatedmemory()
    {
        lnr3 = findViewById(R.id.lnr3);
        lnr2 = findViewById(R.id.lnr2);
        lnr4 = findViewById(R.id.lnr4);
        resetpassword = findViewById(R.id.resetpassword);
        facebooktxt = findViewById(R.id.facebooktxt);
        lnr12 = findViewById(R.id.lnr12);
        lnr5 = findViewById(R.id.lnr5);
    }
    private void LogoutServer()
    {
        String url = Common.GetWebServiceURL() + "logout.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("TAG", "onResponse: " + response);
                try
                {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if(message.equals("success"))
                    {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(SettingActivity.this, SignInWithEmailActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                Log.d("user_id1234",uid);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }

//    public static Intent getOpenFacebookIntent(Context context) {
//
//        try {
//            context.getPackageManager()
//                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.facebook.com/livekitabapp")); //Trys to make intent with FB's URI
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.facebook.com/livekitabapp")); //catches and opens a url to the desired page
//        }
//    }
}