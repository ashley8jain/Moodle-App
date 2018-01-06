package com.ashleyjain.moodleapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ashleyjain.moodleapp.items.AssignmentItems;

import java.util.ArrayList;

/**
 * Created by chandudasari on 20/02/16.
 */
public class OverviewFragment extends ListFragment {
    String[] week;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        week = new String[20];
        for (int i=0 ; i< 20 ;i++){
            week[i] = "Week " + Integer.toString(i+1) +": ";
        }
        ArrayList<String> week1 = new ArrayList<String>();

        for (int i = 0; i < week.length; i++) {

            week1.add(week[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,week1);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_course_overview, container, false);
    }

}
