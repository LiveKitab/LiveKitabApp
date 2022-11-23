package com.zocarro.myvideobook.Model;

public class chaptertestmodel
{
    String v_id,test_id,v_title,v_des,v_link,c_id,sub_id,chap_id,chap_name,sequence,number;

    public chaptertestmodel(String v_id, String test_id, String v_title, String v_des, String v_link, String c_id, String sub_id, String chap_id, String chap_name, String sequence, String number) {
        this.v_id = v_id;
        this.test_id = test_id;
        this.v_title = v_title;
        this.v_des = v_des;
        this.v_link = v_link;
        this.c_id = c_id;
        this.sub_id = sub_id;
        this.chap_id = chap_id;
        this.chap_name = chap_name;
        this.sequence = sequence;
        this.number = number;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
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

    public String getV_link() {
        return v_link;
    }

    public void setV_link(String v_link) {
        this.v_link = v_link;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getChap_id() {
        return chap_id;
    }

    public void setChap_id(String chap_id) {
        this.chap_id = chap_id;
    }

    public String getChap_name() {
        return chap_name;
    }

    public void setChap_name(String chap_name) {
        this.chap_name = chap_name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
