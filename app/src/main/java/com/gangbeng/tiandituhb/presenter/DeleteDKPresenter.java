package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.model.DeleteDKModel;
import com.gangbeng.tiandituhb.model.DeletePicModel;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/22.
 */

public class DeleteDKPresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;

    public DeleteDKPresenter(BaseView view) {
        this.view = view;
        model = new DeleteDKModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter) {
        view.showLoadingDialog("请稍后", "正在删除数据", false);
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
