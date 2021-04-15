package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static com.ismaeldivita.chipnavigation.ChipNavigationBar.*;


public class TeacherClassRoom extends AppCompatActivity {


    String GradeId;
    String Grade;
    String GradeNo;
    String Subject;
    String TeacherSSN;
    String TeacherName;
    ChipNavigationBar myNavbtn;
    Fragment myfragment= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_room);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Intent myIntent = getIntent();
         GradeId = myIntent.getStringExtra("GradeId");
         Grade = myIntent.getStringExtra("Grade");
         GradeNo = myIntent.getStringExtra("GradeNo");
         Subject = myIntent.getStringExtra("Subject");
         TeacherSSN = myIntent.getStringExtra("TeacherSSN");
         TeacherName = myIntent.getStringExtra("TeacherName");






         myNavbtn=findViewById(R.id.naviagtionbtn);
         myNavbtn.setItemSelected(R.id.ClassVideos,true);
         myfragment = VideosFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
         getSupportFragmentManager().beginTransaction().replace(R.id.myframe,myfragment).commit();




         myNavbtn.setOnItemSelectedListener(new OnItemSelectedListener() {
             @Override
             public void onItemSelected(int i) {
                 switch (i)
                 {
                     case R.id.ClassVideos:

                         myfragment = VideosFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
                         break;

                     case R.id.ClassDocumnets:
                         myfragment =   DocumentsFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
                         break;

                     case R.id.ClassMarks:
                         myfragment =   teacherMarksFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
                         break;

                     case R.id.ClassNotes:
                         myfragment =   teacherNotesFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
                         break;
                     case R.id.ClassChat:
                          myfragment = ClassSettingsFragment.newInstance(GradeId,Grade,GradeNo,Subject,TeacherSSN,TeacherName);
                         break;
                 }


                 getSupportFragmentManager().beginTransaction().replace(R.id.myframe,myfragment).commit();
             }
         });


    }








}