package com.zocarro.myvideobook.Dashboard;

public class courseCategories {

    String b_id,categories;

    public courseCategories(String b_id, String categories) {
        this.b_id = b_id;
        this.categories = categories;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
