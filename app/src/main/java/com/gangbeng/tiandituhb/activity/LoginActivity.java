package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-16
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_login);
        setToolbarTitle("用户登录");
        setToolbarRightVisible(false);
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
                break;
            case R.id.register:
                skip(RegisterActivity.class,false);
                break;
        }
    }
}
