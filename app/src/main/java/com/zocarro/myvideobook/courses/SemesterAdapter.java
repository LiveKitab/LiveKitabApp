package com.zocarro.myvideobook.courses;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.ChipGroup;
import com.zocarro.myvideobook.Category.CategoryActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.courseCategories;
import com.zocarro.myvideobook.Dashboard.courseCategoriesAdapter;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemesterAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private ArrayList<SemesterModel> semesterModelArrayList;
    static String term_id;
    int row_index=0;


    public SemesterAdapter(Context ctx, ArrayList<SemesterModel> semesterModelArrayList) {
        this.ctx = ctx;
        this.semesterModelArrayList = semesterModelArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_semester,null);
        SemesterHolder container = new SemesterHolder(myview);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final SemesterModel semesterModel = semesterModelArrayList.get(position);
        final SemesterHolder container =(SemesterHolder) holder;


        container.semester.setText(semesterModel.getTerm_name());
        container.semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //row_index=position;
                selectCurrItem(position);
                term_id=semesterModel.getTerm_id();
                course_semester.term=term_id;
                getCoursesSemester();
            }
        });

    }
    private void selectCurrItem(int position)
    {
        int size = semesterModelArrayList.size();
        for(int i = 0; i<size; i++)
        {
            if(i==position) {
                semesterModelArrayList.get(i).setChecked(false);
            }
            else {
                semesterModelArrayList.get(i).setChecked(true);
            }
        }
    }
    private void getCoursesSemester() {
        Common.progressDialogShow(ctx);
        String Webserviceurl=Common.GetWebServiceURL()+"course_semester_wise.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    Log.d("response", response);
                    JSONArray array=new JSONArray(response);
                    course_semester.subjectSemesterArrayList.clear();
                    for(int i=0;i<array.length();i++) {
                        JSONObject object=array.getJSONObject(i);
                        course_semester.subjectSemesterArrayList.add(new SubjectSemester(object.getString("c_id"),
                                object.getString("course_id"),object.getString("course_name"), object.getString("course_bg"),
                                object.getString("rating"), object.getString("enrolled")));
                    }
                    CourseAdapter adapter=new CourseAdapter(ctx, course_semester.subjectSemesterArrayList);
                    course_semester.recsubject.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(ctx);
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("stream_id", "STREAM2");
                data.put("pr_id", "PROGRAM3");
                data.put("b_id", "BID1");
                String term_id=course_semester.term;
                data.put("term_id",term_id);
                Log.d("aaa", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);
    }

    @Override
    public int getItemCount() {
        return semesterModelArrayList.size();
    }
    class SemesterHolder extends RecyclerView.ViewHolder{

        TextView semester;
        ChipGroup chipGroup;
        public SemesterHolder(@NonNull View itemView) {
            super(itemView);

            semester = itemView.findViewById(R.id.semester);
            chipGroup = itemView.findViewById(R.id.chip_group);
        }
    }
}
