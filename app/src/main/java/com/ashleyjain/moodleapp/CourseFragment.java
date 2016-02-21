package com.ashleyjain.moodleapp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;


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
                fragment = new GradesFragment();
                bundle.putString("cCode", cCode);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
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
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
    public void replaceFragment(Fragment courseFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, courseFrag, courseFrag.toString());
        fragmentTransaction.addToBackStack(courseFrag.toString());
        fragmentTransaction.commit();
    }
}
