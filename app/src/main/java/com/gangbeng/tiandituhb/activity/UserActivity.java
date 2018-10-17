package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-10-17
 */

public class UserActivity extends BaseActivity {
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

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_user);
        setToolbarTitle("个人中心");
        setToolbarRightVisible(false);
        UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
        tvUsername.setText(user.getUsername());
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
                break;
            case R.id.quit:
                SharedUtil.removeData("user");
                MoreActivity.instence().setListData();
                finish();
                break;
        }
    }

}
