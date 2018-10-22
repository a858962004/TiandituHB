package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.DKLVAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.DKHCInfo;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.GetDKPresenter;
import com.gangbeng.tiandituhb.presenter.SubmitDKPresenter;
import com.gangbeng.tiandituhb.utils.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-21
 */

public class DKListActivity extends BaseActivity implements BaseView {
    @BindView(R.id.list_dk)
    ListView listDk;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.ll_tijiao)
    LinearLayout llTijiao;
    private static DKListActivity activity;
    private static BasePresenter presenter, submitPresenter;
    private static UserEvent user;
    private DKLVAdapter adapter;
    private List<SoapObject> dkhcInfo;
    private String infostate;
    private String activitystring;

    public static DKListActivity getInstence() {
        return activity;
    }

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_dklist);
        activity = this;
        setRightImageBtnText("添加");
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        presenter = new GetDKPresenter(this);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        activitystring = bundleExtra.getString("activity");
        setToolbarTitle(activitystring);
        if (activitystring.equals("地块核查")) {
            infostate = "0";
        }
        if (activitystring.equals("添加信息点")) {
            infostate = "1";
        }
        setNetWork(infostate);
        listDk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getState() == 0) {
                    setToolbarLeftIcon(R.mipmap.icon_quxiao);
                    setRightImageBtnText("全选");
                    setToolbarTitle("已选中0项");
                    llTijiao.setVisibility(View.VISIBLE);
                    adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, 1, false, new DKLVAdapter.CallBack() {
                        @Override
                        public void checkBoxChangeListen() {
                            setToolbarTitle("已选中" + adapter.getCheckData().size() + "项");
                        }
                    });
                    listDk.setAdapter(adapter);
                }
                return false;
            }
        });
        listDk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getState() == 0) {
                    SoapObject soapObject = dkhcInfo.get(position);
                    DKHCInfo dkhcInfo = RequestUtil.soapObjectToDKHCInfo(soapObject);
                    Bundle bundle = new Bundle();
                    bundle.putString("activity",activitystring);
                    bundle.putSerializable("data", dkhcInfo);
                    skip(DKDitailActivity.class, bundle, false);
                }
//                else {
//                    DKLVAdapter.ViewHolder tag = (DKLVAdapter.ViewHolder) view.getTag();
//                    tag.checkBox.setChecked(!tag.checkBox.isChecked());
//                }
            }
        });
    }

    public static void setNetWork(String infostate) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", user.getLoginname());
        parameter.put("submitstate", "0");
        parameter.put("infostate", infostate);
        presenter.setRequest(parameter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void setRightClickListen() {
        if (adapter == null || adapter.getState() == 0) {
            if (activitystring.equals("地块核查")) skip(DKCheckActivity.class, false);
            if (activitystring.equals("添加信息点")) skip(PointBackActivity.class, false);
        } else if (adapter.getState() == 1) {
            adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, 1, !adapter.getAllCheck(), new DKLVAdapter.CallBack() {
                @Override
                public void checkBoxChangeListen() {
                    setToolbarTitle("已选中" + adapter.getCheckData().size() + "项");
                }
            });
            listDk.setAdapter(adapter);
            int size = adapter.getAllCheck() ? dkhcInfo.size() : 0;
            setToolbarTitle("已选中" + size + "项");
        }
    }

    @Override
    protected void setLeftClickListen() {
        if (adapter == null || adapter.getState() == 0) {
            super.setLeftClickListen();
        } else if (adapter.getState() == 1) {
            setToolbarTitle(activitystring);
            setRightImageBtnText("添加");
            setToolbarLeftIcon(R.mipmap.icon_arrow_left);
            llTijiao.setVisibility(View.GONE);
            adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, 0, false, null);
            listDk.setAdapter(adapter);
        }
    }

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void setData(Object data) {
        if (data instanceof SoapObject) {
            SoapObject soapObject = (SoapObject) data;
            if (submitPresenter == null) {
                if (soapObject.toString().equals("anyType{}")) {
                    tvNodata.setVisibility(View.VISIBLE);
                } else {
                    tvNodata.setVisibility(View.GONE);
                    dkhcInfo = RequestUtil.getObjectValue(soapObject, "DKHCInfo");
                    adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, 0, false, null);
                    listDk.setAdapter(adapter);
                }
            } else {
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    showMsg("提交成功");
                    setToolbarTitle(activitystring);
                    setRightImageBtnText("添加");
                    setToolbarLeftIcon(R.mipmap.icon_arrow_left);
                    llTijiao.setVisibility(View.GONE);
                    submitPresenter = null;
                    setNetWork(infostate);
                } else {
                    showMsg("提交失败：" + errReason);
                }
            }

        }
    }

    @OnClick(R.id.ll_tijiao)
    public void onViewClicked() {
        submitPresenter = new SubmitDKPresenter(DKListActivity.this);
        List<SoapObject> checkData = adapter.getCheckData();
        String idlist = "";
        for (int i = 0; i < checkData.size(); i++) {
            if (i == 0) {
                idlist = RequestUtil.getSoapObjectValue(checkData.get(i), "ID");
            } else {
                idlist += "," + RequestUtil.getSoapObjectValue(checkData.get(i), "ID");
            }
        }
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("dkid", idlist);
        submitPresenter.setRequest(parameter);
    }
}
