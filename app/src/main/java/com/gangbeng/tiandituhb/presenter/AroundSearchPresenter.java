package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.AroundSearchModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-09-21
 */

public class AroundSearchPresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;

    public AroundSearchPresenter(BaseView view) {
        this.view = view;
        model = new AroundSearchModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter) {
        view.showLoadingDialog("请稍等","正在搜索数据...",false);
        model.setRequest(parameter, this);
    }

    @Override
    public void success(Object bean) {
        view.canelLoadingDialog();
        view.setData(bean);
    }

    @Override
    public void failed(String failReason) {
        view.canelLoadingDialog();
        view.showMsg("连接超时");
    }
}
