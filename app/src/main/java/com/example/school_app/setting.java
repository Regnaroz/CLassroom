package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class setting extends AppCompatActivity {
    ArrayList  school_nameinArrayList;
    Intent intent3;
    EditText editText;
    RelativeLayout r1,r2;
    CardView change_logo, AdminInfo,change_name,logout,enterNameToChange;

    StorageReference myStorageRef;
    DatabaseReference myRef;
    private String adminSsn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        change_logo=findViewById(R.id.change_logo);

          Intent i=getIntent();
          adminSsn=i.getStringExtra("ssn");



        AdminInfo =findViewById(R.id.adminInfo);
        AdminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(setting.this,Admin_information.class);
               intent.putExtra("ssn",adminSsn);
                intent.putExtra("chick","F");
               startActivity(intent);
                System.out.println("ssn admin"+adminSsn);


            }
        });


        change_logo=findViewById(R.id.change_logo);
        change_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chang=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
                startActivityForResult(chang,100);

            }
        });



        change_name=findViewById(R.id.change_name);

        change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout r1=findViewById(R.id.re_name);
                r1.setVisibility(View.INVISIBLE);

                RelativeLayout r2=findViewById(R.id.re_name1);
                r2.setVisibility(View.VISIBLE);






            }
        });


        logout=findViewById(R.id.logout);
        myStorageRef = FirebaseStorage.getInstance().getReference();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent3 =new Intent(setting.this,MainActivity.class);
                startActivity(intent3);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri u1 = data.getData();


           myRef = FirebaseDatabase.getInstance().getReference().child("Admins").child("School Settings").child("Logo");
            StorageReference myUploader = myStorageRef.child("School Logo");
            myUploader.putFile(u1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    new SweetAlertDialog(setting.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Success !").show();
                                    myRef.setValue(uri.toString());



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    new SweetAlertDialog(setting.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Failed!").show();
                                }
                            });
                        }
                    });

        }
        Toast.makeText(setting.this, "Done", Toast.LENGTH_LONG).show();

    }

    public void change_name_school_button(View v){
        r1=findViewById(R.id.re_name);
        r2=findViewById(R.id.re_name1);
        r2.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.VISIBLE);
        EditText e=findViewById(R.id.edit_name);
        String schoolName=e.getText().toString();
        myRef = FirebaseDatabase.getInstance().getReference().child("Admins").child("School Settings");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    myRef.child("School Name").setValue(schoolName).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            new SweetAlertDialog(setting.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Changed Successfully !").show();

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

