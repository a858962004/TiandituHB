package com.gangbeng.tiandituhb.base;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-05-29
 */

public interface BaseModel {
    /**
     * 请求接口
     * @param parameter
     * @param back
     */
    void setRequest(Map<String, Object> parameter, OnCallBack back);
}
