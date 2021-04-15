package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teacherVideoHolder extends RecyclerView.ViewHolder {

  TextView videoName ;
  TextView deleteVideo;
  String videoUrl ;
   Button videoBtn ;
    public teacherVideoHolder(@NonNull View itemView) {
        super(itemView);


         videoName = itemView.findViewById(R.id.teacherVideoText);
         videoBtn = itemView.findViewById(R.id.teachervidBtn);
         deleteVideo = itemView.findViewById(R.id.teacherdeleteVideo);

    }
}
