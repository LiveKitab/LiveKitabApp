package com.zocarro.myvideobook;

class TermModel {

    String termId,termName;

    public TermModel(String termId, String termName) {
        this.termId = termId;
        this.termName = termName;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
