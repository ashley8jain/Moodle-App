package com.ashleyjain.moodleapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

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


public class Main2Activity extends AppCompatActivity implements FragmentChangeListener{
    protected DrawerBuilder builder = null;
    protected AccountHeader headerResult= null;
    String notJSON;


    //after pressing back button,go to homepage instead of login page
    @Override
    public void onBackPressed() {
        Log.d("CDA", "Onback");
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

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
        setSupportActionBar(toolbar);
        /*mToolbar.setTitle("gnappo");
        mToolbar.showOverflowMenu();
        setSupportActionBar(mToolbar);*/

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        final String courselist_response = intent.getStringExtra("courses");

        notJSON = intent.getStringExtra("notJSON");
        try {
            JSONObject jsonObject = new JSONObject(notJSON);
            JSONArray notif_array = jsonObject.getJSONArray("notifications");
            int no_of_notifs = notif_array.length();
        }
        catch (JSONException e) {
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
                        new SectionDrawerItem().withName("My courses"),
                        new PrimaryDrawerItem().withName("Course 1").withIdentifier(4),
                        new PrimaryDrawerItem().withName("Course 2").withIdentifier(5)

                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                        Bundle courseB = new Bundle();
                        switch (position) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:

                                break;
                            case 4:
                                try {
                                    JSONObject jsonObject = new JSONObject(courselist_response);
                                    JSONArray coursearray = jsonObject.getJSONArray("courses");
                                    String cCode = coursearray.getJSONObject(0).getString("code");
                                    courseB.putString("cCode", cCode);
                                    CourseFragment coursefragment = new CourseFragment();
                                    coursefragment.setArguments(courseB);
                                    replaceFragment(coursefragment);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 5:
                                try {
                                    JSONObject jsonObject = new JSONObject(courselist_response);
                                    JSONArray coursearray = jsonObject.getJSONArray("courses");
                                    String cCode = coursearray.getJSONObject(0).getString("code");
                                    courseB.putString("cCode", cCode);
                                    CourseFragment coursefragment = new CourseFragment();
                                    coursefragment.setArguments(courseB);
                                    replaceFragment(coursefragment);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        return false;
                    }

                });

        Drawer drawer = builder.build();

    }

    public void replaceFragment(Fragment courseFrag){
        getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, courseFrag, courseFrag.toString())
                            .addToBackStack(courseFrag.toString())
                            .commit();
    }

}
