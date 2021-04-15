package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import cn.pedant.SweetAlert.SweetAlertDialog;

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

public class Admin_Search_Student extends AppCompatActivity {

    String student_ssn,student_name,student_phone,parent_name,parent_phone,parent_ssn,student_password;
    TextView studentname,studentssn,studentphone,parentname,parentphone,parentssn,studentpassword;
    DatabaseReference myRef;
    String currentPassword ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__search__student);
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
        studentpassword=findViewById(R.id.textview_student_password);
        myRef = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+student_ssn);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student_name= snapshot.child("_StudentName").getValue().toString();
                student_phone=snapshot.child("_StudentPhone").getValue().toString();
                parent_name= snapshot.child("_Parentname").getValue().toString();
                parent_ssn=snapshot.child("_ParentSSN").getValue().toString();
                parent_phone=snapshot.child("_ParentPhone").getValue().toString();
                student_password=snapshot.child("_StudentPassword").getValue().toString();
                studentname.setText(student_name);
                studentssn.setText(student_ssn);
                studentphone.setText(student_phone);

                parentname.setText(parent_name);
                parentssn.setText(parent_ssn);
                parentphone.setText(parent_phone);

                studentpassword.setText(student_password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CardView c=findViewById(R.id.save_admin_pass_card_view);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists())
                {


                    c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText current_password=findViewById(R.id.admin_edit_text_current_password);
                            EditText new_password=findViewById(R.id.admin_edit_text_new_password);
                            EditText confirm_new_password=findViewById(R.id.admin_edit_confirm_new_password);
                            currentPassword = snapshot.child("_StudentPassword").getValue().toString();
                            if(current_password.getText().toString().equals("")&& new_password.getText().toString().equals("")&&confirm_new_password.getText().toString().equals(""))
                            {
                                new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Password Fields Are  Empty!").show();

                            }else if(current_password.getText().toString().equals("")){

                                new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Empty!").show();


                            }else if(new_password.getText().toString().equals("")){

                                new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password is Empty!").show();

                            }else if(confirm_new_password.getText().toString().equals("")){
                                new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Confirm  Password is Empty").show();

                            }else if(new_password.getText().toString().equals(confirm_new_password.getText().toString())){
                                if (!(currentPassword.equals(current_password.getText().toString())))
                                    new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Incorrect").show();

                                else {


                                    String newPassword = new_password.getText().toString();
                                    DatabaseReference myreff2 = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+student_ssn);
                                    myreff2.child("_StudentPassword").setValue(newPassword);
                                    new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Password Changed Successfully ").show();


                                    new_password.setText("");
                                    confirm_new_password.setText("");
                                    current_password.setText("");




                                }


                            }else {
                                new SweetAlertDialog(Admin_Search_Student.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password Doesn't Match ").show();


                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}