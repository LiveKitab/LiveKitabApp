package com.zocarro.myvideobook.VideoCourse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommentBottomSheetDialog extends BottomSheetDialogFragment {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<CommentModel> list = new ArrayList<>();
    EditText commentEditText;
    ImageButton addCommentImageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_comment_sheet, container, false);
        context = v.getContext();

        recyclerView = v.findViewById(R.id.commentRecyclerView);
        commentEditText = v.findViewById(R.id.commentEditText);
        addCommentImageButton = v.findViewById(R.id.addCommentImageButton);

        addCommentImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.d("DDD",list.toString());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getComment()
    {
        Common.progressDialogShow(context);
        String url = Common.GetWebServiceURL()+"videoComment.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String uid = mPrefs.getString("u_id", "none");

        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONArray res = new JSONArray(response);
                    Log.d("!!!", response);
                    list.clear();
                    for (int i=0;i<res.length();i++) {
                        JSONObject object=res.getJSONObject(i);
                           list.add(new CommentModel(object.getString("username"),
                                object.getString("comment"),object.getString("date"),object.getString("id")
                                   ,object.getString("status"),object.getString("comment_mentor"),object.getString("c_name"),object.getString("c_lname")));
                    }
                    Collections.reverse(list);
                    CommentAdapter commentAdapter = new CommentAdapter(context, list);
                    recyclerView.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(context);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(context);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("v_id",ViewCourseVideo.video_id);
                Log.d("###", data.toString());

                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    private void postComment ()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String u_id=mPrefs.getString("u_id","none");
        String postCommentUrl = Common.GetWebServiceURL() + "postComment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postCommentUrl, response ->
        {
            try
            {
                Common.progressDialogDismiss(context);
                JSONArray array= new JSONArray(response);
                for (int i=0;i<array.length();i++)
                {
                    JSONObject object=array.getJSONObject(i);
                    String message = object.getString("message");
                    Toast.makeText(context  , message, Toast.LENGTH_LONG).show();
                    getComment();
                    commentEditText.setText("");
                    Log.d("@@@", response);
                }
            }
            catch (JSONException e)
            {
                Common.progressDialogDismiss(context);
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                {
                    Log.d("@@", Objects.requireNonNull(e.getMessage()));
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(context);
                error.printStackTrace();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                String comment =  TextUtils.htmlEncode(commentEditText.getText().toString().trim());
                HashMap<String, String> HashMapParams = new HashMap<>();
                HashMapParams.put("user_id", u_id);
                HashMapParams.put("v_id", ViewCourseVideo.video_id);
                HashMapParams.put("comment", comment);

                Log.v("VVVVV", String.valueOf(HashMapParams));

                return HashMapParams;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(stringRequest);
        int socketTimeout = 30000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        getComment();
    }
}