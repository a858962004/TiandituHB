package com.gangbeng.tiandituhb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-09-21
 */

public class NewSearchBean implements Serializable {

    /**
     * total : 7374
     * count : 10
     * list : [{"gid":"26910","简称":"英伦时光","yif1":"11","erf1":"1105","sanf1":"110502","代码":"13100400110124030160","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.75062235492,"y":39.60174700465,"weight":100},{"gid":"26912","简称":"顺子烧烤","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030158","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.75057677473,"y":39.601889624453,"weight":100},{"gid":"26915","简称":"马金龙麻辣烫","yif1":"11","erf1":"1101","sanf1":"110100","代码":"1310040021078404800","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.75048227102,"y":39.602093211105,"weight":100},{"gid":"26926","简称":"便宜坊东北菜海鲜烧烤城","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030144","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74807453412,"y":39.608098167332,"weight":100},{"gid":"26927","简称":"小东北海鲜烧烤城","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030143","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74806728993,"y":39.608180927856,"weight":100},{"gid":"26932","简称":"知味轩","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030138","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74787597565,"y":39.608824274521,"weight":100},{"gid":"26938","简称":"小辉烧烤海鲜大排档","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030132","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74776925686,"y":39.609149602191,"weight":100},{"gid":"26940","简称":"中国兰州牛肉拉面","yif1":"11","erf1":"1101","sanf1":"110100","代码":"1310030031031400042","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74772192159,"y":39.609343652769,"weight":100},{"gid":"26962","简称":"鲜果多多","yif1":"11","erf1":"1105","sanf1":"110502","代码":"13100400110124030106","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74296979656,"y":39.609745996103,"weight":100},{"gid":"26967","简称":"夜焰火锅海鲜烧烤","yif1":"11","erf1":"1101","sanf1":"110100","代码":"13100400110124030113","地址":"河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城","x":116.74380641739,"y":39.609757718183,"weight":100}]
     */

    private int total;
    private int count;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean  implements Serializable{
        /**
         * gid : 26910
         * 简称 : 英伦时光
         * yif1 : 11
         * erf1 : 1105
         * sanf1 : 110502
         * 代码 : 13100400110124030160
         * 地址 : 河北省廊坊市廊坊经济技术开发区云鹏道街道东方大学城
         * x : 116.75062235492
         * y : 39.60174700465
         * weight : 100
         */

        private String gid;
        private String 简称;
        private String yif1;
        private String erf1;
        private String sanf1;
        private String 代码;
        private String 地址;
        private double x;
        private double y;
        private int weight;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String get简称() {
            return 简称;
        }

        public void set简称(String 简称) {
            this.简称 = 简称;
        }

        public String getYif1() {
            return yif1;
        }

        public void setYif1(String yif1) {
            this.yif1 = yif1;
        }

        public String getErf1() {
            return erf1;
        }

        public void setErf1(String erf1) {
            this.erf1 = erf1;
        }

        public String getSanf1() {
            return sanf1;
        }

        public void setSanf1(String sanf1) {
            this.sanf1 = sanf1;
        }

        public String get代码() {
            return 代码;
        }

        public void set代码(String 代码) {
            this.代码 = 代码;
        }

        public String get地址() {
            return 地址;
        }

        public void set地址(String 地址) {
            this.地址 = 地址;
        }

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

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
