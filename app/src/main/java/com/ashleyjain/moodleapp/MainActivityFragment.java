package com.ashleyjain.moodleapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private List createList(){
        List l = new ArrayList<String>(4);
        String a = new String("saurabh");
        String b = new String("chandu");
        String c = new String("ashley");
        String d = new String("ricky ponting");

        l.add(a);
        l.add(b);
        l.add(c);
        l.add(d);
        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llManager);

        myRecyleAdapter mAdapter = new myRecyleAdapter(createList());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public class myRecyleAdapter extends RecyclerView.Adapter<myRecyleAdapter.myViewHolder>{

        private List<String> stringList;

        public myRecyleAdapter(List<String> list){
            this.stringList = list;
        }

        @Override
        public int getItemCount(){
            return stringList.size();
        }

        @Override
        public void onBindViewHolder(myViewHolder vHolder,int i){
            String name = stringList.get(i);
            vHolder.txtDisplay.setText(name);
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
            return new myViewHolder(itemView);
        }

        public class myViewHolder extends RecyclerView.ViewHolder{

            private TextView txtDisplay;

            public myViewHolder(View view){
                super(view);
                txtDisplay = (TextView)view.findViewById(R.id.cardTxtView);
                view.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Fragment courseFrag = new CourseFragment();
                        FragmentChangeListener frChgListener = (FragmentChangeListener)getActivity();
                        frChgListener.replaceFragment(courseFrag);
                    }
                });
            }

        }

    }

}
