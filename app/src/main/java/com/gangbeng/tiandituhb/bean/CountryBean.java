package com.gangbeng.tiandituhb.bean;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-10-31
 */

public class CountryBean {

    private List<CountriesBean> countries;

    public List<CountriesBean> getCountries() {
        return countries;
    }

    public void setCountries(List<CountriesBean> countries) {
        this.countries = countries;
    }

    public static class CountriesBean {
        /**
         * Latitude : 39.436466
         * Longitude : 116.2999
         * name : 固安
         */

        private String Latitude;
        private String Longitude;
        private String name;

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
