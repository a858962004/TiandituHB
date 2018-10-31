package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.WeatherModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-10-31
 */

public class WeatherPresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;
    public WeatherPresenter (BaseView view){
        this.view=view;
        model=new WeatherModel();
    }
    @Override
    public void setRequest(Map<String, Object> parameter) {
        model.setRequest(parameter,this);
    }

    @Override
    public void success(Object bean) {
        view.setData(bean);
    }

    @Override
    public void failed(String failReason) {

    }
}
