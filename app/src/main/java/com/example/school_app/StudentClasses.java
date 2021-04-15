package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentClasses extends AppCompatActivity {

    private static final String studentSSNText = "studentSSN";
    private static final String studentNameText = "studentName";
    private static final String studentPhoneText = "studentPhone";
    private static final String studentPasswordText = "studentPassword";
    private static final String parentSSNText = "parentSSN";
    private static final String parentNameText = "parentName";
    private static final String parentPhoneText = "parentPhone";

    private  String studentSSN ;
    private String studentName;
    private String studentPhone;
    private String studentPassword;
    private String parentSSN;
    private String parentName;
    private String parentPhone;

    ArrayList<String> studentData;



   private VideoView background_my_class;

   RecyclerView myRecycler ;
   StudentClassesAdapter myAdapter;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        Intent intent=getIntent();
        studentSSN=intent.getStringExtra(studentSSNText);
        studentName = intent.getStringExtra(studentNameText);
        studentPassword = intent.getStringExtra(studentPasswordText);
        studentPhone = intent.getStringExtra(studentPhoneText);
        parentSSN = intent.getStringExtra(parentSSNText);
        parentName = intent.getStringExtra(parentNameText);
        parentPhone = intent.getStringExtra(parentPhoneText);


        studentData= new ArrayList<>();
        studentData.add(studentSSN);
        studentData.add(studentName);
        studentData.add(studentPassword);
        studentData.add(studentPhone);
        studentData.add(parentSSN);
        studentData.add(parentName);
        studentData.add(parentPhone);



        background_my_class=findViewById(R.id.video_background_my_class);
        Uri u=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.backmyclass);
        background_my_class.setVideoURI(u);

        background_my_class.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(false);

            }
        });

        background_my_class.start();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);

                    ScrollView s=findViewById(R.id.scrolview1);
                    s.setVisibility(View.VISIBLE);



                } catch (Exception e) {
                }
            }
        });
        t.start();



         myRecycler = findViewById(R.id.studentClassesRecycler);
         myRecycler.setLayoutManager(new LinearLayoutManager(this));

        myRef = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentSSN).child("Classes");



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public  void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<StudentClassModel> myList = new ArrayList<>();
                for ( DataSnapshot shot : snapshot.getChildren())
                {

                    String Gradeid = shot.child("classID").getValue().toString();
                    String GradeSubject = shot.child("classSubject").getValue().toString();
                    String GradeTeacher = shot.child("classTeacherName").getValue().toString();

                    StudentClassModel myClass = new StudentClassModel();
                    myClass.setGradeID(Gradeid);
                    myClass.setGradeSubject(GradeSubject);
                    myClass.setGardeTeacher(GradeTeacher);


                    myList.add(myClass);
                }


                myAdapter = new StudentClassesAdapter(StudentClasses.this , myList,studentData);
                myRecycler.setAdapter(myAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }


    @Override
    public void onStart(){
        background_my_class.start();
        super.onStart();
    }
    @Override
    public void onPause(){
        background_my_class.pause();
        super.onPause();
    }
    @Override
    public void onResume(){
        background_my_class.resume();
        super.onResume();
    }


}