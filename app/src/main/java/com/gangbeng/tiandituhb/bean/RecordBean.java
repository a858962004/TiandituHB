package com.gangbeng.tiandituhb.bean;

import java.io.Serializable;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class RecordBean implements Serializable {
    private String data;
    private boolean isInput;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isInput() {
        return isInput;
    }

    public void setInput(boolean input) {
        isInput = input;
    }
}
