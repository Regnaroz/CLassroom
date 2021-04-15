package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class parentChildClassHolder extends RecyclerView.ViewHolder {
    TextView SubjectName , TeacherName;
    Button gotoClass ;
    String Subject , Grade , GradeNo,GradeID;

    public parentChildClassHolder(@NonNull View itemView) {
        super(itemView);
        SubjectName = itemView.findViewById(R.id.childSubjectText);
        TeacherName = itemView.findViewById(R.id.childteacherName);
        gotoClass=itemView.findViewById(R.id.goToChildClassbtn);
    }
}
