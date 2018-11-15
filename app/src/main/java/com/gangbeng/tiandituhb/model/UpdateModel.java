package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.bean.UpdateBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * @author zhanghao
 * @date 2018-11-15
 */

public class UpdateModel implements NewBaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final String lable, final NewCallBack back) {
        OkHttpUtils.get()
                .url(PubConst.update)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed("服务器连接失败",lable);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        UpdateBean updateBean = gson.fromJson(response, UpdateBean.class);
                        back.success(updateBean,lable);
                    }
                });
    }
}
