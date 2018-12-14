package com.gangbeng.tiandituhb.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.gangbeng.tiandituhb.utils.ToastUtil;
import com.liulishuo.filedownloader.FileDownloader;

import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

public class MyApplication extends LitePalApplication {

    private static MyApplication mInstance = null;
    public BMapManager mBMapManager = null;
    public static Context applicationContext;
    private static MyActivityLifecycle myActivityLifecycle;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        applicationContext = this;
        myActivityLifecycle = new MyActivityLifecycle();
        FileDownloader.init(getApplicationContext());
        registerActivityLifecycleCallbacks(myActivityLifecycle);
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "BMapManager  初始化错误!",
                    Toast.LENGTH_LONG).show();
        }
        Log.d("ljx", "initEngineManager");
    }

    public static Context getContext() {
        if (applicationContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return applicationContext;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
//            if (iError != 0) {
//                // 授权Key错误：
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
//                        "请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError, Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "key认证成功", Toast.LENGTH_LONG)
//                        .show();
//            }
        }
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
//
//    @Override
//    public void onTerminate() {
//        MyLogUtil.showLog("appdestory");
//        if (Contant.ins().isLocalState()){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    UserEvent mUser = (UserEvent) SharedUtil.getSerializeObject("user");
//                    Map<String, Object> parameter = new HashMap<>();
//                    parameter.put("loginname", mUser.getLoginname());
//                    parameter.put("username", mUser.getUsername());
//                    parameter.put("x", String.valueOf(MainActivity.getInstense().getLocal().getX()));
//                    parameter.put("y", String.valueOf(MainActivity.getInstense().getLocal().getY()));
//                    parameter.put("state", "0");
//                    RequestUtil.postob(RequestUtil.UploadLocation, parameter);
//                }
//            }).start();
//        }
//        super.onTerminate();
//    }
}