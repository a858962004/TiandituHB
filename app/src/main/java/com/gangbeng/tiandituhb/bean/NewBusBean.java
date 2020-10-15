package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2020-10-15
 */

public class NewBusBean {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<SegmentsBean> segments;


    public List<SegmentsBean> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentsBean> segments) {
        this.segments = segments;
    }

    public static class SegmentsBean {
        private String mode;//方式
        private String departure_stop;//起始站
        private String arrival_stop;//到达站
        private String name;//公交名称
        private String distance;//距离
        private String duration;//时间
        private String via_num;//站点数

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getDeparture_stop() {
            return departure_stop;
        }

        public void setDeparture_stop(String departure_stop) {
            this.departure_stop = departure_stop;
        }

        public String getArrival_stop() {
            return arrival_stop;
        }

        public void setArrival_stop(String arrival_stop) {
            this.arrival_stop = arrival_stop;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        public String getVia_num() {
            return via_num;
        }

        public void setVia_num(String via_num) {
            this.via_num = via_num;
        }
    }
}
