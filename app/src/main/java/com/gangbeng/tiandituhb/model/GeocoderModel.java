package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.GeocoderBean;
import com.gangbeng.tiandituhb.https.RequestUtil;
import com.gangbeng.tiandituhb.https.RetrofitManager;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/7/29.
 */

public class GeocoderModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String postStr = String.valueOf(parameter.get("postStr"));
        String type = String.valueOf(parameter.get("type"));
        Observable<GeocoderBean> geocoder = RetrofitManager.builder().getService().geocoder(postStr, type);
        RequestUtil<GeocoderBean> requestUtil = new RequestUtil<>();
        requestUtil.setRequest(geocoder, new RequestUtil.RequestCallback() {
            @Override
            public void onSuccess(Object data) {
                back.success(data);
            }

            @Override
            public void onFailed(String error) {
                back.failed(error);
            }
        });

    }
}
