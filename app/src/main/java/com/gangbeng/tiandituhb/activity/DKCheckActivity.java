package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AddPhotoAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/15.
 */

public class DKCheckActivity extends BaseActivity {
    @BindView(R.id.tv_time_photo)
    TextView tvTimePhoto;
    @BindView(R.id.ed_loc_feed)
    TextView edLocFeed;
    @BindView(R.id.tv_city_photo)
    TextView tvCityPhoto;
    @BindView(R.id.ed2_loc_feed)
    TextView ed2LocFeed;
    @BindView(R.id.tv_gd)
    TextView tvGd;
    @BindView(R.id.ed2_gd_feed)
    TextView ed2GdFeed;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_eq)
    EditText edEq;
    @BindView(R.id.grid_feed)
    GridView gridFeed;

    private List<String> uris = new ArrayList<>();
    private AddPhotoAdapter askGridAdpter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_dkcheck);
        setToolbarTitle("地块核查");
        uris.add("0");
        askGridAdpter = new AddPhotoAdapter(this, uris, null);
        gridFeed.setAdapter(askGridAdpter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
