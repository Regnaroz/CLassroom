package com.example.school_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class childClassRoom extends AppCompatActivity {

    String GradeID , StudentSSN ,ParentSSN;
    ChipNavigationBar myNavigationBtn;
    Fragment myfragment= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_class_room);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent myIntent = getIntent();
        GradeID = myIntent.getStringExtra("GradeId");
        StudentSSN = myIntent.getStringExtra("StudentSSN");
        ParentSSN = myIntent.getStringExtra("ParentSSN");


        myNavigationBtn = findViewById(R.id.childNavigation);
        myNavigationBtn.setItemSelected(R.id.childNavigation,true);
        myfragment = childMarksFragment.newInstance(GradeID,StudentSSN,ParentSSN);
        getSupportFragmentManager().beginTransaction().replace(R.id.childFrame,myfragment).commit();

        myNavigationBtn.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i)
                {

                    case R.id.childMarks:
                        myfragment =    childMarksFragment.newInstance(GradeID,StudentSSN,ParentSSN);
                        break;

                    case R.id.childNotes:
                        myfragment = childNoteFragment.newInstance(GradeID,StudentSSN,ParentSSN);
                        break;

                }


                getSupportFragmentManager().beginTransaction().replace(R.id.childFrame,myfragment).commit();
            }
        });



    }
}