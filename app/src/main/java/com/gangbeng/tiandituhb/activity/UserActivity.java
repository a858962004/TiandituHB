package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.GetUserPresenter;
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
 * @date 2018-10-17
 */

public class UserActivity extends BaseActivity implements BaseView {
    @BindView(R.id.quit)
    Button quit;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_updatepassword)
    TextView tvUpdatepassword;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    private BasePresenter presenter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_user);
        setToolbarTitle("个人中心");
        setToolbarRightVisible(false);
        UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
        tvUsername.setText(user.getUsername());
        presenter = new GetUserPresenter(this);
        Map<String,Object>parameter=new HashMap<>();
        parameter.put("loginname",user.getLoginname());
        presenter.setRequest(parameter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_updatepassword, R.id.quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_updatepassword:
                skip(EditPasswordActivity.class, false);
                break;
            case R.id.quit:
                SharedUtil.removeData("user");
                MoreActivity.instence().setListData();
                finish();
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
            SoapObject object=(SoapObject)data;
            String userName = RequestUtil.getSoapObjectValue(object, "UserName");
            String mobilePhone1 = RequestUtil.getSoapObjectValue(object, "MobilePhone1");
            String email = RequestUtil.getSoapObjectValue(object, "EMAIL");
            tvTelephone.setText(mobilePhone1);
            tvEmail.setText(email);
        }

    }
}
