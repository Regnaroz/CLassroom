package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class studentVideoHolder extends RecyclerView.ViewHolder {

    TextView videoNameTV ;
    Button gotoVidBtn ;

    String videoName , videoUrl;
    public studentVideoHolder(@NonNull View itemView) {
        super(itemView);

        videoNameTV = itemView.findViewById(R.id.videoNameTV);
        gotoVidBtn = itemView.findViewById(R.id.gotoVideoBtn);

    }
}
