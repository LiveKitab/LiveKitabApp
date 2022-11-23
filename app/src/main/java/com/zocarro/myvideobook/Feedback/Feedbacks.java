package com.zocarro.myvideobook.Feedback;

public class Feedbacks {
    String user_id, course_id, rating, review, u_img, username;

    public Feedbacks(String user_id, String course_id, String rating, String review, String u_img, String username) {
        this.user_id = user_id;
        this.course_id = course_id;
        this.rating = rating;
        this.review = review;
        this.u_img = u_img;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
