package com.gangbeng.tiandituhb.event;

import java.io.Serializable;

/**
 * @author zhanghao
 * @date 2018-08-02
 */

public class EndPoint implements Serializable {
    private String x;
    private String y;
    private String name;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
