package com.gangbeng.tiandituhb.utils;

import com.gangbeng.tiandituhb.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class Util {

    public static boolean isCollect(SearchBean.PoisBean bean) {
        boolean iscollect = false;
        List<SearchBean.PoisBean> data = (List<SearchBean.PoisBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data != null) {
            for (SearchBean.PoisBean poisBean : data) {
                if (poisBean.getHotPointID().equals(bean.getHotPointID())) {
                    iscollect = true;
                    return iscollect;
                }
            }
        }
        return iscollect;
    }

    public static void setCollect(SearchBean.PoisBean bean) {
        List<SearchBean.PoisBean> data = (List<SearchBean.PoisBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data == null) data = new ArrayList<>();
        data.add(bean);
        SharedUtil.saveSerializeObject("collectpoint", data);
    }

    public static void cancelCollect(SearchBean.PoisBean bean) {
        List<SearchBean.PoisBean> data = (List<SearchBean.PoisBean>) SharedUtil.getSerializeObject("collectpoint");
        for (SearchBean.PoisBean poisBean : data) {
            if (bean.getHotPointID().equals(poisBean.getHotPointID())) {
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
}
