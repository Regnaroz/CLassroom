package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class admin extends AppCompatActivity {


   private String ssn;
   private Intent intent2;
    VideoView back_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Intent intent=getIntent();
        ssn=intent.getStringExtra("ssn");

        back_admin=findViewById(R.id.video_admin_background);
        Uri u=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background_admin_video);
        back_admin.setVideoURI(u);

        back_admin.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        back_admin.start();


        CardView e1=findViewById(R.id.add_student_card);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  intent2=new Intent(admin.this,add.class);
               intent2.putExtra("nametype","Add Student");
               startActivity(intent2);
            }
        });


        CardView e2=findViewById(R.id.add_teacher_card);
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    intent2=new Intent(admin.this,add.class);
                intent2.putExtra("nametype","Add Teacher");
               startActivity(intent2);
            }
        });



        CardView e5=findViewById(R.id.search_card);
        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   intent2=new Intent(admin.this, Admin_Search.class);
                 intent2.putExtra("ssn",ssn);
                startActivity(intent2);
            }
        });

        CardView e6=findViewById(R.id.settings_card);
        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         intent2 =new Intent(admin.this,setting.class);
         intent2.putExtra("ssn",ssn);
         startActivity(intent2);

            }
        });



        CardView e8=findViewById(R.id.add_admin_card);
        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("Admins").child("Admin-"+ssn);
                 myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         String permession = snapshot.child("_AddPermession").getValue().toString();
                         if(permession.equals("Allowed"))
                         {
                             intent2=new Intent(admin.this,add.class);
                             intent2.putExtra("nametype","Add Admin");
                             startActivity(intent2);
                         }

                         else
                             new SweetAlertDialog(admin.this,SweetAlertDialog.ERROR_TYPE).setTitleText("You Arent Allowed to add Admins").show();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

            }
        });



    }


    @Override
    public void onStart(){
        back_admin.start();
        super.onStart();
    }
    @Override
    public void onPause(){
        back_admin.pause();
        super.onPause();
    }
    @Override
    public void onResume(){
        back_admin.resume();
        super.onResume();
    }
}