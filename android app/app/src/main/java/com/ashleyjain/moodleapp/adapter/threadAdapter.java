package com.ashleyjain.moodleapp.adapter;

import android.app.ListFragment;

/**
 * Created by ashleyjain on 20/02/16.
 */
public class threadAdapter extends ListFragment {
    /*TextView fullName,des,createdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thread_adapter, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        fullName = (TextView) getActivity().findViewById(R.id.fullname);
        des = (TextView) getActivity().findViewById(R.id.descrip);
        createdate = (TextView) getActivity().findViewById(R.id.createdat);
        String jsonthread = getArguments().getString("particularthreadjson");
        try {
            JSONObject json = new JSONObject(jsonthread);
            JSONArray jsonArray = json.getJSONArray("comments");

            fullName.setText();
            des.setText();
            createdate.setText();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/
}
