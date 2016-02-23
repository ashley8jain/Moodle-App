package com.ashleyjain.moodleapp;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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



public class NotificationFragment extends Fragment{

    ArrayList<notifObj> notifList = new ArrayList<notifObj>();
    ArrayList<String> stringList = new ArrayList<String>();
    Boolean IsNotif;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        IsNotif = getArguments().getBoolean("IsNotif");
        if(IsNotif) {
            String notJSON = getArguments().getString("notJSON");
            try {
                JSONObject jsonObject = new JSONObject(notJSON);
                JSONArray notif_array = jsonObject.getJSONArray("notifications");
                for (int i = 0; i < notif_array.length(); i++) {
                    JSONObject notif = notif_array.getJSONObject(i);
                    String s = notif.getString("description");
                    stringList.add(i, s);
                    //notifList.add(i,nObj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(IsNotif) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
            TextView tv = (TextView) getView().findViewById(R.id.isNotif);
            tv.setText( String.format("You have %d notifications",stringList.size()) );
            ListView listView = ((ListView) getView().findViewById(R.id.notif_listView));
            listView.setAdapter(adapter);
        }
        else{
            TextView tv = (TextView) getView().findViewById(R.id.isNotif);
            tv.setText("You have 0 notifications");
        }
    }

    /*private class customAdapter extends ArrayAdapter<String>{

        customAdapter(Context context, int resource,
                      int textViewResourceId, String[] array){
            super( context,resource,textViewResourceId, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view = super.getView(position, convertView, parent);
            TextView textView = view.findViewById(R.layout.simple_list_item_1);
        }

    }*/


}
