package com.zocarro.myvideobook.WishList;

public class WishListModel {

    String id,sub_id,sub_name,c_id,c_name,c_no,c_email,c_img,rating;

    public WishListModel(String id, String sub_id, String sub_name, String c_id, String c_name, String c_no, String c_email, String c_img, String rating) {
        this.id = id;
        this.sub_id = sub_id;
        this.sub_name = sub_name;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_no = c_no;
        this.c_email = c_email;
        this.c_img = c_img;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
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
}
