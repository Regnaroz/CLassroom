package com.example.school_app;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link childNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class childNoteFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String GradeID;
    private String StudentID;
    private String ParentID;

    RecyclerView myRec ;
    DatabaseReference myreff;
    ArrayList<noteModel> myList;
    teacherNotAdapter myAdapter;

    TextView myText ;

    public childNoteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static childNoteFragment newInstance(String GradeID, String StudentID , String ParentID) {
        childNoteFragment fragment = new childNoteFragment();
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
        View v = inflater.inflate(R.layout.fragment_child_note,null);


        myRec =v.findViewById(R.id.childNoteRecycle);
        myRec.setHasFixedSize(true);
        myRec.setLayoutManager(new LinearLayoutManager(getContext()));

        myreff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Class Students")
                .child("student-"+StudentID).child("Notes");

        myreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList = new ArrayList<>();
                if(snapshot.exists())
                {

                    for (DataSnapshot myShot : snapshot.getChildren())
                    {
                        noteModel note = new noteModel();
                        note.setNoteText( myShot.child("noteName").getValue().toString());
                        myList.add(note);

                    }

                }

                myAdapter = new teacherNotAdapter(getContext(),myList,GradeID,StudentID,false);
                myRec.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return v;
    }
}