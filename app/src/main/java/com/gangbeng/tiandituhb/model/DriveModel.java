package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.DriveRouteBean;
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
        String points = String.valueOf(parameter.get("points"));
        OkHttpUtils.get()
                .url(PubConst.driveUrl)
                .addParams("type","shortest")
                .addParams("points",points)
                .addParams("oneway","no")
                .addParams("format","gpx-route")
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
                        if (response.equals("{\"exceptionCode\":\"400\",\"exceptionText\":\"未得到结果\"}")){
                            Gson gson=new Gson();
                            DriveRouteBean bean = gson.fromJson(response, DriveRouteBean.class);
                            back.success(bean);
                        }else {
                            DriveRouteBean newDriveRouteBean = ParserXMLWithPull.getXmlContentForPull(response);
                            newDriveRouteBean.setExceptionCode("200");
                            back.success(newDriveRouteBean);
                        }
                    }
                });

    }
}
