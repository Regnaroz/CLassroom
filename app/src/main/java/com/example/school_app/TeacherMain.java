package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherMain extends AppCompatActivity {

    String userSSN, userName;
    TextView myView;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }



        final Intent intent=getIntent();
        userSSN =intent.getStringExtra("ssn");

        myRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+userSSN);
        myView = findViewById(R.id.SSNText);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userSSN = snapshot.child("_TeacherSSN").getValue().toString();
                userName= snapshot.child("_TeacherName").getValue().toString();

                myView.setText("Welcome Mr "+userName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//click on teacher information
        CardView teacherinfo=findViewById(R.id.teacher_info_cardview);
        teacherinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent1=new Intent(TeacherMain.this,Teacher_Information.class);
                intent1.putExtra("ssn",userSSN);
                intent1.putExtra("chick","F");
                startActivity(intent1);



            }
        });

//click on teacher my class
        CardView teacher_myclass=findViewById(R.id.teacher_my_class_cardview);
        teacher_myclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(TeacherMain.this,TeacherUI.class);
                intent1.putExtra("ssn",userSSN);
                startActivity(intent1);



            }
        });

        //click on log out
        CardView logout=findViewById(R.id.log_out_cardview);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TeacherMain.this,MainActivity.class);
                startActivity(i);

            }
        });


    }
}