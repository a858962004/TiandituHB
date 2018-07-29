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
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.GeocoderBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.presenter.GeocoderPresenter;
import com.gangbeng.tiandituhb.presenter.SearchPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private BasePresenter geocoderPresenter, searchPresenter;
    private boolean isfirst = true;
    private boolean isSingletap = false;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_main);
        setToolbarVisibility(false);
        geocoderPresenter = new GeocoderPresenter(this);
        searchPresenter = new SearchPresenter(this);
        setMapView();
        locationGPS();

        String result="{\"landmarkcount\":1,\"searchversion\":\"4.3.0\",\"count\":\"48\",\"engineversion\":\"20180412\",\"resultType\":1,\"pois\":[{\"eaddress\":\"\",\"ename\":\"Langfang Railway Northen Station\",\"address\":\"河北省廊坊市广阳区\",\"phone\":\"\",\"name\":\"廊坊北站\",\"hotPointID\":\"51DC41024B8B95D9\",\"url\":\"\",\"lonlat\":\"116.699688 39.512216\"}],\"dataversion\":\"2018-7-25 15:51:51\",\"prompt\":[{\"type\":4,\"admins\":[{\"name\":\"廊坊市\",\"adminCode\":156131000}]}],\"mclayer\":\"\",\"keyWord\":\"廊坊北站\"}";
        Gson gson = new Gson();
        SearchBean bean = gson.fromJson(result, SearchBean.class);
        MyLogUtil.showLog(bean.getPois().get(0).getAddress());


    }

    private void setMapView() {
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);
        bmapsView.addLayer(mapServiceLayer, 0);
        bmapsView.addLayer(maptextLayer, 1);
        bmapsView.addLayer(mapRSServiceLayer, 2);
        bmapsView.addLayer(mapRStextLayer, 3);
        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        bmapsView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (mapRStextLayer == o && status == STATUS.LAYER_LOADED) {
                    bmapsView.zoomToScale(new Point(116.70057500024, 39.51963700025), 50000);
                }
            }
        });
        bmapsView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float v, float v1) {
                Point point = bmapsView.toMapPoint(v, v1);
                MyLogUtil.showLog(point.getX() + "," + point.getY());
                isSingletap = true;
                getCityInfo(point, isSingletap);
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
                    getCityInfo(ptCurrent, isfirst);
                    isfirst = false;
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

    private void getCityInfo(Point point, boolean isfirst) {
        if (isfirst) {
            Map<String, Object> parameter = new HashMap<>();
            Map<String, Object> postStr = new HashMap<>();
            postStr.put("lon", point.getX());
            postStr.put("lat", point.getY());
            postStr.put("ver", 1);
            MyLogUtil.showLog(postStr.toString());
            parameter.put("postStr", postStr.toString());
            parameter.put("type", "geocode");
            geocoderPresenter.setRequest(parameter);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_around, R.id.bt_route, R.id.bt_set, R.id.ll_searchview, R.id.change_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_around:
                break;
            case R.id.bt_route:
                break;
            case R.id.bt_set:
                break;
            case R.id.ll_searchview:
                break;
            case R.id.change_map:
                if (mapRSServiceLayer.isVisible()) {
                    mapRSServiceLayer.setVisible(false);
                    mapRStextLayer.setVisible(false);
                    mapServiceLayer.setVisible(true);
                    maptextLayer.setVisible(true);
                } else {
                    mapRSServiceLayer.setVisible(true);
                    mapRStextLayer.setVisible(true);
                    mapServiceLayer.setVisible(false);
                    maptextLayer.setVisible(false);
                }
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
        if (data instanceof GeocoderBean) {
            GeocoderBean bean = (GeocoderBean) data;
            String city = bean.getResult().getAddressComponent().getCity();
            if (!city.contains("廊坊")) {
                if (!isSingletap) showMsg("您所在位置不在廊坊市内");
                if (isSingletap) showMsg("您所点击的位置不在廊坊市内");
                return;
            }
            if (isSingletap) {
                isSingletap = false;
                GeocoderBean.ResultBean.AddressComponentBean addressComponent = bean.getResult().getAddressComponent();
                String poi_distance = addressComponent.getPoi_distance();
                Integer integer = Integer.valueOf(poi_distance);
                MyLogUtil.showLog(integer);
                if (integer < 20) {
                    String poi = addressComponent.getPoi();
                    Map<String, Object> postStr = new HashMap<>();
                    postStr.put("keyWord", poi);
                    postStr.put("level", "11");
                    postStr.put("mapBound", PubConst.mapBound);
                    postStr.put("queryType", "1");
                    postStr.put("count", "1");
                    postStr.put("start", "0");
                    Map<String, Object> parameter = new HashMap<>();
                    Gson gson = new Gson();
                    String s = gson.toJson(postStr);
                    MyLogUtil.showLog(s);
                    parameter.put("postStr", s);
                    parameter.put("type", "query");
                    searchPresenter.setRequest(postStr);
                }

            }
        }
        if (data instanceof SearchBean) {
            SearchBean bean = (SearchBean) data;
            SearchBean.PoisBean poisBean = bean.getPois().get(0);
            String lonlat = poisBean.getLonlat();
            String[] split = lonlat.split(" ");
            String lon = split[0];
            String lat = split[1];
            bmapsView.zoomToScale(new Point(Double.valueOf(lon), Double.valueOf(lat)), 10000);
        }

    }
}
