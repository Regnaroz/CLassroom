package com.example.school_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassSettingsFragment extends Fragment {
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
    Button showAdding ;
    Button AddStudent ;
    Button showRemove ;
    Button RemoveStudent;
    Button showList;

    EditText addStudentID;
    EditText removeStudentID;

    ListView studentsList;



    public ClassSettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ClassSettingsFragment newInstance(String GradeId, String Grade , String GradeNo , String Subject , String TeacherSSN, String TeacherName) {
        ClassSettingsFragment fragment = new ClassSettingsFragment();
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

        View v =  inflater.inflate(R.layout.class_settings, container, false);;
        if (getArguments() != null) {
            GradeId = getArguments().getString(GradeIdText);
            Grade =  getArguments().getString(GradeText);
            GradeNo =  getArguments().getString(GradeNoText);
            Subject = getArguments().getString(SubjectText);
            TeacherSSN =  getArguments().getString(TeacherSSNText);
            TeacherName = getArguments().getString(TeacherNameText);

        }

        showAdding= v.findViewById(R.id.addStudentbtn);
        AddStudent = v.findViewById(R.id.confirmAdd);
        showRemove = v.findViewById(R.id.showRemove);
        RemoveStudent = v.findViewById(R.id.RemoveStudentIdButton);
        showList = v.findViewById(R.id.toggleStudentList);

         addStudentID = v.findViewById(R.id.AddStudentID);
         removeStudentID = v.findViewById(R.id.RemoveStudentID);

        studentsList = v.findViewById(R.id.StudentList);



        showAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStudent.setVisibility(View.VISIBLE);
                addStudentID.setVisibility(View.VISIBLE);
            }
        });

        showRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStudentID.setVisibility(View.VISIBLE);
                RemoveStudent.setVisibility(View.VISIBLE);
            }
        });

        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = addStudentID.getText().toString();
                if(ID.isEmpty())
                {
                    new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("The ID is Empty").show();


                }
                else
                {

                        Query registerSSN = FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("_StudentSSN").equalTo(ID);

                    registerSSN.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount()>0)
                            {

                                String studentName = snapshot.child("student-"+ID).child("_StudentName").getValue().toString();
                                String studentPhone = snapshot.child("student-"+ID).child("_StudentPhone").getValue().toString();
                                String parentName = snapshot.child("student-"+ID).child("_Parentname").getValue().toString();
                                String parentPhone = snapshot.child("student-"+ID).child("_ParentPhone").getValue().toString();
                                String parentID = snapshot.child("student-"+ID).child("_ParentSSN").getValue().toString();




                                 Query classCheck = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students").orderByChild("_StudentSSN").equalTo(ID);
                                 classCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   if (snapshot.getChildrenCount()>0)
                                       {
                                                new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Student Is Already in the class  ").show();
                                       }
                                    else
                                               {
                                                   StudentRegister student = new StudentRegister();
                                                   student.set_StudentSSN(ID);
                                                   student.set_StudentName(studentName);
                                                   student.set_StudentPhone(studentPhone);
                                                   student.set_StudentPassword(ID);
                                                   student.set_ParentSSN(parentID);
                                                   student.set_Parentname(parentName);
                                                   student.set_ParentPhone(parentPhone);



                                               myreff= FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students");

                                              myreff.child("student-"+ID).setValue(student);

                                           myreff= FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+ID).child("Classes");
                                           ClassRoomModel classRoom = new ClassRoomModel();

                                                   classRoom.setClassID(GradeId);
                                                   classRoom.setClassGarde(Grade);
                                                   classRoom.setClassTeacherSSN(TeacherSSN);
                                                   classRoom.setClassTeacherName(TeacherName);
                                                   classRoom.setClassSubject(Subject);
                                                   classRoom.setClassNo(GradeNo);
                                                   myreff.child(GradeId).setValue(classRoom);

                                             new SweetAlertDialog(v.getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText("Student is Added").show();
                                                      AddStudent.setVisibility(View.INVISIBLE);
                                                     addStudentID.setVisibility(View.INVISIBLE);
                                                     addStudentID.setText("");
                                             }


                  }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

                            }

                            else
                                new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Student Isn't Registered ").show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });





        RemoveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = removeStudentID.getText().toString();
                if(ID.isEmpty())
                {
                    new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("The ID is Empty").show();
                }
                else

                {
                    Query registerSSN = FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("_StudentSSN").equalTo(ID);

                    registerSSN.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount()>0)
                            {
                                Query classCheck = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students").orderByChild("_StudentSSN").equalTo(ID);

                                classCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.getChildrenCount()>0)
                                        {
                                            myreff =  FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Class Students").child("student-"+ID);
                                            myreff.removeValue();
                                            myreff = FirebaseDatabase.getInstance().getReference().child("Students").child("student-"+ID).child("Classes").child(GradeId);
                                            myreff.removeValue();
                                            new SweetAlertDialog(v.getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText("Student is Removed").show();
                                            removeStudentID.setVisibility(View.INVISIBLE);
                                            RemoveStudent.setVisibility(View.INVISIBLE);
                                            removeStudentID.setText("");

                                        }
                                        else
                                            new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Student Isn't In This Class !").show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else
                                new SweetAlertDialog(v.getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Student Isn't Registered").show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


        registerForContextMenu(showList);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (studentsList.getVisibility()== View.VISIBLE)
             {
                 studentsList.setVisibility(View.INVISIBLE);
             }
             else
                 studentsList.setVisibility(View.VISIBLE);

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
                             String studentID = myListID.get(position);
                             System.out.println("hehe , "+studentID);
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


                                         String Message = "Name :   "+studentName+"\nID :   "+studentID+"\nPhone :   "+studentPhone
                                                         +   "\nParent Name :   "+parentName + "\nParent ID :   "+parentID+"\nParent Phone :   "+parentPhone;
                                         openDialogInfo(Message);




                                     }
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







            }
        });


        return  v ;
    }



    private void openDialogInfo(String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Student Information ")
                .setMessage(Message)
                .setPositiveButton("Close . ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
builder.show();

    }


}