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

public class parentChildren extends AppCompatActivity {
String ParenSSN;

RecyclerView myRecycle;
DatabaseReference myReff;
ArrayList<childModel> myList;
parentChildrenadapter myAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_children);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


        Intent myIntent = getIntent();
        ParenSSN = myIntent.getStringExtra("parentSSN");

        myRecycle=findViewById(R.id.parentChildrenRecycle);
        myRecycle.setHasFixedSize(true);
        myRecycle.setLayoutManager(new LinearLayoutManager(this));

myReff = FirebaseDatabase.getInstance().getReference().child("Students");
myReff.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        myList = new ArrayList<>();

        for (DataSnapshot myshot : snapshot.getChildren())
        {
            if(myshot.child("_ParentSSN").getValue().toString().equals(ParenSSN))
            {
                childModel child = new childModel();


                child.childSSN = myshot.child("_StudentSSN").getValue().toString();
                child.childName = myshot.child("_StudentName").getValue().toString();
                child.parentSSN = ParenSSN;

                myList.add(child);
            }

        }
           myAdapter = new parentChildrenadapter(parentChildren.this,myList,ParenSSN);
           myRecycle.setAdapter(myAdapter);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




    }
}