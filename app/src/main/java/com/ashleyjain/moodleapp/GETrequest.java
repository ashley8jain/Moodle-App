package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by ashleyjain on 15/02/16.
 */
public class GETrequest {
    static String resp;
    public static String response(Context context,String url) {

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        // Result handling
                        System.out.println(response.substring(0, 100));
                        resp=response;
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
        ProgressDialog dialog = ProgressDialog.show(context, "", "Loading.Please wait...", true);
        while(resp!=null) {
            break;
        }

        if(resp!=null){
            dialog.dismiss();
            System.out.println("true");
        }
        else
            System.out.println("false");
        return resp;
    }

}

