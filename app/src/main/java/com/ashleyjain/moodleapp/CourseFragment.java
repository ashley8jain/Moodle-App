package com.ashleyjain.moodleapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends ListFragment implements OnItemClickListener{
    public  String cCode;
    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cCode = getArguments().getString("cCode");
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.courseMenu, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                Fragment fragment = new OverviewFragment();
                replaceFragment(fragment);
                break;
            case 1:
                final ProgressDialog dialog1 = ProgressDialog.show(getContext(), "", "Fetching Details...", true);
                String url2 = "http://"+((myApplication) getActivity().getApplication()).getLocalHost()+"/courses/course.json/"+cCode+"/grades";

                GETrequest.response(new GETrequest.VolleyCallback() {
                    @Override
                    public void onSuccess(String result1) {
                        Fragment fragment = new GradesFragment();
                        dialog1.dismiss();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("gradesJSON", result1);
                        bundle1.putBoolean("IsOverall",false);
                        fragment.setArguments(bundle1);
                        replaceFragment(fragment);

                    }
                }, getContext(), url2, dialog1);
                break;
            case 2:
                fragment = new AssignmentFragment();
                bundle.putString("cCode", cCode);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case 3:
                fragment = new ThreadsFragment();
                bundle.putString("cCode", cCode);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case 4:
                fragment = new ResourcesFragment();
                replaceFragment(fragment);
                break;
        }
    }
    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
