package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.model.UploadLocationModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public class UploadLocationPresenter implements NewBasePresenter, NewCallBack {
    private NewBaseView view;
    private NewBaseModel model;

    public UploadLocationPresenter(NewBaseView view) {
        this.view = view;
        model = new UploadLocationModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter, String lable) {
//        view.showLoadingDialog("请稍后", "正在打开位置共享", false);
        model.setRequest(parameter,lable, this);
    }

    @Override
    public void success(Object bean, String lable) {
//        view.canelLoadingDialog();
        view.setData(bean,lable);
    }

    @Override
    public void failed(String failReason, String lable) {
//        view.canelLoadingDialog();
//        view.showMsg(failReason);
    }
}
