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
    private List<RoutesBean> routes;

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

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class RoutesBean{
        private String strguide;//每段线路文字描述
        private String signage;//“路牌”引导提示/高速路收费站出口信息
        private String streetName;//当前路段名称
        private String nextStreetName;//下一段道路名称
        private String tollStatus;//道路收费信息(0=免费路段，1=收费路段，2=部分收费路段)
        private String turnlatlon;//转折点经纬度

        public String getStrguide() {
            return strguide;
        }

        public void setStrguide(String strguide) {
            this.strguide = strguide;
        }

        public String getSignage() {
            return signage;
        }

        public void setSignage(String signage) {
            this.signage = signage;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getNextStreetName() {
            return nextStreetName;
        }

        public void setNextStreetName(String nextStreetName) {
            this.nextStreetName = nextStreetName;
        }

        public String getTollStatus() {
            return tollStatus;
        }

        public void setTollStatus(String tollStatus) {
            this.tollStatus = tollStatus;
        }

        public String getTurnlatlon() {
            return turnlatlon;
        }

        public void setTurnlatlon(String turnlatlon) {
            this.turnlatlon = turnlatlon;
        }
    }

    public static class SimpleBean{
        private String strguide;//每段线路文字描述
        private String streetNames;//当前行驶路段名称（含多个路段）
        private String lastStreetName;//最后一段道路名称
        private String linkStreetName;//合并段之间衔接的道路名称
        private String signage;//“路牌”引导提示/高速路收费站出口信息
        private String tollStatus;//道路收费信息(0=免费路段，1=收费路段，2=部分收费路段)
        private String turnlatlon;//转折点经纬度
        private String streetLatLon;//线路经纬度
        private String streetDistance;//行驶总距离（单位：米）
        private String segmentNumber;//合并后的号段，对应详细描述中的号段

        public String getStrguide() {
            return strguide;
        }

        public void setStrguide(String strguide) {
            this.strguide = strguide;
        }

        public String getStreetNames() {
            return streetNames;
        }

        public void setStreetNames(String streetNames) {
            this.streetNames = streetNames;
        }

        public String getLastStreetName() {
            return lastStreetName;
        }

        public void setLastStreetName(String lastStreetName) {
            this.lastStreetName = lastStreetName;
        }

        public String getLinkStreetName() {
            return linkStreetName;
        }

        public void setLinkStreetName(String linkStreetName) {
            this.linkStreetName = linkStreetName;
        }

        public String getSignage() {
            return signage;
        }

        public void setSignage(String signage) {
            this.signage = signage;
        }

        public String getTollStatus() {
            return tollStatus;
        }

        public void setTollStatus(String tollStatus) {
            this.tollStatus = tollStatus;
        }

        public String getTurnlatlon() {
            return turnlatlon;
        }

        public void setTurnlatlon(String turnlatlon) {
            this.turnlatlon = turnlatlon;
        }

        public String getStreetLatLon() {
            return streetLatLon;
        }

        public void setStreetLatLon(String streetLatLon) {
            this.streetLatLon = streetLatLon;
        }

        public String getStreetDistance() {
            return streetDistance;
        }

        public void setStreetDistance(String streetDistance) {
            this.streetDistance = streetDistance;
        }

        public String getSegmentNumber() {
            return segmentNumber;
        }

        public void setSegmentNumber(String segmentNumber) {
            this.segmentNumber = segmentNumber;
        }
    }



}
