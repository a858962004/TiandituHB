package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.EditPasswordModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-10-18
 */

public class EditPasswordPresenter implements BasePresenter, OnCallBack {

    private BaseView view;
    private BaseModel model;

    public EditPasswordPresenter(BaseView view) {
        this.view = view;
        model = new EditPasswordModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter) {
        view.showLoadingDialog("请稍后", "正在修改密码...", false);
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
