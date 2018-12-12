package com.gangbeng.tiandituhb.activity;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.webkit.WebView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.utils.MyLogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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
        setTitle("免责声明");
        setToolbarRightVisible(false);

        getXmlResouse();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void getXmlResouse() {

    }
}
