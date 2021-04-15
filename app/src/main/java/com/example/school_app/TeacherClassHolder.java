package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherClassHolder extends RecyclerView.ViewHolder {
    TextView className , classNo ;
    TextView deleteClass;
    Button gotoClass;

    String GradeId , GradeSubject,classGrade ,GradeNo ,TeacherSSN , TeacherName;
    public TeacherClassHolder(@NonNull View itemView) {
        super(itemView);

        className = itemView.findViewById(R.id.classNameTV);
        deleteClass = itemView.findViewById(R.id.deleteimg);
        gotoClass = itemView.findViewById(R.id.teacherClassBtn);
    }
}
