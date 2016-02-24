package com.ashleyjain.moodleapp.items;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.ashleyjain.moodleapp.R;

public class ProfileDetail extends AppCompatActivity {
    EditText fn,usnm,eml,entno,typ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fn = (EditText) findViewById(R.id.fullname);
        usnm = (EditText) findViewById(R.id.username2);
        eml = (EditText) findViewById(R.id.email2);
        entno = (EditText) findViewById(R.id.entryno2);
        typ = (EditText) findViewById(R.id.type2);

        Intent intent = getIntent();
        fn.setText(intent.getStringExtra("fullname"));
        usnm.setText(intent.getStringExtra("username"));
        eml.setText(intent.getStringExtra("email"));
        entno.setText(intent.getStringExtra("entry_no"));
        typ.setText(intent.getStringExtra("type_"));


    }
}
