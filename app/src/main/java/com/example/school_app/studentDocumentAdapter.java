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
import cn.pedant.SweetAlert.SweetAlertDialog;

public class studentDocumentAdapter extends RecyclerView.Adapter<studentDocumentHolder> {

    ArrayList<FileModel> myList ;
    Context myContext ;
    Uri myUrl;

    public studentDocumentAdapter(ArrayList<FileModel> myList, Context myContext) {
        this.myList = myList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public studentDocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_document_row , null);

        return new studentDocumentHolder(myVIew);
    }

    @Override
    public void onBindViewHolder(@NonNull studentDocumentHolder holder, int position) {

        holder.DocName = myList.get(position).getFileName();
        holder.DocUrl = myList.get(position).getFileUril();
        holder.docNameTV.setText(holder.DocName);

        holder.gotoDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUrl=Uri.parse("");
                myUrl = Uri.parse(holder.DocUrl);
                myContext.startActivity(new Intent(Intent.ACTION_VIEW,myUrl));

            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
