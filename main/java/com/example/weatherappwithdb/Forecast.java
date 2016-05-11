/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    Forecast.java
 */

package com.example.weatherappwithdb;

import java.io.Serializable;

public class Forecast implements Serializable {

    String Date="",Hightemp="",lowTemp="",clouds="",iconUrl="",maxwindSpeed="",windDirection="",avghumidity="";

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHightemp() {
        return Hightemp;
    }

    public void setHightemp(String hightemp) {
        Hightemp = hightemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMaxwindSpeed() {
        return maxwindSpeed;
    }

    public void setMaxwindSpeed(String maxwindSpeed) {
        this.maxwindSpeed = maxwindSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getAvghumidity() {
        return avghumidity;
    }

    public void setAvghumidity(String avghumidity) {
        this.avghumidity = avghumidity;
    }


}
