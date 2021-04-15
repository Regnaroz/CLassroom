package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class  act2 extends AppCompatActivity {
    EditText passwordEditText;
    EditText userNameEditText;
    ArrayList<String> Database;
    String user_type;
    ArrayList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Database = new ArrayList();
        passwordEditText =   findViewById(R.id.LoginPassword);
        userNameEditText = findViewById(R.id.use_name);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent i = getIntent();
        user_type = i.getStringExtra("idname");


        TextView text = (TextView) findViewById(R.id.tvtu);
        text.setText(user_type + " log in");


    }


    public void login_button(View v) {

        userNameEditText = findViewById(R.id.use_name);
        passwordEditText = findViewById(R.id.LoginPassword);
        final String userName = userNameEditText.getText().toString();
        final  String password = passwordEditText.getText().toString();
        if (user_type.equals("Students")) {

            DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+userName);
            myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        String dataPassword = snapshot.child("_StudentPassword").getValue().toString();
                        if (dataPassword.equals(password))
                        {
                            new SweetAlertDialog(act2.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Correct !").show();
                            userNameEditText.setText("");
                            passwordEditText.setText("");
                              Intent i = new Intent(act2.this, StudentUi.class);
                            i.putExtra("ssn",userName);
                            startActivity(i);
                        }
                        else
                            new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();

                    }

                    else
                    {
                        new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }


        else if (user_type.equals("Teachers")) {



            DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+userName);
            myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        String dataPassword = snapshot.child("_TeacherPassword").getValue().toString();
                        if (dataPassword.equals(password))
                        {
                            new SweetAlertDialog(act2.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Correct !").show();
                            userNameEditText.setText("");
                            passwordEditText.setText("");


                            Intent i = new Intent(act2.this, TeacherMain.class);
                            i.putExtra("ssn",userName);
                            startActivity(i);

                        }
                        else
                            new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();

                    }

                    else
                    {
                        new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        } else if (user_type.equals("Admins")) {


            DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("Admins").child("Admin-"+userName);
            myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        String dataPassword = snapshot.child("_AdminPassword").getValue().toString();
                        if (dataPassword.equals(password))
                        {

                            new SweetAlertDialog(act2.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Correct !").show();
                            userNameEditText.setText("");
                            passwordEditText.setText("");

                            Intent i = new Intent(act2.this, admin.class);
                            i.putExtra("ssn",userName);
                            startActivity(i);
                        }
                        else
                            new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();

                    }

                    else
                    {
                        new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        } else if (user_type.equals("Parents")) {
            {


                DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("Parents").child("Parent-"+userName);
                myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            String dataPassword = snapshot.child("_ParentPassword").getValue().toString();
                            if (dataPassword.equals(password))
                            {
                                new SweetAlertDialog(act2.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Correct !").show();
                                userNameEditText.setText("");
                                passwordEditText.setText("");
                                  Intent i = new Intent(act2.this, Parent.class);
                            i.putExtra("ssn",userName);
                            startActivity(i);
                            }
                            else
                                new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();

                        }

                        else
                        {
                            new SweetAlertDialog(act2.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Data Not correct !").show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


//        }

        }
    }}




