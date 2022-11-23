package com.zocarro.myvideobook.VideoCourse;

public class CommentModel {
    private String username, comment, date, id,status,comment_mentor,c_name,c_lname;

    public CommentModel(String username, String comment, String date, String id, String status, String comment_mentor, String c_name,String c_lname) {
        this.username = username;
        this.comment = comment;
        this.date = date;
        this.id = id;
        this.status = status;
        this.comment_mentor = comment_mentor;
        this.c_name = c_name;
        this.c_lname = c_lname;
    }

    public String getC_lname() {
        return c_lname;
    }

    public void setC_lname(String c_lname) {
        this.c_lname = c_lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment_mentor() {
        return comment_mentor;
    }

    public void setComment_mentor(String comment_mentor) {
        this.comment_mentor = comment_mentor;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
