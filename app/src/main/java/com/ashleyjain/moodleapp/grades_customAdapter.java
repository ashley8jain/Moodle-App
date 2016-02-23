package com.ashleyjain.moodleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by saurabh on 22/2/16.
 */

public class grades_customAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<gradeObj> objects;

    private class ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }

    public grades_customAdapter(Context context, ArrayList<gradeObj> objects) {

        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public gradeObj getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grade_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.grade_list_sno);
            holder.textView1 = (TextView) convertView.findViewById(R.id.grade_list_gradeItem);
            holder.textView2 = (TextView) convertView.findViewById(R.id.grade_list_score);
            holder.textView3 = (TextView) convertView.findViewById(R.id.grade_list_outof);
            holder.textView4 = (TextView) convertView.findViewById(R.id.grade_list_weight);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(Integer.toString(position + 1));
        holder.textView1.setText(objects.get(position).getName());
        holder.textView2.setText(String.format("%s of %s",objects.get(position).getScore(),objects.get(position).getOutOf()));
        holder.textView3.setText(objects.get(position).getWeight());
        float weight = Float.parseFloat(objects.get(position).getWeight());
        float score = Float.parseFloat(objects.get(position).getScore());
        float outof = Float.parseFloat(objects.get(position).getOutOf());
        float f = (score / outof * weight / 100);
        DecimalFormat df = new DecimalFormat("##.##");
        String s = df.format(f);
        holder.textView4.setText(s);
        return convertView;
    }
}