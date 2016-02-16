package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText id,pass;
    Button login;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                final ProgressDialog dialog = ProgressDialog.show(context,"","Loading.Please wait...",true);
                username[0] = id.getText().toString();
                password[0] = pass.getText().toString();
                String url = "http://10.192.43.84:8000/default/login.json?userid=" + username[0] + "&password=" + password[0];
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                // Result handling
                                System.out.println(response.toString());
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if(success=="false"){
                                        Toast.makeText(context, "Wrong username or password!!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        dialog.dismiss();
                                        Intent intent = new Intent(context,DashBoard.class);
                                        intent.putExtra("response",response);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                System.out.println("Something went wrong!");
                                error.printStackTrace();

                            }
                        });

// Add the request to the queue
                Volley.newRequestQueue(context).add(stringRequest);

            }
        });

    }
}
