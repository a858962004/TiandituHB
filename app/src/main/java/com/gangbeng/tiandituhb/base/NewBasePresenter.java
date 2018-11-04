package com.gangbeng.tiandituhb.base;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public interface NewBasePresenter {
    /**
     * 设置参数
     * @param parameter
     */
    void setRequest(Map<String, Object> parameter,String lable);
}
