package com.zocarro.myvideobook.Model;

public class univercitymodel
{
    String uni_id,uni_name;

    public univercitymodel(String uni_id, String uni_name) {
        this.uni_id = uni_id;
        this.uni_name = uni_name;
    }

    public String getUni_id() {
        return uni_id;
    }

    public void setUni_id(String uni_id) {
        this.uni_id = uni_id;
    }

    public String getUni_name() {
        return uni_name;
    }

    public void setUni_name(String uni_name) {
        this.uni_name = uni_name;
    }
}
