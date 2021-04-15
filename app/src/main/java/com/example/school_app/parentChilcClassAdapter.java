package com.example.school_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class parentChilcClassAdapter extends RecyclerView.Adapter<parentChildClassHolder> {

    Context myContext ;

    ArrayList<ClassRoomModel> myList;
    String StudentSSN , ParentSSN;

    public parentChilcClassAdapter(Context myContext, ArrayList<ClassRoomModel> myList, String studentSSN, String parentSSN) {
        this.myContext = myContext;
        this.myList = myList;
        StudentSSN = studentSSN;
        ParentSSN = parentSSN;
    }

    @NonNull
    @Override
    public parentChildClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_class_row,null);

        return new parentChildClassHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull parentChildClassHolder holder, int position) {

        holder.Subject = myList.get(position).getClassSubject();
        holder.Grade = myList.get(position).getClassGarde();
        holder.GradeNo= myList.get(position).getClassNo();
        holder.TeacherName.setText("Mr."+myList.get(position).getClassTeacherName());

        holder.SubjectName.setText(holder.Subject+"\n"+holder.Grade+"\n"+"No."+holder.GradeNo);
        holder.GradeID = myList.get(position).getClassID();

        holder.gotoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("haha , "+holder.GradeID);

                Intent myIntent = new Intent(myContext,childClassRoom.class);
                myIntent.putExtra("GradeId",holder.GradeID);
                myIntent.putExtra("StudentSSN",StudentSSN);
                myIntent.putExtra("ParentSSN",ParentSSN);
                myContext.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
