package com.gangbeng.tiandituhb;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.gangbeng.tiandituhb.utils.ToastUtil;

/**
 * Created by Administrator on 2018-05-29.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    public static Context applicationContext;

    public static MyApplication getInstance() {
        return instance;
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;

    }




    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.toast((String) (msg.obj));
                    break;
                case 1:
                    ToastUtil.toastLong((String) (msg.obj));
                    break;
            }
        }
    };

}
