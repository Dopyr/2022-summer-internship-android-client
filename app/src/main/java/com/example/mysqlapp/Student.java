package com.example.mysqlapp;

import java.math.BigDecimal;

public class Student extends User {

    private String major;
    private BigDecimal gpa;

    public Student() {

    }

    public Student(String name, String surname, int id, String major, BigDecimal gpa) {

        super(name, surname, id, false);

        this.major = major;
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

}
