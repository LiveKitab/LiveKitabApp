package com.zocarro.myvideobook.courses;

class SubjectSemester {

        String c_id,course_id,course_name,course_bg,rating,enrolled;

    public SubjectSemester(String c_id, String course_id, String course_name, String course_bg, String rating, String enrolled) {
        this.c_id = c_id;
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_bg = course_bg;
        this.rating = rating;
        this.enrolled = enrolled;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_bg() {
        return course_bg;
    }

    public void setCourse_bg(String course_bg) {
        this.course_bg = course_bg;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }
}
