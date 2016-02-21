package com.ashleyjain.moodleapp;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class NotificationFragment extends ListFragment {

    ArrayList<notifObj> notifList = new ArrayList<notifObj>();
    ArrayList<String> stringList = new ArrayList<String>();

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        String notJSON = getArguments().getString("notJSON");
        try {
            JSONObject jsonObject = new JSONObject(notJSON);
            JSONArray notif_array = jsonObject.getJSONArray("notifications");
            for (int i = 0; i < notif_array.length(); i++) {
                JSONObject notif = notif_array.getJSONObject(i);
                //System.out.println("<<<<<-----------"+courselist.get(0).getCode()+"&&&"+courselist.get(1).getCode()+"------------------>>>>>>>");
                notifObj nObj = new notifObj(notif.getString("user_id"),notif.getString("description"),notif.getString("is_seen "),notif.getString("created_at"),notif.getString("id"));
                String s = notif.getString("description");
                stringList.add(i,s);
                notifList.add(i,nObj);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lv = ((ListView) view.findViewById(R.id.notif_listView));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_notification,stringList);
        lv.setAdapter(adapter);


        return view;
    }


}
