package com.ashleyjain.moodleapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ashleyjain.moodleapp.customAdapter.AssignmentCustomAdapter;
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
    private List<AssignmentItems> assignmentItems;
    ThreadsCustomAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cCode = getArguments().getString("cCode");

        String url = "http://"+ ((myApplication) getActivity().getApplication()).getLocalHost() +"/courses/course.json/"+cCode+"/threads";

        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
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
                        combined[i]=title[i]+" ---> "+descp[i];
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assignmentItems = new ArrayList<AssignmentItems>();

                for (int i = 0; i < title.length; i++) {
                    AssignmentItems items = new AssignmentItems(combined[i],updates[i] );

                    assignmentItems.add(items);
                }
                adapter = new ThreadsCustomAdapter(getActivity(), assignmentItems);
                setListAdapter(adapter);
            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_course_threads, container, false);
    }
    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle bundle = new Bundle();
        //Fragment fragment = new threadAdapter();
        String url = MainActivity.localhost+"/threads/thread.json/"+Integer.toString(position);
        final String[] json = new String[1];
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                json[0] =result;
            }
        }, getActivity(), url, dialog);
        bundle.putString("particularthreadjson", json[0]);
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }*/

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
