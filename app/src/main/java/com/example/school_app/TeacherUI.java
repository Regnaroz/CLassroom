package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherUI extends AppCompatActivity {

 String userSSN, userName , userPassword , userPhone;
 Button CreateClassBtn;
  DatabaseReference myRef;


  private RecyclerView myRecycler;
  TeacherClassadapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_u_i);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        final Intent intent=getIntent();
        userSSN =intent.getStringExtra("ssn");



        myRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+userSSN);
        myRecycler=findViewById(R.id.classesRecycler);
        myRecycler.setHasFixedSize(true);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 userSSN = snapshot.child("_TeacherSSN").getValue().toString();
                userName= snapshot.child("_TeacherName").getValue().toString();
                 userPassword= snapshot.child("_TeacherPassword").getValue().toString();
                userPhone = snapshot.child("_TeacherPhone").getValue().toString();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+userSSN).child("_Classes");


 myRef.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot snapshot) {
         ArrayList<ClassRoomModel> myList = new ArrayList<>();

         for (DataSnapshot shot : snapshot.getChildren())
         {
             String GradeId  = shot.child("classID").getValue().toString();
             String GradeClass  = shot.child("classGarde").getValue().toString();
             String GardeNo = shot.child("classNo").getValue().toString();
             String GradeSubject = shot.child("classSubject").getValue().toString();
             String TeacherSSN = shot.child("classTeacherSSN").getValue().toString();
             String TeacherName= shot.child("classTeacherName").getValue().toString();

             ClassRoomModel classroom = new ClassRoomModel();
             classroom.setClassID(GradeId);
             classroom.setClassGarde(GradeClass);
             classroom.setClassNo(GardeNo);
             classroom.setClassSubject(GradeSubject);
             classroom.setClassTeacherSSN(TeacherSSN);
             classroom.setClassTeacherName(TeacherName);

             myList.add(classroom);
         }

         myAdapter = new TeacherClassadapter(TeacherUI.this , myList);
         myRecycler.setAdapter(myAdapter);
     }

     @Override
     public void onCancelled(@NonNull DatabaseError error) {

     }
 });



        CreateClassBtn =findViewById(R.id.createClassBtn);
        CreateClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntene = new Intent(TeacherUI.this , AddClass.class);
                myIntene.putExtra("ssn",userSSN);
                myIntene.putExtra("Name",userName);
                startActivity(myIntene);
            }
        });


    }




}