package com.zocarro.myvideobook.Dashboard;

public class topRatedCourses {

    String c_id,enrolled,rating,m_name,c_img;

    public topRatedCourses(String c_id, String enrolled, String rating, String m_name, String c_img) {
        this.c_id = c_id;
        this.enrolled = enrolled;
        this.rating = rating;
        this.m_name = m_name;
        this.c_img = c_img;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }
}
