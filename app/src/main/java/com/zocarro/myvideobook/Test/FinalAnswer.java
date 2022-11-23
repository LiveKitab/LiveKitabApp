package com.zocarro.myvideobook.Test;

public class FinalAnswer {
    String qid,Selected,tst_id,mark;

    public FinalAnswer(String qid, String selected, String tst_id,String mark) {
        this.qid = qid;
        this.Selected = selected;
        this.tst_id = tst_id;
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getSelected() {
        return Selected;
    }

    public void setSelected(String selected) {
        Selected = selected;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }
}
