package com.zocarro.myvideobook.Model;

public class chaptermodel
{
    String sub_id,c_id,chapter_id,chapter_name,number,sub_name,sub_code,check,countvideo,counttest,countmaterial,countvideonumber,counttestnumber,countmaterialnumber;

    public chaptermodel(String sub_id, String c_id, String chapter_id, String chapter_name, String number, String sub_name, String sub_code, String check, String countvideo, String counttest, String countmaterial, String countvideonumber, String counttestnumber, String countmaterialnumber) {
        this.sub_id = sub_id;
        this.c_id = c_id;
        this.chapter_id = chapter_id;
        this.chapter_name = chapter_name;
        this.number = number;
        this.sub_name = sub_name;
        this.sub_code = sub_code;
        this.check = check;
        this.countvideo = countvideo;
        this.counttest = counttest;
        this.countmaterial = countmaterial;
        this.countvideonumber = countvideonumber;
        this.counttestnumber = counttestnumber;
        this.countmaterialnumber = countmaterialnumber;
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

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCountvideo() {
        return countvideo;
    }

    public void setCountvideo(String countvideo) {
        this.countvideo = countvideo;
    }

    public String getCounttest() {
        return counttest;
    }

    public void setCounttest(String counttest) {
        this.counttest = counttest;
    }

    public String getCountmaterial() {
        return countmaterial;
    }

    public void setCountmaterial(String countmaterial) {
        this.countmaterial = countmaterial;
    }

    public String getCountvideonumber() {
        return countvideonumber;
    }

    public void setCountvideonumber(String countvideonumber) {
        this.countvideonumber = countvideonumber;
    }

    public String getCounttestnumber() {
        return counttestnumber;
    }

    public void setCounttestnumber(String counttestnumber) {
        this.counttestnumber = counttestnumber;
    }

    public String getCountmaterialnumber() {
        return countmaterialnumber;
    }

    public void setCountmaterialnumber(String countmaterialnumber) {
        this.countmaterialnumber = countmaterialnumber;
    }
}
