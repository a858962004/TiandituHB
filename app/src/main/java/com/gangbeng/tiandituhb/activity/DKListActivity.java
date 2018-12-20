package com.gangbeng.tiandituhb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.gangbeng.tiandituhb.presenter.DeleteDKPresenter;
import com.gangbeng.tiandituhb.presenter.GetDKPresenter;
import com.gangbeng.tiandituhb.presenter.SubmitDKPresenter;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
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
    private static BasePresenter presenter, submitPresenter,deletePresenter;
    private static UserEvent user;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    private DKLVAdapter adapter;
    private List<SoapObject> dkhcInfo;
    private String infostate;
    private String activitystring;
    private List<String>deletedata=new ArrayList<>();
    private DialogInterface mdialog;

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
                    adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, infostate,1, false, new DKLVAdapter.CallBack() {
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
                    bundle.putString("activity", activitystring);
                    bundle.putSerializable("data", dkhcInfo);
                    skip(DKDitailActivity.class, bundle, false);
                }
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
            adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo,infostate, 1, !adapter.getAllCheck(), new DKLVAdapter.CallBack() {
                @Override
                public void checkBoxChangeListen() {
                    setToolbarTitle("已选中" + adapter.getCheckData().size() + "项");
                }
            });
            listDk.setAdapter(adapter);
            int size = adapter.getAllCheck() ? dkhcInfo.size() : 0;
            setToolbarTitle("已选中" + size + "项");
            if (size>0)setRightImageBtnText("取消");
            if (size==0)setRightImageBtnText("全选");
        }
    }

    @Override
    protected void setLeftClickListen() {
        if (adapter == null || adapter.getState() == 0) {
            super.setLeftClickListen();
        } else if (adapter.getState() == 1) {
            cancelLongpress();
        }
    }

    @Override
    public void showMsg(String msg) {
        if (msg.equals("服务器连接失败")){
            if (deletePresenter!=null)deletedata.add(deletedata.size()+"");
            if (deletedata.size()==adapter.getCheckData().size()){
                setToolbarTitle(activitystring);
                setRightImageBtnText("添加");
                setToolbarLeftIcon(R.mipmap.icon_arrow_left);
                llTijiao.setVisibility(View.GONE);
                setNetWork(infostate);
                deletePresenter = null;
            }
            submitPresenter = null;
        }
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
            if (submitPresenter!=null){
                submitPresenter = null;
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    mdialog.dismiss();
                    showMsg("提交成功");
                    setToolbarTitle(activitystring);
                    setRightImageBtnText("添加");
                    setToolbarLeftIcon(R.mipmap.icon_arrow_left);
                    llTijiao.setVisibility(View.GONE);
                    setNetWork(infostate);
                } else {
                    showMsg("提交失败：" + errReason);
                }
            }else if (deletePresenter!=null){
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                deletedata.add(deletedata.size()+"");
                if (deletedata.size()==adapter.getCheckData().size()){
                    mdialog.dismiss();
                    deletePresenter = null;
                    setToolbarTitle(activitystring);
                    setRightImageBtnText("添加");
                    setToolbarLeftIcon(R.mipmap.icon_arrow_left);
                    llTijiao.setVisibility(View.GONE);
                    setNetWork(infostate);
                }
            }else {
                if (soapObject.toString().equals("anyType{}")) {
                    tvNodata.setVisibility(View.VISIBLE);
                    listDk.setVisibility(View.GONE);
                    adapter=null;
                } else {
                    tvNodata.setVisibility(View.GONE);
                    listDk.setVisibility(View.VISIBLE);
                    dkhcInfo = RequestUtil.getObjectValue(soapObject, "dkhcinfo");
                    adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo, infostate,0, false, null);
                    listDk.setAdapter(adapter);
                }
            }
        }
    }

    @OnClick({R.id.tv_delete, R.id.tv_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                final List<SoapObject> deletedata = adapter.getCheckData();
                if (deletedata.size()>0){
                    ShowDialog.showAttention(DKListActivity.this, "请注意", "是否删除？", new ShowDialog.DialogCallBack() {
                        @Override
                        public void dialogSure(DialogInterface dialog) {
                            mdialog=dialog;
                            deletePresenter=new DeleteDKPresenter(DKListActivity.this);
                            for (SoapObject deletedatum : deletedata) {
                                String dkid = RequestUtil.getSoapObjectValue(deletedatum, "ID");
                                Map<String,Object> parameter=new HashMap<>();
                                parameter.put("loginname",user.getLoginname());
                                parameter.put("dkid",dkid);
                                deletePresenter.setRequest(parameter);
                            }
                        }

                        @Override
                        public void dialogCancle(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });

                }

                break;
            case R.id.tv_upload:
                final List<SoapObject> checkData = adapter.getCheckData();
                final String[] idlist = {""};
                if (checkData.size()>0){
                    ShowDialog.showAttention(DKListActivity.this, "请注意", "是否提交？", new ShowDialog.DialogCallBack() {
                        @Override
                        public void dialogSure(DialogInterface dialog) {
                            mdialog=dialog;
                            submitPresenter = new SubmitDKPresenter(DKListActivity.this);
                            for (int i = 0; i < checkData.size(); i++) {
                                if (i == 0) {
                                    idlist[0] = RequestUtil.getSoapObjectValue(checkData.get(i), "ID");
                                } else {
                                    idlist[0] += "," + RequestUtil.getSoapObjectValue(checkData.get(i), "ID");
                                }
                            }
                            Map<String, Object> parameter = new HashMap<>();
                            parameter.put("idlist", idlist[0]);
                            submitPresenter.setRequest(parameter);
                        }

                        @Override
                        public void dialogCancle(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });

                }
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            if (adapter == null || adapter.getState() == 0) {
                finish();
            } else if (adapter.getState() == 1) {
                cancelLongpress();
            }
        }
        return false;
    }

    private void cancelLongpress() {
        setToolbarTitle(activitystring);
        setRightImageBtnText("添加");
        setToolbarLeftIcon(R.mipmap.icon_arrow_left);
        llTijiao.setVisibility(View.GONE);
        adapter = new DKLVAdapter(DKListActivity.this, dkhcInfo,infostate, 0, false, null);
        listDk.setAdapter(adapter);
    }
}
