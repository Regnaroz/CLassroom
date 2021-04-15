package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentUi extends AppCompatActivity {

    private static final String studentSSNText = "studentSSN";
    private static final String studentNameText = "studentName";
    private static final String studentPhoneText = "studentPhone";
    private static final String studentPasswordText = "studentPassword";
    private static final String parentSSNText = "parentSSN";
    private static final String parentNameText = "parentName";
    private static final String parentPhoneText = "parentPhone";

    private String studentSSN ;
    private String studentName;
    private String studentPhone;
    private String studentPassword;
    private String parentSSN;
    private String parentName;
    private String parentPhone;


    DatabaseReference myReff;

    VideoView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ui);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }




        v=findViewById(R.id.video_background);
        Uri u=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background_studen_video2);
        v.setVideoURI(u);

        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        v.start();





        Intent intent=getIntent();
        studentSSN=intent.getStringExtra("ssn");

        //Model to Get student Info , will be used to get Data in future classes ;
        myReff = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentSSN);
        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0)
                {
                    studentName=snapshot.child("_StudentName").getValue().toString();
                    studentPhone = snapshot.child("_StudentPhone").getValue().toString();
                    studentPassword =  snapshot.child("_StudentPassword").getValue().toString();
                    parentSSN = snapshot.child("_ParentSSN").getValue().toString();
                    parentName =snapshot.child("_Parentname").getValue().toString();
                    parentPhone = snapshot.child("_ParentPhone").getValue().toString();

                    TextView text_name=findViewById(R.id.textview_name_student);

                    text_name.setText("Welcome "+" "+studentName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




           //click on my class
        CardView myclass=findViewById(R.id.my_class_cardview);
        myclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myReff = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentSSN);
                myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getChildrenCount()>0)
                        {
                            studentName=snapshot.child("_StudentName").getValue().toString();
                            studentPhone = snapshot.child("_StudentPhone").getValue().toString();
                            studentPassword =  snapshot.child("_StudentPassword").getValue().toString();
                            parentSSN = snapshot.child("_ParentSSN").getValue().toString();
                            parentName =snapshot.child("_Parentname").getValue().toString();
                            parentPhone = snapshot.child("_ParentPhone").getValue().toString();

                            Intent intent1=new Intent(StudentUi.this, StudentClasses.class);
                            intent1.putExtra(studentSSNText,studentSSN);
                            intent1.putExtra(studentNameText,studentName);
                            intent1.putExtra(studentPhoneText,studentPhone);
                            intent1.putExtra(studentPasswordText,studentPassword);
                            intent1.putExtra(parentSSNText,parentSSN);
                            intent1.putExtra(parentNameText,parentName);
                            intent1.putExtra(studentSSNText,studentSSN);
                            startActivity(intent1);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });

        //click on student information
        CardView studentinfo=findViewById(R.id.student_info_cardview);
        studentinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(StudentUi.this,Student_information.class);
                intent1.putExtra("ssn",studentSSN);
                startActivity(intent1);



            }
        });

        //click on log out
        CardView logout=findViewById(R.id.log_out_cardview);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentUi.this,MainActivity.class);
                startActivity(i);

            }
        });

    }


    @Override
    public void onStart(){
        v.start();
        super.onStart();
    }
@Override
    public void onPause(){
        v.pause();
        super.onPause();
}
    @Override
    public void onResume(){
        v.resume();
        super.onResume();
    }




    }









