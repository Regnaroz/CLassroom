package com.example.school_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class studentVideoAdapter extends RecyclerView.Adapter<studentVideoHolder> {
    ArrayList<VideoModel> myList ;
    ArrayList<String> studentData;
    Context myContxt;
    Uri myUrl;

    public studentVideoAdapter(ArrayList<VideoModel> myList, ArrayList<String> studentData, Context myContxt) {
        this.myList = myList;
        this.studentData = studentData;
        this.myContxt = myContxt;
    }

    @NonNull
    @Override

    public studentVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_video_row,null);
        return new studentVideoHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull studentVideoHolder holder, int position) {

        holder.videoNameTV.setText(myList.get(position).getVideoName());
        holder.videoName = myList.get(position).getVideoName();
        holder.videoUrl = myList.get(position).getVideoUri();

        holder.gotoVidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myUrl=Uri.parse("");
                 myUrl = Uri.parse(holder.videoUrl);
                myContxt.startActivity(new Intent(Intent.ACTION_VIEW,myUrl));
            }
        });
    }



    @Override
    public int getItemCount() {
        return myList.size();
    }
}
