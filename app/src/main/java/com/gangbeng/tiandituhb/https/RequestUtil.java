package com.gangbeng.tiandituhb.https;

/**
 * @author zhanghao
 * @fileName RequestUtil
 * @date 2018-04-25
 */

public class RequestUtil<T> {
//
//    public void setRequest(final Observable<T> data, final RequestCallback callback){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                data.subscribeOn(Schedulers.newThread());
//                data.observeOn(AndroidSchedulers.mainThread());
//                data.subscribe(new Subject<T>() {
//                    @Override
//                    public boolean hasObservers() {
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean hasThrowable() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean hasComplete() {
//                        return false;
//                    }
//
//                    @Override
//                    public Throwable getThrowable() {
//                        return null;
//                    }
//
//                    @Override
//                    protected void subscribeActual(Observer<? super T> observer) {
//
//                    }
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(final T value) {
//                        Handler mainHandler = new Handler(Looper.getMainLooper());
//                        mainHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                //已在主线程中，可以更新UI
//                                MyLogUtil.showLog(value.toString());
//                                String s = value.toString();
//                                callback.onSuccess(value);
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//                        Handler mainHandler = new Handler(Looper.getMainLooper());
//                        mainHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                //已在主线程中，可以更新UI
//                                MyLogUtil.showLog(e);
//                                callback.onFailed("服务器连接失败");
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//            }
//        }).start();
//
//    }
//
//    public interface RequestCallback{
//        void onSuccess(Object data);
//        void onFailed(String error);
//    }
}
