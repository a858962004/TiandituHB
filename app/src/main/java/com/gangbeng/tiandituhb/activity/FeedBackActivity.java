package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-09-04
 */

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.ed_telephone)
    EditText edTelephone;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_content)
    EditText edContent;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_feedback);
        setToolbarTitle("信息反馈");
        setRightImageBtnText("提交");
    }

    @Override
    protected void setRightClickListen() {
        String telephone = String.valueOf(edTelephone.getText());
        String email = String.valueOf(edEmail.getText());
        String content = String.valueOf(edContent.getText());
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
//        ShowToast("提交失败");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
