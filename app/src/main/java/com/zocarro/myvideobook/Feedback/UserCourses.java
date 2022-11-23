package com.zocarro.myvideobook.Feedback;

public class UserCourses
{

    private String c_img,c_id,c_name,c_lname,sub_name,sub_id,no_of_video,no_of_test,rating;

    public UserCourses(String c_img, String c_id, String c_name, String c_lname, String sub_name, String sub_id, String no_of_video, String no_of_test, String rating) {
        this.c_img = c_img;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_lname = c_lname;
        this.sub_name = sub_name;
        this.sub_id = sub_id;
        this.no_of_video = no_of_video;
        this.no_of_test = no_of_test;
        this.rating = rating;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_lname() {
        return c_lname;
    }

    public void setC_lname(String c_lname) {
        this.c_lname = c_lname;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getNo_of_video() {
        return no_of_video;
    }

    public void setNo_of_video(String no_of_video) {
        this.no_of_video = no_of_video;
    }

    public String getNo_of_test() {
        return no_of_test;
    }

    public void setNo_of_test(String no_of_test) {
        this.no_of_test = no_of_test;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
