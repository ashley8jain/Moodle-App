package com.ashleyjain.moodleapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class assignment extends ListActivity {
    Context context = assignment.this;
    String[] assignName,assignDeadline,assignDescr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Intent intent = getIntent();
        String coursecode = intent.getStringExtra("coursecode");
        String url = "http://10.192.43.84:8000/courses/course.json/"+coursecode+"/assignments";
        final ProgressDialog dialog = ProgressDialog.show(context,"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("assignments");
                    assignName = new String[arr.length()];
                    assignDeadline = new String[arr.length()];
                    assignDescr = new String[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        assignName[i] = ass.getString("name");
                        assignDeadline[i] = ass.getString("deadline");
                        assignDescr[i] = ass.getString("description");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,assignName);
                setListAdapter(adapter);
            }
        }, context, url, dialog);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(assignment.this,assignmentAdapter.class);
        intent.putExtra("name",assignName[position]);
        intent.putExtra("description", assignDescr[position]);
        intent.putExtra("deadline",assignDeadline[position]);
        startActivity(intent);
    }

}
