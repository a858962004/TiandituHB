package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.lbsapi.model.BaiduPanoData;
import com.baidu.lbsapi.panoramaview.PanoramaRequest;
import com.baidu.lbsapi.tools.CoordinateConverter;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.github.library.bubbleview.BubbleTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseView {

    public static final int WGS84 = 9;// 大地坐标系方式
    public static final int GCJ02 = 10;// 国测局加密方式

    @BindView(R.id.bt_around)
    Button btAround;
    @BindView(R.id.bt_route)
    Button btRoute;
    @BindView(R.id.bt_more)
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
    @BindView(R.id.location_quanjing)
    CardView locationQuanjing;
    @BindView(R.id.img_quanjing)
    ImageView imgQuanjing;
    @BindView(R.id.bt_sure)
    CardView btSure;
    @BindView(R.id.location_tianqi)
    CardView locationTianqi;
    @BindView(R.id.img_quanjing2)
    ImageView imgQuanjing2;
    @BindView(R.id.bubbletextview)
    BubbleTextView bubbletextview;
//    @BindView(R.id.bubblell)
//    BubbleLinearLayout bubblell;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
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
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);

        bmapsView.addLayer(mapServiceLayer, 0);
        bmapsView.addLayer(maptextLayer, 1);
        bmapsView.addLayer(mapRSServiceLayer, 2);
        bmapsView.addLayer(mapRStextLayer, 3);

        bmapsView.addLayer(map_lf, 4);
        bmapsView.addLayer(map_lfimg, 5);
        bmapsView.addLayer(map_xzq, 6);
        bmapsView.addLayer(map_lf_text, 7);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        bmapsView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                MyLogUtil.showLog("tag", o.toString() + ":" + status);
            }
        });
        bmapsView.setOnPanListener(new OnPanListener() {
            @Override
            public void prePointerMove(float v, float v1, float v2, float v3) {
                bubbletextview.setVisibility(View.GONE);
                bubbletextview.setText("正在加载...");
                if (imgQuanjing.getVisibility() == View.VISIBLE) {
                    imgQuanjing.setVisibility(View.GONE);
                    imgQuanjing2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void postPointerMove(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("pan", "postPointerMove");
            }

            @Override
            public void prePointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("pan", "prePointerUp");
            }

            @Override
            public void postPointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("pan", "postPointerUp");
                bubbletextview.setVisibility(View.VISIBLE);
                if (imgQuanjing2.getVisibility() == View.VISIBLE) {
                    imgQuanjing2.setVisibility(View.GONE);
                    imgQuanjing.setVisibility(View.VISIBLE);
                }
                // 通过百度经纬度坐标获取当前位置相关全景信息，包括是否有外景，外景PID，外景名称等
                Point center = bmapsView.getCenter();
                setStreetPano(center);
            }
        });

    }

    private void setStreetPano(Point center) {
        com.baidu.lbsapi.tools.Point sourcePoint = new com.baidu.lbsapi.tools.Point(center.getX(), center.getY());
        com.baidu.lbsapi.tools.Point converter = CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_WGS84, sourcePoint);
        PanoramaRequest panoramaRequest = PanoramaRequest.getInstance(MainActivity.this);
        BaiduPanoData mPanoDataWithLatLon = panoramaRequest.getPanoramaInfoByLatLon(converter.x, converter.y);
//                Gps bd09 = PositionUtil.gps84_To_bd09(center.getY(), center.getX());
        bubbletextview.setText("查看全景");
//        if (mPanoDataWithLatLon.hasStreetPano()) {
//        }else {
//            bubbletextview.setText("该地区没有全景数据");
//        }
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
                    if (isFirstlocal) {
                        bmapsView.zoomToScale(ptCurrent, 50000);
                    }
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

    @OnClick({R.id.bt_around, R.id.bt_route, R.id.bt_more, R.id.ll_searchview, R.id.change_map,
            R.id.bt_navi, R.id.location_map, R.id.location_quanjing, R.id.bubbletextview, R.id.location_tianqi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_around:
                setEventBus("around");
                skip(AroundActivity.class, false);
                break;
            case R.id.bt_route:
                setEventBus("route");
                skip(PlanActivity.class, false);
                break;
            case R.id.bt_more:
                skip(MoreActivity.class, false);
                break;
            case R.id.ll_searchview:
                setEventBus("search");
                skip(AroundActivity.class, false);
                break;
            case R.id.change_map:
                if (map_lfimg.isVisible()) {
                    map_lfimg.setVisible(false);
                    map_lf.setVisible(true);
                    mapRSServiceLayer.setVisible(false);
                    mapRStextLayer.setVisible(false);
                    mapServiceLayer.setVisible(true);
                    maptextLayer.setVisible(true);
                } else {
                    map_lfimg.setVisible(true);
                    map_lf.setVisible(false);
                    mapRSServiceLayer.setVisible(true);
                    mapRStextLayer.setVisible(true);
                    mapServiceLayer.setVisible(false);
                    maptextLayer.setVisible(false);
                }
                break;
            case R.id.bt_navi:
                setEventBus("navi");
                skip(PlanActivity.class, false);
                break;
            case R.id.location_map:
                bmapsView.zoomToScale(ptCurrent, 50000);
                break;
            case R.id.location_tianqi:
                skip(WeatherActivity.class, false);
                break;
            case R.id.location_quanjing:
                if (imgQuanjing.getVisibility() == View.VISIBLE) {
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
//                    btSure.setVisibility(View.GONE);
                } else {
                    imgQuanjing.setVisibility(View.VISIBLE);
                    bubbletextview.setVisibility(View.VISIBLE);
                    bubbletextview.setText("正在查询...");
                    Point center = bmapsView.getCenter();
                    setStreetPano(center);
                }
                break;
            case R.id.bubbletextview:
                Point center = bmapsView.getCenter();
                DemoInfo demoInfo = new DemoInfo(GCJ02, R.string.demo_title_panorama, R.string.demo_desc_gcj02, PanoDemoMain.class);
                Intent intent = new Intent(MainActivity.this, demoInfo.demoClass);
                intent.putExtra("type", demoInfo.type);
                Gps gps = PositionUtil.gps84_To_Gcj02(center.getY(), center.getX());
                double[] doubles = new double[]{gps.getWgLat(), gps.getWgLon()};
                MyLogUtil.showLog("zuobiao", gps.getWgLat() + "---" + gps.getWgLon());
                intent.putExtra("lontitude", doubles);
                this.startActivity(intent);
                break;
        }
    }

    private void setEventBus(String route) {
        ChannelEvent channelEvent = new ChannelEvent(route);
        EventBus.getDefault().postSticky(channelEvent);
        PointBean pointBean = new PointBean();
        pointBean.setX(String.valueOf(ptCurrent.getX()));
        pointBean.setY(String.valueOf(ptCurrent.getY()));
        EventBus.getDefault().postSticky(pointBean);
        StartPoint startPoint = new StartPoint();
        startPoint.setX(String.valueOf(ptCurrent.getX()));
        startPoint.setY(String.valueOf(ptCurrent.getY()));
        startPoint.setName("当前位置");
        EventBus.getDefault().postSticky(startPoint);
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

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().removeStickyEvent(SearchBean.PoisBean.class);
    }


    private static class DemoInfo {
        public int type;
        public int title;
        public int desc;
        public Class<? extends Activity> demoClass;

        public DemoInfo(int type, int title, int desc, Class<? extends Activity> demoClass) {
            this.type = type;
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }


}
