package com.example.school_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link childMarksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class childMarksFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String GradeID;
    private String StudentID;
    private String ParentID;


    RecyclerView myRecycle ;
    DatabaseReference myReff ;
    ArrayList<markModel> myList;
    studentMarkAdapter myAdapter;



    public childMarksFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static childMarksFragment newInstance(String GradeID, String StudentID , String ParentID) {
        childMarksFragment fragment = new childMarksFragment();
        Bundle args = new Bundle();
        args.putString("GradeID", GradeID);
        args.putString("StudentID", StudentID);
        args.putString("ParentID",ParentID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            GradeID = getArguments().getString("GradeID");
            StudentID = getArguments().getString("StudentID");
            ParentID = getArguments().getString("ParentID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_child_marks, container, false);







        myRecycle = v.findViewById(R.id.childMarksRecycle);
        myRecycle.setHasFixedSize(true);
        myRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        myReff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students")
                .child("student-"+StudentID).child("Marks");

        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList = new ArrayList<>();
                if (snapshot.exists())
                {

                    for(DataSnapshot myShot : snapshot.getChildren())
                    {
                        markModel mark = new markModel();

                        mark.setMarkText(myShot.child("markText").getValue().toString());
                        mark.setMarkValue(myShot.child("markValue").getValue().toString());
                        mark.setMarkMax(myShot.child("markMax").getValue().toString());

                        myList.add(mark);


                    }


                }


                myAdapter = new studentMarkAdapter(getContext(),myList,StudentID,GradeID);
                myRecycle.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return v;
    }
}