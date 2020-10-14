package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.bean.NewDriveRouteBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.xmlparser.ParserXMLWithPull;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/8/1.
 */

public class DriveModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String origin = String.valueOf(parameter.get("origin"));
        String destination = String.valueOf(parameter.get("destination"));
        OkHttpUtils.get()
                .url(PubConst.directionurl +"driving")
                .addParams("origin",origin)
                .addParams("destination",destination)
                .addParams("output","json")
                .addParams("key",PubConst.gaodeDirectionKey)
                .build()
                .connTimeOut(50000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyLogUtil.showLog("faild:"+e.getMessage());
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        NewDriveRouteBean newDriveRouteBean = gson.fromJson(response, NewDriveRouteBean.class);
                        back.success(newDriveRouteBean);
                    }
                });

    }
}
