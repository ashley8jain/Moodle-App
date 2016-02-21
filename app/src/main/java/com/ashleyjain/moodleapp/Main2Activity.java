package com.ashleyjain.moodleapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity implements FragmentChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("gnappo");
        mToolbar.showOverflowMenu();
        setSupportActionBar(mToolbar);*/

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String courselist_response = intent.getStringExtra("courses");

        /*String notJSON = intent.getStringExtra("notJSON");
        ArrayList<notifObj> notifList = new ArrayList<notifObj>();
        try {
            JSONObject jsonObject = new JSONObject(notJSON);
            JSONArray notif_array = jsonObject.getJSONArray("notifications");
            for (int i = 0; i < notif_array.length(); i++) {
                JSONObject notif = notif_array.getJSONObject(i);
                //System.out.println("<<<<<-----------"+courselist.get(0).getCode()+"&&&"+courselist.get(1).getCode()+"------------------>>>>>>>");
                notifObj nObj = new notifObj(notif.getString("user_id"),notif.getString("description"),notif.getString("is_seen "),notif.getString("created_at"),notif.getString("id"));
                notifList.add(i,nObj);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }*/

        Toast.makeText(this, "Welcome on Moodle+ " + name + "!!", Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("course_list", courselist_response);
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment);
        fTransaction.addToBackStack(fragment.toString());
        fTransaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }

}
