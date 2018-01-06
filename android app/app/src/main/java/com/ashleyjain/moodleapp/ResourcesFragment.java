package com.ashleyjain.moodleapp;

import android.content.ActivityNotFoundException;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chandudasari on 20/02/16.
 */
public class ResourcesFragment extends Fragment {
    final int PICKFILE_RESULT_CODE = 0;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_resources, container, false);
    }
   @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        Button registerbutton = (Button) getView().findViewById(R.id.attachment);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("images/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == PICKFILE_RESULT_CODE&& intent != null && intent.getData() != null)
        {

            Uri uri = intent.getData();

            if (uri.getScheme().toString().compareTo("content")==0)
            {
                Cursor cursor =getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                    Uri filePathUri = Uri.parse(cursor.getString(column_index));
                    String file_name = getContext().getContentResolver().getType(uri);

                    EditText filename = (EditText)getView().findViewById(R.id.filename);
                    filename.setText(file_name, TextView.BufferType.EDITABLE);
                    String file_path=filePathUri.getPath();

                    Toast.makeText(getActivity(), "File Name & PATH are:" + file_name + "\n" + file_path, Toast.LENGTH_LONG).show();
                }
            }

        }else {
            Toast.makeText(getActivity(), "No file selected" , Toast.LENGTH_LONG).show();
        }
    }
}

