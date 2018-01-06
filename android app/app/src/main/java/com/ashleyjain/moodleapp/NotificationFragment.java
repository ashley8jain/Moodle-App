package com.ashleyjain.moodleapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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

    //ArrayList<notifObj> notifList = new ArrayList<notifObj>();
    Boolean IsNotif = true;
    private JSONArray notifsJSON;
    private ListView listView;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        try {
            IsNotif = getArguments().getBoolean("IsNotif");
            if(IsNotif){
                JSONObject jsonObject = (new JSONObject(getArguments().getString("notJSON")));
                notifsJSON = jsonObject.getJSONArray("notifications");
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification,container,false);
        listView = (ListView) view.findViewById(R.id.notif_listView);

        ArrayList<String> stringList = new ArrayList<String>();
        if(IsNotif) {
            TextView tv = (TextView) view.findViewById(R.id.isNotif);
            for (int i = 0; i < notifsJSON.length(); i++) {
                try {
                    JSONObject notif = notifsJSON.getJSONObject(i);
                    stringList.add(new String(notif.getString("description")));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            tv.setText(String.format("You have %d notifications", stringList.size()));
            customAdapter adapter = new customAdapter(inflater.getContext(), stringList);
            listView.setAdapter(adapter);
        }
        else{
            TextView tv = (TextView) view.findViewById(R.id.isNotif);
            tv.setText("You have 0 notifications");
        }
        return view;
    }

    private class customAdapter extends ArrayAdapter<String> {
        private View v;
        private ArrayList<String> stringArrayList;
        public customAdapter(Context context,ArrayList<String> sList){
            super(context, android.R.layout.simple_list_item_1);
            stringArrayList = sList;
        }

        @Override
        public int getCount() {
            return stringArrayList.size();
        }

        @Override
        public String getItem(int position) {
            return stringArrayList.get(position);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent){
            System.out.println("<<<<<<<---------------------hi------------------->>>>>>>>>>>>>>>>>>>>>>>>");

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v= inflater.inflate(android.R.layout.simple_list_item_1, null);
                TextView tv = (TextView) v.findViewById(android.R.id.text1);
                tv.setText(Html.fromHtml(stringArrayList.get(pos)));
            return v;
        }
    }


}
