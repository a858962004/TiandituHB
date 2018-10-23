package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.LoginPrenter;
import com.gangbeng.tiandituhb.utils.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-16
 */

public class LoginActivity extends BaseActivity implements BaseView {
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    private BasePresenter presenter;
    private String loginnamestring;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_login);
        setToolbarTitle("用户登录");
        setToolbarRightVisible(false);
        presenter = new LoginPrenter(this);
        String memorylogin = SharedUtil.getString("memorylogin", "");
        account.setText(memorylogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                loginnamestring = String.valueOf(account.getText());
                String passwordstring = String.valueOf(password.getText());
                if (loginnamestring.equals("")) {
                    ShowToast("请输入登录名");
                    return;
                }
                if (passwordstring.equals("")) {
                    ShowToast("请输入密码");
                    return;
                }
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("name", loginnamestring);
                parameter.put("password", passwordstring);
                presenter.setRequest(parameter);
                break;
            case R.id.register:
                skip(RegisterActivity.class, false);
                break;
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
            SoapObject soapObject=(SoapObject)data;
            String result = RequestUtil.getSoapObjectValue(soapObject, "result");
            String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
            String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
            if (result.equals("ok")){
                showMsg("登录成功-"+okString);
                UserEvent userEvent = new UserEvent();
                userEvent.setUsername(okString);
                userEvent.setLoginname(loginnamestring);
                SharedUtil.saveSerializeObject("user",userEvent);
                SharedUtil.setString("memorylogin",userEvent.getLoginname());
                MoreActivity.instence().setListData();
                finish();
            }else {
                if (errReason.equals("nouser")||errReason.equals("wrong password")){
                    showMsg("用户名密码错误");
                }else {
                    showMsg("服务器连接失败");
                }
            }
        }

    }
}
