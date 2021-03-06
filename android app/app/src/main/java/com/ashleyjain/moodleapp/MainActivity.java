package com.ashleyjain.moodleapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText id,pass;
    Button login;
    TextView signup;

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "cookie";
    private static final String SESSION_COOKIE = "session_id_moodleplus";
    private static MainActivity _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static MainActivity get() {
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((myApplication) this.getApplication()).setLocalHost("10.192.38.186:8000");


        final Context context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);

        id = (EditText) findViewById(R.id.userid);
        pass = (EditText) findViewById(R.id.password);
        final String[] username = new String[1];
        final String[] password = new String[1];


        login = (Button) findViewById(R.id.button);
        signup = (TextView) findViewById(R.id.textView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignUpActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected(context)) {
                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
                    alertbuilder.setTitle("No Network Connection");
                    alertbuilder.setCancelable(true);
                    alertbuilder.setPositiveButton("Go to wifi settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = alertbuilder.create();
                    alertDialog.show();
                } else {
                    final ProgressDialog dialog = ProgressDialog.show(context,"", "Authenticating...", true);
                    username[0] = id.getText().toString();
                    password[0] = pass.getText().toString();

                    String url = "http://"+ ((myApplication) getApplication()).getLocalHost() +"/default/login.json?userid=" + username[0] + "&password=" + password[0];

                    GETrequest.response(new GETrequest.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                                            try {

                                                JSONObject jsonObject = new JSONObject(result);
                                                String success = jsonObject.getString("success");
                                                if (success == "false") {
                                                    Toast.makeText(context, "Wrong username or password!!", Toast.LENGTH_LONG).show();
                                                } else {

                                                    final ProgressDialog dialog1 = ProgressDialog.show(context, "", "Fetching Details...", true);
                                                    String url2 = "http://"+ ((myApplication) getApplication()).getLocalHost() +"/courses/list.json";
                                                    final JSONObject user = jsonObject.getJSONObject("user");
                                                    final String fname = user.getString("first_name");
                                                    final String lname = user.getString("last_name");
                                                    final String email = user.getString("email");
                                                    final String username = user.getString("username");
                                                    final String entryn = user.getString("entry_no");
                                                    final String type = user.getString("type_");
                                                    //final String  = user.getString();
                                                    final Intent main2frag_intent = new Intent(context, Main2Activity.class);

                                                    GETrequest.response(new GETrequest.VolleyCallback() {
                                                        @Override
                                                        public void onSuccess(String result1) {

                                                            dialog1.dismiss();
                                                            //System.out.println(result1);
                                                            //Intent main2frag_intent = new Intent(context, Main2Activity.class);
                                                            main2frag_intent.putExtra("name", fname+" "+lname);
                                                            main2frag_intent.putExtra("email",email);
                                                            main2frag_intent.putExtra("username", username);
                                                            main2frag_intent.putExtra("type_",type);
                                                            main2frag_intent.putExtra("entry_no",entryn);
                                                            main2frag_intent.putExtra("courses", result1);
                                                            startActivity(main2frag_intent);
                                                        }
                                                    }, context, url2, dialog1);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, context, url, dialog);
                }
            }
        });



    }

    //Check if there is network connection or not
    public static boolean isNetworkConnected(Context con) {
        ConnectivityManager connMgr = (ConnectivityManager) con.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    public void AlertDialog(String message, Context contex) {
        AlertDialog.Builder build = new AlertDialog.Builder(contex)
                .setTitle("Oops!!")
                .setMessage(message)
                .setPositiveButton("OK", null);

        AlertDialog dialog = build.create();
        dialog.show();

    }


    //checking session cookie
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }



}
