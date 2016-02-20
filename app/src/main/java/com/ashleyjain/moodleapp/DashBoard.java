package com.ashleyjain.moodleapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashBoard extends ListActivity {
    final Context context = DashBoard.this;
    String[] coursecode,coursename;
    @Override
    public void onBackPressed() {
        Log.d("CDA", "Onback");
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);



        Intent intent = getIntent();
        String stringjson = intent.getStringExtra("response");
        String courselis = intent.getStringExtra("courselist");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringjson);
            JSONObject userjson = jsonObject.getJSONObject("user");

            Toast.makeText(this,"Welcome on Moodle+ "+userjson.getString("first_name")+"!!", Toast.LENGTH_LONG).show();


            JSONObject jsonObject2 = new JSONObject(courselis);
            JSONArray coursearray = jsonObject2.getJSONArray("courses");
            coursecode = new String[coursearray.length()];
            coursename = new  String[coursearray.length()];
            for (int i = 0; i < coursearray.length(); i++) {
                JSONObject course = coursearray.getJSONObject(i);
                coursecode[i] = course.getString("code");
                coursename[i] = course.getString("name");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,coursecode);
            setListAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(DashBoard.this,courseAdapter.class);
        intent.putExtra("coursecode",coursecode[position]);
        intent.putExtra("coursename",coursename[position]);
        startActivity(intent);
    }

}
