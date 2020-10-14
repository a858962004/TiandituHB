package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-01
 */

public class DriveRouteBean {
    private List<StreetLatLonBean> streetLatLon;
    private String desc;
    private String exceptionCode;
    private String exceptionText;

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionText() {
        return exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
