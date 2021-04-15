package com.example.school_app;

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


public class teacherNotesFragment extends Fragment {
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
    Button addnote ;
    Button backtolist ;
    Button submitnote ;
    EditText editnote;
    RecyclerView myRec ;
    ArrayList<noteModel> myList;
    teacherNotAdapter myAdapter;


    ListView studentsList;
    public teacherNotesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static teacherNotesFragment newInstance(String GradeId, String Grade , String GradeNo , String Subject , String TeacherSSN, String TeacherName) {
        teacherNotesFragment fragment = new teacherNotesFragment();
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

        View v =  inflater.inflate(R.layout.teacher_notes_fragment, container, false);;
        if (getArguments() != null) {
            GradeId = getArguments().getString(GradeIdText);
            Grade =  getArguments().getString(GradeText);
            GradeNo =  getArguments().getString(GradeNoText);
            Subject = getArguments().getString(SubjectText);
            TeacherSSN =  getArguments().getString(TeacherSSNText);
            TeacherName = getArguments().getString(TeacherNameText);

        }

        backtolist=v.findViewById(R.id.backtoList);
        addnote=v.findViewById(R.id.add_note);
        submitnote=v.findViewById(R.id.submit_note);
        editnote=v.findViewById(R.id.edit_note);
         myRec =v.findViewById(R.id.teacher_note_recycler);
         myRec.setHasFixedSize(true);
         myRec.setLayoutManager(new LinearLayoutManager(getContext()));


        backtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnote.setVisibility(View.INVISIBLE);
                myRec.setVisibility(View.INVISIBLE);
                editnote.setVisibility(View.INVISIBLE);
                backtolist.setVisibility(View.INVISIBLE);
                submitnote.setVisibility(View.INVISIBLE);
                studentsList.setVisibility(View.VISIBLE);
            }
        });
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editnote.setVisibility(View.VISIBLE);
                submitnote.setVisibility(View.VISIBLE);
                addnote.setVisibility(View.INVISIBLE);
            }
        });


        studentsList = v.findViewById(R.id.nodelist);
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
                        addnote.setVisibility(View.VISIBLE);
                        studentsList.setVisibility(View.INVISIBLE);
                        myRec.setVisibility(View.VISIBLE);
                        backtolist.setVisibility(View.VISIBLE);
                        String studentID = myListID.get(position);

                        Query classCheck = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+studentID);

                        classCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {

                                    String studentName = snapshot.child("_StudentName").getValue().toString();
                                    String studentPhone = snapshot.child("_StudentPhone").getValue().toString();
                                    String parentName = snapshot.child("_Parentname").getValue().toString();
                                    String parentPhone = snapshot.child("_ParentPhone").getValue().toString();
                                    String parentID = snapshot.child("_ParentSSN").getValue().toString();

                                    myreff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students")
                                            .child("student-"+studentID).child("Notes");

                                  submitnote.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          myreff.addListenerForSingleValueEvent(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 if (editnote.getText().toString().isEmpty())
                                                 {
                                                     new SweetAlertDialog(getContext() , SweetAlertDialog.ERROR_TYPE).setTitleText("Note Text Is Empty !").show();

                                                 }
                                                 else
                                                 {

                                                     String NoteText = editnote.getText().toString();
                                                  NoteText=NoteText.replace(".","");
                                                     noteModel note = new noteModel();
                                                     note.setNoteName(NoteText);
                                                     myreff.child(NoteText).setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                         @Override
                                                         public void onSuccess(Void aVoid) {
                                                             new SweetAlertDialog(getContext() , SweetAlertDialog.SUCCESS_TYPE).setTitleText("Note Added").show();

                                                         }
                                                     });
                                                 }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {

                                              }
                                          });







                                      }
                                  });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        myreff = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students")
                                .child("student-"+studentID).child("Notes");

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

                                myAdapter = new teacherNotAdapter(getContext(),myList,GradeId,studentID,true);
                                myRec.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

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