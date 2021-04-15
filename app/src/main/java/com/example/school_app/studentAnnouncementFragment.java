package com.example.school_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studentAnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentAnnouncementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String studentDataText = "studentData";
    private static final String GradeIDText = "GradeID";

    // TODO: Rename and change types of parameters
    private ArrayList<String> studentData;
    private String GradeID;

    public studentAnnouncementFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static studentAnnouncementFragment newInstance(ArrayList<String> studentData, String GradeID) {
        studentAnnouncementFragment fragment = new studentAnnouncementFragment();
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
     View  myView =  inflater.inflate(R.layout.fragment_student_announcement, container, false);



        return myView;
    }
}