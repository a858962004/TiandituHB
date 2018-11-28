package com.gangbeng.tiandituhb.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
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
    private List<Map<String, String>> data;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_groupset);
        setToolbarTitle("我的队伍");
        setToolbarRightVisible(false);
        presenter = new ShareGroupPresenter(this);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        mcommend = bundleExtra.getString("commend");
        data = (List<Map<String, String>>) bundleExtra.getSerializable("data");
        setview();
    }

    private void setview() {
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        for (Map<String, String> map : data) {
            String leader = map.get("leader");
            String name = map.get("name");
            if (leader.equals("0") && name.equals(user.getUsername())) {
                isCreator = true;
            }
        }
        if (isCreator) {
            btExitgroup.setText("解散队伍");
        } else {
            btExitgroup.setText("退出队伍");
        }
        adapter = new GroupAdapter(this, data, adaptercallback, isCreator);
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
        switch (lable) {
            case PubConst.LABLE_EXITGROUP:
                showMsg("退出成功！");
                break;
            case PubConst.LABLE_DELETEGROUP:
                showMsg("解散成功");
                break;
        }

    }
}
