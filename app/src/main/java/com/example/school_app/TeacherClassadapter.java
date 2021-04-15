package com.example.school_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeacherClassadapter extends RecyclerView.Adapter<TeacherClassHolder> {

    Context myContext ;
    ArrayList<ClassRoomModel> myList;
    DatabaseReference myReff;
    DatabaseReference myReff2;









    public TeacherClassadapter(Context myContext, ArrayList<ClassRoomModel> myList) {
        this.myContext = myContext;
        this.myList = myList;
    }

    @NonNull
    @Override
    public TeacherClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_class_row,null);

        return new TeacherClassHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassHolder holder, int position) {
        holder.className.setText(myList.get(position).getClassSubject()+"\n"+myList.get(position).getClassGarde()+"\nGrade No . "+myList.get(position).getClassNo());

        holder.GradeId = myList.get(position).getClassID();
        holder.GradeSubject = myList.get(position).getClassSubject();
        holder.classGrade = myList.get(position).getClassGarde();
        holder.GradeNo = myList.get(position).getClassNo();
        holder.TeacherSSN = myList.get(position).getClassTeacherSSN();
        holder.TeacherName = myList.get(position).getClassTeacherName();


        holder.gotoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(myContext,TeacherClassRoom.class);
                myIntent.putExtra("GradeId",  holder.GradeId);
                myIntent.putExtra("Grade",   holder.classGrade);
                myIntent.putExtra("GradeNo", holder.GradeNo);
                myIntent.putExtra("Subject",  holder.GradeSubject);
                myIntent.putExtra("TeacherSSN", holder.TeacherSSN);
                myIntent.putExtra("TeacherName",holder.TeacherName);
                myContext.startActivity(myIntent);
            }
        });

        holder.deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Delete Class ");
                builder .setMessage("This Class Will Be Deleted ");
                builder.setPositiveButton("Delete ! . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String TeacherSSN = holder.TeacherSSN;
                       String  GradeID = holder.GradeId;

                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students");
                        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String studentSSN;

                                for(DataSnapshot myshot : snapshot.getChildren())
                                {
                                    studentSSN = myshot.child("_StudentSSN").getValue().toString();


                                  myReff2 = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentSSN).child("Classes");
                                  myReff2.child(GradeID).removeValue();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes");
                        myReff.child(GradeID).removeValue();

                        myReff = FirebaseDatabase.getInstance().getReference().child("Teachers").child("teacher-"+TeacherSSN).child("_Classes");
                        myReff.child(GradeID).removeValue();

                        new SweetAlertDialog(myContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Class Has Been Deleted .").show();

                    }
                });
                builder.setNegativeButton("Cancel . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.show();

            }
        });





    }



    @Override
    public int getItemCount() {
        return myList.size();
    }


}
