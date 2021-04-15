package com.example.school_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FileViewHolder extends RecyclerView.ViewHolder {

    TextView fileNameSingle , fileUrlSingle;
    public FileViewHolder(@NonNull View itemView) {
        super(itemView);

        fileNameSingle = itemView.findViewById(R.id.fileNameSingle);
        fileUrlSingle = itemView.findViewById(R.id.fileUrlSingle);



    }
}
