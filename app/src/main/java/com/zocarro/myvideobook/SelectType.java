package com.zocarro.myvideobook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.zocarro.myvideobook.BottomNavigation.BottomNavigationActivity;
import com.zocarro.myvideobook.Model.univercitymodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectType extends AppCompatActivity
{
    Toolbar toolbar;
    Spinner branchSpinner, programSpinner, streamSpinner,termSpinner,universitySpinner,mediumSpinner;
    MaterialButton btnContinue;
    List<String> stream = new ArrayList<String>();
    List<String> program = new ArrayList<String>();
    TextInputLayout branchSelection,programSelection,termSelection;
    TextView txtProgram,txtBranch,txtTerm;
    List<String> branch = new ArrayList<String>();
    List<String> universityname = new ArrayList<String>();
    List<String> term = new ArrayList<String>();
    List<String> university = new ArrayList<String>();
    List<String> medium = new ArrayList<String>();

    ArrayList<univercitymodel> univercitymodelArrayList = new ArrayList<>();
    ArrayList<StreamModel> streamModelArrayList = new ArrayList<>();
    ArrayList<ProgramModel> programModelArrayList = new ArrayList<>();
    ArrayList<BranchModel> branchModelArrayList = new ArrayList<>();
    ArrayList<TermModel> termModelArrayList = new ArrayList<>();
    String streamId,selectedStream,programId,selectedProgram,branchId,selectedterm,term_id,selectedbranch,branch_id,selectedUni,uni_id;
    static String stream_id,pr_id,b_id,t_id,selctedmedium,uni_id12;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Change class");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent i = getIntent();
        if(i.hasExtra("user_id"))
        {
            userid = i.getStringExtra("user_id");
        }
        else
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
             userid = preferences.getString("u_id", "none");
        }
