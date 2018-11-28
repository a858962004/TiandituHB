package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.presenter.RegisterPresenter;
import com.gangbeng.tiandituhb.http.RequestUtil;

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
//    @BindView(R.id.img_yzm)
//    ImageView imgYzm;
//    @BindView(R.id.ed_yzm)
//    EditText edYzm;

    private BasePresenter presenter;
//    private CodeUtils instance;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_register);
        setToolbarTitle("注册");
        setToolbarRightVisible(false);
        presenter = new RegisterPresenter(this);
//        instance = CodeUtils.getInstance();
//        Bitmap bitmap = instance.createBitmap();
//        imgYzm.setImageBitmap(bitmap);
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
            SoapObject soapObject=(SoapObject)data;
            String result = RequestUtil.getSoapObjectValue(soapObject, "result");
            String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
            String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
            if (result.equals("ok")){
                showMsg("注册成功");
                finish();
            }else {
                showMsg(errReason);
            }
        }
    }

    @OnClick({R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.img_yzm:
//                Bitmap bitmap = instance.createBitmap();
//                imgYzm.setImageBitmap(bitmap);
//                break;
            case R.id.register:
                String loginname = String.valueOf(edLoginname.getText());
                String password = String.valueOf(edPassword.getText());
                String password2 = String.valueOf(edPassword2.getText());
                String username = String.valueOf(edUsename.getText());
                String telephone = String.valueOf(edTelephone.getText());
                String email = String.valueOf(edEmail.getText());
//                String yzm = String.valueOf(edYzm.getText());
//                String code = instance.getCode();
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
//                if (yzm.equals("")){
//                    ShowToast("请输入验证码");
//                    return;
//                }
//                if (!yzm.equals(code)){
//                    ShowToast("您输入的验证码有误");
//                    return;
//                }
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("loginname", loginname);
                parameter.put("username", username);
                parameter.put("password", password);
                parameter.put("tel", telephone);
                parameter.put("email", email);
                presenter.setRequest(parameter);
                break;
        }
    }
}
