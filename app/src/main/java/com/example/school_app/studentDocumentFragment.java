package com.example.school_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studentDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentDocumentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String studentDataText = "studentData";
    private static final String GradeIDText = "GradeID";


    RecyclerView docrecycle;
    DatabaseReference myRef ;
    studentDocumentAdapter myAdapter ;

    // TODO: Rename and change types of parameters
    private ArrayList<String> studentData;
    private String GradeID;

    public studentDocumentFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static studentDocumentFragment newInstance(ArrayList<String> studentData, String GradeID) {
        studentDocumentFragment fragment = new studentDocumentFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(studentDataText, studentData);
        args.putString(GradeIDText, GradeID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentData = getArguments().getStringArrayList(studentDataText);
            GradeID = getArguments().getString(GradeIDText);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View  myView =  inflater.inflate(R.layout.fragment_student_document, container, false);


     docrecycle = myView.findViewById(R.id.studentDocumentRecycler);
     docrecycle.setHasFixedSize(true);
     docrecycle.setLayoutManager(new LinearLayoutManager(getContext()));

     myRef = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Files");
     myRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             ArrayList<FileModel> myList = new ArrayList<>();
             for (DataSnapshot myshot : snapshot.getChildren())
             {
                 String docName = myshot.child("fileName").getValue().toString();
                 String docUrl = myshot.child("fileUril").getValue().toString();

                 FileModel document = new FileModel();
                 document.setFileName(docName);
                 document.setFileUril(docUrl);

                 myList.add(document);

             }

             myAdapter = new studentDocumentAdapter(myList , getContext());
             docrecycle.setAdapter(myAdapter);

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


        return myView;
    }
}