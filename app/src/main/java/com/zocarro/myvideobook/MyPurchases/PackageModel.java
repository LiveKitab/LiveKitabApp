package com.zocarro.myvideobook.MyPurchases;

public class PackageModel {
    String pkg_img, c_id, course_id, course_name, course_des, course_bg, uni, pkg_id, pkg_title, price, dis,course_plan_duration, sub_code, credit, rating, enrolled, purchased, transaction_id;

    public PackageModel(String pkg_img, String c_id, String course_id, String course_name, String course_des, String course_bg,  String uni, String pkg_id, String pkg_title, String price, String dis, String course_plan_duration, String sub_code, String credit, String rating, String enrolled, String purchased, String transaction_id) {
        this.pkg_img = pkg_img;
        this.c_id = c_id;
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_des = course_des;
        this.course_bg = course_bg;
        this.uni = uni;
        this.pkg_id = pkg_id;
        this.pkg_title = pkg_title;
        this.price = price;
        this.dis = dis;
        this.course_plan_duration = course_plan_duration;
        this.sub_code = sub_code;
        this.credit = credit;
        this.rating = rating;
        this.enrolled = enrolled;
        this.purchased = purchased;
        this.transaction_id = transaction_id;
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

    public String getCourse_des() {
        return course_des;
    }

    public void setCourse_des(String course_des) {
        this.course_des = course_des;
    }

    public String getCourse_bg() {
        return course_bg;
    }

    public void setCourse_bg(String course_bg) {
        this.course_bg = course_bg;
    }

    public String getPkg_img() {
        return pkg_img;
    }

    public void setPkg_img(String pkg_img) {
        this.pkg_img = pkg_img;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getPkg_id() {
        return pkg_id;
    }

    public void setPkg_id(String pkg_id) {
        this.pkg_id = pkg_id;
    }

    public String getPkg_title() {
        return pkg_title;
    }

    public void setPkg_title(String pkg_title) {
        this.pkg_title = pkg_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getCourse_plan_duration() {
        return course_plan_duration;
    }

    public void setCourse_plan_duration(String course_plan_duration) {
        this.course_plan_duration = course_plan_duration;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
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

    public String getPurchased() {
        return purchased;
    }

    public void setPurchased(String purchased) {
        this.purchased = purchased;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
