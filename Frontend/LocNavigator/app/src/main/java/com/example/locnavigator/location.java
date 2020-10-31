package com.example.locnavigator;

public class location  {
    private String Duration,distance,turn,place_jname;

    public location(String duration, String distance, String turn, String place_jname) {
        this.Duration = duration;
        this.distance = distance;
        this.turn = turn;
        this.place_jname = place_jname;

    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }





    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getPlace_jname() {
        return place_jname;
    }

    public void setPlace_jname(String place_jname) {
        this.place_jname = place_jname;
    }


}
