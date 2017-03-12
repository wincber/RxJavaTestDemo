package com.example.wincber.rxjavatestdemo.common;

/**
 * Created by wincber on 3/11/2017.
 */

public class Weather {
    private String city;
    private String date;
    private String temperature="";
    private String direction;
    private String power;
    private String status;

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDirection() {
        return direction;
    }

    public String getPower() {
        return power;
    }

    public String getStatus() {
        return status;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemperature(String temperature) {
        this.temperature +=  temperature;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("城市:" + city + "\r\n");
        builder.append("日期:" + date + "\r\n");
        builder.append("天气状况:" + status + "\r\n");
        builder.append("温度:" + temperature + "\r\n");
        builder.append("风向:" + direction + "\r\n");
        builder.append("风力:" + power + "\r\n");
        return builder.toString();
    }
}
