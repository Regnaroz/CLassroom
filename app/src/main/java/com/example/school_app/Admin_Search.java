package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Search extends AppCompatActivity {



    EditText SSNSearch;
    ImageView searchBtn;
    String type[]={"Student","Teacher","Admin","Parent"};
    String SearchType;
    String SSN;

    String addPermession ;

    DatabaseReference myReff ;
    private String adminssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,type);
        Spinner s1=findViewById(R.id.typeSearch);
        s1.setAdapter(arrayAdapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchType=parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent=getIntent();
        adminssn=intent.getStringExtra("ssn");

        SSNSearch = findViewById(R.id.textSearch);
        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SSN = SSNSearch.getText().toString();
                if (SSN.equals("")) {
                    new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Text Is Empty").show();

                } else {

                    if (SearchType.equals(type[0])) {
                        myReff = FirebaseDatabase.getInstance().getReference().child("Students");
                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean flag = false;
                                for (DataSnapshot myShot : snapshot.getChildren()) {
                                    if (SSN.equals(myShot.child("_StudentSSN").getValue().toString())) {
                                        flag = true;
                                        Intent myIntent = new Intent(Admin_Search.this, Admin_Search_Student.class);
                                        myIntent.putExtra("ssn", SSN);
                                        startActivity(myIntent);

                                    }

                                }
                                if (flag == false) {
                                    new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else if (SearchType.equals(type[1])) {

                        myReff = FirebaseDatabase.getInstance().getReference().child("Teachers");
                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean flag = false;
                                for (DataSnapshot myShot : snapshot.getChildren()) {
                                    if (SSN.equals(myShot.child("_TeacherSSN").getValue().toString())) {
                                        flag = true;
                                        Intent myIntent = new Intent(Admin_Search.this, Teacher_Information.class);
                                        myIntent.putExtra("ssn", SSN);
                                        myIntent.putExtra("chick", "T");
                                        startActivity(myIntent);

                                    }

                                }
                                if (flag == false) {
                                    new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else if (SearchType.equals(type[2])) {


                        myReff = FirebaseDatabase.getInstance().getReference().child("Admins").child("Admin-" + adminssn);
                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    addPermession = snapshot.child("_AddPermession").getValue().toString();


                                    if (addPermession.equals("Allowed")) {
                                        myReff = FirebaseDatabase.getInstance().getReference().child("Admins");
                                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                boolean flag = false;
                                                for (DataSnapshot myShot : snapshot.getChildren()) {
                                                    try {
                                                        if (SSN.equals(myShot.child("_AdminSSN").getValue().toString())) {
                                                            flag = true;
                                                            Intent myIntent = new Intent(Admin_Search.this, Admin_information.class);
                                                            myIntent.putExtra("ssn", SSN);
                                                            myIntent.putExtra("chick", "T");
                                                            startActivity(myIntent);

                                                        }
                                                    } catch (Exception e) {
                                                        new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();
                                                    }

                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    } else
                                        new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("You Aren't Allowed To Edit Admins Information.").show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }







                    else  if (SearchType.equals(type[3]))
                    {

                        myReff = FirebaseDatabase.getInstance().getReference().child("Parents");
                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean flag = false;
                                for (DataSnapshot myShot : snapshot.getChildren())
                                {
                                    if (SSN.equals(myShot.child("_ParentSSN").getValue().toString())){
                                        flag = true;
                                        Intent myIntent = new Intent(Admin_Search.this , Parent_information.class);
                                        myIntent.putExtra("ssn" , SSN);
                                        myIntent.putExtra("chick" , "T");
                                        startActivity(myIntent);

                                    }

                                }
                                if(flag == false){
                                    new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else
                        new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();











                }
            }
        });








    }


}

/*
*     boolean flag = false;
                                for (DataSnapshot myShot : snapshot.getChildren())
                                {
                                   if (SSN.equals(myShot.child("_AdminSSN").getValue().toString())){
                                        flag = true;
                                        Intent myIntent = new Intent(Admin_Search.this , Admin_information.class);
                                        myIntent.putExtra("ssn" , SSN);
                                        startActivity(myIntent);

                                    }

                                }
                                if(flag == false){
                                    new SweetAlertDialog(Admin_Search.this, SweetAlertDialog.ERROR_TYPE).setTitleText("SSN Not Found .").show();
                                }
* */