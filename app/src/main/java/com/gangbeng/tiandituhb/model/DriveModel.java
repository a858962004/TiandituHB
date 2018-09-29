package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.xmlparser.ParserXMLWithPull;
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
        String postStr = String.valueOf(parameter.get("postStr"));
//        String postStr="{\"orig\":\"111.77952852400398,37.15078227322101\",\"dest\":\"111.76558103712654,37.14408747951984\",\"style\":\"0\"}";
        OkHttpUtils.get()
                .url(PubConst.url+"drive")
                .addParams("postStr",postStr)
                .addParams("type","search")
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
                        DriveRouteBean xmlContentForPull = ParserXMLWithPull.getXmlContentForPull(response);
                        back.success(xmlContentForPull);
                    }
                });

    }
}
