package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class studentClassRoom extends AppCompatActivity {
    ChipNavigationBar myNavigationBtn;
    Fragment myfragment= null;


    final String  studentDataText = "studentData";
    final String  GradeIDText = "GradeID";

    String studentSSN ;
    String studentName;

    ArrayList<String> studentData ;
    String GradeID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_room);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent myIntent = getIntent();
        studentData = myIntent.getStringArrayListExtra(studentDataText);
        GradeID = myIntent.getStringExtra(GradeIDText);

        studentSSN = studentData.get(0);
        studentName = studentData.get(1);





        myNavigationBtn = findViewById(R.id.studentNavigation);
        myNavigationBtn.setItemSelected(R.id.studentClassVideos,true);
        myfragment = studentVideosFragment.newInstance(studentData,GradeID);
        getSupportFragmentManager().beginTransaction().replace(R.id.studentFrame,myfragment).commit();




        myNavigationBtn.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i)
                {

                    case R.id.studentClassVideos:
                        myfragment =    studentVideosFragment.newInstance(studentData,GradeID);
                        break;

                    case R.id.studentClassDocumnets:
                        myfragment =  studentDocumentFragment.newInstance(studentData,GradeID);
                        break;
                    case R.id.studentClassMarks:
                        myfragment = studentMarkFragment.newInstance(studentData,GradeID);
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.studentFrame,myfragment).commit();
            }
        });


    }
}