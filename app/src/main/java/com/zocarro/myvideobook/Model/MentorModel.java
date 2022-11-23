package com.zocarro.myvideobook.Model;

public class MentorModel {
    String sub_id, c_id, c_name, c_no, c_email, c_img, rating, review, sub_name, vno, tno, sub_code, isInFav;

    public MentorModel(String sub_id, String c_id, String c_name, String c_no, String c_email, String c_img, String rating, String review, String sub_name, String vno, String tno, String sub_code, String isInFav) {
        this.sub_id = sub_id;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_no = c_no;
        this.c_email = c_email;
        this.c_img = c_img;
        this.rating = rating;
        this.review = review;
        this.sub_name = sub_name;
        this.vno = vno;
        this.tno = tno;
        this.sub_code = sub_code;
        this.isInFav = isInFav;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_no() {
        return c_no;
    }

    public void setC_no(String c_no) {
        this.c_no = c_no;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
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

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getVno() {
        return vno;
    }

    public void setVno(String vno) {
        this.vno = vno;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getIsInFav() {
        return isInFav;
    }

    public void setIsInFav(String isInFav) {
        this.isInFav = isInFav;
    }
}
