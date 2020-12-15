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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.AttachmentModel;
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.SharedPrefrences;
import com.a1techandroid.studentconsultant.StudentViewFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.SliderView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AttachmentFragment extends Fragment {
   CardView passport, iD, ssc, hssc, ba, ma, submit;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference reference;
    private DatabaseReference reference1;
    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    private ProgressDialog mProgressDialog;
    private Uri mImageUri = null;
    AttachmentModel attachmentModel;
    ProgressDialog progressDialog;
    boolean passportUrl = false, iDurl = false, sscUrl = false, hsscUrl= false, baUrl= false, maUrl= false;
    String passport1 = "", iD1 = "", ssc1 = "", hssc1 = "", ba1 = "", ma1 = "";
    RequestModel model;
    TextView passTxt, idTxt, sscTxt, hsscTxt, baTxt, maTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attachment_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("requests");
        reference = mDatabase.getReference("Student");
//        reference1 = mDatabase.getReference("ApplyFor");
        progressDialog = new ProgressDialog(getActivity());
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(getContext());
        Bundle bundle = getArguments();
        model= (RequestModel) bundle.getSerializable("key");
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

        passTxt=view.findViewById(R.id.pasTxt);
        idTxt=view.findViewById(R.id.idTxt);
        sscTxt=view.findViewById(R.id.sscTxt);
        hsscTxt=view.findViewById(R.id.hsscTxt);
        baTxt=view.findViewById(R.id.baTxt);
        maTxt=view.findViewById(R.id.maTxt);

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
                passportUrl = true;
                iDurl = false;
                sscUrl = false;
                hsscUrl = false;
                baUrl = false;
                maUrl = false;

            readImage();
            }
        });  iD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
                passportUrl = false;
                iDurl = true;
                sscUrl = false;
                hsscUrl = false;
                baUrl = false;
                maUrl = false;
            }
        });  ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
                passportUrl = false;
                iDurl = false;
                sscUrl = true;
                hsscUrl = false;
                baUrl = false;
                maUrl = false;
            }
        });  hssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
                passportUrl = false;
                iDurl = false;
                sscUrl = false;
                hsscUrl = true;
                baUrl = false;
                maUrl = false;
            }
        });  ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
                passportUrl = false;
                iDurl = false;
                sscUrl = false;
                hsscUrl = false;
                baUrl = true;
                maUrl = false;
            }
        });  ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
                passportUrl = false;
                iDurl = false;
                sscUrl = false;
                hsscUrl = false;
                baUrl = false;
                maUrl = true;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passport1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                } else if (iD1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                }else if (ssc1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                }else if (hssc1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                }else if (ba1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                }else if (ma1.equals("")){
                    Toast.makeText(getActivity(), "Please Upload All Documents", Toast.LENGTH_SHORT).show();
                } else {
                    model.setPassport(passport1);
                    model.setId(iD1);
                    model.setSsc(ssc1);
                    model.setHssc(hssc1);
                    model.setBa(ba1);
                    model.setMa(ma1);
                    model.setStatus("pending");

                    mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""))
                            .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressDialog.setMessage("Submiting ");
                            mProgressDialog.show();
                            if (task.isSuccessful()) {
                                updateProfileStatus();
                                Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
                                mProgressDialog.hide();
                            } else {
                                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                                mProgressDialog.hide();
                            }
                        }
                    });
                }

            }
        });

    }
    public void updateProfileStatus(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = reference.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    mProgressDialog.hide();
                    Map<String, Object> postValues = new HashMap<String,Object>();
                    String key = appleSnapshot.getRef().getKey();
                    postValues.put("profileStatus", "Approved");
                    UserModel model = appleSnapshot.getValue(UserModel.class);
                    model.setProfileStatus("Approved");
                    reference.child(key).updateChildren(postValues);
                    SharedPrefrences.saveUSer(model, getActivity());
                    StudentViewFragment fragment = new StudentViewFragment();
                    replaceFragment(fragment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
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
            Uri mImageUri1 = data.getData();
//            profilePic.setImageURI(mImageUri);
            mImageUri = mImageUri1;
//                profilePic.setImageURI(mImageUri);
            final StorageReference imagePath = mFirebaseStorage.child("requests_attachments")
                    .child(mImageUri.getLastPathSegment());
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            imagePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri donwloadUrl = uri;
                            if (passportUrl == true){
                                passport1 = donwloadUrl.toString();
                                passTxt.setText("Uploaded");
                            }else if (iDurl == true){
                                iD1 = donwloadUrl.toString();
                                idTxt.setText("Uploaded");
                            }else if (sscUrl == true){
                                ssc1 = donwloadUrl.toString();
                                sscTxt.setText("Uploaded");
                            }else if (hsscUrl == true){
                                hssc1 = donwloadUrl.toString();
                                hsscTxt.setText("Uploaded");
                            }else if (baUrl == true){
                                ba1 = donwloadUrl.toString();
                                baTxt.setText("Uploaded");
                            }else if (maUrl == true){
                                ma1 = donwloadUrl.toString();
                                maTxt.setText("Uploaded");
                            }
                            progressDialog.hide();
                            String imageUrl = donwloadUrl.toString();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();
//                profilePic.setImageURI(mImageUri);
                final StorageReference imagePath = mFirebaseStorage.child("requests_attachments")
                        .child(mImageUri.getLastPathSegment());
                imagePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri donwloadUrl = uri;
                                if (passportUrl == true){
                                    passport1 = donwloadUrl.toString();
                                }else if (iDurl == true){
                                    iD1 = donwloadUrl.toString();
                                }else if (sscUrl == true){
                                    ssc1 = donwloadUrl.toString();
                                }else if (hsscUrl == true){
                                    hssc1 = donwloadUrl.toString();
                                }else if (baUrl == true){
                                    ba1 = donwloadUrl.toString();
                                }else if (maUrl == true){
                                    ma1 = donwloadUrl.toString();
                                }
                                String imageUrl = donwloadUrl.toString();
                                Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
