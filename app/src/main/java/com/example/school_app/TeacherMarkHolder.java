package com.example.school_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherMarkHolder extends RecyclerView.ViewHolder {
    TextView MarkText, deleteMark;
    String markValue , markMax , GradeID , StudentiD, markName;
    public TeacherMarkHolder(@NonNull View itemView) {
        super(itemView);
        MarkText = itemView.findViewById(R.id.teacherMarkText);
        deleteMark = itemView.findViewById(R.id.teacherdeletemark);
    }
}
