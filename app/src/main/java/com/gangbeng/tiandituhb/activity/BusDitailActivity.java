package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.BusDitailAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.constant.PubConst;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-08-23
 */

public class BusDitailActivity extends BaseActivity {
    @BindView(R.id.tv_busditail)
    TextView tvBusditail;
    @BindView(R.id.tv2_busditail)
    TextView tv2Busditail;
    @BindView(R.id.lv_busditail)
    ListView lvBusditail;

    private BusDitailAdapter adapter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_busditail);
        setToolbarTitle("详情");
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        BusBean.ResultsBean.LinesBean linesBean =
                (BusBean.ResultsBean.LinesBean) bundleExtra.getSerializable("data");
        String name = bundleExtra.getString("name");
        String distence = bundleExtra.getString("distence");
        String start=bundleExtra.getString("start");
        String end=bundleExtra.getString("end");
        tvBusditail.setText(name);
        tv2Busditail.setText(distence);
        adapter=new BusDitailAdapter(this,linesBean.getSegments(),start,end);
        lvBusditail.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
