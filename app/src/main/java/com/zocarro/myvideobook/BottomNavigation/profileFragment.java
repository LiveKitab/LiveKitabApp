package com.zocarro.myvideobook.BottomNavigation;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Activity.UpdatePassword;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.Change_Profile;
import com.zocarro.myvideobook.Feedback.UserCoursesList;
import com.zocarro.myvideobook.MyPurchases.MyPurchasesActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.Registeration.SignInWithEmailActivity;
import com.zocarro.myvideobook.SelectType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class profileFragment extends Fragment {


    RelativeLayout resetpassword,changeclass , printer;
    LinearLayout lnr3,lnr2,lnr4,lnr12,lnr5 ,lnfeeddback;
    TextView facebooktxt , txtUserName;
    String delBoy_mobile_number = "9099535481";

    ConstraintLayout profile;
    TextView txtName , txtBranch ;
    ImageView studentImg;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        getProfileDetails();

        txtName = view.findViewById(R.id.txtUserName);
        txtBranch = view.findViewById(R.id.userselectedbranch);
        studentImg = view.findViewById(R.id.imageUSER);
        profile = view.findViewById(R.id.profile);



        profile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), Change_Profile.class);
                 startActivity(intent);
             }
         });




        lnr3 = view.findViewById(R.id.lnr3);
        lnr2 = view.findViewById(R.id.lnr2);
        lnr4 = view.findViewById(R.id.lnr4);
        resetpassword = view.findViewById(R.id.resetpassword);
        printer = view.findViewById(R.id.invoice);
        changeclass = view.findViewById(R.id.changeclass);
        facebooktxt = view.findViewById(R.id.facebooktxt);
        lnr12 = view.findViewById(R.id.lnr12);
        lnr5 = view.findViewById(R.id.lnr5);
        lnfeeddback = view.findViewById(R.id.lnfeeddback);



        resetpassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), UpdatePassword.class);
                startActivity(intent);
            }
        });

        changeclass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), SelectType.class);
                startActivity(intent);
            }
        });

        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPurchasesActivity.class);
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
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
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

        lnfeeddback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(getContext() , UserCoursesList.class));

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
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                LogoutServer();
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
                                Intent intent = new Intent(getContext(), SignInWithEmailActivity.class);
                                startActivity(intent);
                                
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


        return view;
    }


    private void LogoutServer()
    {
        String url = Common.GetWebServiceURL() + "logout.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
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
                        Intent intent = new Intent(getContext(), SignInWithEmailActivity.class);
                        startActivity(intent);
                        
                    }
                    else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
                Log.d("ksvbhsd",response);
                try
                {
                    Common.progressDialogDismiss(getContext());
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
                                txtBranch.setText(current.getString("b_name") +" "+ "(" +current.getString("term_name") + ")");
                                String image = current.getString("u_img");


                                if(image.equals("NOT SET"))
                                {

                                }

                                else {
                                    String URL_IMAGE = Common.GetBaseImageURL() + "src/user/" + image;
                                    Glide.with(getContext()).load(URL_IMAGE).into(studentImg);
                                }


                            }
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(getContext());
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(getContext());
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("u_id",u_id);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

}