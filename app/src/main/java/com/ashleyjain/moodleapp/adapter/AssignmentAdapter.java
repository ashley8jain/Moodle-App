package com.ashleyjain.moodleapp.adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashleyjain.moodleapp.R;

/**
 * Created by ashleyjain on 20/02/16.
 */
public class AssignmentAdapter extends Fragment {
    TextView name,desc,dead;
    final int PICKFILE_RESULT_CODE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.assignment_adapter, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        Button browsebutton = (Button) getView().findViewById(R.id.browse);
        browsebutton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICKFILE_RESULT_CODE);

                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == PICKFILE_RESULT_CODE && intent != null && intent.getData() != null)
        {
            Uri uri = null;
            uri = intent.getData();

            if (uri.getScheme().toString().compareTo("content")==0)
            {
                Cursor cursor =getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                    Uri filePathUri = Uri.parse(cursor.getString(column_index));
                    String file_name = filePathUri.getLastPathSegment().toString();
                    EditText filename = (EditText)getView().findViewById(R.id.editText);
                    filename.setText(file_name);
                    String file_path=filePathUri.getPath();

                    Toast.makeText(getActivity(), "File Name & PATH are:" + file_name + "\n" + file_path, Toast.LENGTH_LONG).show();
                }
            }

        }else {
            Toast.makeText(getActivity(), "No file selected" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        name = (TextView) getActivity().findViewById(R.id.name);
        desc = (TextView) getActivity().findViewById(R.id.description);
        dead = (TextView) getActivity().findViewById(R.id.deadline);

        name.setText(getArguments().getString("name"));
        desc.setText(getArguments().getString("description"));
        dead.setText("Deadline:"+getArguments().getString("deadline"));
    }

}
