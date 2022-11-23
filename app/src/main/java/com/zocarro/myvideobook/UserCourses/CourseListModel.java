package com.zocarro.myvideobook.UserCourses;

public class CourseListModel {

    String title,imageView,rating,users,course_id, count, total,sub_code;

    public CourseListModel(String title, String imageView, String rating, String users,String course_id, String total, String count,String sub_code) {
        this.title = title;
        this.imageView = imageView;
        this.rating = rating;
        this.users = users;
        this.course_id = course_id;
        this.count = count;
        this.total = total;
        this.sub_code = sub_code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
