package com.zocarro.myvideobook.Model;

public class Notifications {
    String nt_id, title, des,date, status , link;

    public Notifications(String nt_id, String title, String des, String date, String status, String link) {
        this.nt_id = nt_id;
        this.title = title;
        this.des = des;
        this.date = date;
        this.status = status;
        this.link = link;
    }

    public String getNt_id() {
        return nt_id;
    }

    public void setNt_id(String nt_id) {
        this.nt_id = nt_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
