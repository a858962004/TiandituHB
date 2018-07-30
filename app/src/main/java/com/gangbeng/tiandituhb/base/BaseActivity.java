package com.gangbeng.tiandituhb.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.widget.MyToolbar;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @fileName BaseActivity
 * @date 2018-04-26
 */

public abstract class BaseActivity extends AppCompatActivity {

    private MyToolbar toolbar;
    private RelativeLayout relativeLayout;
    protected BaseActivity baseContext = BaseActivity.this;
    private ProgressDialog dialog;

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (MyToolbar) findViewById(R.id.myToolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.fragment_container);
        setToolbarVisibility(true);
        setToolbarLeftIcon(R.mipmap.ic_back);
        setToolbarRightVisible(true);
        setToolbarLeftVisible(true);
        setToolbarTitle("");
        toolbar.setMyToolBarBtnListenter(new MyToolbar.MyToolBarBtnListenter() {
            @Override
            public void ImageRightBtnclick() {
                setRightClickListen();
            }

            @Override
            public void ImageLeftBtnclick() {
                setLeftClickListen();
            }

            @Override
            public void searchBtnclick(String content) {

            }
        });
        initView();
    }


    /**
     * 为内容区域添加布局
     *
     * @param relId
     */
    public void setContentLayout(int relId) {
        View contentView = LayoutInflater.from(this).inflate(relId, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(contentView, layoutParams);
        ButterKnife.bind(this);
    }

    private Toast toast;

    /**
     * 显示吐司
     *
     * @param title
     */
    public void ShowToast(String title) {
        toast = Toast.makeText(BaseActivity.this, title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 隐藏正在显示的吐司
     */
    public void HideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 初始化progressdialog
     *
     * @return
     */
    public ProgressDialog showProgressDialog() {
        ProgressDialog proDialog = ProgressDialog.show(BaseActivity.this, "连接中..",
                "连接中..请稍后....", true, true);
//        proDialog.setCanceledOnTouchOutside(false);
//        proDialog.setCancelable(false);
        return proDialog;
    }

    /**
     * 点击空白软键盘消失
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        jianpandelete();
        return super.onTouchEvent(event);
    }

    //此方法，如果显示则隐藏，如果隐藏则显示
    public void hintKbOne() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    public void jianpandelete() {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 封装Intent跳转
     *
     * @param clazz       要跳向的界面的class
     * @param isCloseSelf 是否关闭本界面
     */
    protected void skip(Class<?> clazz, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    protected void skip(Class<?> clazz, Bundle data, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    protected void skip(Class<?> clazz, ArrayList<?> dataList, boolean isCloseSelf) {
        Intent intent = new Intent(baseContext, clazz);
        if (dataList != null) intent.putExtra(PubConst.DATA, dataList);
        startActivity(intent);
        if (isCloseSelf) baseContext.finish();
        leftOutRightIn(baseContext);
    }

    /**
     * 封装Intent跳转
     *
     * @param clazz 要跳向的界面的class
     */
    protected void skipForResult(Class<?> clazz, Bundle data, int requestCode) {
        Intent intent = new Intent(baseContext, clazz);
        if (data != null) intent.putExtra(PubConst.DATA, data);
        startActivityForResult(intent, requestCode);
        leftOutRightIn(baseContext);
    }

    protected void skipForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(baseContext, clazz);
        startActivityForResult(intent, requestCode);
        leftOutRightIn(baseContext);
    }

    /**
     * 做出右进的效果
     *
     * @param context
     */
    public static void leftOutRightIn(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 右侧出的效果
     *
     * @param context
     */
    public static void rightOut(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    /**
     * 功能 ：显示一个进度条对话框
     */
    public void showProcessDialog(String title, String msg, boolean falg) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(falg);
        dialog.show();
    }

    /**
     * 功能 ：取消一个进度条对话框
     */
    protected void dismissProcessDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    //设置toolbar显示隐藏
    protected void setToolbarVisibility(boolean a) {
        if (a) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    //显示搜索框，默认隐藏
    protected void showEditText() {
        toolbar.showEditText();
    }


    //设置toolbar左侧按钮显示隐藏
    protected void setToolbarLeftVisible(boolean b) {
        if (b) {
            toolbar.showLeftBtnIcon();
        } else {
            toolbar.hintLeftBtnIcon();
        }
    }

    //设置toolbar右侧按钮显示隐藏
    protected void setToolbarRightVisible(boolean b) {
        if (b) {
            toolbar.showRightBtnIcon();
        } else {
            toolbar.hintRightBtnIcon();
        }
    }

    //设置toolbar右侧按钮图标
    protected void setToolbarLeftIcon(int id) {
        toolbar.setLeftImageBtnDrawable(id);
    }

    protected void setRightImageBtnText(String string){
        toolbar.setRightImageBtnText(string);
    }

    //设置toolbar文字
    protected void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    //设置右侧点击事件
    protected void setRightClickListen() {
    }

    //设置左侧点击事件
    protected void setLeftClickListen() {
        finish();
    }

    /**
     * 设置listview高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (DensityUtil.dip2px(this, 1) * listAdapter.getCount());

        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0); // 可删除

        listView.setLayoutParams(params);
    }
}
