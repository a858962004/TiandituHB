package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.LoginModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-10-17
 */

public class LoginPrenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;

    public LoginPrenter(BaseView view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter) {
        view.showLoadingDialog("请稍后", "正在登录...", true);
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
