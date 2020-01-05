package com.janne.weatheronmars.Model;


import java.io.Serializable;
import java.util.Date;

public class Sol implements Serializable,Comparable {

    private int number;

    private Unit temp;
    private Unit wind;
    private Unit pressure;

    private String season;
    private Date startTime;
    private Date endTime;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Unit getTemp() {
        return temp;
    }

    public void setTemp(Unit temp) {
        this.temp = temp;
    }

    public Unit getWind() {
        return wind;
    }

    public void setWind(Unit wind) {
        this.wind = wind;
    }

    public Unit getPressure() {
        return pressure;
    }

    public void setPressure(Unit pressure) {
        this.pressure = pressure;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Object o) {
        Sol other = (Sol) o;
        return other.getNumber() - getNumber();
    }
}
