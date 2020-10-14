package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * FileName: NewDriveRouteBean
 * Author: gangber
 * Date: 2020/10/14 14:45
 * Description: 高德路径规划Bean
 */
public class NewDriveRouteBean {

    private String status;
    private String info;
    private String infocode;
    private String count;
    private RouteBean route;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public static class RouteBean {

        private String origin;
        private String destination;
        private List<PathsBean> paths;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public List<PathsBean> getPaths() {
            return paths;
        }

        public void setPaths(List<PathsBean> paths) {
            this.paths = paths;
        }

        public static class PathsBean {

            private String distance;
            private String duration;
            private List<StepsBean> steps;

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

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public static class StepsBean {

                private String polyline;


                public String getPolyline() {
                    return polyline;
                }

                public void setPolyline(String polyline) {
                    this.polyline = polyline;
                }

            }
        }
    }
}
