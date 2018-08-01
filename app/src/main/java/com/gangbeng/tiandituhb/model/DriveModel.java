package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.xmlparser.ParserXMLWithPull;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/8/1.
 */

public class DriveModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String postStr = String.valueOf(parameter.get("postStr"));
//        String postStr="{\"orig\":\"116.35506,39.92277\",\"dest\":\"116.39751,39.90854\",\"style\":\"0\"}";
        OkHttpUtils.get()
                .url(PubConst.url+"drive")
                .addParams("postStr",postStr)
                .addParams("type","search")
                .build()
                .writeTimeOut(20000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DriveRouteBean xmlContentForPull = ParserXMLWithPull.getXmlContentForPull(response);
                        back.success(xmlContentForPull);
                    }
                });

    }
}
