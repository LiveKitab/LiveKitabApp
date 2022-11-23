package com.zocarro.myvideobook.Dashboard;

class SearchQuery
{
    String course_id, title, imageView, rating, users, subjectCode, university;

    public SearchQuery(String course_id, String title, String imageView, String rating, String users, String subjectCode, String university) {
        this.course_id = course_id;
        this.title = title;
        this.imageView = imageView;
        this.rating = rating;
        this.users = users;
        this.subjectCode = subjectCode;
        this.university = university;
    }

    public String getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
