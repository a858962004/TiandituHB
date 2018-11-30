package com.gangbeng.tiandituhb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.RemoveGroupAdpter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.presenter.ShareGroupPresenter;
import com.gangbeng.tiandituhb.utils.ShowDialog;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-11-29
 */

public class GroupRemoveActivity extends BaseActivity implements NewBaseView {
    @BindView(R.id.lv_removegroup)
    ListView lvRemovegroup;
    @BindView(R.id.tv_removegroup)
    TextView tvRemovegroup;

    private RemoveGroupAdpter adpter;
    private List<Map<String, String>> data = new ArrayList<>();
    private NewBasePresenter presenter;
    private int removeposition = -1;
    private DialogInterface mdialog;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_removegroup);
        setToolbarTitle("移除");
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        data = (List<Map<String, String>>) bundleExtra.getSerializable("removedata");
        adpter = new RemoveGroupAdpter(this, data, callBack);
        lvRemovegroup.setAdapter(adpter);
        presenter = new ShareGroupPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    RemoveGroupAdpter.RemoveCallBack callBack = new RemoveGroupAdpter.RemoveCallBack() {
        @Override
        public void removeCallBack(int position, Map<String, String> data) {
            removeposition = position;
            final String removeloginname = data.get("loginname");
            String name = data.get("username");
            ShowDialog.showAttention(GroupRemoveActivity.this, "请注意", "是否移除" + name+"?", new ShowDialog.DialogCallBack() {
                @Override
                public void dialogSure(DialogInterface dialog) {
                    mdialog = dialog;
                    Map<String, Object> parameter = new HashMap<>();
                    parameter.put("loginname", removeloginname);
                    presenter.setRequest(parameter, PubConst.LABLE_EXITGROUP);
                }

                @Override
                public void dialogCancle(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });


        }
    };

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
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    mdialog.dismiss();
                    showMsg("移除成功");
                    adpter.removeItem(removeposition);
//                    this.data.remove(removeposition);
                    removeposition = -1;
                    if (this.data.size() == 0) {
                        tvRemovegroup.setVisibility(View.VISIBLE);
                    }
                } else {
                    showMsg(errReason);
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GroupSetActivity.getInstence().refreshGroup();
    }
}
