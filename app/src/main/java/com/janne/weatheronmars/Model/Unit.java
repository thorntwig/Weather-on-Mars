package com.janne.weatheronmars.Model;

import java.io.Serializable;

public class Unit implements Serializable {


    private double avg;
    private double min;
    private double max;
    private String title;
    private String sign;

    public Unit(double avg, double min, double max) {
        this.avg = avg;
        this.min = min;
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
