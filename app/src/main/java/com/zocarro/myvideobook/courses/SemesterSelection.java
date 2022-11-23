package com.zocarro.myvideobook.courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class SemesterSelection extends AppCompatActivity {

    ArrayList<SemesterModel> semesterModelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_selection);

       // SendRequest();
    }

    private void SendRequest() {
        Common.progressDialogShow(SemesterSelection.this);

    }


}
