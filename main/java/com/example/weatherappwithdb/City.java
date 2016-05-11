/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    City.java
 */

package com.example.weatherappwithdb;

import java.io.Serializable;

public class City implements Serializable {


    String cityKey,cityName, state, cityCurrTemp;

    public String getCityCurrTemp() {
        return cityCurrTemp;
    }

    public void setCityCurrTemp(String cityCurrTemp) {
        this.cityCurrTemp = cityCurrTemp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityKey='" + cityKey + '\'' +
                ", cityName='" + cityName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
