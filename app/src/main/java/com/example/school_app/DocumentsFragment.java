package com.example.school_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
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
    Button uploadFilebtn;


    RecyclerView myRecycler ;
    teacherDocAdapter myAdapter;

    DatabaseReference myref ;






    // TODO: Rename and change types and number of parameters
    // TODO: Rename and change types and number of parameters
    public static DocumentsFragment newInstance(String GradeId,String Grade , String GradeNo ,String Subject , String TeacherSSN,String TeacherName) {
        DocumentsFragment fragment = new DocumentsFragment();
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
          View v = inflater.inflate(R.layout.fragment_documents, container, false);
        if (getArguments() != null) {
            GradeId = getArguments().getString(GradeIdText);
            Grade =  getArguments().getString(GradeText);
            GradeNo =  getArguments().getString(GradeNoText);
            Subject = getArguments().getString(SubjectText);
            TeacherSSN =  getArguments().getString(TeacherSSNText);
            TeacherName = getArguments().getString(TeacherNameText);


        }

    uploadFilebtn= v.findViewById(R.id.uploadFileBtn);
        uploadFilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity() , uploadFileActivity.class);
                myIntent.putExtra(GradeIdText,GradeId);
                myIntent.putExtra(TeacherSSNText,TeacherSSN);
                startActivity(myIntent);

            }
        });



        myRecycler = v.findViewById(R.id.teacherDocRecycle);
        myRecycler.setHasFixedSize(true);
        myRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        myref = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Files");


        myref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<FileModel> myList  = new ArrayList<>();

                for(DataSnapshot myShot : snapshot.getChildren())
                {
                    FileModel myDoc = new FileModel();
                    myDoc.setFileName(myShot.child("fileName").getValue().toString());
                    myDoc.setFileUril(myShot.child("fileUril").getValue().toString());

                    myList.add(myDoc);
                }

                myAdapter = new teacherDocAdapter(getContext() , myList , GradeId);
                myRecycler.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;


    }








}