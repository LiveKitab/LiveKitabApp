package com.zocarro.myvideobook.Model;

public class FavVideoModel
{
    String id,vid,c_id,subject_id,ch_name,sequence,v_id,v_title,v_des,player,link,i;

    public FavVideoModel(String id, String vid, String c_id, String subject_id, String ch_name, String sequence, String v_id, String v_title, String v_des, String player, String link, String i) {
        this.id = id;
        this.vid = vid;
        this.c_id = c_id;
        this.subject_id = subject_id;
        this.ch_name = ch_name;
        this.sequence = sequence;
        this.v_id = v_id;
        this.v_title = v_title;
        this.v_des = v_des;
        this.player = player;
        this.link = link;
        this.i = i;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getV_title() {
        return v_title;
    }

    public void setV_title(String v_title) {
        this.v_title = v_title;
    }

    public String getV_des() {
        return v_des;
    }

    public void setV_des(String v_des) {
        this.v_des = v_des;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
}

