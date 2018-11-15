package com.gangbeng.tiandituhb.presenter;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.model.UpdateModel;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-15
 */

public class UpdatePresenter implements NewBasePresenter, NewCallBack {
    private NewBaseView view;
    private NewBaseModel model;

    public UpdatePresenter(NewBaseView view){
        this.view=view;
        model=new UpdateModel();
    }

    @Override
    public void setRequest(Map<String, Object> parameter, String lable) {
        model.setRequest(parameter,lable,this);
    }

    @Override
    public void success(Object bean, String lable) {
        view.setData(bean,lable);
    }

    @Override
    public void failed(String failReason, String lable) {

    }
}
