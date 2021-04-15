package com.example.school_app;

public class noteModel {
    String noteText ;
    String noteName ;

    public noteModel(String noteText, String noteName) {
        this.noteText = noteText;
        this.noteName = noteName;
    }

    public noteModel() {
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
}
