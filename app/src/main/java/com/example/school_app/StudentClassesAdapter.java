package com.example.school_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentClassesAdapter extends RecyclerView.Adapter<StudentClassesHolder> {

    Context myContext;
    ArrayList<StudentClassModel> myList;
    ArrayList<String> studentData;

    public StudentClassesAdapter(Context myContext, ArrayList<StudentClassModel> myList ,ArrayList<String> studentData) {
        this.myContext = myContext;
        this.myList = myList;
        this.studentData=studentData;


    }

    @NonNull
    @Override
    public StudentClassesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_classes_row,null);

        return new StudentClassesHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentClassesHolder holder, int position) {

        holder.GradeID = myList.get(position).getGradeID();
        holder.GradeSubject = myList.get(position).getGradeSubject();
        holder.GradeTeacher = myList.get(position).getGardeTeacher();
        holder.GradeSubjectTv.setText(myList.get(position).getGradeSubject());
        holder.GradeTeacherTv.setText(myList.get(position).getGardeTeacher());

        holder.myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(myContext,studentClassRoom.class);
                myIntent.putStringArrayListExtra("studentData",studentData);
                myIntent.putExtra("GradeID",holder.GradeID);
                myContext.startActivity(myIntent);




            }
        });


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
