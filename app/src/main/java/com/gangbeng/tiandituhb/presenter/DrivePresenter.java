package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.model.DriveModel;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/1.
 */

public class DrivePresenter implements BasePresenter, OnCallBack {
    private BaseView view;
    private BaseModel model;
    public DrivePresenter(BaseView view){
        this.view=view;
        model=new DriveModel();
    }
    @Override
    public void setRequest(Map<String, Object> parameter) {
        view.showLoadingDialog("请稍等","正在请求数据...",false);
        model.setRequest(parameter,this);
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
