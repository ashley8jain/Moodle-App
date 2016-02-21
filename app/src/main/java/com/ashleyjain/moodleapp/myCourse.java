package com.ashleyjain.moodleapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ashleyjain.moodleapp.adapter.courseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class myCourse extends ListActivity {
    String[] courselist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent2 = getIntent();
        String courselis = intent2.getStringExtra("courselist");

        try {

            JSONObject jsonObject = new JSONObject(courselis);
            JSONArray coursearray = jsonObject.getJSONArray("courses");
            courselist = new String[coursearray.length()];
            for (int i = 0; i < coursearray.length(); i++) {
                JSONObject course = coursearray.getJSONObject(i);
                courselist[i] = course.getString("code");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courselist);

        setListAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(myCourse.this,courseAdapter.class);
        intent.putExtra("course",courselist[position]);
        startActivity(intent);
    }
}
