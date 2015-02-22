package com.example.tenthana.timetable;

public class EventListItem {
    private String courseid = "";
    private String coursename = "";
    private String place = "";
    private String instructor = "";
    private String tstart = "";
    private String tend = "";

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setTstart(String tstart) {
        this.tstart = tstart;
    }

    public void setTend(String tend) {
        this.tend = tend;
    }

    public String getCourseid() {
        return courseid;
    }

    public String getCoursename() {
        return "Course Name: " +coursename;
    }

    public String getPlace() {
        return "Location: "+place;
    }

    public String getInstructor() {
        return "Instructor: "+instructor;
    }
    public String getTstart() {
        return "From: "+tstart;
    }
    public String getTend() {
        return "To: "+tend;
    }
}