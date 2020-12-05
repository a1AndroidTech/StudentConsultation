package com.a1techandroid.studentconsultant.Fragments;

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

import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.R;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class AttachmentFragment extends Fragment {
    SliderView sliderView;
    ImageView attaach;
    EditText path1, path2, path3, path4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attachment_fragment, container, false);
//        attaach = view.findViewById(R.id.attaach);
//        path1 = view.findViewById(R.id.p1);
//        path2 = view.findViewById(R.id.p2);
//        path3 = view.findViewById(R.id.p3);
//        path4 = view.findViewById(R.id.p4);
//
//        path1.setText("WA1414240995236.jpg");
//        path2.setText("IMG-20191018-WA0001.jpg");
//        path3.setText("IMG-20191018-WA0002.jpg");
//        path4.setText("IMG-20201018-WA0069.jpg");

//
        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.title.setText("Upload Documents");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1  && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File casted_image = new File(picturePath);

            cursor.close();
            // String picturePath contains the path of selected Image
        }
    }
}
