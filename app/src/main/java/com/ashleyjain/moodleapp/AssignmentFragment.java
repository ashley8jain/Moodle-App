package com.ashleyjain.moodleapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ashleyjain.moodleapp.adapter.AssignmentAdapter;
import com.ashleyjain.moodleapp.customAdapter.AssignmentCustomAdapter;
import com.ashleyjain.moodleapp.items.AssignmentItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandudasari on 20/02/16.
 */
public class AssignmentFragment extends ListFragment {
    public String cCode;
    String[] assignName,assignDeadline,assignDescr;
    private List<AssignmentItems> assignmentItems;
    AssignmentCustomAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         cCode = getArguments().getString("cCode");
        String url = "http://"+((myApplication) getActivity().getApplication()).getLocalHost()+"/courses/course.json/"+cCode+"/assignments";
        final ProgressDialog dialog = ProgressDialog.show(getActivity(),"", "Loading.Please wait...", true);
        GETrequest.response(new GETrequest.VolleyCallback() {
            @Override
            public void onSuccess(final String result) {
                //dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arr = jsonObject.getJSONArray("assignments");
                    assignName = new String[arr.length()];
                    assignDeadline = new String[arr.length()];
                    assignDescr = new String[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ass = arr.getJSONObject(i);
                        assignName[i] = ass.getString("name");
                        assignDeadline[i] = ass.getString("deadline");
                        assignDescr[i] = ass.getString("description");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assignmentItems = new ArrayList<AssignmentItems>();

                for (int i = 0; i < assignName.length; i++) {
                    AssignmentItems items = new AssignmentItems(assignName[i],assignDeadline[i] );

                    assignmentItems.add(items);
                }
                adapter = new AssignmentCustomAdapter(getActivity(), assignmentItems);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,assignName);
                setListAdapter(adapter);

            }
        }, getActivity(), url, dialog);
        return inflater.inflate(R.layout.fragment_course_assignment, container, false);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle bundle = new Bundle();
        Fragment fragment = new AssignmentAdapter();
        bundle.putString("name" , assignName[position]);
        bundle.putString("deadline" , assignDeadline[position]);
        bundle.putString("description" , assignDescr[position]);
        fragment.setArguments(bundle);
        replaceFragment(fragment);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        assignmentItems = new ArrayList<AssignmentItems>();
//
//        for (int i = 0; i < assignName.length; i++) {
//            AssignmentItems items = new AssignmentItems(assignName[i],assignName[i] );
//
//            assignmentItems.add(items);
//        }
//        adapter = new AssignmentCustomAdapter(getActivity(), assignmentItems);
//        ListView lv = (ListView) getView().findViewById(android.R.id.list);
//        lv.setAdapter(adapter);
    }

    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
