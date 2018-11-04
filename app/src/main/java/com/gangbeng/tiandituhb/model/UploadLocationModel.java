package com.gangbeng.tiandituhb.model;

import android.os.Handler;
import android.os.Looper;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.RequestUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public class UploadLocationModel implements NewBaseModel {
    @Override
    public void setRequest(final Map<String, Object> parameter, final String lable, final NewCallBack back) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String math="";
                switch (lable){
                    case PubConst.LABLE_START_SHARE:
                        math = RequestUtil.UploadLocation;
                        break;
                    case PubConst.LABLE_CLOSE_SHARE:
                        math = RequestUtil.UploadLocation;
                        break;
                    case PubConst.LABLE_GET_SHARE:
                        math = RequestUtil.GetNewestLocation;
                        break;
                }
                final SoapObject postob = RequestUtil.postob(math, parameter);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (postob != null) back.success(postob,lable);
                        if (postob == null) back.failed("服务器连接失败",lable);
                    }
                });
            }
        }).start();
    }
}
