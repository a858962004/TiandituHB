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
 * @date 2018-11-27
 */

public class ShareGroupModel implements NewBaseModel {
    @Override
    public void setRequest(final Map<String, Object> parameter, final String lable, final NewCallBack back) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String math="";
                switch (lable){
                    case PubConst.LABLE_CREATEGROUP:
                        math = RequestUtil.CreateShareGroup;
                        break;
                    case PubConst.LABLE_ADDGROUP:
                        math = RequestUtil.AddtoShareGroup;
                        break;
                    case PubConst.LABLE_DELETEGROUP:
                        math = RequestUtil.DeleteShareGroup;
                        break;
                    case PubConst.LABLE_EXITGROUP:
                        math = RequestUtil.ExitShareGroup;
                        break;
                    case PubConst.LABLE_GETSHAREGROUP:
                        math=RequestUtil.GetShareGroup;
                        break;
                    case PubConst.LABLE_GETGROUPLOCATION:
                        math=RequestUtil.GetGroupShareLocation;
                        break;
                    case PubConst.LABLE_START_SHARE:
                        math = RequestUtil.UploadLocation;
                        break;
                    case PubConst.LABLE_ZOOMGETGROUP:
                        math=RequestUtil.GetGroupShareLocation;
                        break;
                }
                MyLogUtil.showLog("request");

                final SoapObject postob = RequestUtil.postob(math, parameter);
                MyLogUtil.showLog(postob);
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
