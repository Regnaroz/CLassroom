package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadFileActivity extends AppCompatActivity {






    private static final String GradeIdText = "GradeId";
    private static final String GradeText = "Grade";
    private static final String GradeNoText = "GradeNo";
    private static final String SubjectText = "Subject";
    private static final String TeacherSSNText = "TeacherSSN";
    private static final String TeacherNameText = "TeacherName";

    private String GradeId;
    private String Grade;
    private String GradeNo;
    private String Subject;
    private String TeacherSSN;
    private String TeacherName;
    StorageReference storageReference;
    DatabaseReference myRef;


    TextView selectFilebtn;
    Button UploadFileBtn;
    ProgressBar mybar;
    EditText fileNameedittext ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        Intent myIntent = getIntent();
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        GradeId = myIntent.getStringExtra(GradeIdText);
        TeacherSSN = myIntent.getStringExtra(TeacherSSNText);

        mybar = findViewById(R.id.fileprogress);
        UploadFileBtn = findViewById(R.id.confirmFile);
        selectFilebtn = findViewById(R.id.selectFilebtn);
        fileNameedittext = findViewById(R.id.fileNameText);
        UploadFileBtn.setEnabled(false);

        storageReference = FirebaseStorage.getInstance().getReference();
        myRef = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Files");
        selectFilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
    }


    public void selectFile() {

        Intent filesSelect = new Intent();
        filesSelect.setType("application/pdf");
        filesSelect.setAction(filesSelect.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(filesSelect, "PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK) {
            UploadFileBtn.setEnabled(true);
            selectFilebtn.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            UploadFileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(fileNameedittext.getText().toString().equals("")){
                        new SweetAlertDialog(uploadFileActivity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("File name is empty!").show();

                    }
                    else {
                        uploadToFire(data.getData());
                        fileNameedittext.setText("");
                        selectFilebtn.setText("");
                        UploadFileBtn.setEnabled(false);
                    }
                }
            });


        }
    }


    public void uploadToFire(Uri data)
    {
        String fileName = fileNameedittext.getText().toString();
        mybar.setVisibility(View.VISIBLE);
        StorageReference myStorage = storageReference.child("Files").child(GradeId).child(fileName);

        myStorage.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        FileModel file = new FileModel(fileName , uri.toString());

                        myRef.child(fileName).setValue(file);

                        mybar.setVisibility(View.INVISIBLE);
                        new SweetAlertDialog(uploadFileActivity.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Success !").show();







                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new SweetAlertDialog(uploadFileActivity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Failed !").show();

                    }
                });
    }


}