package com.example.school_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent1;


    private CardView stu;
    private CardView tesch;
    private CardView adm;
    private CardView par;





    //static TextView textView55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }




        setContentView(R.layout.activity_main);


        //event on card student
        stu = findViewById(R.id.student_card);
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(MainActivity.this, act2.class);
                intent1.putExtra("idname", "Students");
                startActivity(intent1);
            }
        });
        //event card tracher
        tesch = findViewById(R.id.teacher_card);
        tesch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent1 = new Intent(MainActivity.this, act2.class);
                intent1.putExtra("idname", "Teachers");
                startActivity(intent1);
            }
        });
        //event card admin
        adm = findViewById(R.id.admin_card);
        adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(MainActivity.this, act2.class);
                intent1.putExtra("idname", "Admins");
                startActivity(intent1);
            }
        });


        par = findViewById(R.id.parents_card);
        par.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(MainActivity.this, act2.class);
                intent1.putExtra("idname", "Parents");
                startActivity(intent1);
            }
        });









    }




    }











