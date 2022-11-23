package com.zocarro.myvideobook.Test;

public class TestQuestion {
  /*  {
        "qid": "QST1",
            "question": "Test Question ?",
            "a": "A",
            "b": "B",
            "c": "C",
            "d": "D"
    },*/
  String q_id,question,a,b,c,d,selected,tst_id,correct;
    boolean mark;

    public TestQuestion(String q_id, String question, String a, String b, String c, String d, String selected, String tst_id, boolean mark,String correct) {
        this.q_id = q_id;
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.selected = selected;
        this.tst_id = tst_id;
        this.mark = mark;
        this.correct = correct;
    }

    public String getCorrect()
    {
        return correct;
    }

    public void setCorrect(String correct)
    {
        this.correct = correct;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
