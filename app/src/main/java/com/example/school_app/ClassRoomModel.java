package com.example.school_app;

import java.util.ArrayList;

public class ClassRoomModel {

   String classID;
   String classGarde;
   String classTeacherName;
   String classTeacherSSN;
   String classSubject;
   String classNo;
   ArrayList classStudents;

   public ClassRoomModel() {

   }

   public String getClassID() {
      return classID;
   }

   public void setClassID(String classID) {
      this.classID = classID;
   }

   public String getClassTeacherName() {
      return classTeacherName;
   }

   public void setClassTeacherName(String classTeacherName) {
      this.classTeacherName = classTeacherName;
   }

   public String getClassSubject() {
      return classSubject;
   }

   public void setClassSubject(String classSubject) {
      this.classSubject = classSubject;
   }

   public ArrayList getClassStudents() {
      return classStudents;
   }

   public void setClassStudents(ArrayList classStudents) {
      this.classStudents = classStudents;
   }
   public String getClassNo() {
      return classNo;
   }

   public void setClassNo(String classNo) {
      this.classNo = classNo;
   }

   public String getClassTeacherSSN() {
      return classTeacherSSN;
   }

   public void setClassTeacherSSN(String classTeacherSSN) {
      this.classTeacherSSN = classTeacherSSN;
   }

   public String getClassGarde() {
      return classGarde;
   }

   public void setClassGarde(String classGarde) {
      this.classGarde = classGarde;
   }
}
