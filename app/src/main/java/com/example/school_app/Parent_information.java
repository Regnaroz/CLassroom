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
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Parent_information extends AppCompatActivity {

    private String parent_ssn,userName,phoneparent,passparent;
    DatabaseReference myRef;
    TextView name,ssn,pho,pass,viewpass,delete;
    String currentPassword ;
    String chick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_information2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Intent intent=getIntent();
        parent_ssn=intent.getStringExtra("ssn");
        chick=intent.getStringExtra("chick");

        name=findViewById(R.id.text_view_name_parent);
        ssn=findViewById(R.id.text_view_ssn_parent);
        pho=findViewById(R.id.text_view_phone_parent);
        pass=findViewById(R.id.textview_parent_password);
        viewpass=findViewById(R.id.tt9);
        delete=findViewById(R.id.deletparent);

        if(chick.equals("T")){
            pass.setVisibility(View.VISIBLE);
            viewpass.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }

        myRef = FirebaseDatabase.getInstance().getReference().child("Parents").child("Parent-"+parent_ssn);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName= snapshot.child("_ParentName").getValue().toString();
                phoneparent=snapshot.child("_ParentPhone").getValue().toString();
                passparent=snapshot.child("_ParentPassword").getValue().toString();
                name.setText(userName);
                ssn.setText(parent_ssn);
                pho.setText(phoneparent);
                pass.setText(passparent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // change password button
        CardView c=findViewById(R.id.save_parent_pass_card_view);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists())
                {
                    currentPassword = snapshot.child("_ParentPassword").getValue().toString();

                    c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText current_password=findViewById(R.id.parent_edit_text_current_password);
                EditText new_password=findViewById(R.id.parent_edit_text_new_password);
                EditText confirm_new_password=findViewById(R.id.parent_edit_confirm_new_password);
                if(current_password.getText().toString().equals("")&& new_password.getText().toString().equals("")&&confirm_new_password.getText().toString().equals(""))
                {
                    new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Password Fields Are  Empty!").show();

                }else if(current_password.getText().toString().equals("")){

                    new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Empty!").show();


                }else if(new_password.getText().toString().equals("")){

                    new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password is Empty!").show();

                }else if(confirm_new_password.getText().toString().equals("")){
                    new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Confirm  Password is Empty").show();

                }else if(new_password.getText().toString().equals(confirm_new_password.getText().toString())){
                    if (!(currentPassword.equals(current_password.getText().toString())))
                        new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Current Password is Incorrect").show();

                    else {


                        String newPassword = new_password.getText().toString();
                        DatabaseReference myreff2 = FirebaseDatabase.getInstance().getReference().child("Parents").child("Parent-"+parent_ssn);
                        myreff2.child("_ParentPassword").setValue(newPassword);
                        new SweetAlertDialog(Parent_information.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Password Changed Successfully ").show();


                        new_password.setText("");
                        confirm_new_password.setText("");
                        current_password.setText("");




                    }


                }else {
                    new SweetAlertDialog(Parent_information.this, SweetAlertDialog.ERROR_TYPE).setTitleText("New Password Doesn't Match ").show();


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