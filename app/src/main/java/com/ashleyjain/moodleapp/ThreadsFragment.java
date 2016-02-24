package com.ashleyjain.moodleapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ashleyjain.moodleapp.customAdapter.ThreadsCustomAdapter;
import com.ashleyjain.moodleapp.items.AssignmentItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandudasari on 20/02/16.
 */
public class ThreadsFragment extends ListFragment {
    public String cCode;
    String[] title,updates,combined,descp;
    private List<AssignmentItems> threadsItem;
    Button submit;
    EditText tit,descript;
    String newtitle,newdesc;
    ThreadsCustomAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_threads, container, false);
        cCode = getArguments().getString("cCode");
        tit = (EditText) view.findViewById(R.id.tit);
        descript = (EditText) view.findViewById(R.id.description2);
        submit = (Button) view.findViewById(R.id.submit22);
        jsonThread();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtitle = tit.getText().toString();
                newdesc = descript.getText().toString();
                String url2 = "http://" + ((myApplication) getActivity().getApplication()).getLocalHost() + "/threads/new.json?title=" + newtitle + "&description=" + newdesc + "&course_code=" + cCode;
                final ProgressDialog dialog2 = ProgressDialog.show(getActivity(), "", "Loading.Please wait...", true);
                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(final String result) {
                        //dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            jsonThread();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, getActivity(), url2, dialog2);
            }
        });
        return view;
    }

    private void jsonThread() {
        String url = "http://" + ((myApplication) getActivity().getApplication()).getLocalHost() + "/courses/course.json/" + cCode + "/threads";
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("course_threads");
                    title = new String[arr.length()];
                    updates = new String[arr.length()];
                    combined = new String[arr.length()];
                    descp = new String[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        title[i] = ass.getString("title");
                        updates[i] = ass.getString("updated_at");
                        descp[i] = ass.getString("description");
                        combined[i] = Character.toUpperCase(title[i].charAt(0))+title[i].substring(1) + " => Description: " + descp[i];
                        //Character.toUpperCase(input.charAt(0)) + input.substring(1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                threadsItem = new ArrayList<AssignmentItems>();

                for (int i = 0; i < title.length; i++) {
                    AssignmentItems items = new AssignmentItems(combined[i], updates[i]);

                    threadsItem.add(items);
                }
                adapter = new ThreadsCustomAdapter(getActivity(), threadsItem);
                setListAdapter(adapter);
            }
        }, getActivity(), url, dialog);
    }

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }

}


