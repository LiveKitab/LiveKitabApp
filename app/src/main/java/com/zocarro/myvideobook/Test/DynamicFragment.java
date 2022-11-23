package com.zocarro.myvideobook.Test;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamicFragment extends Fragment {
    View view;
    int val;
    WebView question1_text_view;
    WebView imga, imgb, imgc, imgd;
    RadioGroup firstanswer;
    Button btnmark, btnsave;
    private int currentval;
    private int pre = 0;
    private DBHelper mydb;
    CardView card_view_question1;
    RadioButton a, b, c, d, e;
    ArrayList<TestQuestion> testQuestions;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_fragmnet, container, false);
        val = getArguments().getInt("someInt", 0);
        question1_text_view = view.findViewById(R.id.question1_text_view);
        imga = view.findViewById(R.id.imga);
        imgb = view.findViewById(R.id.imgb);
        imgc = view.findViewById(R.id.imgc);
        imgd = view.findViewById(R.id.imgd);
        btnmark = view.findViewById(R.id.btnmark);
        btnsave = view.findViewById(R.id.btnsave);
        firstanswer = view.findViewById(R.id.firstanswer);
        card_view_question1 = view.findViewById(R.id.card_view_question1);
        testQuestions = new ArrayList<>();
        mydb = new DBHelper(getContext());
        a = view.findViewById(R.id.a);
        b = view.findViewById(R.id.b);
        c = view.findViewById(R.id.c);
        d = view.findViewById(R.id.d);
        e = view.findViewById(R.id.e);
        if (TestActivity.dataflags == true) {

            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
            question1_text_view.getSettings().setJavaScriptEnabled(true);
            question1_text_view.getSettings().setDomStorageEnabled(true);
            question1_text_view.setHorizontalScrollBarEnabled(false);
            question1_text_view.setVerticalScrollBarEnabled(false);
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getA() + "</body></html>";
            String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getB() + "</body></html>";
            String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getC() + "</body></html>";
            String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getD() + "</body></html>";
            imga.getSettings().setJavaScriptEnabled(true);
            imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
            imga.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgb.getSettings().setJavaScriptEnabled(true);
            imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
            imgb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgc.getSettings().setJavaScriptEnabled(true);
            imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
            imgc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgd.getSettings().setJavaScriptEnabled(true);
            imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
            imgd.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            Cursor rs = mydb.getQuestions(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id());
            if (rs.moveToFirst()) {
                // record exists
                ArrayList<TestAnswer> array_list = mydb.getAllTestData(TestActivity.testQuestionArrayList.get(val).getTst_id());
                if (array_list.get(val).getAnswer().equals("A")) {
                    if (array_list.get(val).getMark() == 1) {
                        TestActivity.testQuestionArrayList.get(val).setMark(true);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#9400D3"));
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        a.setChecked(true);
                        TestActivity.testQuestionArrayList.get(val).setSelected("A");
                    } else {
                        a.setChecked(true);
                        TestActivity.testQuestionArrayList.get(val).setSelected("A");
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                    }

                } else if (array_list.get(val).getAnswer().equals("B")) {
                    if (array_list.get(val).getMark() == 1) {
                        TestActivity.testQuestionArrayList.get(val).setMark(true);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#9400D3"));
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.b);
                        TestActivity.testQuestionArrayList.get(val).setSelected("B");
                    } else {

                        firstanswer.check(R.id.b);
                        TestActivity.testQuestionArrayList.get(val).setSelected("B");
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("C")) {
                    if (array_list.get(val).getMark() == 1) {
                        TestActivity.testQuestionArrayList.get(val).setMark(true);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#9400D3"));
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.c);
                        TestActivity.testQuestionArrayList.get(val).setSelected("C");
                    } else {
                        firstanswer.check(R.id.c);
                        TestActivity.testQuestionArrayList.get(val).setSelected("C");
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("D")) {
                    if (array_list.get(val).getMark() == 1) {
                        TestActivity.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.d);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#9400D3"));
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        TestActivity.testQuestionArrayList.get(val).setSelected("D");
                    } else {
                        firstanswer.check(R.id.d);
                        TestActivity.testQuestionArrayList.get(val).setSelected("D");
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("Not Set")) {
                    if (array_list.get(val).getMark() == 1) {
                        TestActivity.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.e);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#9400D3"));
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        TestActivity.testQuestionArrayList.get(val).setSelected("Not Set");
                    } else {
                        firstanswer.check(R.id.e);
                        TestActivity.testQuestionArrayList.get(val).setSelected("Not Set");
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                    }
                }
            }
            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("A");

                            break;
                        case R.id.b:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("B")) {

                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {

                                }
                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "B")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("B");
                            break;
                        case R.id.c:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("C");
                            break;
                        case R.id.d:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("D");
                            break;
                        case R.id.e:
                            TestActivity.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                TestActivity.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#9400D3"));
                                TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                TestActivity.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                                TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {
                                }


                            } else {
                                // record not found
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {
                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
                            }

                            break;


                    }


                }
            });
        } else {
                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
                question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                question1_text_view.getSettings().setJavaScriptEnabled(true);
                question1_text_view.getSettings().setDomStorageEnabled(true);
                question1_text_view.setHorizontalScrollBarEnabled(false);
                question1_text_view.setVerticalScrollBarEnabled(false);
                String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getA() + "</body></html>";
                String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getB() + "</body></html>";
                String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getC() + "</body></html>";
                String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + TestActivity.testQuestionArrayList.get(val).getD() + "</body></html>";
                imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
                imga.getSettings().setJavaScriptEnabled(true);
                imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
                imgb.getSettings().setJavaScriptEnabled(true);
                imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
                imgc.getSettings().setJavaScriptEnabled(true);
                imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
                imgd.getSettings().setJavaScriptEnabled(true);



            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("A"))
                                    {
                                        TestActivity.score++;
                                    }
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("A"))
                                    {
                                        TestActivity.score++;
                                    }
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {

                                }

                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("A");


                            break;
                        case R.id.b:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("B")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("B"))
                                    {
                                        TestActivity.score++;
                                    }
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("B"))
                                    {
                                        TestActivity.score++;
                                    }
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "B")) {


                                } else {

                                }
                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "B")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("B");

                            break;
                        case R.id.c:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("C"))
                                    {
                                        TestActivity.score++;
                                    }
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("C"))
                                    {
                                        TestActivity.score++;
                                    }
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            } else {

                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("C");

                            break;
                        case R.id.d:
                            if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("D"))
                                    {
                                        TestActivity.score++;
                                    }
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    if(TestActivity.testQuestionArrayList.get(val).getCorrect().equals("D"))
                                    {
                                        TestActivity.score++;
                                    }
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            } else {
                                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#9400D3"));
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    TestActivity.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            }
                            TestActivity.testQuestionArrayList.get(val).setSelected("D");

                            break;
                        case R.id.e:
                            TestActivity.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {

                                TestActivity.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#9400D3"));
                                TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {

                                TestActivity.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                                TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {

                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {


                                } else {

                                }


                            } else {
                                // record not found
                                if (mydb.updateTest(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {


                                } else {

                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
                            }

                            break;


                    }


                }
            });


        }
        btnmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (TestActivity.testQuestionArrayList.get(val).isMark() == false) {

                    TestActivity.testQuestionArrayList.get(val).setMark(true);
                    TestActivity.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#9400D3"));
                    TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    if (mydb.updateMark(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), 1)) {

                    } else {

                    }
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (TestActivity.testQuestionArrayList.get(val).isMark() == true) {
                    if (TestActivity.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                        TestActivity.testQuestionArrayList.get(val).setMark(false);
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        if (mydb.updateMark(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), 0)) {

                        } else {

                        }
                    } else {
                        TestActivity.unanswered = TestActivity.unanswered - 1;
                        TestActivity.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        TestActivity.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        TestActivity.testQuestionArrayList.get(val).setMark(false);

                        if (mydb.updateMark(TestActivity.testQuestionArrayList.get(val).getTst_id(), TestActivity.testQuestionArrayList.get(val).getQ_id(), 0)) {

                        } else {

                        }

                    }
                } else {
                    Toast.makeText(getContext(), "This Question is unmarked for Review", Toast.LENGTH_LONG).show();
                }
            }
        });


        currentval = val;
        return view;
    }

    public static DynamicFragment addfrag(int val) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }


}