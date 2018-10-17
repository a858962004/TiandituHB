package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.presenter.RegisterPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-16
 */

public class RegisterActivity extends BaseActivity implements BaseView {
    @BindView(R.id.ed_loginname)
    EditText edLoginname;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_password2)
    EditText edPassword2;
    @BindView(R.id.ed_usename)
    EditText edUsename;
    @BindView(R.id.ed_telephone)
    EditText edTelephone;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.register)
    Button register;

    private BasePresenter presenter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_register);
        setToolbarTitle("注册");
        setToolbarRightVisible(false);
        presenter = new RegisterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        String loginname = String.valueOf(edLoginname.getText());
        String password = String.valueOf(edPassword.getText());
        String password2 = String.valueOf(edPassword2.getText());
        String username = String.valueOf(edUsename.getText());
        String telephone = String.valueOf(edTelephone.getText());
        String email = String.valueOf(edEmail.getText());
        if (loginname.equals("")) {
            ShowToast("请填入登录名");
            return;
        }
        if (password.equals("")) {
            ShowToast("请填入密码");
            return;
        }
        if (password2.equals("")) {
            ShowToast("请确认密码");
            return;
        }
        if (username.equals("")) {
            ShowToast("请填入用户名");
            return;
        }
        if (telephone.equals("")) {
            ShowToast("请填入联系电话");
            return;
        }
        if (email.equals("")) {
            ShowToast("请填入电子邮箱");
            return;
        }
        if (!password.equals(password2)) {
            ShowToast("确认密码填写错误");
            return;
        }
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("loginname",loginname);
        parameter.put("username",username);
        parameter.put("password",password);
        parameter.put("tel",telephone);
        parameter.put("email",email);
        presenter.setRequest(parameter);
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

    }
}
