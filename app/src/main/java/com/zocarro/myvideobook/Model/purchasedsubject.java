package com.zocarro.myvideobook.Model;

public class purchasedsubject {
    String sub_id,catImage,catName,cid,cname,durability;

    public purchasedsubject(String sub_id, String catImage, String catName, String cid, String cname, String durability) {
        this.sub_id = sub_id;
        this.catImage = catImage;
        this.catName = catName;
        this.cid = cid;
        this.cname = cname;
        this.durability = durability;
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

    public String getDurability() {
        return durability;
    }

    public void setDurability(String durability) {
        this.durability = durability;
    }
}
