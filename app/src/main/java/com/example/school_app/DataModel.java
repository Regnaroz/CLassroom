package com.example.school_app;

public class DataModel {
    String data1;
    String data2;


    public DataModel(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public DataModel() {
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
