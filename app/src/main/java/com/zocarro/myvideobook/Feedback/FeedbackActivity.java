package com.zocarro.myvideobook.Feedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity
{
    Button submitButton;
    EditText feedbackEditText;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Feedback");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);


        submitButton = findViewById(R.id.submitButton);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        ratingBar = findViewById(R.id.ratingBar);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isValidFeedback())
                {
                    postFeedback();
                }
            }
        });
    }

    private boolean isValidFeedback(){
        submitButton = findViewById(R.id.submitButton);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        ratingBar = findViewById(R.id.ratingBar);

        if(feedbackEditText.getText().toString().isEmpty()){
            feedbackEditText.setError("Please enter some feedback");
            feedbackEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void postFeedback() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(FeedbackActivity.this);
        final String u_id=mPrefs.getString("u_id","none");

        Common.progressDialogShow(FeedbackActivity.this);
        String postCommentUrl = Common.GetWebServiceURL() + "postFeedback.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(FeedbackActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postCommentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(FeedbackActivity.this);
                        JSONObject object = new JSONObject(response);
                        String message = object.getString("message");
                        if(message.equals("Feedback added"))
                        {
                            Toast.makeText(FeedbackActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        } else{
                            Toast.makeText(FeedbackActivity.this, "You have already submitted feedback!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    Log.d("@@@", response);
                } catch (JSONException e) {
                    Common.progressDialogDismiss(FeedbackActivity.this);
                    e.printStackTrace();
                    Toast.makeText(FeedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(FeedbackActivity.this);
                error.printStackTrace();
                Toast.makeText(FeedbackActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {

                HashMap<String, String> HashMapParams = new HashMap<>();
                Intent getData = getIntent();
                String sub_id = getData.getStringExtra("sub_id");
                String c_id = getData.getStringExtra("c_id");
                String feedback= TextUtils.htmlEncode(feedbackEditText.getText().toString());
                HashMapParams.put("user_id", u_id);
                HashMapParams.put("sub_id", sub_id);
                HashMapParams.put("review", feedback);
                HashMapParams.put("c_id",c_id);
                HashMapParams.put("rating", String.valueOf(ratingBar.getRating()));
                Log.v("VVVVV", String.valueOf(HashMapParams));
                return HashMapParams;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
