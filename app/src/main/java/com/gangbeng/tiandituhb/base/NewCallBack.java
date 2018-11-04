package com.gangbeng.tiandituhb.base;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public interface NewCallBack {
    void success(Object bean,String lable);
    void failed(String failReason,String lable);
}
