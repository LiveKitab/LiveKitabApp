package com.zocarro.myvideobook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Activity.TrendingMentorActivity;
import com.zocarro.myvideobook.Dashboard.topCoursesAdapter;
import com.zocarro.myvideobook.Dashboard.topRatedCourses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutUsActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView txt2,txt23;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = findViewById(R.id.toolbar);
        txt2 = findViewById(R.id.txt2);
        txt23 = findViewById(R.id.txt23);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setrequest();
    }
    private void setrequest()
    {
        Common.progressDialogShow(AboutUsActivity.this);
        String topcourseurl = Common.GetWebServiceURL()+"aboutus.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, response ->
        {
            try
            {
                Common.progressDialogDismiss(AboutUsActivity.this);
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("message");
                if(message.equalsIgnoreCase("sucess"))
                {
                    txt2.setText(jsonObject.getString("title"));
                    txt23.setText(jsonObject.getString("description"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(AboutUsActivity.this);
                Toast.makeText(AboutUsActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        },error ->
        {
            Common.progressDialogDismiss(AboutUsActivity.this);
            Toast.makeText(AboutUsActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return new HashMap<>();
            }
        };
        Volley.newRequestQueue(AboutUsActivity.this).add(request);
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
}