package com.janne.weatheronmars.Model;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Sol implements Serializable, Comparable {

    private int number;

    private Unit temp;
    private Unit wind;
    private Unit pressure;

    private String season;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDatee(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {

            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }

        Sol s = (Sol) other;
        if (this.number != s.getNumber()) {
            return false;
        }

        if (!this.temp.equals(s.getTemp())) {
            return false;
        }

        if (!this.wind.equals(s.getWind())) {
            return false;
        }
        if (!this.pressure.equals(s.getPressure())) {
            return false;
        }
        if (!this.season.equals(s.getSeason())) {
            return false;
        }
        if (!this.date.equals(s.getDate())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, temp, wind, pressure, season, date);
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
        private Date date;

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

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Sol build() {
            Sol sol = new Sol();
            sol.number = this.number;
            sol.temp = this.temp;
            sol.wind = this.wind;
            sol.pressure = this.pressure;
            sol.season = this.season;
            sol.date = this.date;


            return sol;
        }

    }
}
