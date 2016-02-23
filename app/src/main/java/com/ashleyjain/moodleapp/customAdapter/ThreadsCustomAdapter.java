package com.ashleyjain.moodleapp.customAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ashleyjain.moodleapp.R;
import com.ashleyjain.moodleapp.items.AssignmentItems;

import java.util.List;

/**
 * Created by chandudasari on 21/02/16.
 */
public class ThreadsCustomAdapter extends BaseAdapter {
    Context context;
    List<AssignmentItems> assignmentItem;
    public ThreadsCustomAdapter(Context context, List<AssignmentItems> assignmentItem) {
        this.context = context;
        this.assignmentItem = assignmentItem;

    }

    @Override
    public int getCount() {
        return assignmentItem.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return assignmentItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.assignment_list_item, null);
        }

        TextView assignmentName = (TextView) convertView.findViewById(R.id.assignment_name);
        TextView assignmentTime = (TextView) convertView.findViewById(R.id.assignment_time);

        AssignmentItems assign_row = assignmentItem.get(position);
        // setting the image resource and title
        assignmentTime.setText(assign_row.getAssignmenttime());
        assignmentName.setText(assign_row.getAssignmentname());

        return convertView;
    }
}
