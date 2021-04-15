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
 * Use the {@link studentVideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentVideosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String studentDataText = "studentData";
    private static final String GradeIDText = "GradeID";

    // TODO: Rename and change types of parameters
    private ArrayList<String> studentData;
    private String GradeID;

    RecyclerView videoRecycle;
    studentVideoAdapter myAdapter;
    DatabaseReference myref;

    public studentVideosFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static studentVideosFragment newInstance(ArrayList<String> studentData, String GradeID) {
        studentVideosFragment fragment = new studentVideosFragment();
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
     View  myView =  inflater.inflate(R.layout.fragment_student_videos, container, false);

     videoRecycle = myView.findViewById(R.id.studentVideoRecycler);
     videoRecycle.setHasFixedSize(true);
     videoRecycle.setLayoutManager(new LinearLayoutManager(getContext()));


     myref = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeID).child("Videos");

     myref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             ArrayList<VideoModel> myList = new ArrayList<>();
             for (DataSnapshot myshot : snapshot.getChildren())
             {

                String videoName = myshot.child("videoName").getValue().toString();
                String videoUrl = myshot.child("videoUri").getValue().toString();



                 VideoModel video = new VideoModel();
                 video.setVideoName(videoName);
                 video.setVideoUri(videoUrl);

                 myList.add(video);

             }
             myAdapter = new studentVideoAdapter( myList , studentData , getContext());
             videoRecycle.setAdapter(myAdapter);


         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



        return myView;
    }
}