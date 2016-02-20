package com.ashleyjain.moodleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class assignmentAdapter extends AppCompatActivity {
    TextView name,desc,dead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_adapter);
        name = (TextView) findViewById(R.id.name);
        desc = (TextView) findViewById(R.id.description);
        dead = (TextView) findViewById(R.id.deadline);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        desc.setText(intent.getStringExtra("description"));
        dead.setText("Deadline:"+intent.getStringExtra("deadline"));
    }
}
