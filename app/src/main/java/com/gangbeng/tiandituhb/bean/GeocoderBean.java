package com.gangbeng.tiandituhb.bean;

/**
 * Created by Administrator on 2018/7/29.
 */

public class GeocoderBean {

    /**
     * result : {"formatted_address":"河北省廊坊市广阳区廊坊北站附近中心旅馆西南约79米","location":{"lon":116.70020140262342,"lat":39.51163133703768},"addressComponent":{"address":"廊坊北站附近","city":"河北省廊坊市广阳区","road":"光明西道","poi_position":"西南","address_position":"西南","road_distance":150,"poi":"中心旅馆","poi_distance":"69","address_distance":69}}
     * msg : ok
     * status : 0
     */

    private ResultBean result;
    private String msg;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * formatted_address : 河北省廊坊市广阳区廊坊北站附近中心旅馆西南约79米
         * location : {"lon":116.70020140262342,"lat":39.51163133703768}
         * addressComponent : {"address":"廊坊北站附近","city":"河北省廊坊市广阳区","road":"光明西道","poi_position":"西南","address_position":"西南","road_distance":150,"poi":"中心旅馆","poi_distance":"69","address_distance":69}
         */

        private String formatted_address;
        private LocationBean location;
        private AddressComponentBean addressComponent;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class LocationBean {
            /**
             * lon : 116.70020140262342
             * lat : 39.51163133703768
             */

            private double lon;
            private double lat;

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AddressComponentBean {
            /**
             * address : 廊坊北站附近
             * city : 河北省廊坊市广阳区
             * road : 光明西道
             * poi_position : 西南
             * address_position : 西南
             * road_distance : 150
             * poi : 中心旅馆
             * poi_distance : 69
             * address_distance : 69
             */

            private String address;
            private String city;
            private String road;
            private String poi_position;
            private String address_position;
            private int road_distance;
            private String poi;
            private String poi_distance;
            private int address_distance;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getRoad() {
                return road;
            }

            public void setRoad(String road) {
                this.road = road;
            }

            public String getPoi_position() {
                return poi_position;
            }

            public void setPoi_position(String poi_position) {
                this.poi_position = poi_position;
            }

            public String getAddress_position() {
                return address_position;
            }

            public void setAddress_position(String address_position) {
                this.address_position = address_position;
            }

            public int getRoad_distance() {
                return road_distance;
            }

            public void setRoad_distance(int road_distance) {
                this.road_distance = road_distance;
            }

            public String getPoi() {
                return poi;
            }

            public void setPoi(String poi) {
                this.poi = poi;
            }

            public String getPoi_distance() {
                return poi_distance;
            }

            public void setPoi_distance(String poi_distance) {
                this.poi_distance = poi_distance;
            }

            public int getAddress_distance() {
                return address_distance;
            }

            public void setAddress_distance(int address_distance) {
                this.address_distance = address_distance;
            }
        }
    }
}
