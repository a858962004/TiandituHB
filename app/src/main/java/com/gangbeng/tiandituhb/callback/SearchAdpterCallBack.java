package com.gangbeng.tiandituhb.callback;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public interface SearchAdpterCallBack {
    void aroundclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean);
    void routeclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean,String name);
    void itemclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean);
}
