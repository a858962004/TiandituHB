package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.model.ShareGroupModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-27
 */

public class ShareGroupPresenter implements NewBasePresenter, NewCallBack {
    private NewBaseView view;
    private NewBaseModel model;

    public ShareGroupPresenter(NewBaseView view) {
        this.view = view;
        model = new ShareGroupModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter, String lable) {
        view.showLoadingDialog(lable, "请稍后", "正在请求数据...", false);
        model.setRequest(parameter, lable, this);
    }

    @Override
    public void success(Object bean, String lable) {
        view.setData(bean, lable);
        view.canelLoadingDialog(lable);
    }

    @Override
    public void failed(String failReason, String lable) {
        view.canelLoadingDialog(lable);
        view.showMsg(failReason);
    }
}
