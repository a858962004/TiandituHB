package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/12.
 */

public class ExemptionActivity extends BaseActivity {
    @BindView(R.id.web_exemption)
    WebView webExemption;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_exemption);
        setToolbarTitle("免责声明");
        setToolbarRightVisible(false);
        webExemption.loadUrl("file:///android_asset/web/exemption.html");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
