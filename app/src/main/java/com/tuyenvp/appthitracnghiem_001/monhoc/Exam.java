package com.tuyenvp.appthitracnghiem_001.monhoc;

public class Exam {


    private String name;
    private int logo;

    public Exam(String name,int logo) {
        this.name = name;
        this.logo=logo;
    }


    public Exam() {
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
