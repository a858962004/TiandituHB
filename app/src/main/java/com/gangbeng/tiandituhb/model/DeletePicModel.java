package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.http.RequestUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/22.
 */

public class DeletePicModel implements BaseModel {
    @Override
    public void setRequest(final Map<String, Object> parameter, final OnCallBack back) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final SoapObject postob = RequestUtil.postob(RequestUtil.DeleteDKCheckPic, parameter);
//                Handler handler = new Handler(Looper.myLooper());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (postob != null) back.success(postob);
//                        if (postob == null) back.failed("服务器连接失败");
//                    }
//                });
            }
        }).start();
    }
}
