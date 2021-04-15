package com.example.school_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teacherVideoadapter extends RecyclerView.Adapter<teacherVideoHolder> {

    Context myContext ;
    ArrayList<VideoModel> myList ;
    String GradeId;
    Uri myUrl;
    DatabaseReference myReff ;

    public teacherVideoadapter(Context myContext, ArrayList<VideoModel> myList , String GradeId) {
        this.myContext = myContext;
        this.myList = myList;
        this.GradeId= GradeId;
    }


    @NonNull
    @Override
    public teacherVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_video_raw , null);
        return  new teacherVideoHolder(myVIew);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherVideoHolder holder, int position) {

        holder.videoName.setText(myList.get(position).getVideoName());
        holder.videoUrl = myList.get(position).getVideoUri();
        holder.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myUrl= Uri.parse("");
                myUrl = Uri.parse(holder.videoUrl);
                myContext.startActivity(new Intent(Intent.ACTION_VIEW,myUrl));
            }
        });

        holder.deleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Delete Video ");
                builder .setMessage("This Video Will Be Deleted ");
                builder.setPositiveButton("Delete ! . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Videos");


                        myReff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if ( snapshot.exists()) {
                                    myReff.child(holder.videoName.getText().toString()).removeValue();
                                }

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }
                });
                builder.setNegativeButton("Cancel . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

        }



    @Override
    public int getItemCount() {
        return myList.size();
    }
}
