package com.example.school_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class teacherNoteHolder extends RecyclerView.ViewHolder {
    String GradeID , StudentID ;
    TextView NoteText,DeleteNote;
    public teacherNoteHolder(@NonNull View itemView) {
        super(itemView);
        NoteText = itemView.findViewById(R.id.NoteTextView);
        DeleteNote = itemView.findViewById(R.id.teacherDeleteNote);
    }
}
