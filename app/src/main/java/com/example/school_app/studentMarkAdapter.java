package com.example.school_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class studentMarkAdapter extends RecyclerView.Adapter<TeacherMarkHolder> {

    Context myContect ;
    ArrayList<markModel> myList;
    String StudentID , GradeID ;

    public studentMarkAdapter(Context myContect, ArrayList<markModel> myList, String studentID, String gradeID) {
        this.myContect = myContect;
        this.myList = myList;
        StudentID = studentID;
        GradeID = gradeID;
    }

    @NonNull
    @Override
    public TeacherMarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_mark_row , null);

        return new TeacherMarkHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherMarkHolder holder, int position) {

        holder.markName = myList.get(position).getMarkText();
        holder.StudentiD = StudentID;
        holder.GradeID = GradeID;
        holder.markValue=myList.get(position).getMarkValue();
        holder.markMax = myList.get(position).getMarkMax();
        String text ="\t\t\t\t"+ myList.get(position).getMarkText()+"\n\t\t\t\t"+myList.get(position).getMarkValue()+" / "+myList.get(position).getMarkMax();
        holder.MarkText.setText(text);
        holder.deleteMark.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
