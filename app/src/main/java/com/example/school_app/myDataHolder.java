package com.example.school_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myDataHolder extends RecyclerView.ViewHolder {

    TextView dataName , dataMajor;
    public myDataHolder(@NonNull View itemView) {
        super(itemView);

        dataName= itemView.findViewById(R.id.dataName);
        dataMajor=itemView.findViewById(R.id.dataMajor);
    }
}
