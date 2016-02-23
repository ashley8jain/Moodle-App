package com.ashleyjain.moodleapp.adapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashleyjain.moodleapp.R;

/**
 * Created by ashleyjain on 20/02/16.
 */
public class AssignmentAdapter extends Fragment {
    TextView name,desc,dead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.assignment_adapter, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        name = (TextView) getActivity().findViewById(R.id.name);
        desc = (TextView) getActivity().findViewById(R.id.description);
        dead = (TextView) getActivity().findViewById(R.id.deadline);

        name.setText(getArguments().getString("name"));
        desc.setText(getArguments().getString("description"));
        dead.setText("Deadline:"+getArguments().getString("deadline"));
    }

}
