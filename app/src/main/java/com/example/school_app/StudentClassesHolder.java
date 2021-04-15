package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentClassesHolder extends RecyclerView.ViewHolder {

    String GradeID , GradeSubject , GradeTeacher ;
    TextView GradeSubjectTv , GradeTeacherTv;
    Button myBtn ;
    public StudentClassesHolder(@NonNull View itemView) {
        super(itemView);

        GradeSubjectTv = itemView.findViewById(R.id.subjectTv);
        GradeTeacherTv = itemView.findViewById(R.id.teacherNameTv);
        myBtn = itemView.findViewById(R.id.goToStudentclass);

    }
}
