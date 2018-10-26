package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.lbsapi.model.BaiduPanoData;
import com.baidu.lbsapi.panoramaview.PanoramaRequest;
import com.baidu.lbsapi.tools.CoordinateConverter;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.github.library.bubbleview.BubbleLinearLayout;
import com.github.library.bubbleview.BubbleTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.mapviewscale)
    MapScaleView mapviewscale;
    @BindView(R.id.mapzoom)
    MapZoomView mapzoom;
    @BindView(R.id.location_tuceng)
    CardView locationTuceng;
    @BindView(R.id.cb_lyxz)
    CheckBox cbLyxz;
    @BindView(R.id.cb_xcbj)
    CheckBox cbXcbj;
    @BindView(R.id.bubbletuceng)
    BubbleLinearLayout bubbletuceng;
    @BindView(R.id.img_collect)
    ImageView imgCollect;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_around)
    LinearLayout llAround;
    @BindView(R.id.ll_route)
    LinearLayout llRoute;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_lfimg_text, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer pointlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private boolean isFirstlocal = true;
    private BasePresenter presenter;
    private NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean;
    private boolean islocation = false;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_main);
        setToolbarVisibility(false);
        presenter = new AroundSearchPresenter(this);
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
        map_lfimg_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CIA_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);
        pointlayer = new GraphicsLayer();
        bmapsView.setMaxScale(4000);
        bmapsView.addLayer(mapServiceLayer, 0);
        bmapsView.addLayer(maptextLayer, 1);
        bmapsView.addLayer(mapRSServiceLayer, 2);
        bmapsView.addLayer(mapRStextLayer, 3);

        bmapsView.addLayer(map_lf, 4);
        bmapsView.addLayer(map_lfimg, 5);
        bmapsView.addLayer(map_xzq, 6);
        bmapsView.addLayer(map_lf_text, 7);
        bmapsView.addLayer(map_lfimg_text, 8);
        bmapsView.addLayer(pointlayer, 9);
        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        map_lfimg_text.setVisible(false);

        bmapsView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                MyLogUtil.showLog("tag", o.toString() + ":" + status);
            }
        });

        bmapsView.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {
//                mapviewscale.refreshScaleView(bmapsView.getScale());
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscale.refreshScaleView(bmapsView.getScale());
            }
        });
        mapzoom.setMapView(bmapsView);
        bmapsView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float v, float v1) {
                islocation = false;
                hideBottom();
                Point point = bmapsView.toMapPoint(v, v1);
                setPointRequest(point,"50");
            }
        });
    }

    private void setPointRequest(Point point,String distence) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("maxitems", "20");
        parameter.put("page", "1");
        Graphic graphic = MapUtil.setDistanceGraphicsLayer(point, distence);
        Geometry geometry = graphic.getGeometry();
        Envelope envelope = new Envelope();
        geometry.queryEnvelope(envelope);
        String geo = envelope.getXMin() + "," + envelope.getYMin() + "," + envelope.getXMax() + "," + envelope.getYMax();
        parameter.put("geo", geo);
        parameter.put("where", "1=1");
        presenter.setRequest(parameter);
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
                        mapviewscale.refreshScaleView(50000);
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
            R.id.bt_navi, R.id.location_map, R.id.location_quanjing, R.id.bubbletextview,
            R.id.location_tianqi, R.id.location_tuceng, R.id.ll_around, R.id.ll_route})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_tuceng:
                if (bubbletuceng.getVisibility() == View.VISIBLE) {
                    bubbletuceng.setVisibility(View.GONE);
                } else {
                    bubbletuceng.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_around:
                setEventBus("around");
                skip(AroundActivity.class, false);
                break;
            case R.id.bt_route:
                setEventBus("route");
                skip(PlanActivity.class, false);
                break;
            case R.id.bt_more:
                MapExtent extent = new MapExtent();
                extent.setCenter(bmapsView.getCenter());
                extent.setScale(bmapsView.getScale());
                EventBus.getDefault().postSticky(extent);
                skip(MoreActivity.class, false);
                break;
            case R.id.ll_searchview:
                setEventBus("search");
                skip(AroundActivity.class, false);
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
                setEventBus("navi");
                skip(PlanActivity.class, false);
                break;
            case R.id.location_map:
                this.bean = null;
                islocation = true;
                hideBottom();
                setPointRequest(ptCurrent,"100");
                bmapsView.zoomToScale(ptCurrent, 50000);
                RefreshOnThread();
//                mapviewscale.refreshScaleView(bmapsView.getScale());
                break;
            case R.id.location_tianqi:
                skip(WeatherActivity.class, false);
                break;
            case R.id.location_quanjing:
                if (imgQuanjing.getVisibility() == View.VISIBLE) {
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
                    bmapsView.setOnPanListener(null);
//                    btSure.setVisibility(View.GONE);
                } else {
//                    this.bean = null;
//                    islocation = false;
                    hideBottom();
                    imgQuanjing.setVisibility(View.VISIBLE);
                    bubbletextview.setVisibility(View.VISIBLE);
                    bubbletextview.setText("正在查询...");
                    Point center = bmapsView.getCenter();
                    setStreetPano(center);
                    bmapsView.setOnPanListener(new OnPanListener() {
                        @Override
                        public void prePointerMove(float v, float v1, float v2, float v3) {
                            if (imgQuanjing.getVisibility() == View.VISIBLE) {
                                bubbletextview.setVisibility(View.GONE);
                                bubbletextview.setText("正在加载...");
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
                            if (imgQuanjing2.getVisibility() == View.VISIBLE) {
                                bubbletextview.setVisibility(View.VISIBLE);
                                imgQuanjing2.setVisibility(View.GONE);
                                imgQuanjing.setVisibility(View.VISIBLE);
                            }
                            // 通过百度经纬度坐标获取当前位置相关全景信息，包括是否有外景，外景PID，外景名称等
                            Point center = bmapsView.getCenter();
                            setStreetPano(center);
                        }
                    });
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
            case R.id.ll_around:
                setEventBus("around");
                if (!islocation) {
                    EventBus.getDefault().postSticky(bean);
                    EventBus.getDefault().postSticky(new ChannelEvent("around"));
                }
                skip(AroundActivity.class, false);
                break;
            case R.id.ll_route:
                setEventBus("route");
                if (!islocation) {
                    EndPoint endPoint = new EndPoint();
                    endPoint.setName(bean.getProperties().get名称());
                    endPoint.setX(String.valueOf(bean.getGeometry().getCoordinates().get(0)));
                    endPoint.setY(String.valueOf(bean.getGeometry().getCoordinates().get(1)));
                    EventBus.getDefault().postSticky(endPoint);
                    EventBus.getDefault().postSticky(new ChannelEvent("route"));
                }
                skip(PlanActivity.class, false);
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
        if (data instanceof NewSearchBean) {
            NewSearchBean bean = (NewSearchBean) data;
            NewSearchBean.ContentBean content = bean.getContent();
            if (content != null) {
                NewSearchBean.ContentBean.FeaturesBeanX features = content.getFeatures();
                List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> features1 = features.getFeatures();
                if (features1.size() > 0) {
                    this.bean = features1.get(0);
                    setbottom(this.bean);
                }
            } else {
                this.bean = null;
            }
        }

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

    private void setbottom(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        pointlayer.removeAll();
        rlBottom.setVisibility(View.VISIBLE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.rl_bottom); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ABOVE, R.id.rl_bottom); //设置控件的位置
        params2.setMargins(0, 0, i, i1);//左上右下
        mapzoom.setLayoutParams(params2);
        imgCollect.setVisibility(View.GONE);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei03);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
        picSymbol.setOffsetY(drawable1.getIntrinsicHeight() / 2);
        if (islocation) {
//            Graphic g = new Graphic(ptCurrent, picSymbol);
//            pointlayer.addGraphic(g);
            tvName.setText(bean.getProperties().get名称() + "附近");
            tvName.setMaxLines(3);
            tvAddress.setText("");
        } else {
            Point point = zoom2bean(bean.getGeometry().getCoordinates());
            Graphic g = new Graphic(point, picSymbol);
            pointlayer.addGraphic(g);
            tvName.setText(bean.getProperties().get名称());
            tvName.setMaxLines(3);
            tvAddress.setText(bean.getProperties().get地址());
            tvAddress.setMaxLines(3);
        }
    }

    private void hideBottom() {
        pointlayer.removeAll();
        rlBottom.setVisibility(View.GONE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.id_tab_map); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ABOVE, R.id.id_tab_map); //设置控件的位置
        params2.setMargins(0, 0, i, i1);//左上右下
        mapzoom.setLayoutParams(params2);

    }

    private Point zoom2bean(List<Double> coordinates) {
        Point point = new Point();
        point.setX(coordinates.get(0));
        point.setY(coordinates.get(1));
        bmapsView.zoomToScale(point, 5000);
        RefreshOnThread();
        return point;
    }

    private void RefreshOnThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mapviewscale.refreshScaleView(bmapsView.getScale());
                        }
                    });
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            if (rlBottom.getVisibility() == View.VISIBLE) {
                hideBottom();
                islocation = false;
            } else {
                finish();
            }
        }
        return false;
    }
}
