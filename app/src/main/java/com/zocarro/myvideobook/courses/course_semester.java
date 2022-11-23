package com.zocarro.myvideobook.courses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.DashboardMain;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link course_semester.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link course_semester#newInstance} factory method to
 * create an instance of this fragment.
 */
public class course_semester extends Fragment {

    private String[] sliderImageId = new String[3];
    ViewPager viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recSemester;
    static RecyclerView recsubject;
    ArrayList<SemesterModel> semesterModelArrayList = new ArrayList<>();
    static ArrayList<SubjectSemester> subjectSemesterArrayList=new ArrayList<>();
    Context ctx;
    static String term="";
    ChipGroup chipGroup;
    ArrayList<String> semester=new ArrayList<>();
    String str_id,Pro_id,t_id,br_id;
    List<Integer> color;
    List<String> colorName;
    int chipNumber,size,lastpos=0;
    ProgressBar progressBar;


    private OnFragmentInteractionListener mListener;

    public course_semester() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment course_semester.
     */
    // TODO: Rename and change types and number of parameters
    public static course_semester newInstance(String param1, String param2) {
        course_semester fragment = new course_semester();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_semester, container, false);
        recsubject = view.findViewById(R.id.recsubject);
        ctx = getContext();
        str_id = this.getArguments().getString("stream_id");
        Pro_id = this.getArguments().getString("pr_id");
        t_id = this.getArguments().getString("t_id");
        br_id = this.getArguments().getString("br_id");
        //Toast.makeText(ctx, str_id, Toast.LENGTH_LONG).show();
        chipGroup=view.findViewById(R.id.semester);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        recsubject.setLayoutManager(layoutManager);
        /*final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        term= mPrefs.getString("term_id","none");*/
       /* if(term.equals(""))
        {

        }
        else
        {
            getCoursesSemester();
        }*/
        viewPager = view.findViewById(R.id.viewPager);
        progressBar = view.findViewById(R.id.progressBar);
        loadBanner();
/*
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Log.d("Chip on create", ""+(checkedId+1));
               */
/* int check=checkedId+1;
                term=semesterModelArrayList.get(check).getTerm_id();
                getCoursesSemester();*//*



            }
        });
*/


