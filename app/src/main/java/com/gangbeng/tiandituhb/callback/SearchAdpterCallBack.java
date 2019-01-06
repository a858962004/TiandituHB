package com.gangbeng.tiandituhb.callback;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public interface SearchAdpterCallBack {
    void aroundclick(NewSearchBean.ListBean bean);
    void routeclick(NewSearchBean.ListBean bean,String name);
    void itemclick(NewSearchBean.ListBean bean);
}
