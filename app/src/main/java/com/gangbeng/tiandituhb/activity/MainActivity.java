package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.widget.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout_main)
    TabLayout tablayoutMain;
    @BindView(R.id.customviewpager_main)
    CustomViewPager customviewpagerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        MapView mMapView = (MapView) findViewById(R.id.bmapsView);
////设置启用内置的缩放控件
//        mMapView.setBuiltInZoomControls(true);
////得到mMapView的控制权,可以用它控制和驱动平移和缩放
//        MapController mMapController = mMapView.getController();
////用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
//        GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
////设置地图中心点
//        mMapController.setCenter(point);
////设置地图zoom级别
//        mMapController.setZoom(12);
    }
}
