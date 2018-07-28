package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseFragment;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/28.
 */

public class MapFragment extends BaseFragment {
    @BindView(R.id.bmapsView)
    MapView bmapsView;

    private static MapFragment fragment;

    public static MapFragment newInstance() {
        if (fragment == null) {
            fragment = new MapFragment();
        }
        return fragment;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        //设置启用内置的缩放控件
        bmapsView.setBuiltInZoomControls(true);
        //得到mMapView的控制权,可以用它控制和驱动平移和缩放
        MapController mMapController = bmapsView.getController();
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
        //设置地图中心点
        mMapController.setCenter(point);
        //设置地图zoom级别
        mMapController.setZoom(12);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

}
