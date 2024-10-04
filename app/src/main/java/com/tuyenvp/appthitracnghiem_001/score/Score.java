package com.tuyenvp.appthitracnghiem_001.score;

public class Score {
    private int id;
    private  String name;
    private  int score ;
    private  String date;
    private  String room;

    public Score( String name, int score, String room) {
        this.name = name;
        this.score = score;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
