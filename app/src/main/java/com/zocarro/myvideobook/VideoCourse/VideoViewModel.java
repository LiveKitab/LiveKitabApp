package com.zocarro.myvideobook.VideoCourse;

public class VideoViewModel
{
  String c_id,subject_id,ch_name,sequence,v_id,v_title,v_des,link,sub_name,sub_code,chapter_id,isInFav,file;

    public VideoViewModel(String c_id, String subject_id, String ch_name, String sequence, String v_id, String v_title, String v_des, String link, String sub_name, String sub_code, String chapter_id, String isInFav, String file) {
        this.c_id = c_id;
        this.subject_id = subject_id;
        this.ch_name = ch_name;
        this.sequence = sequence;
        this.v_id = v_id;
        this.v_title = v_title;
        this.v_des = v_des;
        this.link = link;
        this.sub_name = sub_name;
        this.sub_code = sub_code;
        this.chapter_id = chapter_id;
        this.isInFav = isInFav;
        this.file = file;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getIsInFav() {
        return isInFav;
    }

    public void setIsInFav(String isInFav) {
        this.isInFav = isInFav;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
