package com.gangbeng.tiandituhb.base;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public interface NewBaseModel {
    /**
     * 请求接口
     * @param parameter
     * @param back
     */
    void setRequest(Map<String, Object> parameter,String lable, NewCallBack back);
}
