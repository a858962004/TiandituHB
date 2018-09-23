package com.gangbeng.tiandituhb.utils;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class Util {

    public static boolean isCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        boolean iscollect = false;
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data != null) {
            for (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean poisBean : data) {
                if (poisBean.getId().equals(bean.getId())) {
                    iscollect = true;
                    return iscollect;
                }
            }
        }
        return iscollect;
    }

    public static void setCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data == null) data = new ArrayList<>();
        data.add(bean);
        SharedUtil.saveSerializeObject("collectpoint", data);
    }

    public static void cancelCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        for (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean poisBean: data) {
            if (bean.getId().equals(poisBean.getId())) {
                data.remove(poisBean);
                break;
            }
        }
        SharedUtil.saveSerializeObject("collectpoint", data);
    }

    public static String saveTwoU(String s) {
        int i = s.length() - s.indexOf(".");
        if (!"".equals(s)) {
            if (s.indexOf(".") > 0 && i > 2) {
                s = s.substring(0, s.indexOf(".") + 3);
            } else if (s.indexOf(".") > 0 && i == 2) {
                s = s + "0";
            } else if (s.indexOf(".") < 0) {
                s = s + ".00";
            }
        }else {
            s="0.00";
        }
        return s;
    }

    public static String secondToHour(String second){
        String time="";
        double aDouble = Double.valueOf(second);
        long h = (long) (aDouble/3600);
        if (h==0){
            long m = (long)(aDouble/60);
            time=m+"分钟";
        }else {
            aDouble=aDouble-h*3600;
            long m = (long)(aDouble/60);
            time=h+"小时"+m+"分钟";
        }
        return time;
    }
}
