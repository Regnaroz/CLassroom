package com.example.school_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class uploadVideo extends AppCompatActivity {
    //the index id of data stored from intent
    private static final String GradeIdText = "GradeId";
    private static final String GradeText = "Grade";
    private static final String GradeNoText = "GradeNo";
    private static final String SubjectText = "Subject";
    private static final String TeacherSSNText = "TeacherSSN";
    private static final String TeacherNameText = "TeacherName";


    // to store data from intent
    private String GradeId;
    private String Grade;
    private String GradeNo;
    private String Subject;
    private String TeacherSSN;
    private String TeacherName;
    static String testuri;

    Button chooseBtn;
    Button uploadBtn;
    EditText videoName;
    static Uri videoUri;
    VideoView vidView;
    MediaController myController;
    StorageReference myStorageRef;
    DatabaseReference myDataRef;
    ProgressBar mybar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Intent myIntent = getIntent();
        GradeId = myIntent.getStringExtra(GradeIdText);
        Grade = myIntent.getStringExtra(GradeText);
        GradeNo = myIntent.getStringExtra(GradeNoText);
        Subject = myIntent.getStringExtra(SubjectText);
        TeacherSSN = myIntent.getStringExtra(TeacherSSNText);
        TeacherName = myIntent.getStringExtra(TeacherNameText);

        chooseBtn = findViewById(R.id.chooseVidBtn);
        uploadBtn = findViewById(R.id.vidConfirmBtn);
        videoName = findViewById(R.id.VidoeName);
        vidView = findViewById(R.id.myVidView);
        mybar = findViewById(R.id.uploadProgress);

        System.out.println("haha ... " + (videoName.getText().toString().equals("")));
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadEvent();

            }
        });

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVideo();

            }
        });

        myController = new MediaController(this);


        vidView.setMediaController(myController);
        myController.setAnchorView(vidView);
        vidView.start();

        myStorageRef = FirebaseStorage.getInstance().getReference();
        myDataRef = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Video");

    }

    // get Video from storage method
    public void chooseVideo() {

        Intent chang = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chang, 100);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            videoUri = data.getData();
            vidView.setVideoURI(videoUri);

        }
        Toast.makeText(uploadVideo.this, "Done", Toast.LENGTH_LONG).show();

    }

    // get the extension of media
    private String getFileExtenstion(Uri videoUri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));

    }


    public void uploadEvent() {
        if (videoName.getText().toString().equals("")) {
            new SweetAlertDialog(uploadVideo.this, SweetAlertDialog.ERROR_TYPE).setTitleText("the Vidoe name is Empty").show();
        } else if (videoUri != null){
            try {
                mybar.setVisibility(View.VISIBLE);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Classes").child(GradeId).child("Videos");
                StorageReference myUploader = myStorageRef.child("Videos").child(GradeId).child(videoName.getText().toString());
                myUploader.putFile(videoUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                myUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mybar.setVisibility(View.INVISIBLE);
                                        VideoModel myvid = new VideoModel();
                                        myvid.setVideoName(videoName.getText().toString());
                                        myvid.setVideoUri(uri.toString());
                                        myRef.child(videoName.getText().toString()).setValue(myvid);
                                        new SweetAlertDialog(uploadVideo.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Success !").show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        new SweetAlertDialog(uploadVideo.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Failed!").show();
                                    }
                                });
                            }
                        });


            } catch (Exception e) {
                System.out.println("ggggghghghghghg" + e.getMessage());
            }
    }


            else
              new SweetAlertDialog(uploadVideo.this, SweetAlertDialog.ERROR_TYPE).setTitleText("No Video Is selected!").show();


        }




}







