package com.ashleyjain.moodleapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class threads extends ListActivity {
    Context context = threads.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);



    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(context,threadsAdapter.class);
        //intent.putExtra("name", assignName[position]);
        //intent.putExtra("description", assignDescr[position]);
        //intent.putExtra("deadline", assignDeadline[position]);
        startActivity(intent);
    }

}
