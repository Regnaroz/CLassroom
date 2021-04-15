package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

public class splashWelcom extends AppCompatActivity {
    String logoStringUrl;
   DatabaseReference myReff ;
    String DataBaseName;
    TextView SchoolName ;
    ImageView imaglogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_welcom);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        imaglogo=findViewById(R.id.schoolLogo);
        myReff = FirebaseDatabase.getInstance().getReference("Admins").child("School Settings").child("Logo");
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                    logoStringUrl = snapshot.getValue(String.class);
                    Picasso.with(splashWelcom.this).load(logoStringUrl).into(imaglogo);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        myReff = FirebaseDatabase.getInstance().getReference("Admins").child("School Settings");
        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    SchoolName = findViewById(R.id.schoolName);
                    DataBaseName = snapshot.child("School Name").getValue().toString();
                    SchoolName.setText(DataBaseName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent HomeIntent = new Intent(splashWelcom.this,MainActivity.class);
                startActivity(HomeIntent);
                finish();
            }
        },6000);
    }



}