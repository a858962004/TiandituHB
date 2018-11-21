package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.WeatherBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * @author zhanghao
 * @date 2018-10-31
 */

public class WeatherModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String city = String.valueOf(parameter.get("city"));
        OkHttpUtils.get()
                .url(PubConst.weatherapi)
                .addParams("location",city)
                .addParams("key",PubConst.weatherKey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed("服务器连接失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson =new Gson();
                        WeatherBean weatherBean = gson.fromJson(response, WeatherBean.class);
                        back.success(weatherBean);
                    }
                });
    }
}
