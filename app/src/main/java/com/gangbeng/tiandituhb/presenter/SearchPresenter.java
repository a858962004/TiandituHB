package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.SearchModel;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/29.
 */

public class SearchPresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;

    public SearchPresenter(BaseView view) {
        this.view = view;
        model = new SearchModel();
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
        view.showMsg(failReason);
    }
}
