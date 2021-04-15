package com.example.school_app;

public class FileModel {


    String fileName ;
    String fileUril;


    public FileModel(String fileName, String fileUril) {
        this.fileName = fileName;
        this.fileUril = fileUril;
    }



    public FileModel() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUril() {
        return fileUril;
    }

    public void setFileUril(String fileUril) {
        this.fileUril = fileUril;
    }
}
