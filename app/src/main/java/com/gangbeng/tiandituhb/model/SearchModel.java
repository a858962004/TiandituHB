package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/7/29.
 */

public class SearchModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String postStr = String.valueOf(parameter.get("postStr"));
        String type = String.valueOf(parameter.get("type"));
        OkHttpUtils.get()
                .url(PubConst.url+"search")
                .addParams("postStr",postStr)
                .addParams("type",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SearchBean bean = gson.fromJson(response, SearchBean.class);
                        back.success(bean);
                    }
                });
    }
}
