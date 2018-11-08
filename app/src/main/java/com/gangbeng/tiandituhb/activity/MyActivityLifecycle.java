package com.gangbeng.tiandituhb.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.utils.Util;

/**
 * @author zhanghao
 * @date 2018-11-08
 */

public class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private int startCount;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("==============", "======>onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("==============", "======>onActivityStarted");
        startCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e("==============", "======>onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e("==============", "======>onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("==============", "======>onActivityStopped");
        startCount--;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity.getClass()==MainActivity.class){
            if (Contant.ins().isLocalState()) {
                Contant.ins().setLocalState(false);
                MyLogUtil.showLog(Contant.ins().isLocalState());
                if (!Contant.ins().isNormalQuit()) {
                    Util.setUnnormalQuit(PubConst.LABLE_UNNORMAL_QUIT);
                }
            }else {
                Util.setUnnormalQuit(PubConst.LABLE_NORMAL_QUIT);
            }
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("==============", "======>onActivitySaveInstanceState");
    }

    public int getStartCount() {
        return startCount;
    }
}
