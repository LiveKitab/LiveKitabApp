package com.zocarro.myvideobook.courses;

public class CourseVideo {

    String v_id,ch_name,v_title,v_des,v_link;

    public CourseVideo(String v_id, String ch_name, String v_title, String v_des, String v_link) {
        this.v_id = v_id;
        this.ch_name = ch_name;
        this.v_title = v_title;
        this.v_des = v_des;
        this.v_link = v_link;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getV_title() {
        return v_title;
    }

    public void setV_title(String v_title) {
        this.v_title = v_title;
    }

    public String getV_des() {
        return v_des;
    }

    public void setV_des(String v_des) {
        this.v_des = v_des;
    }

    public String getV_link() {
        return v_link;
    }

    public void setV_link(String v_link) {
        this.v_link = v_link;
    }
}
