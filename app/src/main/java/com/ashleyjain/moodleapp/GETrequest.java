package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by ashleyjain on 15/02/16.
 */
public class GETrequest {

    public static void response(final VolleyCallback callback, final Context context,String url, final ProgressDialog pd) {

        // Request a string response

        com.ashleyjain.moodleapp.StringRequest stringRequest = new com.ashleyjain.moodleapp.StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        // Result handling
                        pd.dismiss();
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        pd.dismiss();
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("Something went wrong!");
                        error.printStackTrace();
                    }
                });

// Add the request to the queue
        MainActivity.get().getRequestQueue().add(stringRequest);
    }
    public interface VolleyCallback {
        void onSuccess(String result);
    }

}



