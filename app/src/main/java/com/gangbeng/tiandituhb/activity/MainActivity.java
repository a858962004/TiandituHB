package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.gangbeng.tiandituhb.BuildConfig;
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
import com.gangbeng.tiandituhb.bean.UpdateBean;
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
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.presenter.ShareGroupPresenter;
import com.gangbeng.tiandituhb.presenter.UpdatePresenter;
import com.gangbeng.tiandituhb.presenter.UploadLocationPresenter;
import com.gangbeng.tiandituhb.presenter.WeatherPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;
import com.gangbeng.tiandituhb.utils.Util;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.github.library.bubbleview.BubbleLinearLayout;
import com.github.library.bubbleview.BubbleTextView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.serialization.SoapObject;

import java.io.File;
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
    @BindView(R.id.img_more_tab)
    ImageView imgMoreTab;
    @BindView(R.id.cover_tv)
    TextView coverTv;
    @BindView(R.id.qttv_searchlocal)
    TextView qttvSearchlocal;
    @BindView(R.id.ll_tianqi)
    LinearLayout llTianqi;
    @BindView(R.id.ll_quanjing)
    LinearLayout llQuanjing;
    @BindView(R.id.img_hefeng)
    ImageView imgHefeng;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_lfimg_text,map_cj,map_tdlyxz, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer pointlayer, weatherlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private boolean isFirstlocal = true;
    private BasePresenter presenter, weatherpresenter;
    private NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean;
    private boolean islocation = false;
    private NewBasePresenter uploadpresenter, updatepresenter, grouppresenter;
    private UserEvent user;
    private static MainActivity activity;
    private boolean isIMG=false;

    public static MainActivity getInstense() {
        return activity;
    }


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_main);
        setToolbarVisibility(false);
        activity = this;
        presenter = new AroundSearchPresenter(this);
        weatherpresenter = new WeatherPresenter(this);
        uploadpresenter = new UploadLocationPresenter(this);
        updatepresenter = new UpdatePresenter(this);
        grouppresenter = new ShareGroupPresenter(this);
        getGroup();
        setMapView();
        locationGPS();
        setWeather();
        updatepresenter.setRequest(null, PubConst.LABLE_UPDATE);
        cbLyxz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) map_tdlyxz.setVisible(true);
                else map_tdlyxz.setVisible(false);
            }
        });
        cbXcbj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) map_cj.setVisible(true);
                else map_cj.setVisible(false);
            }
        });
    }

    public void getGroup() {
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        if (user != null) {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("loginname", user.getLoginname());
            grouppresenter.setRequest(parameter, PubConst.LABLE_GETSHAREGROUP);
        }
    }

    private void setWeather() {
        String spath = Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF" + File.separator + "WeatherCash";
        File file = new File(spath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Gson gson = new Gson();
        String countryString = PubConst.countryString;
        CountryBean countryBean = gson.fromJson(countryString, CountryBean.class);
        List<CountryBean.CountriesBean> countries = countryBean.getCountries();
        for (CountryBean.CountriesBean country : countries) {
            String weatherCash = Util.getWeatherCash(country.getName());
            if (weatherCash.equals("")) {
                Map<String, Object> parameter = new HashMap<>();
                MyLogUtil.showLog(country.getLatitude() + "," + country.getLongitude());
                parameter.put("city", country.getLatitude() + "," + country.getLongitude());
                weatherpresenter.setRequest(parameter);
//                showMsg("天气请求");
            } else {
                WeatherBean bean = gson.fromJson(weatherCash, WeatherBean.class);
                setWeatherGraphics(bean);
            }
        }
    }

    private void setWeatherGraphics(WeatherBean bean) {
        WeatherBean.HeWeather6Bean heWeather6Bean = bean.getHeWeather6().get(0);
        WeatherBean.HeWeather6Bean.BasicBean basic = heWeather6Bean.getBasic();
        List<WeatherBean.HeWeather6Bean.DailyForecastBean> daily_forecast = heWeather6Bean.getDaily_forecast();
        if (daily_forecast != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getAssets().open(daily_forecast.get(0).getCond_code_d() + ".png"));
                View inflate = LayoutInflater.from(this).inflate(R.layout.view_weather, null);
                ImageView view = inflate.findViewById(R.id.img_weather);
                TextView textView = inflate.findViewById(R.id.tv_weather);
                view.setImageBitmap(bitmap);
                textView.setText(daily_forecast.get(0).getCond_txt_d());
                Bitmap viewbitmap = DensityUtil.convertViewToBitmap(inflate);
                Drawable drawable = new BitmapDrawable(viewbitmap);
                Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
                PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable1);
                Point point = new Point(Double.valueOf(basic.getLon()), Double.valueOf(basic.getLat()));
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("basiccity", basic.getLocation());
                parameter.put("tmpmax", daily_forecast.get(0).getTmp_max());//最高温度
                parameter.put("tmpmin", daily_forecast.get(0).getTmp_min());//最低温度
                parameter.put("condtext", daily_forecast.get(0).getCond_txt_d());//当前天气
                parameter.put("uv_index", daily_forecast.get(0).getUv_index());//紫外线强度指数
                parameter.put("winddir", daily_forecast.get(0).getWind_dir());//风向
                parameter.put("windsc", daily_forecast.get(0).getWind_sc());//风级
                parameter.put("pop", daily_forecast.get(0).getPop());//降水概率
                parameter.put("hum", daily_forecast.get(0).getHum());//湿度
                parameter.put("vis", daily_forecast.get(0).getVis());//能见度
                Graphic graphic = new Graphic(point, symbol, parameter);
                weatherlayer.addGraphic(graphic);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        map_cj=new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CJ_C);
        map_tdlyxz=new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.TDLYXZ_C);
        pointlayer = new GraphicsLayer();
        weatherlayer = new GraphicsLayer();
        bmapsView.setMaxScale(500);
