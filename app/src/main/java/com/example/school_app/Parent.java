package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class Parent extends AppCompatActivity {


    VideoView v;
    String ssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);


        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        v=findViewById(R.id.parent_video_background);
        Uri u=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.videobackparent);
        v.setVideoURI(u);

        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        v.start();


        Intent intent=getIntent();
        ssn=intent.getStringExtra("ssn");


       // TextView text_name=findViewById(R.id.text_view_name_parent);
        //String name="saqer";
        //text_name.setText("welcome"+" "+name);

        //click on my class
        CardView mysons=findViewById(R.id.my_sons_cardview);
        mysons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(Parent.this,parentChildren.class);
                intent1.putExtra("parentSSN",ssn);
                startActivity(intent1);




            }
        });

        //click on parent information
        CardView studentinfo=findViewById(R.id.parent_info_cardview);
        studentinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(Parent.this,Parent_information.class);
                intent1.putExtra("ssn",ssn);
                intent1.putExtra("chick","F");
                startActivity(intent1);





            }
        });

        //click on log out
        CardView logout=findViewById(R.id.log_out_cardview);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Parent.this,MainActivity.class);
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