        color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.GREEN);
        color.add(Color.BLUE);

        colorName = new ArrayList<>();
        colorName.add("RED");
        colorName.add("GREEN");
        colorName.add("BLUE");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 6000);

        SendRequest();

        return view;
    }

    private void getCoursesSemester() {
        //Common.progressDialogShow(ctx);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id","none");
        final String pr_id = mPrefs.getString("pr_id","none");
        String term_id = mPrefs.getString("term_id","none");
        String Webserviceurl=Common.GetWebServiceURL()+"course_semester_wise.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Common.progressDialogDismiss(ctx);
                progressBar.setVisibility(View.GONE);
                try {
                    Log.d("response", response);
                    JSONArray array=new JSONArray(response);
                    subjectSemesterArrayList.clear();
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {

                        Toast.makeText(ctx, "No Course Found :( ", Toast.LENGTH_LONG).show();
                        CourseAdapter adapter = new CourseAdapter(ctx, subjectSemesterArrayList);
                        recsubject.setAdapter(adapter);
                    }
                    else {

                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            subjectSemesterArrayList.add(new SubjectSemester(object.getString("c_id"),
                                    object.getString("course_id"), object.getString("course_name"),
                                    object.getString("course_bg"), object.getString("rating"),
                                    object.getString("enrolled")));
                        }
                        CourseAdapter adapter = new CourseAdapter(ctx, subjectSemesterArrayList);
                        recsubject.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
//                    Common.progressDialogDismiss(ctx);
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
//                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("stream_id", stream_id);
                data.put("pr_id", pr_id);
                data.put("b_id",b_id);
                String term_id=term;
                data.put("term_id",term_id);
                Log.d("aaa", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);
    }

    private void SendRequest() {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id","none");
        final String pr_id = mPrefs.getString("pr_id","none");

        String Webserviceurl=Common.GetWebServiceURL()+"getTerm.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);
                    semesterModelArrayList.clear();
                    semester.clear();
                    chipGroup.clearCheck();
                    chipGroup.removeView(course_semester.this.chipGroup.getRootView());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        semesterModelArrayList.add(new SemesterModel(object.getString("term_id"),
                                object.getString("term_name"), false));
                        semester.add(object.getString("term_name"));

                    }
                    Log.d("size", String.valueOf(semesterModelArrayList.size()));
                    size=semesterModelArrayList.size();
                   // setCategoryChips(semester);


                    for(int inc=0;inc<size;inc++)
                    {
                            ChipMaking(inc);


                    }

                    chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(ChipGroup chipGroup, @IdRes int i) {
                            Chip chip = chipGroup.findViewById(i);


                            //Toast.makeText(ctx, "Last Pos:" + lastpos + "\n Chip Pos:" + i, Toast.LENGTH_SHORT).show();

                            if(i!=lastpos)
                            {
                                chip.setChecked(true);
                                chip.setCheckedIcon(getResources().getDrawable(R.drawable.ic_check_black));
                                Chip chiplast = (Chip) chipGroup.getChildAt(lastpos);
                                chiplast.setEnabled(true);
                                chip.setEnabled(false);


                                lastpos=i;

                                term=semesterModelArrayList.get(i).getTerm_id();
                                progressBar.setVisibility(View.VISIBLE);

                            }


                        }
                    });

                    /*for (int j = 0; j < semesterModelArrayList.size(); j++) {
                        // Here I am creating Chip view dynamically using current Context

                        Chip chip = new Chip(course_semester.this.chipGroup.getContext());
                        chip.setTag(j);

                        LinearLayout.LayoutParams layoutParams = new
                                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(5, 5, 5, 5);

                        chip.setLayoutParams(layoutParams);
                        chip.setText(semesterModelArrayList.get(j).getTerm_name());
                        //chip.setCloseIconEnabled(true);
                        chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorAccent));
                        chip.setTextColor(getResources().getColorStateList(R.color.white));

                        chip.setClickable(true);
                        chip.setCheckable(true);
                        chipGroup.addView(chip);
                       *//* if(j==0) {
                            chip.setChecked(true);
                            term=semesterModelArrayList.get(0).getTerm_id();
                            getCoursesSemester();
                        }*//*

                        //defaultChip(j, chip);


                        //course_semester.this.chipGroup.addView(chip);
                        chipGroup.invalidate();

                    }

                    chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(ChipGroup chipGroup, int i) {


                            Chip chip = chipGroup.findViewById(i);

                            if (chip != null) {
                                Toast.makeText(ctx, "Chip is " + i, Toast.LENGTH_SHORT).show();
                                Log.d("@@@", String.valueOf(i));
                            *//*term=semesterModelArrayList.get(i-1).getTerm_id();
                            getCoursesSemester();*//*

                                Log.e("OnCheckedChangeListener", "Called");
                            }
                        }
                    });



                    ChipGroup.OnCheckedChangeListener onCheckedChangeListener = new ChipGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(ChipGroup chipGroup, int i) {
                            Log.d("BBB", String.valueOf(i));
                        }
                    };*/


                }
                 catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> data=new HashMap<>();
               data.put("stream_id",stream_id);
               data.put("pr_id",pr_id);
               data.put("b_id",b_id);
               Log.d("term data", data.toString());
               return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);
    }
    public void ChipMaking(int tag) {
        Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.item_chip_category, null, false);
        chip.setId(tag);
        if(tag==0) {
            term=semesterModelArrayList.get(0).getTerm_id();
            getCoursesSemester();
            chip.setChecked(true);
            chip.setEnabled(false);
            chip.setCheckedIcon(getResources().getDrawable(R.drawable.ic_check_black));
            lastpos=tag;
        }
        chip.setText(semesterModelArrayList.get(tag).getTerm_name());
        chip.setPaddingRelative(5, 5, 5, 5);
        chip.setElevation(5);
        //chip.setChecked(true);
        chip.setCheckable(true);
        chip.setClickable(true);
        chipGroup.addView(chip);
        chipGroup.invalidate();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        context =getContext();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void defaultChip(Integer integer,Chip chip){
        if(integer == 0){
            chip.performClick();
            final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            String term_id = mPrefs.getString("term_id","none");
            term=semesterModelArrayList.get(0).getTerm_id();

            getCoursesSemester();
        }

    }
    private class SliderTimer extends TimerTask {

       @Override
        public void run() {
            if (getActivity()!= null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < color.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
 /*   private class SliderTimer  {

    Handler handler = new Handler();

    Runnable update = new Runnable() {
        public void run() {
            if (viewPager.getCurrentItem() == color.size()  - 1) {
                viewPager.getCurrentItem() = 0;
            }
            featureViewPager.setCurrentItem(viewPager.getCurrentItem()++, true);
        }
    };*//*


        new Timer().schedule(new TimerTask() {

        @Override
        public void run() {
            handler.post(update);
        }
    }, 100, 500);
    }*/
    private void loadBanner() {
        String webserviceurl=Common.GetWebServiceURL()+"bannerAds.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("&&&", response);
                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        sliderImageId[i] = object.getString("banner_img");
                    }
                    AdvertismentAdapter adapterView = new AdvertismentAdapter(getContext(), sliderImageId);
                    viewPager.setAdapter(adapterView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(getContext()).add(request);

    }


}
