package com.ashleyjain.moodleapp;

import java.util.ArrayList;

/**
 * Created by saurabh on 20/2/16.
 */
public class notifObj {
    private String user_id;
    private String description;
    private String is_seen;
    private String created_at;
    private String id;

    notifObj(String a, String b, String c, String d, String e){
        this.user_id = a;
        this.description = b;
        this.is_seen = c;
        this.created_at = d;
        this.id = e;
    }

    public String getUid(){
        return user_id;
    }
    public String getIs_seen(){
        return is_seen;
    }
    public String getDescription(){
        return description;
    }
    public String getCreated_at(){
        return created_at;
    }
    public String getId(){
        return id;
    }

}
