package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teacherDocHolder extends RecyclerView.ViewHolder {
    TextView docName ;
    TextView deleteDOc ;
    Button docBtn ;
    String docUrl ;
    public teacherDocHolder(@NonNull View itemView) {
        super(itemView);

        docName = itemView.findViewById(R.id.teacherdocName);
        deleteDOc = itemView.findViewById(R.id.teacherDeleteDoc);
        docBtn = itemView.findViewById(R.id.teacherDocBtn);
    }
}
