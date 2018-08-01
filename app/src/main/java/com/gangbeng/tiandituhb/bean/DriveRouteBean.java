package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-01
 */

public class DriveRouteBean {
    private String distance;//全长（单位：公里）
    private String duration;//行驶总时间（单位：秒）
    private String center;//全部结果同时显示的适宜中心经纬度
    private String scale;//全部结果同时显示的适宜缩放比例
    private List<StreetLatLonBean> streetLatLon;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public List<StreetLatLonBean> getStreetLatLon() {
        return streetLatLon;
    }

    public void setStreetLatLon(List<StreetLatLonBean> streetLatLon) {
        this.streetLatLon = streetLatLon;
    }

    public static class StreetLatLonBean{
        private double x;
        private double y;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }
}
