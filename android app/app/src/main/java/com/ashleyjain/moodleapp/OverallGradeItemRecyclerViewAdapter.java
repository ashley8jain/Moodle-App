package com.ashleyjain.moodleapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.ashleyjain.moodleapp.gradeObj;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 */
public class OverallGradeItemRecyclerViewAdapter extends RecyclerView.Adapter<OverallGradeItemRecyclerViewAdapter.myViewHolder> {

    private final List<gradeObj> mValues;

    public OverallGradeItemRecyclerViewAdapter(List<gradeObj> items) {
        mValues = items;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        System.out.println("<<<<<<<<<<<-------------hi-------------->>>>>>>>>");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_overall_grade_item, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        gradeObj mItem = mValues.get(position);
        holder.snoView.setText(String.format("%d",(position+1)));
        holder.courseView.setText(mItem.getCourse());
        holder.nameView.setText(mItem.getName());
        holder.scoreView.setText(String.format("%s of %s", mItem.getScore(), mItem.getOutOf()));
        holder.weightView.setText(mItem.getWeight());
        float weight = Float.parseFloat(mItem.getWeight());
        float score = Float.parseFloat(mItem.getScore());
        float outof = Float.parseFloat(mItem.getOutOf());
        float f = (score / outof * weight / 100);
        DecimalFormat df = new DecimalFormat("##.##");
        String s = df.format(f);
        holder.totalView.setText(s);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView courseView;
        private TextView nameView;
        private TextView snoView;
        private TextView scoreView;
        private TextView weightView;
        private TextView totalView;

        public myViewHolder(View view) {
            super(view);
            courseView = (TextView) view.findViewById(R.id.overall_grade_course);
            snoView = (TextView) view.findViewById(R.id.overall_grade_list_sno);
            nameView = (TextView) view.findViewById(R.id.overall_grade_list_gradeItem);
            scoreView = (TextView) view.findViewById(R.id.overall_grade_list_score);
            weightView = (TextView) view.findViewById(R.id.overall_grade_list_weight);
            totalView = (TextView) view.findViewById(R.id.overall_grade_list_total);
        }
    }
}
