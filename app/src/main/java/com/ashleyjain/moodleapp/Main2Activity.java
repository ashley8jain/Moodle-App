package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;

import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashleyjain.moodleapp.items.ProfileDetail;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity implements FragmentChangeListener{

    protected Drawer drawer = null;
    protected AccountHeader headerResult= null;
    String notJSON;
    Toolbar toolbar;
    Context context = Main2Activity.this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.notification:
                String url3 = "http://"+ ((myApplication) getApplication()).getLocalHost()+"/default/notifications.json";
                final ProgressDialog dialog1 = ProgressDialog.show(this, "", "Fetching Details...", true);
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String notResult) {
                        System.out.println(notResult);
                        notJSON = notResult;
                        if (!((getSupportFragmentManager().findFragmentById(R.id.fragment_container)) instanceof NotificationFragment)) {
                            Fragment notifFragment = new NotificationFragment();
                            Bundle bundle = new Bundle();
                            Boolean IsNotif = false;
                            if (notJSON != null) {
                                bundle.putString("notJSON", notJSON);
                                IsNotif = true;
                            }
                            bundle.putBoolean("IsNotif", IsNotif);
                            notifFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, notifFragment, notifFragment.toString())
                                    .addToBackStack(notifFragment.toString())
                                    .commit();
                        }
                    }
                }, this, url3, dialog1);
                return true;
            case R.id.material_drawer_switch:
                drawer.openDrawer();
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
        final Context context = this;
        final myApplication  applcn = (myApplication) getApplication();

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String username = intent.getStringExtra("username");
        final String entryno2 = intent.getStringExtra("entry_no");
        final String type = intent.getStringExtra("type_");

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

        Toast.makeText(this, "Welcome on Moodle+ " + name + "!!", Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("course_list", courselist_response);
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container, fragment)
                                    //.addToBackStack("toMainFragment")
                                    .commit();
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email),
                        new ProfileSettingDrawerItem()
                                .withName("Log Out")
                                .withIcon(R.drawable.ic_power_settings_new_black_24dp)
                )
                .withHeaderBackground(R.drawable.iit_delhi)
                .withProfileImagesClickable(true)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {

                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        replaceFragment(profileFragment);

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
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_LONG).show();
                        return false;
                    }
                })
                .build();

        final DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Overview").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Grades").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Notification").withIdentifier(3),
                        new SectionDrawerItem().withName("My courses"));
        for (int i = 0; i < coursearray.length(); i++) {
            builder.addDrawerItems(
                    new PrimaryDrawerItem().withName(cCodeArray.get(i).toUpperCase()).withIdentifier(4+i)
            );
        }
        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position, IDrawerItem drawerItem) {
                Bundle courseB = new Bundle();
                switch (position) {
                    default:
                        if (position == 1) {
                            MainOverview overview = new MainOverview();
                            replaceFragment(overview);
                        } else if (position == 2) {
                            final ProgressDialog dialog1 = ProgressDialog.show(context, "", "Fetching Details...", true);
                            String url2 = "http://" + applcn.getLocalHost() + "/default/grades.json/";

                            GETrequest.response(new GETrequest.VolleyCallback() {
                                @Override
                                public void onSuccess(String result1) {
                                    Fragment fragment = new gradesOverallFragment();
                                    dialog1.dismiss();
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("gradesJSON", result1);
                                    fragment.setArguments(bundle1);
                                    replaceFragment(fragment);

                                }
                            }, context, url2, dialog1);
                        } else if (position == 3) {
                            Fragment notifFragment = new NotificationFragment();
                            Bundle bundle = new Bundle();
                            Boolean IsNotif = false;
                            if (notJSON != null) {
                                bundle.putString("notJSON", notJSON);
                                IsNotif = true;
                            }
                            bundle.putBoolean("IsNotif", IsNotif);
                            notifFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, notifFragment, notifFragment.toString())
                                    .addToBackStack(notifFragment.toString())
                                    .commit();
                            return false;
                        } else {
                            courseB.putString("cCode", cCodeArray.get(position - 5));
                            CourseFragment coursefragment = new CourseFragment();
                            Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                            coursefragment.setArguments(courseB);
                            replaceFragment(coursefragment);
                        }

                        break;
                }
                return false;
            }
        });
        drawer = builder.build();
        TextView Name = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);
        TextView Email = (TextView) headerResult.getView().findViewById(R.id.material_drawer_account_header_name);
        int greenColorValue = Color.parseColor("#00ff00");
        Name.setTextColor(greenColorValue);
        Email.setTextColor(greenColorValue);

    }

    public void replaceFragment(Fragment courseFrag){
        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, courseFrag, courseFrag.toString())
                            .addToBackStack(courseFrag.toString())
                            .commit();
    }

}
