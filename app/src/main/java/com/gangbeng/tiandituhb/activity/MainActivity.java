package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseView {
    @BindView(R.id.bt_around)
    Button btAround;
    @BindView(R.id.bt_route)
    Button btRoute;
    @BindView(R.id.bt_set)
    Button btSet;
    @BindView(R.id.ll_searchview)
    LinearLayout llSearchview;
    @BindView(R.id.change_map)
    CardView changeMap;
    @BindView(R.id.bmapsView)
    MapView bmapsView;
    @BindView(R.id.bt_navi)
    Button btNavi;
    @BindView(R.id.location_map)
    CardView locationMap;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg_text, map_lfimg;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private boolean isFirstlocal = true;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_main);
        setToolbarVisibility(false);
        setMapView();
        locationGPS();
    }

    private void setMapView() {
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
//        arcGISTiledMapServiceLayer=new ArcGISTiledMapServiceLayer("http://222.222.66.230/newmapserver4/tianditu/tianditu/lfgzvector/wmts?");
//        bmapsView.addLayer(arcGISTiledMapServiceLayer);
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_lfimg_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        bmapsView.addLayer(mapServiceLayer, 0);
        bmapsView.addLayer(maptextLayer, 1);
        bmapsView.addLayer(mapRSServiceLayer, 2);
        bmapsView.addLayer(mapRStextLayer, 3);

        bmapsView.addLayer(map_lf, 4);
        bmapsView.addLayer(map_lf_text, 5);
        bmapsView.addLayer(map_lfimg, 6);
        bmapsView.addLayer(map_lfimg_text, 7);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        map_lfimg_text.setVisible(false);
        bmapsView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (map_lfimg_text == o && status == STATUS.LAYER_LOADED) {

//                    bmapsView.zoomToScale(new Point(116.70057500024, 39.51963700025), 50000);
                }
            }
        });

    }

    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = bmapsView.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    ptCurrent = new Point(location.getLongitude(), location.getLatitude());
                    if (isFirstlocal) bmapsView.zoomToScale(ptCurrent, 50000);
                    isFirstlocal = false;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else {
            ldm.resume();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_around, R.id.bt_route, R.id.bt_set, R.id.ll_searchview, R.id.change_map,R.id.bt_navi, R.id.location_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_around:
                Bundle bundle = new Bundle();
                bundle.putString("key","around");
                skip(AroundActivity.class,bundle,false);
                break;
            case R.id.bt_route:
                break;
            case R.id.bt_set:
                break;
            case R.id.ll_searchview:
                Bundle bundle2 = new Bundle();
                bundle2.putString("key","search");
                skip(AroundActivity.class,bundle2,false);
                break;
            case R.id.change_map:
                if (map_lfimg.isVisible()) {
                    map_lfimg.setVisible(false);
                    map_lfimg_text.setVisible(false);
                    map_lf.setVisible(true);
                    map_lf_text.setVisible(true);

                    mapRSServiceLayer.setVisible(false);
                    mapRStextLayer.setVisible(false);
                    mapServiceLayer.setVisible(true);
                    maptextLayer.setVisible(true);
                } else {
                    map_lfimg.setVisible(true);
                    map_lfimg_text.setVisible(true);
                    map_lf.setVisible(false);
                    map_lf_text.setVisible(false);

                    mapRSServiceLayer.setVisible(true);
                    mapRStextLayer.setVisible(true);
                    mapServiceLayer.setVisible(false);
                    maptextLayer.setVisible(false);

                }
                break;
            case R.id.bt_navi:

                break;
            case R.id.location_map:
                bmapsView.zoomToScale(ptCurrent, 50000);
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

    }

}
