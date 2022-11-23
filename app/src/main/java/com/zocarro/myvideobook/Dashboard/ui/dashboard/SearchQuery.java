package com.zocarro.myvideobook.Dashboard.ui.dashboard;

class SearchQuery {
                        /*intent.putExtra("courseDesc", courseDesc);
                        intent.putExtra("course_id", courseid);
                        intent.putExtra("v_id", videoid);
                        intent.putExtra("v_link", videolink);
                        intent.putExtra("v_attach", videoAttachment);*/
    String title,imageView,rating,users,courseDesc,courseid,videoid,videolink,videoAttachment;

    public SearchQuery(String title, String imageView, String rating, String users, String courseDesc,
                       String courseid, String videoid, String videolink, String videoAttachment) {
        this.title = title;
        this.imageView = imageView;
        this.rating = rating;
        this.users = users;
        this.courseDesc = courseDesc;
        this.courseid = courseid;
        this.videoid = videoid;
        this.videolink = videolink;
        this.videoAttachment = videoAttachment;
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

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getVideoAttachment() {
        return videoAttachment;
    }

    public void setVideoAttachment(String videoAttachment) {
        this.videoAttachment = videoAttachment;
    }
}
