package com.ashleyjain.moodleapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    ArrayList<courseObj> courselist = new ArrayList<courseObj>();

    private String courselist_JSONstring;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String clist = getArguments().getString("course_list");
        courselist_JSONstring = clist;
        try {
            JSONObject jsonObject = new JSONObject(clist);
            JSONArray coursearray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < coursearray.length(); i++) {
                JSONObject course = coursearray.getJSONObject(i);
                //System.out.println("<<<<<-----------"+courselist.get(0).getCode()+"&&&"+courselist.get(1).getCode()+"------------------>>>>>>>");
                courseObj csObj = new courseObj(course.getString("code"), course.getString("name"), course.getString("description"),
                        course.getString("credits"), course.getString("l_t_p"));
                courselist.add(i,csObj);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getView()==null){
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
            llManager.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(llManager);

            myRecyleAdapter mAdapter = new myRecyleAdapter(courselist);
            recyclerView.setAdapter(mAdapter);
            return view;
        }
        else{
            return getView();
        }
    }

    public class myRecyleAdapter extends RecyclerView.Adapter<myRecyleAdapter.myViewHolder>{

        private ArrayList<courseObj> stringList;
        private String courseJSONstring;
        private Bundle bundle = new Bundle();

        public myRecyleAdapter(ArrayList<courseObj> list){
            this.stringList = list;
            this.courseJSONstring = courselist_JSONstring;
        }

        @Override
        public int getItemCount(){
            return stringList.size();
        }

        @Override
        public void onBindViewHolder(myViewHolder vHolder,int i){
            courseObj Obj = stringList.get(i);
            //System.out.println("<<<<<-----------the course is "+ Obj.getCode() +"------------------>>>>>>>");
            vHolder.notxtDisplay.setText(Obj.getCode());
            vHolder.nameTxtDisplay.setText(Obj.getName());
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
            myViewHolder vHolder =  new myViewHolder(itemView);
            vHolder.setI(stringList.get(i).getCode());
            return vHolder;
        }

        public class myViewHolder extends RecyclerView.ViewHolder{

            private TextView notxtDisplay;
            private TextView nameTxtDisplay;

            public void setI(String cCode){
                bundle.putString("cCode",cCode);
            }

            public myViewHolder(View view){
                super(view);
                notxtDisplay = (TextView)view.findViewById(R.id.courseNo);
                nameTxtDisplay = (TextView)view.findViewById(R.id.courseName);
                view.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Fragment courseFrag = new CourseFragment();
                        courseFrag.setArguments(bundle);
                        FragmentChangeListener frChgListener = (FragmentChangeListener)getActivity();
                        frChgListener.replaceFragment(courseFrag);
                    }
                });
            }

        }

    }

}
