package com.example.school_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class teacherMarksFragment extends Fragment {
    private static final String GradeIdText = "GradeId";
    private static final String GradeText = "Grade";
    private static final String GradeNoText = "GradeNo";
    private static final String SubjectText = "Subject";
    private static final String TeacherSSNText = "TeacherSSN";
    private static final String TeacherNameText = "TeacherName";



    private String GradeId ;
    private String Grade;
    private String GradeNo;
    private String Subject;
    private String TeacherSSN;
    private String TeacherName;
    ArrayList<String> myListNames;
    ArrayList<String> myListID;

    DatabaseReference myreff;
    RecyclerView myRec ;
    Button addMark , submitMark,backtoList;
    teacherMarkAdapter myAdapter;


    EditText addStudentID , MarkName , MarkValue , MarkMax;
    EditText removeStudentID;

    ListView studentsList;
    public teacherMarksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static teacherMarksFragment newInstance(String GradeId, String Grade , String GradeNo , String Subject , String TeacherSSN, String TeacherName) {
        teacherMarksFragment fragment = new teacherMarksFragment();
        Bundle args = new Bundle();
        args.putString(GradeIdText, GradeId);
        args.putString(GradeText, Grade);
        args.putString(GradeNoText, GradeNo);
        args.putString(SubjectText, Subject);
        args.putString(TeacherSSNText, TeacherSSN);
        args.putString(TeacherNameText, TeacherName);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.teacher_marks_fragment, container, false);;
        if (getArguments() != null) {
            GradeId = getArguments().getString(GradeIdText);
            Grade =  getArguments().getString(GradeText);
            GradeNo =  getArguments().getString(GradeNoText);
            Subject = getArguments().getString(SubjectText);
            TeacherSSN =  getArguments().getString(TeacherSSNText);
            TeacherName = getArguments().getString(TeacherNameText);

        }

        studentsList = v.findViewById(R.id.markelist);
        MarkName = v.findViewById(R.id.teacherMarkEditText);

        MarkValue = v.findViewById(R.id.teacherMarkValueEdit);
        MarkMax = v.findViewById(R.id.teacherMarkMax);
        submitMark = v.findViewById(R.id.teacherSubmitMark);
        addMark = v.findViewById(R.id.teacherAddMark);
        myRec = v.findViewById(R.id.teacherMarksRecycler);
        backtoList = v.findViewById(R.id.backtoList);



        myreff =  FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students");
        myListNames = new ArrayList<String>();
        myListID = new ArrayList<String>();
        myreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myListNames.clear();
                myListID.clear();

                for (DataSnapshot shot : snapshot.getChildren())
                {
                    String Name = shot.child("_StudentName").getValue().toString();
                    String SSN = shot.child("_StudentSSN").getValue().toString();

                    myListNames.add(Name);
                    myListID.add(SSN);

                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,myListNames);
                studentsList.setAdapter(arrayAdapter);


                studentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        studentsList.setVisibility(View.INVISIBLE);
                        myRec.setVisibility(View.VISIBLE);
                        addMark.setVisibility(View.VISIBLE);
                        backtoList.setVisibility(View.VISIBLE);
                        String studentID = myListID.get(position);


                        Query classCheck = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentID);

                        classCheck.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {

                                    String studentName = snapshot.child("_StudentName").getValue().toString();
                                    String studentPhone = snapshot.child("_StudentPhone").getValue().toString();
                                    String parentName = snapshot.child("_Parentname").getValue().toString();
                                    String parentPhone = snapshot.child("_ParentPhone").getValue().toString();
                                    String parentID = snapshot.child("_ParentSSN").getValue().toString();


                                    myRec.setHasFixedSize(true);
                                    myRec.setLayoutManager(new LinearLayoutManager(getContext()));

                                    myreff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students")
                                            .child("student-"+studentID).child("Marks");
                                    myreff.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ArrayList<markModel> myList = new ArrayList<>();

                                            if(snapshot.exists())
                                            {
                                                for (DataSnapshot myshot : snapshot.getChildren())
                                                {
                                                    markModel mark = new markModel();
                                                    mark.markText = myshot.child("markText").getValue().toString();
                                                    mark.markValue = myshot.child("markValue").getValue().toString();
                                                    mark.markMax = myshot.child("markMax").getValue().toString();

                                                    myList.add(mark);

                                                }

                                            }
                                            myAdapter = new teacherMarkAdapter(getContext(),myList , GradeId , studentID);
                                            myRec.setAdapter(myAdapter);


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });




                                    addMark.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            addMark.setVisibility(View.INVISIBLE);
                                            MarkName.setVisibility(View.VISIBLE);
                                            MarkValue.setVisibility(View.VISIBLE);
                                            MarkMax.setVisibility(View.VISIBLE);
                                            submitMark.setVisibility(View.VISIBLE);


                                            submitMark.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    myreff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students")
                                                            .child("student-"+studentID).child("Marks");

                                                    markModel mark = new markModel();

                                                   if(MarkName.getText().toString().isEmpty()||MarkValue.getText().toString().isEmpty()||MarkMax.getText().toString().isEmpty())
                                                   {
                                                       new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Information Must not be empty !").show();

                                                   }
                                                else
                                                   {

                                                    mark.markText=MarkName.getText().toString();
                                                    mark.markValue=MarkValue.getText().toString();
                                                    mark.markMax=MarkMax.getText().toString();


                                                    myreff.child(MarkName.getText().toString()).setValue(mark).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            backtoList.setVisibility(View.INVISIBLE);
                                                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Mark Added !").show();

                                                        }
                                                    });

                                                    studentsList.setVisibility(View.VISIBLE);
                                                    myRec.setVisibility(View.INVISIBLE);
                                                    addMark.setVisibility(View.INVISIBLE);
                                                    MarkName.setVisibility(View.INVISIBLE);
                                                    MarkValue.setVisibility(View.INVISIBLE);
                                                    MarkMax.setVisibility(View.INVISIBLE);
                                                    submitMark.setVisibility(View.INVISIBLE);



                                                    MarkName.setText("");
                                                    MarkValue.setText("");
                                                    MarkMax.setText("");




                                                }
                                            }

                                        }



                                            );
                                        }
                                    });








                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

backtoList.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       backtoList.setVisibility(View.INVISIBLE);
        studentsList.setVisibility(View.VISIBLE);
        myRec.setVisibility(View.INVISIBLE);
        addMark.setVisibility(View.INVISIBLE);
        MarkName.setVisibility(View.INVISIBLE);
        MarkValue.setVisibility(View.INVISIBLE);
        MarkMax.setVisibility(View.INVISIBLE);
        submitMark.setVisibility(View.INVISIBLE);



    }
});


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return  v ;
    }






}