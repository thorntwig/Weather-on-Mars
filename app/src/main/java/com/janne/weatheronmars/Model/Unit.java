package com.janne.weatheronmars.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Unit implements Serializable {


    private double avg;
    private double min;
    private double max;
    private String title;
    private String sign;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object other) {

        if(other == null) {
            return false;
        }
        if(this.getClass() != other.getClass()) {
            return false;
        }
        Unit u = (Unit) other;

        if(this.avg != u.getAvg()){
            return false;
        }
        if(this.min != u.getMin()){
            return false;
        }
        if(this.max != u.getMax()){
            return false;
        }

        if(!this.sign.equals(u.getSign())) {
            return false;
        }
        if(!this.title.equals(u.getTitle())){
            return false;
        }
        if(this.date.equals(u.getDate().getTime())){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avg, min, max, title, sign, date);
    }


}
