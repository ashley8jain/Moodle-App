package com.ashleyjain.moodleapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main2Activity extends Activity implements FragmentChangeListener{

    protected DrawerBuilder builder = null;
    protected AccountHeader headerResult= null;
    String notJSON;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.notification:
                Fragment notifFragment = new NotificationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("notJSON",notJSON);
                notifFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, notifFragment, notifFragment.toString())
                        .addToBackStack(notifFragment.toString())
                        .commit();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setActionBar(toolbar);
        /*mToolbar.setTitle("gnappo");
        mToolbar.showOverflowMenu();
        setSupportActionBar(mToolbar);*/

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        final String courselist_response = intent.getStringExtra("courses");
        final ArrayList<String> cCodeArray = new ArrayList<String>();
        JSONArray coursearray = null;
        try {
            JSONObject jsonObject = new JSONObject(courselist_response);
            coursearray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < coursearray.length(); i++) {
                String cCode = coursearray.getJSONObject(i).getString("code");
                cCodeArray.add(cCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notJSON = intent.getStringExtra("notJSON");
        try {
            JSONObject jsonObject = new JSONObject(notJSON);
            JSONArray notif_array = jsonObject.getJSONArray("notifications");
            int no_of_notifs = notif_array.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Welcome on Moodle+ " + name + "!!", Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("course_list", courselist_response);
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment)
                .addToBackStack("toMainFragment")
                .commit();

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email)
                )
                .withProfileImagesClickable(true)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {

                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Toast.makeText(getApplicationContext(), "Click me hoss", Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Overview").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Grades").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Notification").withIdentifier(3),
                        new SectionDrawerItem().withName("My courses")

                );
        for (int i = 0; i < coursearray.length(); i++) {
            builder.addDrawerItems(
                    new PrimaryDrawerItem().withName(cCodeArray.get(i)).withIdentifier(4+i)
            );
        }
        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Bundle courseB = new Bundle();
                switch (position) {
                    default:
                        if (position == 1) {

                        } else if (position == 2) {

                        } else if (position == 3) {
                            Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)", Toast.LENGTH_LONG).show();
                        } else {
                            courseB.putString("cCode", cCodeArray.get(position - 5));
                            CourseFragment coursefragment = new CourseFragment();
                            coursefragment.setArguments(courseB);
                            replaceFragment(coursefragment);

                            break;
                        }

                }
                return false;
            }

        });

        builder.build();

    }

    public void replaceFragment(Fragment courseFrag){
        getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, courseFrag, courseFrag.toString())
                            .addToBackStack(courseFrag.toString())
                            .commit();
    }

}
