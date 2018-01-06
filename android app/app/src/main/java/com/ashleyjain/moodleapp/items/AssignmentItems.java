package com.ashleyjain.moodleapp.items;

/**
 * Created by chandudasari on 21/02/16.
 */
public class AssignmentItems {
    private String assignmentname;
    private String assignmenttime;

    public AssignmentItems(String assignmentname, String assignmenttime) {
        this.assignmentname = assignmentname;
        this.assignmenttime = assignmenttime;

    }
    public String getAssignmentname() {
        return assignmentname;
    }
    public String getAssignmenttime() {
        return assignmenttime;
    }
    public void setAssignmentname(String assignmentname){
        this.assignmentname = assignmentname;
    }
    public void setAssignmenttime(String assignmentname){
        this.assignmenttime = assignmenttime;
    }
}