//        Log.d("kcvsdvd",userid);
        allocateMemory();
        getUniversity();
        btnContinue.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if (validateInput())
                {
                    updateData(stream_id,pr_id,b_id,t_id,selctedmedium,uni_id12);
                }
            }
        });
        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedUni=String.valueOf(universitySpinner.getItemAtPosition(universitySpinner.getSelectedItemPosition()));
                String icdwd = university.get(position);
                uni_id12 = univercitymodelArrayList.get(position).getUni_id();
                getStream(icdwd);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedStream = String.valueOf(streamSpinner.getItemAtPosition(streamSpinner.getSelectedItemPosition()));
                stream_id = streamModelArrayList.get(position).getStreamId();
                Log.d("aaa",stream_id);
                getProgram(stream_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProgram = String.valueOf(programSpinner.getItemAtPosition(
                        programSpinner.getSelectedItemPosition()));
                pr_id = programModelArrayList.get(position).getProgram_id();
                Log.d("vvv",pr_id);
                Log.d("ppp",stream_id);
                getBranch(pr_id,stream_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedbranch = String.valueOf(branchSpinner.getItemAtPosition(
                        branchSpinner.getSelectedItemPosition()));
                b_id = branchModelArrayList.get(position).getBranchId();
                Log.d("vvv",pr_id);
                Log.d("ppp",stream_id);
                Log.d("rrr",b_id);
                getMedium(pr_id,stream_id,b_id);
                //getTerm(pr_id,stream_id,b_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mediumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                selctedmedium=String.valueOf(mediumSpinner.getItemAtPosition(
                        mediumSpinner.getSelectedItemPosition()));
                getTerm(pr_id,stream_id,b_id,selctedmedium);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedterm = String.valueOf(termSpinner.getItemAtPosition(
                        termSpinner.getSelectedItemPosition()));
                t_id = termModelArrayList.get(position).getTermId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void getMedium(final String pr_id, final String stream_id, final String b_id) {
        final String progrmUrl = Common.GetWebServiceURL() + "getMedium.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, progrmUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    medium.clear();
                    Common.progressDialogDismiss(SelectType.this);
                    Log.d("/medium/", response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        medium.add(object.getString("med"));

                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, medium);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    mediumSpinner.setAdapter(spinnerArrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id",stream_id);
                data.put("pr_id",pr_id);
                data.put("b_id",b_id);
                Log.d("PPP", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);

    }


    private void getUniversity()
    {
        final String progrmUrl = Common.GetWebServiceURL() + "getUniversity.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, progrmUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray array = new JSONArray(response);
                    univercitymodelArrayList.clear();
                    university.clear();
                   // Common.progressDialogDismiss(SelectType.this);
                    Log.d("///", response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        univercitymodelArrayList.add(new univercitymodel(
                                object.getString("university_id"),
                                object.getString("uni_name")));
                        university.add(object.getString("university_id"));
                        universityname.add(object.getString("uni_name"));
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, universityname);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// The drop down view
                    universitySpinner.setAdapter(spinnerArrayAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                /*data.put("stream_id",stream_id);
                data.put("pr_id",programId);
                data.put("b_id",branch_id);*/
                Log.d("PPP", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }

    private boolean validateInput()
    {
        if(selectedStream.equals("Select Your Stream"))
        {
            TextView errorText = (TextView)streamSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select Your Stream");
            return false;

        }
        if(selectedProgram.equals("Select Your Program"))
        {
            TextView errorText = (TextView)programSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select Your Program");
            return false;
        }
        if(selectedbranch.equals("Select Your Branch"))
        {
            TextView errorText = (TextView)branchSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select Your Branch");
            return false;

        }
        if(selectedterm.equals("Select Your Term"))
        {
            TextView errorText = (TextView)termSpinner.getSelectedView();
            errorText.setError(" ");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select Your Term");
            return false;
        }
        return true;
    }

    private void getTerm(final String programId, final String stream_id, final String branch_id,final String selctedmedium)
    {
        final String progrmUrl = Common.GetWebServiceURL() + "getTerm.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, progrmUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    JSONArray array = new JSONArray(response);
                    termModelArrayList.clear();
                    term.clear();
                    Common.progressDialogDismiss(SelectType.this);
                    Log.d("///", response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        termModelArrayList.add(new TermModel(object.getString("term_id"),
                                object.getString("term_name")));
                        term.add(object.getString("term_name"));
                        //term_id = object.getString("term_id");
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, term);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    termSpinner.setAdapter(spinnerArrayAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id",stream_id);
                data.put("pr_id",programId);
                data.put("b_id",branch_id);
                data.put("medium",selctedmedium);
                Log.d("PPP", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }

    private void updateData(final String stream_id, final String programId, final String branchId, final String term_id, final String selctedmedium,final String uni_id)
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String uid = mPrefs.getString("u_id", "none");
        final String updateUrl = Common.GetWebServiceURL()+"updatestream.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, updateUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(SelectType.this);
                    Log.d("yyy", response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("Data Updated"))
                    {
                        Toast.makeText(SelectType.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SelectType.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stream_id",stream_id);
                        editor.putString("pr_id", programId);
                        editor.putString("br_id", b_id);
                        editor.putString("term_id", term_id);
                        editor.putString("med", selctedmedium);
                        editor.commit();
                        Intent i = new Intent(SelectType.this, BottomNavigationActivity.class);
                        i.putExtra("stream_id", stream_id);
                        i.putExtra("pr_id", programId);
                        i.putExtra("br_id", b_id);
                        i.putExtra("term_id", term_id);
                        i.putExtra("med", selctedmedium);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(SelectType.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream", stream_id);
                data.put("program", programId);
                data.put("dep", branchId);
                data.put("u_id", userid);
                data.put("term_id", term_id);
                data.put("uni", uni_id);
                Log.d("uuu", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }
    private void getProgram(final String stream_id)
    {
        final String progrmUrl = Common.GetWebServiceURL() + "getProgram.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, progrmUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray array = new JSONArray(response);
                    programModelArrayList.clear();
                    program.clear();
                    Common.progressDialogDismiss(SelectType.this);
                    Log.d("---", response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        programModelArrayList.add(new ProgramModel(object.getString("pr_id"),
                                object.getString("program_name")));
                        program.add(object.getString("program_name"));
                       // programId = object.getString("pr_id");


                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, program);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    programSpinner.setAdapter(spinnerArrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id",stream_id);
                Log.d("LLL", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }
    private void getBranch(final String programId,final String stream_id)
    {
        final String progrmUrl = Common.GetWebServiceURL() + "getBranch.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, progrmUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray array = new JSONArray(response);
                    branchModelArrayList.clear();
                    branch.clear();
                    Log.d("***", response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        branchModelArrayList.add(new BranchModel(object.getString("b_id"), object.getString("b_name")));
                        branch.add(object.getString("b_name"));
                        branchId = object.getString("b_id");
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, branch);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    branchSpinner.setAdapter(spinnerArrayAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id",stream_id);
                data.put("pr_id",programId);
                Log.d("branch", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }

    private void allocateMemory()
    {
        branchSpinner = findViewById(R.id.branchSpinner);
        programSpinner = findViewById(R.id.programspinner);
        streamSpinner = findViewById(R.id.streamSpinner);
        universitySpinner = findViewById(R.id.universitySpinner);
        mediumSpinner = findViewById(R.id.mediumSpinner);
        termSpinner = findViewById(R.id.termSpinner);
        btnContinue = findViewById(R.id.btnContinue);
        branchSelection = findViewById(R.id.branchSelection);
        programSelection =findViewById(R.id.programSelection);
        termSelection = findViewById(R.id.termSelection);
        txtProgram = findViewById(R.id.txtProgram);
        txtBranch  = findViewById(R.id.txtBranch);
        txtTerm  = findViewById(R.id.txtTerm);
    }
    public void getStream(final String selectedUni)
    {
        Common.progressDialogShow(SelectType.this);
        final String subjecturl = Common.GetWebServiceURL() + "getStream.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, subjecturl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONArray array = new JSONArray(response);
                    String changes = array.getJSONObject(0).getString("change");
                    if(changes.equalsIgnoreCase("gone"))
                    {
//                        branchSelection.setVisibility(View.INVISIBLE);
//                        programSelection.setVisibility(View.INVISIBLE);
//                        termSelection.setVisibility(View.INVISIBLE);
//                        txtProgram.setVisibility(View.INVISIBLE);
//                        txtBranch.setVisibility(View.INVISIBLE);
//                        txtTerm.setVisibility(View.INVISIBLE);
                    }
                    Common.progressDialogDismiss(SelectType.this);
                    Log.d("%%%", response);
                    streamModelArrayList.clear();
                    stream.clear();
                    for (int i = 1; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        streamModelArrayList.add(new StreamModel(
                                object.getString("stream_id"),
                                object.getString("stream_name")));
                        stream.add(object.getString("stream_name"));
                        streamId = object.getString("stream_id");
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectType.this,
                            R.layout.spinner_item, stream);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    streamSpinner.setAdapter(spinnerArrayAdapter);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(SelectType.this);
                    Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(SelectType.this);
                Toast.makeText(SelectType.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("uni",selectedUni);
                Log.d("uni",selectedUni);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SelectType.this).add(sr);
    }

}
