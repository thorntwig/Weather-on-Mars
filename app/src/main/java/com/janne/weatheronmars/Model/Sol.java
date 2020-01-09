package com.janne.weatheronmars.Model;


import java.io.Serializable;
import java.util.Date;

public class Sol implements Serializable, Comparable {

    private int number;

    private Unit temp;
    private Unit wind;
    private Unit pressure;

    private String season;
    private Date startTime;
    private Date endTime;

    private Sol() {

    }

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
    public boolean equals(Object other) {
        if(other == null) {

            return false;
        }
        if(this.getClass() != other.getClass()) {
            return false;
        }

        Sol s = (Sol) other;
        if(this.number != s.getNumber()) {
            return false;
        }

        if(!this.temp.equals(s.getTemp())) {
            return false;
        }

        if(!this.wind.equals(s.getWind())) {
            return false;
        }
        if(!this.pressure.equals(s.getPressure())) {
            return false;
        }
        if(!this.season.equals(s.getSeason())) {
            return false;
        }
        if(!this.startTime.equals(s.getStartTime())) {
            return false;
        }
        if(!this.endTime.equals(s.getEndTime())) {
            return false;
        }


        return true;

    }


    @Override
    public int compareTo(Object o) {
        Sol other = (Sol) o;
        return other.getNumber() - getNumber();
    }

    public static class Builder {
        private int number;
        private Unit temp;
        private Unit wind;
        private Unit pressure;
        private String season;
        private Date startTime;
        private Date endTime;

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder temp(Unit temp) {
            this.temp = temp;
            return this;
        }

        public Builder wind(Unit wind) {
            this.wind = wind;
            return this;
        }

        public Builder pressure(Unit pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder season(String season) {
            this.season = season;
            return this;
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Sol build() {
            Sol sol = new Sol();
            sol.number = this.number;
            sol.temp = this.temp;
            sol.wind = this.wind;
            sol.pressure = this.pressure;
            sol.season = this.season;
            sol.startTime = this.startTime;
            sol.endTime = this.endTime;

            return sol;
        }

    }
}
