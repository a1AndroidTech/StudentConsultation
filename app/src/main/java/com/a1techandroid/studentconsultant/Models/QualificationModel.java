package com.a1techandroid.studentconsultant.Models;

public class QualificationModel {
    String program;
    String Uni;
    String Subject;
    String grade;
    String passingyear;

    public QualificationModel(){

    }

    public QualificationModel(String program, String uni, String subject, String grade, String passingyear) {
        this.program = program;
        Uni = uni;
        Subject = subject;
        this.grade = grade;
        this.passingyear = passingyear;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getUni() {
        return Uni;
    }

    public void setUni(String uni) {
        Uni = uni;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassingyear() {
        return passingyear;
    }

    public void setPassingyear(String passingyear) {
        this.passingyear = passingyear;
    }
}
