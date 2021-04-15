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

public class Admin_information extends AppCompatActivity {


    private String admin_ssn,userName,phoneadmin,passAdmin;
    TextView name,ssn,pho,pass,viewpass,delete;
    DatabaseReference myRef;
    String currentPassword ;
    EditText current_password;
  String chick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_information);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Intent intent=getIntent();
        admin_ssn=intent.getStringExtra("ssn");
        chick=intent.getStringExtra("chick");

         current_password=findViewById(R.id.admin_edit_text_current_password);


        name=findViewById(R.id.text_view_admin_name);
        ssn=findViewById(R.id.text_view_ssn_admin);
        pho=findViewById(R.id.text_view_phone_admin);
        pass=findViewById(R.id.textview_admin_password);
        viewpass=findViewById(R.id.tt9);
        delete=findViewById(R.id.deletadmin);
        if(chick.equals("T")){
            pass.setVisibility(View.VISIBLE);
            viewpass.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }



        myRef = FirebaseDatabase.getInstance().getReference().child("Admins").child("Admin-"+admin_ssn);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName= snapshot.child("_AdminName").getValue().toString();
                phoneadmin=snapshot.child("_AdminPhone").getValue().toString();
                passAdmin=snapshot.child("_AdminPassword").getValue().toString();
                name.setText(userName);
                ssn.setText(admin_ssn);
                pho.setText(phoneadmin);
                pass.setText(passAdmin);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // change password button
        CardView c=findViewById(R.id.save_admin_pass_card_view);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists())
                {
                    currentPassword = snapshot.child("_AdminPassword").getValue().toString();



        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 current_password=findViewById(R.id.admin_edit_text_current_password);
                EditText new_password=findViewById(R.id.admin_edit_text_new_password);
                EditText confirm_new_password=findViewById(R.id.admin_edit_confirm_new_password);
                if(current_password.getText().toString().equals("")&& new_password.getText().toString().equals("")&&confirm_new_password.getText().toString().equals(""))
                {
                    new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Password Fields Are  Empty!").show();

                }else if(current_password.getText().toString().equals("")){

                    new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Empty!").show();


                }else if(new_password.getText().toString().equals("")){

                    new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password is Empty!").show();

                }else if(confirm_new_password.getText().toString().equals("")){
                    new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Confirm  Password is Empty").show();

                }else if(new_password.getText().toString().equals(confirm_new_password.getText().toString())){


                    if (!(currentPassword.equals(current_password.getText().toString())))
                        new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Incorrect").show();

                    else {


                        String newPassword = new_password.getText().toString();
                        DatabaseReference myreff2 = FirebaseDatabase.getInstance().getReference().child("Admins").child("Admin-"+admin_ssn);
                        myreff2.child("_AdminPassword").setValue(newPassword);
                        new SweetAlertDialog(Admin_information.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Password Changed Successfully ").show();


                        new_password.setText("");
                        confirm_new_password.setText("");





                    }


                }else {
                    new SweetAlertDialog(Admin_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password Doesn't Match ").show();


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