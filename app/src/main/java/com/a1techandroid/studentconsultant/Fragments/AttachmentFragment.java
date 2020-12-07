package com.a1techandroid.studentconsultant.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.AttachmentModel;
import com.a1techandroid.studentconsultant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.SliderView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class AttachmentFragment extends Fragment {
   CardView passport, iD, ssc, hssc, ba, ma, submit;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    private ProgressDialog mProgressDialog;
    private Uri mImageUri = null;
    AttachmentModel attachmentModel;
    String passportUrl, iDurl, sscUrl, hsscUrl, baUrl, maUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attachment_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("officers");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(getContext());
        initViews(view);
        return view;
    }



    public void initViews(View view){
        passport=view.findViewById(R.id.passport);
        iD=view.findViewById(R.id.iD);
        ssc=view.findViewById(R.id.ssc);
        hssc=view.findViewById(R.id.hssc);
        ba=view.findViewById(R.id.ba);
        ma=view.findViewById(R.id.ma);
        submit=view.findViewById(R.id.submit);
    }

    public void readImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void image(){
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(i, 1 );
    }

    public void setUpClick(){
        passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            readImage();
            }
        });  iD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();

            }
        });  ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();

            }
        });  hssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();

            }
        });  ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();

            }
        });  ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.title.setText("Upload Documents");
        setUpClick();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
//            profilePic.setImageURI(mImageUri);
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();
//                profilePic.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
