package com.example.school_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class parentChildrenadapter extends RecyclerView.Adapter<parentChildHolder> {
    Context myContext ;
    ArrayList<childModel> myList;
    String parentSSN;


    public parentChildrenadapter(Context myContext, ArrayList<childModel> myList, String parentSSN) {
        this.myContext = myContext;
        this.myList = myList;
        this.parentSSN = parentSSN;

    }

    @NonNull
    @Override
    public parentChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_row,null);
        return new parentChildHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull parentChildHolder holder, int position) {

        holder.childSSN=myList.get(position).getChildSSN();
        holder.childName = myList.get(position).getChildName();
        holder.childtext.setText(myList.get(position).getChildName());
        holder.gotoChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(myContext,parentChidrenClasses.class);
                myIntent.putExtra("studentSSN",holder.childSSN);
                myIntent.putExtra("studentName",holder.childName);
                myIntent.putExtra("parentSSN",parentSSN);
                myContext.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
