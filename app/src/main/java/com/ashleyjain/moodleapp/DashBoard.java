package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoard extends AppCompatActivity {
    final Context context = DashBoard.this;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {

            return true;
        }
        else if(id == R.id.action_mycourse){
            final ProgressDialog dialog = ProgressDialog.show(context, "", "Loading.Please wait...", true);
            String url = "http://10.192.49.56:8000/courses/list.json";
            GETrequest.response(new GETrequest.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                        dialog.dismiss();
                        System.out.println(result);
                        Intent intent1 = new Intent(context, myCourse.class);
                        intent1.putExtra("courselist",result);
                        startActivity(intent1);
                }
            }, context, url, dialog);


            return true;
        }
        else if(id == R.id.action_profile){

            return true;
        }
        else if(id == R.id.action_password){
            return true;
        }
        else if(id == R.id.action_logout){
            final ProgressDialog dialog = ProgressDialog.show(context, "", "Loading.Please wait...", true);
            String url = "http://10.192.49.56:8000/default/logout.json";
            GETrequest.response(new GETrequest.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    dialog.dismiss();
                    System.out.println(result);
                    Intent intent1 = new Intent(context,MainActivity.class);
                    startActivity(intent1);
                }
            }, context, url, dialog);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
