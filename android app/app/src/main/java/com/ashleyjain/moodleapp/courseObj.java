package com.ashleyjain.moodleapp;

/**
 * Created by saurabh on 20/2/16.
 * A class to hold all different attributes of a course
 */
public class courseObj {
    private String code;
    private String name;
    private String description;
    private String credits;
    private String ltp;

    public void setCode(String XXX){
        this.code = XXX;
    }
    public void setName(String XXX){
        this.name = XXX;
    }
    public void setDescription(String XXX){
        this.description = XXX;
    }
    public void setCredits(String XXX){
        this.credits= XXX;
    }
    public void setLtp(String XXX){
        this.ltp = XXX;
    }

    courseObj(String icode, String iname, String idescription, String icredits, String iltp){
        this.code = icode;
        this.name = iname;
        this.description = idescription;
        this.credits= icredits;
        this.ltp = iltp;
    }

    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getCredits(){
        return credits;
    }
    public String getLtp(){
        return ltp;
    }

}
