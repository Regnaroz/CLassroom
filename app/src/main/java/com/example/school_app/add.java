package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class add extends AppCompatActivity {
DatabaseReference myReff;
private String add_type;



    private String [] materials_array={"Math","English","Sciences","Arabic","Art","Sport","Islamic culture","patriotism","Other"};

        private String item;
        private String item2;


        private  EditText userNameEditText; // editTextName Student, teacher, admin
        private  EditText userSSNEditText; // edittextSSN  Student, teacher, admin
        private  EditText userPhoneEditText; // editTextPhoneNum Student, teacher, admin
        private  EditText parentNameEditText; //edittextParentName
        private  EditText parenSSNEditText; //edittextParentSSN
        private  EditText parentPhoneEdittext; //editTextParentPhone

        private Spinner s1;
        private Spinner s2;

        private RadioButton maleRadioButton;
        private RadioButton femaleRadioButton;


        private TextView ageTextView;

        private CheckBox adminCheckBox;



    private DatePickerDialog.OnDateSetListener  mDateSetListener1;
    private DatePickerDialog.OnDateSetListener  mDateSetListener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}


        Intent intent=getIntent();
        add_type=intent.getStringExtra("nametype");


        TextView te=findViewById(R.id.type_add);
        te.setText(add_type);


        // types add click

        if(add_type.equals("Add Student")){
            LinearLayout l1=findViewById(R.id.liner_add_student);
            l1.setVisibility(View.VISIBLE);
            LinearLayout l3=findViewById(R.id.liner_add_parent);
            l3.setVisibility(View.VISIBLE);
        }
        else if(add_type.equals("Add Teacher")){
            LinearLayout l2=findViewById(R.id.liner_add_teacher);
            l2.setVisibility(View.VISIBLE);

        }

        else if(add_type.equals("Add Admin")){
            LinearLayout l4=findViewById(R.id.liner_add_admin);
            l4.setVisibility(View.VISIBLE);
        }







         //spinner teacher materials

        ArrayAdapter arrayAdapter_teacher=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,materials_array);
        Spinner s1=findViewById(R.id.spinner_teacher);
        s1.setAdapter(arrayAdapter_teacher);





      // age student text
   TextView   t1 =findViewById(R.id.text_age_student);
       t1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar c=Calendar.getInstance();
               int year=c.get(Calendar.YEAR);
               int month=c.get(Calendar.MONTH);
               int day=c.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog dialog=new DatePickerDialog(add.this,R.style.Theme_AppCompat_Light_Dialog_MinWidth,mDateSetListener1,year,month,day);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
               dialog.show();
           }
       });

       mDateSetListener1=new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            TextView   t1 =findViewById(R.id.text_age_student);
               month=month+1;
               String date=month+"/"+dayOfMonth+"/"+year;
             t1.setText(date);
           }
       };


       //age teacher text

   TextView  t2 =findViewById(R.id.text_age_teacher);
       t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(add.this,R.style.Theme_AppCompat_Light_Dialog_MinWidth,mDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           TextView     t2 =findViewById(R.id.text_age_teacher);
                month=month+1;
                String date=month+"/"+dayOfMonth+"/"+year;
                t2.setText(date);
            }
        };

        // spinner teacher

      Spinner  teach=findViewById(R.id.spinner_teacher);
        teach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


         public void save_student(View v){
             userNameEditText =findViewById(R.id.edit_name_student);
             userSSNEditText =findViewById(R.id.edit_ssn_student);
             userPhoneEditText =findViewById(R.id.edit_phone_student);
             maleRadioButton =findViewById(R.id.male_student);
             femaleRadioButton =findViewById(R.id.female_student);
             ageTextView =findViewById(R.id.text_age_student);
             parentNameEditText =findViewById(R.id.edit_name_parent);
             parenSSNEditText =findViewById(R.id.edit_ssn_parent);
             parentPhoneEdittext =findViewById(R.id.edit_phone_parent);

             String EnteredSSN = userSSNEditText.getText().toString();
             Query SSNQuery = FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("_StudentSSN").equalTo(EnteredSSN);

             SSNQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if (snapshot.getChildrenCount()>0)
                     {
                         new SweetAlertDialog(add.this,SweetAlertDialog.ERROR_TYPE).setTitleText("ERROR, Student SSN ALRAEDY EXISTS !").show();
                         //Toast.makeText(add.this,"ERROR, Student SSN ALRAEDY EXISTS !",Toast.LENGTH_LONG).show();
                     }
                     else
                     {

                         if(userNameEditText.getText().toString().equals("") && userSSNEditText.getText().toString().equals("") && userPhoneEditText.getText().toString().equals("") && parentNameEditText.getText().toString().equals("") &&
                                 parenSSNEditText.getText().toString().equals("")&& parentPhoneEdittext.getText().toString().equals("") && ageTextView.getText().toString().equals("")){
                             Toast.makeText(add.this,"Please enter all required information",Toast.LENGTH_LONG).show();

                         }
                         else if(userNameEditText.getText().toString().equals("")){
                             Toast.makeText(add.this,"Please enter student name",Toast.LENGTH_LONG).show();

                         }
                         else if(userSSNEditText.getText().toString().equals("")){
                             Toast.makeText(add.this,"Please enter student national id ",Toast.LENGTH_LONG).show();

                         }
                         else if(userPhoneEditText.getText().toString().equals("")){

                             Toast.makeText(add.this,"Please enter phone number",Toast.LENGTH_LONG).show();
                         }

                         else if(ageTextView.getText().toString().equals("")){

                             Toast.makeText(add.this,"Please enter age ",Toast.LENGTH_LONG).show();

                         }
                         else   if(userPhoneEditText.getText().toString().length()!=10 ){
                             Toast.makeText(add.this,"Please enter the phone number in the correct format",Toast.LENGTH_LONG).show();
                         }
                         else if(parentNameEditText.getText().toString().equals("")){
                             Toast.makeText(add.this,"Please enter parent name",Toast.LENGTH_LONG).show();

                         }
                         else if(parenSSNEditText.getText().toString().equals("")){
                             Toast.makeText(add.this,"Please enter parent national id ",Toast.LENGTH_LONG).show();

                         }
                         else if(parentPhoneEdittext.getText().toString().equals("")){

                             Toast.makeText(add.this,"Please enter phone number parent",Toast.LENGTH_LONG).show();
                         }
                         else   if(parentPhoneEdittext.getText().toString().length()!=10 ){
                             Toast.makeText(add.this,"Please enter the phone number in the correct format",Toast.LENGTH_LONG).show();
                         }

                         else if(maleRadioButton.isChecked()|| femaleRadioButton.isChecked()){



                             Thread t = new Thread(new Runnable() {
                                 @Override
                                 public void run() {
                                     try {
                                         Thread.sleep(3000);

                                         myReff = FirebaseDatabase.getInstance().getReference().child("Students");
                                         StudentRegister Student = new StudentRegister();

                                         String userName = (userNameEditText.getText().toString());
                                         String userSSN = (userSSNEditText.getText().toString());
                                         String userPhone =(userPhoneEditText.getText().toString());
                                         String parentName = (parentNameEditText.getText().toString());
                                         String parentSSN = (parenSSNEditText.getText().toString());
                                         String parentPhone = (parentPhoneEdittext.getText().toString());

                                         Student.set_StudentName(userName);
                                         Student.set_StudentSSN(userSSN);
                                         Student.set_StudentPassword(userSSN);
                                         Student.set_StudentPhone(userPhone);
                                         Student.set_Parentname(parentName);
                                         Student.set_ParentSSN(parentSSN);
                                         Student.set_ParentPhone(parentPhone);


                                         myReff.child("student-"+userSSN).setValue(Student);

                                         myReff= FirebaseDatabase.getInstance().getReference().child("Parents");

                                         ParentRegister parentRegister = new ParentRegister();
                                         parentRegister.set_ParentName(parentName);
                                         parentRegister.set_ParentSSN(parentSSN);
                                         parentRegister.set_ParentPassword(parentSSN);
                                         parentRegister.set_ParentPhone(parentPhone);

                                         myReff.child("Parent-"+parentSSN).setValue(parentRegister);


                            Intent i = new Intent(add.this, admin.class);
                            startActivity(i);
                                     } catch (Exception e) {
                                         new SweetAlertDialog(add.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Failed !").show();
                                     }
                                 }
                             });
                             t.start();




                         }else {

                             Toast.makeText(add.this,"Please select the gender",Toast.LENGTH_LONG).show();
                         }



                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });




         }


    public void save_Teacher (View v){
         userNameEditText =findViewById(R.id.edit_name_teacher);
         userSSNEditText =findViewById(R.id.edit_ssn_teacher);
        userPhoneEditText =findViewById(R.id.edit_phone_teacher);
         s1=findViewById(R.id.spinner_teacher);
         maleRadioButton =findViewById(R.id.male_teacher);
         femaleRadioButton =findViewById(R.id.female_teacher);
         ageTextView =findViewById(R.id.text_age_teacher);

        String EnteredSSN = userSSNEditText.getText().toString();
        Query SSNQuery = FirebaseDatabase.getInstance().getReference().child("Teachers").orderByChild("_TeacherSSN").equalTo(EnteredSSN);

        SSNQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()>0)
                {
                    new SweetAlertDialog(add.this,SweetAlertDialog.ERROR_TYPE).setTitleText("ERROR, SSN ALRAEDY EXISTS !").show();
                   // Toast.makeText(add.this,"ERROR, SSN ALRAEDY EXISTS !",Toast.LENGTH_LONG).show();
                }

                else
                {

                    if(userNameEditText.getText().toString().equals("") && userSSNEditText.getText().toString().equals("") && userPhoneEditText.getText().toString().equals("") && ageTextView.getText().toString().equals("")){
                        Toast.makeText(add.this,"Please enter all required information",Toast.LENGTH_LONG).show();

                    }
                    else if(userNameEditText.getText().toString().equals("")){
                        Toast.makeText(add.this,"Please enter teacher name",Toast.LENGTH_LONG).show();

                    }
                    else if(userSSNEditText.getText().toString().equals("")){
                        Toast.makeText(add.this,"Please enter teacher national id ",Toast.LENGTH_LONG).show();

                    }
                    else if(userPhoneEditText.getText().toString().equals("")){

                        Toast.makeText(add.this,"Please enter phone number",Toast.LENGTH_LONG).show();
                    }

                    else if(ageTextView.getText().toString().equals("")){

                        Toast.makeText(add.this,"Please enter age ",Toast.LENGTH_LONG).show();

                    }
                    else   if(userPhoneEditText.getText().toString().length()!=10 ){
                        Toast.makeText(add.this,"Please enter the phone number in the correct format",Toast.LENGTH_LONG).show();
                    }

                    else if(maleRadioButton.isChecked()|| femaleRadioButton.isChecked()){





                        String pass_true = userPhoneEditText.getText().toString();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);

                                    myReff = FirebaseDatabase.getInstance().getReference("Teachers");
                                    TeacherRegister Teacher = new TeacherRegister();


                                    String userName = (userNameEditText.getText().toString());
                                    String userSSN = (userSSNEditText.getText().toString());
                                    String userPhone =(userPhoneEditText.getText().toString());

                                    Teacher.set_TeacherName(userName);
                                    Teacher.set_TeacherSSN(userSSN);
                                    Teacher.set_TeacherPassword(userSSN);
                                    Teacher.set_TeacherPhone(userPhone);


                                    myReff.child("teacher-"+userSSN).setValue(Teacher);
                                    move();


                                     Intent i = new Intent(add.this, admin.class);
                                              startActivity(i);
                                } catch (Exception e) {
                                    new SweetAlertDialog(add.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Failed !").show();
                                }
                            }
                        });
                        t.start();




                    }else {

                        Toast.makeText(add.this,"Please select the gender",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

public void move ()
{

}



    public void save_admin (View v){
         userNameEditText =findViewById(R.id.edit_name_admin);
         userSSNEditText =findViewById(R.id.edit_ssn_admin);
         userPhoneEditText =findViewById(R.id.edit_phone_admin);
         maleRadioButton =findViewById(R.id.male_admin);
        femaleRadioButton =findViewById(R.id.female_admin);
         adminCheckBox =findViewById(R.id.checkbox_admin);

         String EnteredSSN = userSSNEditText.getText().toString();
        Query SSNQuery = FirebaseDatabase.getInstance().getReference().child("Admins").orderByChild("_AdminSSN").equalTo(EnteredSSN);

        SSNQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount()>0)
                {
                    new SweetAlertDialog(add.this,SweetAlertDialog.ERROR_TYPE).setTitleText("ERROR, SSN ALRAEDY EXISTS !").show();
                    //Toast.makeText(add.this,"ERROR, SSN ALRAEDY EXISTS !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(userNameEditText.getText().toString().equals("") && userSSNEditText.getText().toString().equals("") && userPhoneEditText.getText().toString().equals("") ){
                        Toast.makeText(add.this,"Please enter all required information",Toast.LENGTH_LONG).show();

                    }
                    else if(userNameEditText.getText().toString().equals("")){
                        Toast.makeText(add.this,"Please enter admin name",Toast.LENGTH_LONG).show();

                    }
                    else if(userSSNEditText.getText().toString().equals("")){
                        Toast.makeText(add.this,"Please enter admin national id ",Toast.LENGTH_LONG).show();

                    }
                    else if(userPhoneEditText.getText().toString().equals("")){

                        Toast.makeText(add.this,"Please enter phone number",Toast.LENGTH_LONG).show();
                    }


                    else   if(userPhoneEditText.getText().toString().length()!=10 ){
                        Toast.makeText(add.this,"Please enter the phone number in the correct format",Toast.LENGTH_LONG).show();
                    }


                    else if(maleRadioButton.isChecked()|| femaleRadioButton.isChecked()){






                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Thread.sleep(3000);


                                    if (maleRadioButton.isChecked()){

                                        String m= maleRadioButton.getText().toString();

                                    }else {

                                        String f= femaleRadioButton.getText().toString();
                                    }

                                    myReff=FirebaseDatabase.getInstance().getReference("Admins");
                                    AdminRegister Admin = new AdminRegister();


                                    String userName = (userNameEditText.getText().toString());
                                    String userSSN = (userSSNEditText.getText().toString());
                                    String userPhone =(userPhoneEditText.getText().toString());
                                    String addPermession;
                                    if(adminCheckBox.isChecked())
                                    {
                                        addPermession="Allowed";
                                    }
                                    else {
                                        addPermession = "Not Allowed";
                                    }
                                    Admin.set_AdminName(userName);
                                    Admin.set_AdminSSN(userSSN);
                                    Admin.set_AdminPassword(userSSN);
                                    Admin.set_AdminPhone(userPhone);
                                    Admin.set_AddPermession(addPermession);


                                   //Toast.makeText(add.this,"added Admin succefully",Toast.LENGTH_LONG).show();
                                         myReff.child("Admin-"+userSSN).setValue(Admin);


                                    Intent i = new Intent(add.this, admin.class);
                                    startActivity(i);


                                } catch (Exception e) {
                                }
                            }
                        });
                        t.start();


                    }else {

                        Toast.makeText(add.this,"Please select the gender",Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {




            }
        });




    }

}