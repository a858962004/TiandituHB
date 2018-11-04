package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.bean.CountryBean;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.bean.WeatherBean;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.presenter.UploadLocationPresenter;
import com.gangbeng.tiandituhb.presenter.WeatherPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.github.library.bubbleview.BubbleLinearLayout;
import com.github.library.bubbleview.BubbleTextView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseView, NewBaseView {

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
    @BindView(R.id.tv1_weather)
    TextView tv1Weather;
    @BindView(R.id.tv2_weather)
    TextView tv2Weather;
    @BindView(R.id.tv3_weather)
    TextView tv3Weather;
    @BindView(R.id.tv4_weather)
    TextView tv4Weather;
    @BindView(R.id.tv5_weather)
    TextView tv5Weather;
    @BindView(R.id.rl_weather_bottom)
    RelativeLayout rlWeatherBottom;
    @BindView(R.id.tv6_weather)
    TextView tv6Weather;
    @BindView(R.id.tv7_weather)
    TextView tv7Weather;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_lfimg_text, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer pointlayer, weatherlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private boolean isFirstlocal = true;
    private BasePresenter presenter, weatherpresenter;
    private NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean;
    private boolean islocation = false;
    private NewBasePresenter uploadpresenter;
    private UserEvent user;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_main);
        setToolbarVisibility(false);
        presenter = new AroundSearchPresenter(this);
        weatherpresenter = new WeatherPresenter(this);
        uploadpresenter=new UploadLocationPresenter(this);
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        setMapView();
        locationGPS();
        setWeather();
    }

    private void setWeather() {
        Gson gson = new Gson();
        String countryString = PubConst.countryString;
        CountryBean countryBean = gson.fromJson(countryString, CountryBean.class);
        List<CountryBean.CountriesBean> countries = countryBean.getCountries();
        for (CountryBean.CountriesBean country : countries) {
            Map<String, Object> parameter = new HashMap<>();
            MyLogUtil.showLog(country.getLatitude() + "," + country.getLongitude());
            parameter.put("city", country.getLatitude() + "," + country.getLongitude());

            weatherpresenter.setRequest(parameter);
        }


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
        weatherlayer = new GraphicsLayer();
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
        bmapsView.addLayer(weatherlayer, 10);
        weatherlayer.setVisible(false);
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
        bmapsView.setOnSingleTapListener(mapclick);
    }

    private void setPointRequest(Point point, String distence) {
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
                    if (Contant.ins().isLocalState())setLocal("1",PubConst.LABLE_START_SHARE);
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
            R.id.location_tianqi, R.id.location_tuceng, R.id.ll_around, R.id.ll_route,R.id.tv7_weather})
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
                setPointRequest(ptCurrent, "100");
                bmapsView.zoomToScale(ptCurrent, 50000);
                RefreshOnThread();
