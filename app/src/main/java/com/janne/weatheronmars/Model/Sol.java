package com.janne.weatheronmars.Model;


import java.util.Date;

public class Sol {

    private int number;

    private double minTemp;
    private double maxTemp;
    private double averageTemp;

    private double minWind;
    private double maxWind;
    private double averageWind;

    private double minPressure;
    private double maxPressure;
    private double averagePressure;

    private String season;

    private Date startTime;
    private Date endTime;


    public double getMinPressure() {
        return minPressure;
    }

    public void setMinPressure(double minPressure) {
        this.minPressure = minPressure;
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public void setMaxPressure(double maxPressure) {
        this.maxPressure = maxPressure;
    }

    public double getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(double averagePressure) {
        this.averagePressure = averagePressure;
    }

    public double getMinWind() {
        return minWind;
    }

    public void setMinWind(double minWind) {
        this.minWind = minWind;
    }

    public double getMaxWind() {
        return maxWind;
    }

    public void setMaxWind(double maxWind) {
        this.maxWind = maxWind;
    }

    public double getAverageWind() {
        return averageWind;
    }

    public void setAverageWind(double averageWind) {
        this.averageWind = averageWind;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(double averageTemp) {
        this.averageTemp = averageTemp;
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
}
