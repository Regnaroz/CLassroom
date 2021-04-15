package com.example.school_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class studentDocumentHolder extends RecyclerView.ViewHolder {
    TextView docNameTV;
    Button gotoDocBtn ;

    String DocName , DocUrl;
    public studentDocumentHolder(@NonNull View itemView) {
        super(itemView);

        docNameTV = itemView.findViewById(R.id.documentNameTV);
        gotoDocBtn = itemView.findViewById(R.id.gotoDocumentBtn);
    }
}
