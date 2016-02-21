package com.ashleyjain.moodleapp;

import android.app.Application;

/**
 * Created by saurabh on 20/2/16.
 */
public class myApplication extends Application {

    private String localHost;

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String someVariable) {
        this.localHost = someVariable;
    }

}
