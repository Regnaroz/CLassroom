package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class parentChildHolder extends RecyclerView.ViewHolder {
    String childSSN , childName;
    TextView childtext;
    Button gotoChild;
    public parentChildHolder(@NonNull View itemView) {
        super(itemView);
        childtext = itemView.findViewById(R.id.teacherVideoText);
        gotoChild = itemView.findViewById(R.id.teachervidBtn);
    }
}
