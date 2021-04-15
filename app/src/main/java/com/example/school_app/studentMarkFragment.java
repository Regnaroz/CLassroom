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
 * Use the {@link studentMarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentMarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String studentDataText = "studentData";
    private static final String GradeIDText = "GradeID";

    // TODO: Rename and change types of parameters
    private ArrayList<String> studentData;
    private String GradeID;

    RecyclerView myRecycle ;
    DatabaseReference myReff ;
    ArrayList<markModel> myList;
    studentMarkAdapter myAdapter;

    public studentMarkFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static studentMarkFragment newInstance(ArrayList<String> studentData, String GradeID) {
        studentMarkFragment fragment = new studentMarkFragment();
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
     View  myView =  inflater.inflate(R.layout.fragment_student_mark, container, false);

     myRecycle = myView.findViewById(R.id.studentMarkRecycle);
     myRecycle.setHasFixedSize(true);
     myRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

     myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students")
             .child("student-"+studentData.get(0)).child("Marks");

     myReff.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists())
             {
                 myList = new ArrayList<>();
                 for(DataSnapshot myShot : snapshot.getChildren())
                 {
                     markModel mark = new markModel();

                     mark.setMarkText(myShot.child("markText").getValue().toString());
                     mark.setMarkValue(myShot.child("markValue").getValue().toString());
                     mark.setMarkMax(myShot.child("markMax").getValue().toString());

                     myList.add(mark);


                 }
                 myAdapter = new studentMarkAdapter(getContext(),myList,studentData.get(0),GradeID);
                 myRecycle.setAdapter(myAdapter);

             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



        return myView;
    }
}