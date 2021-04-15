package com.example.school_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class teacherNotAdapter extends RecyclerView.Adapter<teacherNoteHolder> {
    Context myContext ;
    ArrayList<noteModel> myList;
    String GradeID;
    String StudentID;
    DatabaseReference myReff;
    boolean type = false;

    public teacherNotAdapter(Context myContext, ArrayList<noteModel> myList, String gardeID, String studentID , boolean type) {
        this.myContext = myContext;
        this.myList = myList;
        GradeID = gardeID;
        StudentID = studentID;
        this.type=type;
    }

    @NonNull
    @Override
    public teacherNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row , null);

        return new teacherNoteHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherNoteHolder holder, int position) {

        holder.NoteText.setText(myList.get(position).getNoteText());
        holder.StudentID = StudentID;
        holder.GradeID = GradeID;

        if(type)
        {
            holder.DeleteNote.setVisibility(View.VISIBLE);
        }
        else
            holder.DeleteNote.setVisibility(View.INVISIBLE);


        holder.DeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Delete Note ");
                builder.setMessage("This Note Will Be Deleted ");
                builder.setPositiveButton("Delete ! . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students")
                                .child("student-" + StudentID).child("Notes");

                        myReff.child(holder.NoteText.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                new SweetAlertDialog(myContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Note removed !").show();
                            }
                        });


                        // indert remove value from parent Class


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

