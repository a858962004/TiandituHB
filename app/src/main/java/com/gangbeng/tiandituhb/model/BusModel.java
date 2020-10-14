package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class BusModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String postStr = String.valueOf(parameter.get("postStr"));
        OkHttpUtils.get()
                .url(PubConst.directionurl +"transit")
                .addParams("type","busline")
                .addParams("postStr",postStr)
                .addParams("tk",PubConst.tiandituKey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        BusBean busBean = gson.fromJson(response, BusBean.class);
                        back.success(busBean);
                    }
                });
    }
}
