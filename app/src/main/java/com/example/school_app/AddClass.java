package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddClass extends AppCompatActivity {
    String TeacherSSN;
    String TeacherName;
    String Grade;
    String GradeNo;
    String GradeID;
    String GradeSubject;
    Spinner GradeSpinner;
    Spinner GradeNoSpinner;
    Spinner SubjectSpinner;
    Button createBtn ;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);


        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }


            final Intent myIntent = getIntent();
         TeacherSSN = myIntent.getStringExtra("ssn");  //getting The Teacher SSN .
         TeacherName=myIntent.getStringExtra("Name");
         GradeSpinner = findViewById(R.id.gradeSpinner);
         GradeNoSpinner = findViewById(R.id.gradeNoSpinner);
         SubjectSpinner = findViewById(R.id.subjectsSpinner);
         createBtn = findViewById(R.id.btncreate);

         createBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Grade = GradeSpinner.getSelectedItem().toString();
                 GradeNo = GradeNoSpinner.getSelectedItem().toString();
                 GradeSubject = SubjectSpinner.getSelectedItem().toString();
                 GradeID = "class-"+GradeSubject+"-"+Grade+"-"+ GradeNo+"-"+TeacherName+"-"+TeacherSSN;

                 ClassRoomModel classRoom = new ClassRoomModel();

                 classRoom.setClassID(GradeID);
                 classRoom.setClassGarde(Grade);
                 classRoom.setClassTeacherSSN(TeacherSSN);
                 classRoom.setClassTeacherName(TeacherName);
                 classRoom.setClassSubject(GradeSubject);
                 classRoom.setClassNo(GradeNo);

                 myRef = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("classInformation");
                 myRef.setValue(classRoom);
                 myRef=FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+TeacherSSN).child("_Classes").child(GradeID);
                 myRef.setValue(classRoom);



                             new SweetAlertDialog(AddClass.this , SweetAlertDialog.SUCCESS_TYPE).setTitleText("Created succesfully").show();
                            final Intent myIntent = new Intent(AddClass.this,TeacherUI.class);
                             myIntent.putExtra("ssn",TeacherSSN);
                             myIntent.putExtra("Name",TeacherName);

                             Thread thread = new Thread(new Runnable() {
                                 @Override
                                 public void run() {
                                     try {
                                         Thread.sleep(2000);
                                         startActivity(myIntent);
                                     } catch (InterruptedException e) {
                                         e.printStackTrace();
                                     }


                                 }
                             });thread.start();








             }
         });









    }
}