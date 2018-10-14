package com.gangbeng.tiandituhb.event;

import com.esri.core.geometry.Point;

/**
 * @author zhanghao
 * @date 2018-10-14
 */

public class MapExtent {
    private Point center;
    private double scale;

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

}
