package com.ashleyjain.moodleapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class courseAdapter extends AppCompatActivity {
    TextView textView,textView2;
    Button assign,res,thr,grade;
    String coursecode;
    private Context context = courseAdapter.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_adapter);
        textView = (TextView) findViewById(R.id.coursecode);
        textView2 = (TextView) findViewById(R.id.coursename);
        assign = (Button) findViewById(R.id.assignments);
        res = (Button) findViewById(R.id.resources);
        thr = (Button) findViewById(R.id.threads);
        grade = (Button) findViewById(R.id.grades);
        final Intent in = getIntent();
        coursecode = in.getStringExtra("coursecode");
        textView.setText(coursecode);
        textView2.setText(in.getStringExtra("coursename"));
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, assignment.class);
                intent.putExtra("coursecode", coursecode);
                startActivity(intent);
            }
        });

    }

}