//                mapviewscale.refreshScaleView(bmapsView.getScale());
                break;
            case R.id.location_tianqi:
                weatherlayer.clearSelection();
                hideBottom();
                if (weatherlayer.isVisible()) {
                    weatherlayer.setVisible(false);
                    bmapsView.setOnSingleTapListener(mapclick);
                    hideWeatherBottom();
                } else {
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
                    bmapsView.setOnPanListener(null);
                    bmapsView.zoomToScale(new Point(116.75750057616959, 39.31351869282022), 1500000);
                    weatherlayer.setVisible(true);
                    bmapsView.setOnSingleTapListener(weatherclick);
                }

                break;
            case R.id.location_quanjing:
                if (imgQuanjing.getVisibility() == View.VISIBLE) {
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
                    bmapsView.setOnPanListener(null);
                    bmapsView.setOnSingleTapListener(mapclick);
                } else {
//                    this.bean = null;
//                    islocation = false;
                    weatherlayer.setVisible(false);
                    hideBottom();
                    hideWeatherBottom();
                    bmapsView.setOnSingleTapListener(null);
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
            case R.id.tv7_weather:
                int[] selectionIDs = weatherlayer.getSelectionIDs();
                Graphic graphic = weatherlayer.getGraphic(selectionIDs[0]);
                Point selectpoint = (Point) graphic.getGeometry();
                Bundle bundle=new Bundle();
                bundle.putString("x", String.valueOf(selectpoint.getX()));
                bundle.putString("y", String.valueOf(selectpoint.getY()));
                skip(WeatherActivity.class,bundle, false);
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
    public void setData(Object data, String lable) {

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
        if (data instanceof WeatherBean) {
            WeatherBean bean = (WeatherBean) data;
            WeatherBean.HeWeather5Bean heWeather5Bean = bean.getHeWeather5().get(0);
            WeatherBean.HeWeather5Bean.AqiBean aqi = heWeather5Bean.getAqi();
            WeatherBean.HeWeather5Bean.NowBean now = heWeather5Bean.getNow();
            WeatherBean.HeWeather5Bean.BasicBean basic = heWeather5Bean.getBasic();
            List<WeatherBean.HeWeather5Bean.DailyForecastBean> daily_forecast = heWeather5Bean.getDaily_forecast();
            try {
                String code = now.getCond().getCode();
                MyLogUtil.showLog(code);
                Bitmap bitmap = BitmapFactory.decodeStream(this.getAssets().open(now.getCond().getCode() + ".png"));
                View inflate = LayoutInflater.from(this).inflate(R.layout.view_weather, null);
                ImageView view = inflate.findViewById(R.id.img_weather);
                TextView textView = inflate.findViewById(R.id.tv_weather);
                view.setImageBitmap(bitmap);
                textView.setText(now.getCond().getTxt());
                Bitmap viewbitmap = DensityUtil.convertViewToBitmap(inflate);
                Drawable drawable = new BitmapDrawable(viewbitmap);
                Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
                PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable1);
                Point point = new Point(Double.valueOf(basic.getLon()), Double.valueOf(basic.getLat()));
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("basiccity", basic.getCity());
                parameter.put("tmpmax", daily_forecast.get(0).getTmp().getMax());//最高温度
                parameter.put("tmpmin", daily_forecast.get(0).getTmp().getMin());//最低温度
                parameter.put("condtext", now.getCond().getTxt());//当前天气
                parameter.put("cityqlty", aqi.getCity().getQlty());//空气质量
                parameter.put("winddir", now.getWind().getDir());//风向
                parameter.put("windsc", now.getWind().getSc());//风级
                parameter.put("nowfl", now.getFl());//体感温度
                parameter.put("nowhum", now.getHum());//湿度
                parameter.put("nowtmp", now.getTmp());//当前气温
                Graphic graphic = new Graphic(point, symbol, parameter);
                weatherlayer.addGraphic(graphic);
            } catch (IOException e) {
                e.printStackTrace();
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

    private void setWeatherBottom(Graphic graphic) {
        rlWeatherBottom.setVisibility(View.VISIBLE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.rl_weather_bottom); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ABOVE, R.id.rl_weather_bottom); //设置控件的位置
        params2.setMargins(0, 0, i, i1);//左上右下
        mapzoom.setLayoutParams(params2);
        String basiccity = String.valueOf(graphic.getAttributeValue("basiccity"));//城市名称
        String tmpmax = String.valueOf(graphic.getAttributeValue("tmpmax"));//最高温度
        String tmpmin = String.valueOf(graphic.getAttributeValue("tmpmin"));//最低温度
        String condtext = String.valueOf(graphic.getAttributeValue("condtext"));//当前天气
        String cityqlty = String.valueOf(graphic.getAttributeValue("cityqlty"));//空气质量
        String winddir = String.valueOf(graphic.getAttributeValue("winddir"));//风向
        String windsc = String.valueOf(graphic.getAttributeValue("windsc"));//风级
        String nowfl = String.valueOf(graphic.getAttributeValue("nowfl"));//体感温度
        String nowhum = String.valueOf(graphic.getAttributeValue("nowhum"));//湿度
        String nowtmp = String.valueOf(graphic.getAttributeValue("nowtmp"));//当前气温
        tv1Weather.setText(basiccity);
        tv2Weather.setText(condtext + "  " + tmpmin + "～" + tmpmax+"℃");
        tv3Weather.setText("空气质量：" + cityqlty);
        tv4Weather.setText(winddir + " " + windsc + "级");
        tv5Weather.setText("体感温度：" + nowfl+"℃");
        tv6Weather.setText("湿度：" + nowhum+"%");
    }

    private void hideWeatherBottom() {
        rlWeatherBottom.setVisibility(View.GONE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.id_tab_map); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ABOVE, R.id.mapviewscale); //设置控件的位置
        params2.setMargins(0, 0, i, i1);//左上右下
        mapzoom.setLayoutParams(params2);
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
        params2.addRule(RelativeLayout.ABOVE, R.id.mapviewscale); //设置控件的位置
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

    OnSingleTapListener mapclick = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
//            Point center = bmapsView.getCenter();
//            double scale = bmapsView.getScale();
//            MyLogUtil.showLog(center+":"+scale);
            islocation = false;
            hideBottom();
            Point point = bmapsView.toMapPoint(v, v1);
            setPointRequest(point, "20");
        }
    };

    OnSingleTapListener weatherclick = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
            int[] graphicIDs = weatherlayer.getGraphicIDs(v, v1, 25);
            if (graphicIDs != null && graphicIDs.length > 0) {
                weatherlayer.clearSelection();
                int graphicID = graphicIDs[0];
                Graphic graphic = weatherlayer.getGraphic(graphicID);
                weatherlayer.setSelectedGraphics(new int[]{graphicID}, true);
                bmapsView.zoomToScale((Point) graphic.getGeometry(), 1500000);
                setWeatherBottom(graphic);
            }
        }
    };

    private void setLocal(String state, String label) {
        MyLogUtil.showLog("1234");
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", user.getLoginname());
        parameter.put("username", user.getUsername());
        parameter.put("x", ptCurrent.getX());
        parameter.put("y", ptCurrent.getY());
        parameter.put("state", state);
        uploadpresenter.setRequest(parameter, label);
    }
}
