package com.zocarro.myvideobook.Model;

public class MentorchapModel {
    String sub_id,catImage,catName,catcode,cid,cname;

    public MentorchapModel(String sub_id, String catImage, String catName, String catcode, String cid, String cname) {
        this.sub_id = sub_id;
        this.catImage = catImage;
        this.catName = catName;
        this.catcode = catcode;
        this.cid = cid;
        this.cname = cname;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
