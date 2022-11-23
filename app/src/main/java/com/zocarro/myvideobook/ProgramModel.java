package com.zocarro.myvideobook;

class   ProgramModel {

    String program_id,program_name;

    public ProgramModel(String program_id, String program_name) {
        this.program_id = program_id;
        this.program_name = program_name;
    }

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }
}
