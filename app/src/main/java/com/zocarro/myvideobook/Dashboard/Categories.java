package com.zocarro.myvideobook.Dashboard;

public class Categories {
    String sub_id,catImage,catName,catcode;

    public Categories(String sub_id, String catImage, String catName, String catcode) {
        this.sub_id = sub_id;
        this.catImage = catImage;
        this.catName = catName;
        this.catcode = catcode;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatcode() {
        return catcode;
    }

    public void setCatcode(String catcode) {
        this.catcode = catcode;
    }
}
