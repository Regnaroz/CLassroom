package com.example.school_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class teacherMarkAdapter extends RecyclerView.Adapter<TeacherMarkHolder> {
    Context myContext ;
    ArrayList<markModel> myList;
    String GradeID;
    String studentID;
    String markName;
    DatabaseReference myReff;

    public teacherMarkAdapter(Context myContext, ArrayList<markModel> myList, String gradeID, String studentID) {
        this.myContext = myContext;
        this.myList = myList;
        GradeID = gradeID;
        this.studentID = studentID;
    }

    @NonNull
    @Override
    public TeacherMarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_mark_row , null);
        return new TeacherMarkHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherMarkHolder holder, int position) {


        holder.MarkText.setText(myList.get(position).getMarkText()+"\n"+myList.get(position).markValue+" / "+myList.get(position).getMarkMax());
        holder.markValue = myList.get(position).getMarkValue();
        holder.markMax = myList.get(position).getMarkMax();
        holder.GradeID = GradeID;
        holder.markName=myList.get(position).getMarkText();
        holder.StudentiD = studentID;



        holder.deleteMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Delete Class ");
                builder .setMessage("This Class Will Be Deleted ");
                builder.setPositiveButton("Delete ! . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        System.out.println( "haha , "+GradeID +", "+studentID+" , "+holder.MarkText.getText().toString());

                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students")
                                .child("student-"+studentID).child("Marks");

                        myReff.child(holder.markName).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                new SweetAlertDialog(myContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Mark removed !").show();
                            }
                        });






                        // indert remove value from parent Class


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
