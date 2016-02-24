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
    List<AssignmentItems> threadsItem;
    public ThreadsCustomAdapter(Context context, List<AssignmentItems> assignmentItem) {
        this.context = context;
        this.threadsItem = assignmentItem;

    }

    @Override
    public int getCount() {
        return threadsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return threadsItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return threadsItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.thread_list_item, null);
        }

        TextView titleName = (TextView) convertView.findViewById(R.id.title_name);
        TextView UpdatedOn = (TextView) convertView.findViewById(R.id.updated_on);

        AssignmentItems assign_row = threadsItem.get(position);
        UpdatedOn.setText(assign_row.getAssignmenttime());
        titleName.setText(assign_row.getAssignmentname());

        return convertView;
    }
}
