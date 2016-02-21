package com.ashleyjain.moodleapp.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ashleyjain.moodleapp.R;

public class courseAdapter extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_adapter);
        textView = (TextView) findViewById(R.id.coursename);
        Intent in = getIntent();
        textView.setText(in.getStringExtra("course"));
    }

}
