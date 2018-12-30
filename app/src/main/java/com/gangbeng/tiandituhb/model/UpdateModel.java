package com.gangbeng.tiandituhb.model;

import android.os.Handler;
import android.os.Looper;

import com.gangbeng.tiandituhb.base.NewBaseModel;
import com.gangbeng.tiandituhb.base.NewCallBack;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-15
 */

public class UpdateModel implements NewBaseModel {
    @Override
    public void setRequest(final Map<String, Object> parameter, final String lable, final NewCallBack back) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String math = "";
                switch (lable) {
                    case PubConst.LABLE_GETVERSION:
                        math = RequestUtil.getVersion;
                        final SoapObject postob = RequestUtil.postob(math, parameter);
                        MyLogUtil.showLog(postob);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (postob != null) back.success(postob, lable);
                                if (postob == null) back.failed("服务器连接失败", lable);
                            }
                        });
                        break;
                    case PubConst.LABLE_GETNEWVERSION:
                        math = RequestUtil.getNewVersion;
                        final String postobin = RequestUtil.postobByBody(math, parameter);
                        MyLogUtil.showLog(postobin);
                        Handler handler2 = new Handler(Looper.getMainLooper());
                        handler2.post(new Runnable() {
                            @Override
                            public void run() {
                                if (postobin != null) back.success(postobin, lable);
                                if (postobin == null) back.failed("服务器连接失败", lable);
                            }
                        });
                        break;

                }
                MyLogUtil.showLog("request");


            }
        }).start();
    }
}
