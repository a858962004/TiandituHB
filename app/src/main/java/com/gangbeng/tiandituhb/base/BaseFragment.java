package com.gangbeng.tiandituhb.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.DensityUtil;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Description：
 * Created on 2017/4/2
 */
public abstract class BaseFragment extends Fragment implements  View.OnClickListener{

    private View view;//缓存Fragment view
    Unbinder unbinder;
    private ProgressDialog dialog;

    /**
     * 初始化控件
     */
    public abstract void initView(View view, Bundle savedInstanceState);

//    public abstract void initListen();


    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
//        initListen();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, Bundle data, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 封装Intent跳转
     */
    public void skip(Class<?> clazz, Serializable serializableObj, boolean isCloseSelf)
    {
        Intent intent = new Intent(mActivity, clazz);
        if (serializableObj != null) intent.putExtra(PubConst.DATA, serializableObj);
        mActivity.startActivity(intent);
        if (isCloseSelf) mActivity.finish();
        leftOutRightIn(mActivity);
    }

    /**
     * 做出右进的效果
     *
     * @param context
     */
    public static void leftOutRightIn(Context context)
    {
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 右侧出的效果
     *
     * @param context
     */
    public static void rightOut(Context context)
    {
        ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    /**
     * 显示吐司
     *
     * @param title
     */
    public void ShowToast(String title) {
        mActivity.HideToast();
        mActivity.ShowToast(title);
    }

    /**
     * 隐藏正在显示的吐司
     */
    public void HideToast(){
        mActivity.HideToast();
    }

    /**
     * 设置listview高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = DensityUtil.getTotalHeightofListView(listView);
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0); // 可删除
        listView.setLayoutParams(params);
    }

    /**
     * 功能 ：显示一个进度条对话框
     */
    public void showProcessDialog(String title,String msg,boolean falg){
        if(dialog==null){
            dialog = new ProgressDialog(getActivity());
        }
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(falg);
        dialog.show();
    }

    /**
     * 功能 ：取消一个进度条对话框
     */
    protected void dismissProcessDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}
