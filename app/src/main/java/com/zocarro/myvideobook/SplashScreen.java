package com.zocarro.myvideobook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.BottomNavigation.BottomNavigationActivity;
import com.zocarro.myvideobook.Registeration.SignInWithEmailActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    TextView txtaspire;
    ImageView imgsplash;
    LottieAnimationView lottieAnimationView;

    Boolean isLogin, isFirstTimeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        txtaspire=findViewById(R.id.txtLabel);
//        lottieAnimationView = findViewById(R.id.lottie);
//        lottieAnimationView.animate().setDuration(700).setStartDelay(700);
        imgsplash=findViewById(R.id.imgsplash);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
        isLogin = sharedPreferences.getBoolean("status",false);
       /* isFirstTimeUser = sharedPreferences.getBoolean("isfirstTime",false);
        Log.d("Firstuser", isFirstTimeUser.toString());*/

     /*   if(!isFirstTimeUser) {*/
        if (isLogin)
        {
            FirstTimeUser();
        }
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent is = new Intent(SplashScreen.this, SignInWithEmailActivity.class);
//                    Intent is = new Intent(SplashScreen.this, BottomNavigationActivity.class);
                    startActivity(is);
                    finish();
                }
            },1500);
            Animation myanim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.mysplashanimation);
            myanim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Animation bounce = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bounce);
                    txtaspire.startAnimation(bounce);
                }
                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }
    private void FirstTimeUser()
    {
        String url = Common.GetWebServiceURL() + "TermFind.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("resfirst", response);
                    final String stream = array.getJSONObject(0).getString("stream");
                    final String program = array.getJSONObject(0).getString("program");
                    final String department = array.getJSONObject(0).getString("department");
                    final String term_id = array.getJSONObject(0).getString("term_id");
                    final String med = array.getJSONObject(0).getString("med");

                    if (stream.equalsIgnoreCase("STREAM NOT SET") || program.equalsIgnoreCase("PROGRAM NOT SET") ||
                            department.equalsIgnoreCase("DEPARTMENT NOT SET") || term_id.equalsIgnoreCase("TERM NOT SET"))
                    {
                        isFirstTimeUser=true;
                    } else
                        {
                        isFirstTimeUser=false;
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stream_id",stream);
                        editor.putString("pr_id", program);
                        editor.putString("br_id", department);
                        editor.putString("term_id", term_id);
                        editor.putString("med", med);
                        editor.commit();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isLogin){
                                // Log.d("Firstuser", isFirstTimeUser.toString());
                                if(isFirstTimeUser)
                                {
                                    Intent go = new Intent(SplashScreen.this, SelectType.class);
                                    startActivity(go);
                                    finish();
                                }
                                else {
//                                    Intent is = new Intent(SplashScreen.this, HomeActivity.class);
                                    Intent is = new Intent(SplashScreen.this, BottomNavigationActivity.class);
//                                    is.putExtra("stream_id", stream);
                                    is.putExtra("pr_id", program);
                                    is.putExtra("br_id",department );
                                    is.putExtra("term_id", term_id);
                                    is.putExtra("med", med);
                                    startActivity(is);
                                    finish();
                                }
                            }
                            else {
                                Intent is = new Intent(SplashScreen.this, SignInWithEmailActivity.class);
                                startActivity(is);
                                finish();
                            }
                        }
                    },1500);
                    Animation myanim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.mysplashanimation);
                    myanim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Animation bounce = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bounce);
                            txtaspire.startAnimation(bounce);
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

//                    imgsplash.startAnimation(myanim);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("u_id", uid);
                Log.d("###", data.toString());

                return data;
            }
        };
        Volley.newRequestQueue(SplashScreen.this).add(request);
    }

}
