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

public class teacherDocAdapter extends RecyclerView.Adapter<teacherDocHolder> {

    Context myContext ;
    ArrayList<FileModel> myList ;
    String GradeId;
    DatabaseReference myReff ;

    Uri myUrl;

    public teacherDocAdapter(Context myContext, ArrayList<FileModel> myList, String gradeId) {
        this.myContext = myContext;
        this.myList = myList;
        GradeId = gradeId;
    }

    @NonNull
    @Override
    public teacherDocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_document_raw, null);
        return new teacherDocHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherDocHolder holder, int position) {

        holder.docName.setText(myList.get(position).getFileName());
        holder.docUrl = myList.get(position).getFileUril();

        holder.docBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myUrl= Uri.parse("");
                myUrl = Uri.parse(holder.docUrl);
                myContext.startActivity(new Intent(Intent.ACTION_VIEW,myUrl));

            }
        });


        holder.deleteDOc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Delete File ");
                builder .setMessage("This File Will Be Deleted ");
                builder.setPositiveButton("Delete ! . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Files");


                        myReff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if ( snapshot.exists()) {
                                    myReff.child(holder.docName.getText().toString()).removeValue();
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
