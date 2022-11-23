package com.zocarro.myvideobook.Test;

public class TestAnswer {
    String tst_id,q_id,answer;
    int mark;

    public TestAnswer(String tst_id, String q_id, String answer, int mark) {
        this.tst_id = tst_id;
        this.q_id = q_id;
        this.answer = answer;
        this.mark = mark;
    }



    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
