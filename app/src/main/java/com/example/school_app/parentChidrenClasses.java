package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class parentChidrenClasses extends AppCompatActivity {

    String StudentSSN , StudentName,ParentSSn;
    RecyclerView myRecycle;
    DatabaseReference myReff;

    parentChilcClassAdapter myAdapter;
    ArrayList<ClassRoomModel> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chidren_classes);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent myIntent = getIntent();
        StudentSSN = myIntent.getStringExtra("studentSSN");
        StudentName = myIntent.getStringExtra("studentName");
        ParentSSn = myIntent.getStringExtra("parentSSN");



        myRecycle= findViewById(R.id.childClassesrecycle);
        myRecycle.setHasFixedSize(true);
        myRecycle.setLayoutManager(new LinearLayoutManager(this));

        myReff= FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+StudentSSN).child("Classes");

        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList = new ArrayList<>();

                   if(snapshot.exists()) {

                       for (DataSnapshot myShot : snapshot.getChildren())

                       {

                           ClassRoomModel classRoom = new ClassRoomModel();
                           classRoom.setClassID(myShot.child("classID").getValue().toString());
                           classRoom.setClassGarde(myShot.child("classGarde").getValue().toString())  ;
                           classRoom.setClassSubject(myShot.child("classSubject").getValue().toString());
                           classRoom.setClassNo(myShot.child("classNo").getValue().toString());
                           classRoom.setClassTeacherName(myShot.child("classTeacherName").getValue().toString());


                           myList.add(classRoom);

                       }


                        }

                   myAdapter = new parentChilcClassAdapter(parentChidrenClasses.this,myList,StudentSSN,ParentSSn);
                   myRecycle.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}