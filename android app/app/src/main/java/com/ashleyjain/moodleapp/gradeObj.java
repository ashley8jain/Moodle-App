package com.ashleyjain.moodleapp;

/**
 * Created by saurabh on 22/2/16.
 */
public class gradeObj {
    private String name;
    private String score;
    private String outOf;
    private String weight;
    private String course;

    gradeObj(String a,String b,String c,String d){
        this.name=a;
        this.outOf=c;
        this.score=b;
        this.weight=d;
    }

    gradeObj(String a,String b,String c,String d,String e){
        this.name=a;
        this.outOf=c;
        this.score=b;
        this.weight=d;
        this.course = e;
    }

    public String getCourse(){return course;}
    public String getName(){
        return name;
    }
    public String getScore(){
        return score;
    }
    public String getOutOf(){
        return outOf;
    }
    public String getWeight(){
        return weight;
    }

}
