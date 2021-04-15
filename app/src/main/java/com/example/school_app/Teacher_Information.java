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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Teacher_Information extends AppCompatActivity {

    private String teacher_ssn,userName,phoneteacher,passteacher;
    TextView name,ssn,pho,pass,viewpass,delete;
    DatabaseReference myRef;
    String currentPassword ;
    String chick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__information);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }



        Intent intent=getIntent();
        teacher_ssn=intent.getStringExtra("ssn");
        chick=intent.getStringExtra("chick");


        name=findViewById(R.id.text_view_name_teacher);
        ssn=findViewById(R.id.text_view_ssn_teacher);
        pho=findViewById(R.id.text_view_phone_teacher);
        pass=findViewById(R.id.textview_teacher_password);
        viewpass=findViewById(R.id.tt9);
        delete=findViewById(R.id.deletteacher);
        if(chick.equals("T")){
            pass.setVisibility(View.VISIBLE);
            viewpass.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }

        myRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+teacher_ssn);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacher_ssn = snapshot.child("_TeacherSSN").getValue().toString();
                userName= snapshot.child("_TeacherName").getValue().toString();
                phoneteacher=snapshot.child("_TeacherPhone").getValue().toString();
               passteacher=snapshot.child("_TeacherPassword").getValue().toString();

               pass.setText(passteacher);
                name.setText(userName);
                ssn.setText(teacher_ssn);
                pho.setText(phoneteacher);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // change password button
        CardView c=findViewById(R.id.save_teacher_pass_card_view);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists())
                {
                    currentPassword = snapshot.child("_TeacherPassword").getValue().toString();

                    c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText current_password=findViewById(R.id.teacher_edit_text_current_password);
                            EditText new_password=findViewById(R.id.teacher_edit_text_new_password);
                            EditText confirm_new_password=findViewById(R.id.teacher_edit_confirm_new_password);
                            if(current_password.getText().toString().equals("")&& new_password.getText().toString().equals("")&&confirm_new_password.getText().toString().equals(""))
                            {
                                new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Password Fields Are  Empty!").show();

                            }else if(current_password.getText().toString().equals("")){

                                new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Empty!").show();


                            }else if(new_password.getText().toString().equals("")){

                                new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password is Empty!").show();

                            }else if(confirm_new_password.getText().toString().equals("")){
                                new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Confirm  Password is Empty").show();

                            }else if(new_password.getText().toString().equals(confirm_new_password.getText().toString())) {



                                if (!(currentPassword.equals(current_password.getText().toString())))
                                    new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Incorrect").show();

                                else {


                                    String newPassword = new_password.getText().toString();
                                DatabaseReference myreff2 = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+teacher_ssn);
                                myreff2.child("_TeacherPassword").setValue(newPassword);
                                    new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Password Changed Successfully ").show();


                                    new_password.setText("");
                                    confirm_new_password.setText("");
                                    current_password.setText("");




                                }


                            }
                            else{
                                new SweetAlertDialog(Teacher_Information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password Doesn't Match ").show();


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