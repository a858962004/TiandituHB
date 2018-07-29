package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.GeocoderModel;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/29.
 */

public class GeocoderPresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;

    public GeocoderPresenter(BaseView view){
        this.view=view;
        model=new GeocoderModel();
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
        view.showMsg(failReason);
    }
}
