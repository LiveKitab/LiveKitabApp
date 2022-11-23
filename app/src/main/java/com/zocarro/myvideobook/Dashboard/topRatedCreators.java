package com.zocarro.myvideobook.Dashboard;

public class topRatedCreators {

    String courseTitle,courseRating,courseUser;

    public topRatedCreators(String courseTitle, String courseRating, String courseUser) {
        this.courseTitle = courseTitle;
        this.courseRating = courseRating;
        this.courseUser = courseUser;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(String courseRating) {
        this.courseRating = courseRating;
    }

    public String getCourseUser() {
        return courseUser;
    }

    public void setCourseUser(String courseUser) {
        this.courseUser = courseUser;
    }
}
