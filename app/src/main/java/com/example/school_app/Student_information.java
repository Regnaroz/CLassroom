package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_information extends AppCompatActivity {


     String student_ssn,student_name,student_phone,parent_name,parent_phone,parent_ssn;
    TextView studentname,studentssn,studentphone,parentname,parentphone,parentssn;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }



        Intent intent=getIntent();
        student_ssn=intent.getStringExtra("ssn");

        studentname=findViewById(R.id.text_name_student);
        studentssn=findViewById(R.id.textview_ssn_student);
        studentphone=findViewById(R.id.textview_phone_student);
        parentname=findViewById(R.id.textview_name_parent);
        parentssn=findViewById(R.id.textview_ssn_parent);
        parentphone=findViewById(R.id.textview_phone_parent);
        myRef = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+student_ssn);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student_name= snapshot.child("_StudentName").getValue().toString();
                student_phone=snapshot.child("_StudentPhone").getValue().toString();
                parent_name= snapshot.child("_Parentname").getValue().toString();
                parent_ssn=snapshot.child("_ParentSSN").getValue().toString();
                parent_phone=snapshot.child("_ParentPhone").getValue().toString();

                studentname.setText(student_name);
                studentssn.setText(student_ssn);
                studentphone.setText(student_phone);

                parentname.setText(parent_name);
                parentssn.setText(parent_ssn);
                parentphone.setText(parent_phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}