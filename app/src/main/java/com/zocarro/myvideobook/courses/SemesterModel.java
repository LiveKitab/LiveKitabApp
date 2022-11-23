package com.zocarro.myvideobook.courses;

class SemesterModel {
    String term_id,term_name;
    boolean isChecked;

    public SemesterModel(String term_id, String term_name,boolean isChecked) {
        this.term_id = term_id;
        this.term_name = term_name;
        this.isChecked=isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }
}
