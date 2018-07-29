package com.gangbeng.tiandituhb.base;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-05-29
 */

public interface BasePresenter {
    /**
     * 设置参数
     * @param parameter
     */
    void setRequest(Map<String, Object> parameter);
}
