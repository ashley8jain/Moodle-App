package com.ashleyjain.moodleapp;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chandudasari on 20/02/16.
 */
public class GradesFragment extends ListFragment {
    public String cCode;
    float totalWeight,absoluteMark;
    String[] grade;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cCode = getArguments().getString("cCode");

        String url = "http://"+((myApplication) getActivity().getApplication()).getLocalHost()+"/courses/course.json/"+cCode+"/grades";

        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("grades");
                    grade = new String[arr.length()];
                    totalWeight = absoluteMark = 0.0f;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        grade[i] = ass.getString("name")+"-->"+ass.getString("score")+"/"+ass.getString("out_of")+"  :weigthage"+ass.getString("weightage");
                        totalWeight+=Float.parseFloat(ass.getString("weightage"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,grade);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_course_grades, container, false);
    }
}
