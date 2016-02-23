package com.ashleyjain.moodleapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chandudasari on 20/02/16.
 */
public class GradesFragment extends Fragment {

    private JSONArray gradesJSON,courseArray;
    private ArrayList<gradeObj> gradeInfo;
    private ListView listView;
    //private Boolean IsOverall= false;

    public GradesFragment() {
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        try {
            JSONObject jsonObject = (new JSONObject(getArguments().getString("gradesJSON")));
            gradesJSON = jsonObject.getJSONArray("grades");
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_grades,container,false);
        listView = (ListView) view.findViewById(R.id.grades_listView);

        ArrayList<gradeObj> names = new ArrayList<gradeObj>();
        ArrayList<String> courseList = new ArrayList<String>();

        for (int i = 0; i < gradesJSON.length(); i++) {
            try {
                JSONObject jsonObject = gradesJSON.getJSONObject(i);
                names.add(new gradeObj(jsonObject.getString("name"),jsonObject.getString("score"),jsonObject.getString("out_of"),jsonObject.getString("weightage")));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        grades_customAdapter adapter = new grades_customAdapter(inflater.getContext(), names);
        listView.setAdapter(adapter);
        return view;
    }
}
