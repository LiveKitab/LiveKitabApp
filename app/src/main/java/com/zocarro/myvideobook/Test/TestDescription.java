package com.zocarro.myvideobook.Test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zocarro.myvideobook.R;

public class TestDescription extends AppCompatActivity {
    WebView txtdesc;
    Button btnstart;
    String tst_id,sub,desc,pos,neg,t_type;
    int hours,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_description);
        allocatememory();
        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
        hours=Integer.parseInt(getIntent().getExtras().getString("hours"));
        min=Integer.parseInt(getIntent().getExtras().getString("min"));
        desc=getIntent().getExtras().getString("des");
        pos=getIntent().getExtras().getString("pos");
        neg=getIntent().getExtras().getString("neg");
        t_type=getIntent().getExtras().getString("t_type");
       // txtdesc.setText(desc);
        String question = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + desc + "</body></html>";
        txtdesc.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
        txtdesc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(TestDescription.this, TestActivity.class);
                intent.putExtra("tst_id", tst_id);
                intent.putExtra("sub", sub);
                intent.putExtra("hours", "" + hours);
                intent.putExtra("min", "" + min);
                intent.putExtra("pos", pos);
                intent.putExtra("neg", neg);
                startActivity(intent);
                finish();*/

                    Intent intent = new Intent(TestDescription.this, TestActivity.class);
                    intent.putExtra("tst_id", tst_id);
                    intent.putExtra("sub", sub);
                    intent.putExtra("hours", "" + hours);
                    intent.putExtra("min", "" + min);
                    intent.putExtra("pos", pos);
                    intent.putExtra("neg", neg);
                    intent.putExtra("type", t_type);
                    startActivity(intent);
                    finish();

            }
        });
    }

    private void allocatememory() {
        txtdesc=findViewById(R.id.txtdesc);
        btnstart=findViewById(R.id.btnstart);
    }
}