//        bmapsView.setMinScale(100000);
        bmapsView.addLayer(mapServiceLayer, 0);
        bmapsView.addLayer(maptextLayer, 1);
        bmapsView.addLayer(mapRSServiceLayer, 2);
        bmapsView.addLayer(mapRStextLayer, 3);

        bmapsView.addLayer(map_lf, 4);
        bmapsView.addLayer(map_lfimg, 5);
        bmapsView.addLayer(map_tdlyxz,6);
        bmapsView.addLayer(map_cj,7);
        bmapsView.addLayer(map_xzq, 8);
        bmapsView.addLayer(map_lf_text, 9);
        bmapsView.addLayer(map_lfimg_text, 10);
        bmapsView.addLayer(pointlayer, 11);
        bmapsView.addLayer(weatherlayer, 12);
        map_tdlyxz.setVisible(false);
        map_cj.setVisible(false);
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
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscale.refreshScaleView(bmapsView.getScale());
                setLayerVisibale();
            }
        });

        mapzoom.setMapView(bmapsView);

        bmapsView.setOnSingleTapListener(mapclick);
    }

    private void setLayerVisibale() {
        if (bmapsView.getScale()>9027.9993438721) {
            if (isIMG){
                mapRSServiceLayer.setVisible(true);
                mapRStextLayer.setVisible(true);
                map_lfimg.setVisible(false);
                map_lfimg_text.setVisible(false);
            }else {
                mapServiceLayer.setVisible(true);
                maptextLayer.setVisible(true);
                map_lf.setVisible(false);
                map_lf_text.setVisible(false);
            }
        }else {
            if (isIMG){
                mapRSServiceLayer.setVisible(false);
                mapRStextLayer.setVisible(false);
                map_lfimg.setVisible(true);
                map_lfimg_text.setVisible(true);
            }else {
                mapServiceLayer.setVisible(false);
                maptextLayer.setVisible(false);
                map_lf.setVisible(true);
                map_lf_text.setVisible(true);
            }
        }
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
        bubbletextview.setText("查看全景");
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
                    if (Contant.ins().isLocalState()) {
                        setLocal("1", PubConst.LABLE_START_SHARE);
                    }
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

    @OnClick({R.id.bt_around, R.id.bt_route, R.id.bt_more, R.id.cover_tv, R.id.change_map,
            R.id.bt_navi, R.id.location_map, R.id.location_quanjing, R.id.bubbletextview,
            R.id.location_tianqi, R.id.location_tuceng, R.id.ll_around, R.id.ll_route,
            R.id.tv7_weather})
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
            case R.id.cover_tv:
                setEventBus("search");
                skip(AroundActivity.class, false);
                break;
            case R.id.change_map:
                if (isIMG) {
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
                isIMG=!isIMG;
                setLayerVisibale();
                break;
            case R.id.bt_navi:
                setEventBus("navi");
                skip(PlanActivity.class, false);
                break;
            case R.id.location_map:
                this.bean = null;
                bmapsView.zoomToScale(ptCurrent, 50000);
                RefreshOnThread();
                if (!weatherlayer.isVisible() && imgQuanjing.getVisibility() == View.GONE) {
                    islocation = true;
                    hideBottom();
                    hideWeatherBottom();
                    setPointRequest(ptCurrent, "100");
                }


                break;
            case R.id.location_tianqi:
                llTianqi.setBackgroundColor(getResources().getColor(R.color.white));
                llQuanjing.setBackgroundColor(getResources().getColor(R.color.white));
                qttvSearchlocal.setVisibility(View.VISIBLE);
                weatherlayer.clearSelection();
                hideBottom();
                if (weatherlayer.isVisible()) {
                    imgHefeng.setVisibility(View.GONE);
                    weatherlayer.setVisible(false);
                    bmapsView.setOnSingleTapListener(mapclick);
                    hideWeatherBottom();
                } else {
                    imgHefeng.setVisibility(View.VISIBLE);
                    llTianqi.setBackgroundColor(getResources().getColor(R.color.lightblue));
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
                    bmapsView.setOnPanListener(null);
//                    bmapsView.setMinScale(1500000);
                    bmapsView.zoomToScale(new Point(116.75750057616959, 39.31351869282022), 1500000);
                    weatherlayer.setVisible(true);
                    bmapsView.setOnSingleTapListener(weatherclick);
                }

                break;
            case R.id.location_quanjing:
                llTianqi.setBackgroundColor(getResources().getColor(R.color.white));
                llQuanjing.setBackgroundColor(getResources().getColor(R.color.white));
                imgHefeng.setVisibility(View.GONE);
                if (imgQuanjing.getVisibility() == View.VISIBLE) {
                    imgQuanjing.setVisibility(View.GONE);
                    bubbletextview.setVisibility(View.GONE);
                    bubbletextview.setText("正在查询...");
                    bmapsView.setOnPanListener(null);
                    bmapsView.setOnSingleTapListener(mapclick);
                } else {
                    llQuanjing.setBackgroundColor(getResources().getColor(R.color.lightblue));
                    qttvSearchlocal.setVisibility(View.VISIBLE);
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
//                MyLogUtil.showLog("zuobiao", gps.getWgLat() + "---" + gps.getWgLon());
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
                    String name="";
                    if (!bean.getProperties().get简称().equals("")){
                        name=bean.getProperties().get简称();
                    }else {
                        if (!bean.getProperties().get名称().equals("")){
                            name=bean.getProperties().get名称();
                        }else {
                            if (!bean.getProperties().get兴趣点().equals("")){
                                name=bean.getProperties().get兴趣点();
                            }else {
                                if (!bean.getProperties().get描述().equals("")){
                                    name=bean.getProperties().get描述();
                                }else {
                                    name=bean.getProperties().get备注();
                                }
                            }
                        }
                    }
                    endPoint.setName(name);
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
                Bundle bundle = new Bundle();
                bundle.putString("x", String.valueOf(selectpoint.getX()));
                bundle.putString("y", String.valueOf(selectpoint.getY()));
                skip(WeatherActivity.class, bundle, false);
                break;
        }
    }

    private void setEventBus(String route) {
        MapExtent extent = new MapExtent();
        extent.setCenter(bmapsView.getCenter());
        extent.setScale(bmapsView.getScale());
        EventBus.getDefault().postSticky(extent);
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
    public void showLoadingDialog(String lable, String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog(String lable) {
        dismissProcessDialog();
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
        SoapObject soapObject = null;
        if (data instanceof SoapObject) soapObject = (SoapObject) data;
        switch (lable) {
            case PubConst.LABLE_UPDATE:
                UpdateBean bean = (UpdateBean) data;
                String versionCode = bean.getData().getData().getVersionCode();
                String versionName = bean.getData().getData().getVersionName();
                String updateUrl = bean.getData().getData().getUpdateUrl();
                MyLogUtil.showLog("服务器版本：" + versionCode + "----" + versionName);
                if (Integer.valueOf(versionCode) > BuildConfig.VERSION_CODE) {
                    Contant.ins().setUpdateUrl(updateUrl);
                    Contant.ins().setIsnewest(false);
                    imgMoreTab.setVisibility(View.VISIBLE);
                }
                break;
            case PubConst.LABLE_GETSHAREGROUP:
                String result5 = RequestUtil.getSoapObjectValue(soapObject, "result");
                if (result5.equals("ok")) {
                    Contant.ins().setLocalState(true);
                } else {
                    Contant.ins().setLocalState(false);
                }
                break;
        }

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
            Gson gson = new Gson();
            String string = gson.toJson(bean);
            if (bean.getHeWeather6().get(0).getBasic() != null) {
                Util.setWeatherCash(bean.getHeWeather6().get(0).getBasic().getLocation(), string);//缓存天气数据
            }
            setWeatherGraphics(bean);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().removeStickyEvent(SearchBean.PoisBean.class);
    }

    @OnClick(R.id.cover_tv)
    public void onViewClicked() {
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
            String name="";
            if (!bean.getProperties().get简称().equals("")){
                name=bean.getProperties().get简称();
            }else {
                if (!bean.getProperties().get名称().equals("")){
                    name=bean.getProperties().get名称();
                }else {
                    if (!bean.getProperties().get兴趣点().equals("")){
                        name=bean.getProperties().get兴趣点();
                    }else {
                        if (!bean.getProperties().get描述().equals("")){
                            name=bean.getProperties().get描述();
                        }else {
                            name=bean.getProperties().get备注();
                        }
                    }
                }
            }
            tvName.setText(name + "附近");
            tvName.setMaxLines(3);
            tvAddress.setText("");
        } else {
            Point point = zoom2bean(bean.getGeometry().getCoordinates());
            Graphic g = new Graphic(point, picSymbol);
            pointlayer.addGraphic(g);
            String name="";
            if (!bean.getProperties().get简称().equals("")){
                name=bean.getProperties().get简称();
            }else {
                if (!bean.getProperties().get名称().equals("")){
                    name=bean.getProperties().get名称();
                }else {
                    if (!bean.getProperties().get兴趣点().equals("")){
                        name=bean.getProperties().get兴趣点();
                    }else {
                        if (!bean.getProperties().get描述().equals("")){
                            name=bean.getProperties().get描述();
                        }else {
                            name=bean.getProperties().get备注();
                        }
                    }
                }
            }
            tvName.setText(name);
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
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params3.addRule(RelativeLayout.ABOVE, R.id.rl_weather_bottom); //设置控件的位置
        params3.setMargins(0, 0, i1, i);//左上右下
        imgHefeng.setLayoutParams(params3);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ABOVE, R.id.img_hefeng); //设置控件的位置
        params2.setMargins(0, 0, i, i1);//左上右下
        mapzoom.setLayoutParams(params2);
        String basiccity = String.valueOf(graphic.getAttributeValue("basiccity"));//城市名称
        String tmpmax = String.valueOf(graphic.getAttributeValue("tmpmax"));//最高温度
        String tmpmin = String.valueOf(graphic.getAttributeValue("tmpmin"));//最低温度
        String condtext = String.valueOf(graphic.getAttributeValue("condtext"));//当前天气
        String uv_index = String.valueOf(graphic.getAttributeValue("uv_index"));//紫外线强度
        String winddir = String.valueOf(graphic.getAttributeValue("winddir"));//风向
        String windsc = String.valueOf(graphic.getAttributeValue("windsc"));//风级
        String pop = String.valueOf(graphic.getAttributeValue("pop"));//降水概率
//        String hum = String.valueOf(graphic.getAttributeValue("hum"));//湿度
        String vis = String.valueOf(graphic.getAttributeValue("vis"));//能见度
        tv1Weather.setText(basiccity);
        tv2Weather.setText(condtext + "  " + tmpmin + "～" + tmpmax + "℃");
        tv3Weather.setText("紫外线强度：" + uv_index + "级");
        tv4Weather.setText(winddir + " " + windsc + "级");
        tv5Weather.setText("能见度：" + vis + "公里");
        tv6Weather.setText("降水概率：" + pop + "%");
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
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params3.addRule(RelativeLayout.ABOVE, R.id.id_tab_map); //设置控件的位置
        params3.setMargins(0, 0, i1, i);//左上右下
        imgHefeng.setLayoutParams(params3);
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
        bmapsView.zoomToScale(point, 500);
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
                ShowDialog.showAttention(MainActivity.this, "请注意", "是否退出程序", new ShowDialog.DialogCallBack() {
                    @Override
                    public void dialogSure(DialogInterface dialog) {
                        finish();
                    }

                    @Override
                    public void dialogCancle(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        }
        return false;
    }

    OnSingleTapListener mapclick = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
            MyLogUtil.showLog(bmapsView.getScale());
            islocation = false;
            hideBottom();
            Point point = bmapsView.toMapPoint(v, v1);
            setPointRequest(point, "5");
            MyLogUtil.showLog(bmapsView.getSpatialReference().toString());
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
                setWeatherBottom(graphic);
            }
        }
    };


    private void setLocal(String state, String label) {
        MyLogUtil.showLog("1234");
        UserEvent mUser = (UserEvent) SharedUtil.getSerializeObject("user");
        if (mUser == null) mUser = user;
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", mUser.getLoginname());
        parameter.put("username", mUser.getUsername());
        parameter.put("x", String.valueOf(ptCurrent.getX()));
        parameter.put("y", String.valueOf(ptCurrent.getY()));
        parameter.put("state", state);
        uploadpresenter.setRequest(parameter, label);
    }

    public void setUser(UserEvent user) {
        this.user = user;
    }

    public Point getPtCurrent() {
        return ptCurrent;
    }

}
