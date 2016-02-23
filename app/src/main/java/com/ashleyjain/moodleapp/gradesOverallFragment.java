package com.ashleyjain.moodleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class gradesOverallFragment extends Fragment {

    private int mColumnCount = 1;
    private JSONArray gradesJSON,coursesJSON;
    private ArrayList<gradeObj> gradeInfo;
    private ListView listView;

    public gradesOverallFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            JSONObject jsonObject = (new JSONObject(getArguments().getString("gradesJSON")));
            gradesJSON = jsonObject.getJSONArray("grades");
            coursesJSON = jsonObject.getJSONArray("courses");

        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overall_grade_list, container, false);

        ArrayList<gradeObj> grades = new ArrayList<gradeObj>();
        for (int i = 0; i < gradesJSON.length(); i++) {
            try {
                JSONObject jsonObject = gradesJSON.getJSONObject(i);
                JSONObject jsonObject1 = coursesJSON.getJSONObject(i);
                grades.add(new gradeObj(jsonObject.getString("name"), jsonObject.getString("score"), jsonObject.getString("out_of"),
                                            jsonObject.getString("weightage"),jsonObject1.getString("code") ) );
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.overall_grade_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new OverallGradeItemRecyclerViewAdapter(grades));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
