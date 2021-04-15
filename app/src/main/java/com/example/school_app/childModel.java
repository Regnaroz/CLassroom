package com.example.school_app;

public class childModel {

    String childSSN ;
    String parentSSN;
    String childName;

    public childModel() {
    }

    public childModel(String childSSN, String parentSSN, String childName) {
        this.childSSN = childSSN;
        this.parentSSN = parentSSN;
        this.childName = childName;
    }

    public String getChildSSN() {
        return childSSN;
    }

    public void setChildSSN(String childSSN) {
        this.childSSN = childSSN;
    }

    public String getParentSSN() {
        return parentSSN;
    }

    public void setParentSSN(String parentSSN) {
        this.parentSSN = parentSSN;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
