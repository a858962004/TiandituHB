package com.gangbeng.tiandituhb.base;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public interface NewBaseView {

    /**
     * Toast数据
     * @param msg
     */
    void showMsg(String msg);

    /**
     * 展示一个进度条对话框
     * @param title 标题
     * @param msg 显示的内容
     * @param flag 是否可以取消
     */
    void showLoadingDialog(String lable,String title, String msg, boolean flag);

    /**
     * 取消进度条
     */
    void canelLoadingDialog(String lable);

    /**
     * 获得数据
     * @param data
     */
    void setData(Object data,String lable);
}
