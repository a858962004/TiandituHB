package com.gangbeng.tiandituhb.base;


/**
 * @author zhanghao
 * @fileName OnCallBack
 * @package com.gongchengyuan.gongchengyuantest.ui.model.callback
 * @date 2018-04-24
 */

public interface OnCallBack {
    void success(Object bean);
    void failed(String failReason);
}
