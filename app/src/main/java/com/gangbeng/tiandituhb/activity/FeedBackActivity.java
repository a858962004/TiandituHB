package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.FeedBackPresenter;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-09-04
 */

public class FeedBackActivity extends BaseActivity implements BaseView {
    @BindView(R.id.ed_telephone)
    EditText edTelephone;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_name)
    EditText edName;
    private BasePresenter presenter;
    private UserEvent user;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_feedback);
        setToolbarTitle("信息反馈");
        setRightImageBtnText("提交");
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        presenter = new FeedBackPresenter(this);
    }

    @Override
    protected void setRightClickListen() {
        String telephone = String.valueOf(edTelephone.getText());
        String email = String.valueOf(edEmail.getText());
        String content = String.valueOf(edContent.getText());
        String name = String.valueOf(edName.getText());
        if (name.equals("")){
            ShowToast("请输入标题");
            return;
        }
        if (telephone.equals("")) {
            ShowToast("请输入电话");
            return;
        }
        if (email.equals("")) {
            ShowToast("请输入Email");
            return;
        }
        if (content.equals("")) {
            ShowToast("请输入信息内容");
            return;
        }
        Map<String,Object>parameter=new HashMap<>();
        parameter.put("loginName",user.getLoginname());
        parameter.put("tel",telephone);
        parameter.put("email",email);
        parameter.put("title",name);
        parameter.put("content",content);
        presenter.setRequest(parameter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
            String result = RequestUtil.getSoapObjectValue(soapObject, "result");
            String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
            String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
            if (result.equals("ok")){
                ShowToast("提交成功！");
                finish();
            }else {
                ShowToast("提交失败:"+errReason);
            }
        }
    }
}
