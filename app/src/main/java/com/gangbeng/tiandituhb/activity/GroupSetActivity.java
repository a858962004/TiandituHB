package com.gangbeng.tiandituhb.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.GroupAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.presenter.ShareGroupPresenter;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-11-28
 */

public class GroupSetActivity extends BaseActivity implements NewBaseView {
    @BindView(R.id.recycler_group)
    RecyclerView recyclerGroup;
    @BindView(R.id.bt_exitgroup)
    Button btExitgroup;

    private GroupAdapter adapter;
    private String mcommend;
    private UserEvent user;
    private boolean isCreator = false;
    private NewBasePresenter presenter;
    private List<Map<String, String>> data=new ArrayList<>();
    List<Map<String,String>> mdata=new ArrayList<>();
    private String createloginname;
    private static GroupSetActivity activity;

    public static GroupSetActivity getInstence(){
        return activity;
    }

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_groupset);
        setToolbarTitle("我的队伍");
        setToolbarRightVisible(false);
        activity=this;
        presenter = new ShareGroupPresenter(this);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        mcommend = bundleExtra.getString("commend");
        createloginname = bundleExtra.getString("createloginname");
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        refreshGroup();
    }

    public void refreshGroup() {
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("loginname",user.getLoginname());
        presenter.setRequest(parameter, PubConst.LABLE_GETGROUPLOCATION);
    }

    private void setview() {
        if (createloginname.equals(user.getLoginname())){
            isCreator = true;
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(createloginname)){
                    Map<String, String> m = new HashMap<>();
                    m.put("loginname",loginname);
                    m.put("username", "我");
                    m.put("leader", "0");
                    m.put("x",x);
                    m.put("y",y);
                    mdata.add(m);
                }
            }
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(createloginname))continue;
                Map<String, String> map2 = new HashMap<>();
                map2.put("loginname",loginname);
                map2.put("username", username);
                map2.put("leader", "1");
                map2.put("x",x);
                map2.put("y",y);
                mdata.add(map2);
            }
        }else {
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(user.getLoginname())){
                    Map<String, String> m = new HashMap<>();
                    m.put("loginname",loginname);
                    m.put("username", "我");
                    m.put("leader", "1");
                    m.put("x",x);
                    m.put("y",y);
                    mdata.add(m);
                }
            }
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String leader="1";
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(user.getLoginname()))continue;
                if (loginname.equals(createloginname)){
                    leader="0";
                }
                Map<String, String> m = new HashMap<>();
                m.put("loginname",loginname);
                m.put("username", username);
                m.put("leader", leader);
                m.put("x",x);
                m.put("y",y);
                mdata.add(m);
            }
        }
        if (isCreator) {
            btExitgroup.setText("解散队伍");
        } else {
            btExitgroup.setText("退出队伍");
        }
        adapter = new GroupAdapter(this, mdata, adaptercallback, isCreator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerGroup.setLayoutManager(linearLayoutManager);
        recyclerGroup.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    GroupAdapter.GroupClick adaptercallback = new GroupAdapter.GroupClick() {
        @Override
        public void addCallBack() {
            // 从API11开始android推荐使用android.content.ClipboardManager
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mcommend);
            ShowDialog.showAttention(GroupSetActivity.this, "组队口令已复制", "队伍口令已复制到剪切板中，可粘贴发送给好友！", new ShowDialog.DialogCallBack() {
                @Override
                public void dialogSure(DialogInterface dialog) {
                    dialog.dismiss();
                }

                @Override
                public void dialogCancle(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }

        @Override
        public void removeCallBack() {
            if (data.size() > 1) {
                List<Map<String,String>>removedata=new ArrayList<>();
                for (int i = 0; i < mdata.size(); i++) {
                    if (i!=0){
                        removedata.add(mdata.get(i));
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("removedata", (Serializable) removedata);
                skip(GroupRemoveActivity.class,bundle,false);
            }else {
                showMsg("没有可移除对象");
            }
        }

        @Override
        public void clickCallBack(int position, Map<String, String> data) {

        }
    };

    @OnClick(R.id.bt_exitgroup)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_exitgroup:
                String attention = isCreator ? "是否要解散队伍？" : "是否要退出队伍？";
                ShowDialog.showAttention(GroupSetActivity.this, "请注意", attention, new ShowDialog.DialogCallBack() {
                    @Override
                    public void dialogSure(DialogInterface dialog) {
                        dialog.dismiss();
                        Map<String, Object> parameter = new HashMap<>();
                        parameter.put("loginname", user.getLoginname());
                        String lable = isCreator ? PubConst.LABLE_DELETEGROUP : PubConst.LABLE_EXITGROUP;
                        presenter.setRequest(parameter, lable);
                    }

                    @Override
                    public void dialogCancle(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                break;

        }

    }

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String lable, String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog(String lable) {
        dismissProcessDialog();
    }

    @Override
    public void setData(Object data, String lable) {
        SoapObject soapObject = (SoapObject) data;

        switch (lable) {
            case PubConst.LABLE_EXITGROUP:
                showMsg("退出成功！");
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    Contant.ins().setLocalState(false);
                    GroupActivity.getInstence().finish();
                    finish();
                } else {
                    showMsg(errReason);
                }
                break;
            case PubConst.LABLE_DELETEGROUP:
                showMsg("解散成功");
                String result2 = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason2 = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString2 = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result2.equals("ok")) {
                    Contant.ins().setLocalState(false);
                    GroupActivity.getInstence().finish();
                    finish();
                } else {
                    showMsg(errReason2);
                }
                break;
            case PubConst.LABLE_GETGROUPLOCATION:
                if (!soapObject.toString().equals("anyType{}")) {
                    this.data.clear();
                    mdata.clear();
                    List<SoapObject> newestLocation = RequestUtil.getObjectValue(soapObject, "NewestLocation");
                    for (SoapObject object : newestLocation) {
                        String id = RequestUtil.getSoapObjectValue(object, "ID");
                        String loginname = RequestUtil.getSoapObjectValue(object, "loginname");
                        String username = RequestUtil.getSoapObjectValue(object, "username");
                        String x = RequestUtil.getSoapObjectValue(object, "x");
                        String y = RequestUtil.getSoapObjectValue(object, "y");
                        Map<String,String>map=new HashMap<>();
                        map.put("loginname",loginname);
                        map.put("username",username);
                        map.put("x", x);
                        map.put("y", y);
                        this.data.add(map);
                    }
                    setview();
                }
                break;
        }
    }

    @Override
    protected void setLeftClickListen() {
        super.setLeftClickListen();
        GroupActivity.getInstence().refreshGroupLocation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            GroupActivity.getInstence().refreshGroupLocation();
            finish();
        }
        return false;
    }


}
