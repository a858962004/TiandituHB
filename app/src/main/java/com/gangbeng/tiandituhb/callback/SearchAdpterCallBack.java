package com.gangbeng.tiandituhb.callback;

import com.gangbeng.tiandituhb.bean.SearchBean;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public interface SearchAdpterCallBack {
    void aroundclick(SearchBean.PoisBean bean);
    void routeclick(SearchBean.PoisBean bean);
    void itemclick(SearchBean.PoisBean bean);
}
