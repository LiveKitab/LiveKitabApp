package com.zocarro.myvideobook.Model;

public class coursefeatures
{
String subjectduration,test,video,material,days;


    public coursefeatures(String subjectduration, String test, String video, String material, String days) {
        this.subjectduration = subjectduration;
        this.test = test;
        this.video = video;
        this.material = material;
        this.days = days;
    }

    public String getSubjectduration() {
        return subjectduration;
    }

    public void setSubjectduration(String subjectduration) {
        this.subjectduration = subjectduration;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
