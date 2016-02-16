package com.ashleyjain.moodleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Moodle Plus");

        Intent intent = getIntent();
        String stringjson = intent.getStringExtra("response");
        try {
            JSONObject jsonObject = new JSONObject(stringjson);
            JSONObject userjson = jsonObject.getJSONObject("user");

            Toast.makeText(this,"Welcome on Moodle+ "+userjson.getString("first_name")+"!!", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
