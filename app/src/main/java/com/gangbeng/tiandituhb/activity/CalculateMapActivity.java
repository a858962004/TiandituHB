package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.Util;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-06
 */

public class CalculateMapActivity extends BaseActivity {
    @BindView(R.id.map_calculate)
    MapView mapCalculate;
    @BindView(R.id.text_tvjuli)
    TextView textTvjuli;
    @BindView(R.id.text_tvjieguo)
    TextView textTvjieguo;
    @BindView(R.id.measureLin)
    LinearLayout measureLin;
    //    @BindView(R.id.btnclear)
//    ImageButton btnclear;
    @BindView(R.id.change_calulate)
    CardView changeCalulate;
    @BindView(R.id.location_calculate)
    CardView locationCalculate;
    @BindView(R.id.btnclear)
    TextView btnclear;
    @BindView(R.id.mapviewscale)
    MapScaleView mapviewscale;
    @BindView(R.id.mapzoom)
    MapZoomView mapzoom;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer, drawLayer;
    private LocationDisplayManager ldm;
    private String activity;
    private boolean isFirstlocal = true;
    private ArrayList<Point> points = new ArrayList<Point>();
    private Point lacation;
    private Point ptCurrent;
    private Point ptPrevious = null;//上一个点
    private Point ptStart = null;//起点
    private PictureMarkerSymbol markerSymbolred, markerSymbolblue;
    private SimpleFillSymbol fillSymbol;
    private SimpleLineSymbol lineSymbol;
    private Polyline polyline;
    private Polygon polygon = null;//记录绘制过程中的多边形
    private MapExtent extent;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_calculatemap);
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        activity = bundleExtra.getString("activity");
        setToolbarTitle(activity);
        if (activity.equals("点距测量")) {
            textTvjuli.setText("距离");
            textTvjieguo.setText("0.0米");
        } else if (activity.equals("面积测量")) {
            textTvjuli.setText("面积");
            textTvjieguo.setText("0.0平方米");
        }
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        locationGPS();
        setMapview();

    }

    private void setMapview() {
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);
        drawPointLayer = new GraphicsLayer();
        drawLayer = new GraphicsLayer();

        mapCalculate.addLayer(mapServiceLayer, 0);
        mapCalculate.addLayer(maptextLayer, 1);
        mapCalculate.addLayer(mapRSServiceLayer, 2);
        mapCalculate.addLayer(mapRStextLayer, 3);

        mapCalculate.addLayer(map_lf, 4);
        mapCalculate.addLayer(map_lf_text, 5);
        mapCalculate.addLayer(map_lfimg, 6);
        mapCalculate.addLayer(map_xzq, 7);

        mapCalculate.addLayer(drawPointLayer, 8);
        mapCalculate.addLayer(drawLayer, 9);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        mapCalculate.setOnSingleTapListener(onSingleTapListener);

        fillSymbol = new SimpleFillSymbol(Color.RED);
        fillSymbol.setAlpha(90);
        lineSymbol = new SimpleLineSymbol(Color.RED, 2, SimpleLineSymbol.STYLE.SOLID);
        Drawable imagered = getBaseContext().getResources()
                .getDrawable(R.mipmap.redpush);
        markerSymbolred = new PictureMarkerSymbol(imagered);
        Drawable imageblue = getBaseContext().getResources()
                .getDrawable(R.mipmap.bluepush);
        markerSymbolblue = new PictureMarkerSymbol(imageblue);
        mapCalculate.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscale.refreshScaleView(mapCalculate.getScale());
            }
        });
        mapCalculate.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                    mapCalculate.zoomToScale(extent.getCenter(), extent.getScale());
                    mapviewscale.refreshScaleView(extent.getScale());

                }
            }
        });
        mapzoom.setMapView(mapCalculate);
    }

    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = mapCalculate.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lacation = new Point(location.getLongitude(), location.getLatitude());
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
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnclear, R.id.change_calulate, R.id.location_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnclear:
                if (activity.equals("点距测量")) {
                    textTvjieguo.setText("0.0米");
                } else {
                    textTvjieguo.setText("0.0平方米");
                }
                ptStart = null;
                ptPrevious = null;
                points.clear();
                polyline = null;
                drawLayer.removeAll();
                drawPointLayer.removeAll();
                break;
            case R.id.change_calulate:
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
            case R.id.location_calculate:
                mapCalculate.zoomToScale(lacation, 50000);
                break;
        }
    }

    OnSingleTapListener onSingleTapListener = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
            ptCurrent = mapCalculate.toMapPoint(new Point(v, v1));
            points.add(ptCurrent);
            if (ptStart == null) {//画线或多边形的第一个点
                drawLayer.removeAll();//第一次开始前，清空全部graphic
                drawPointLayer.removeAll();
                ptStart = ptCurrent;
                //绘制第一个点
                Graphic graphic = new Graphic(ptStart, markerSymbolred);
                drawPointLayer.addGraphic(graphic);
            } else {//画线或多边形的其他点
                //绘制其他点
                Graphic graphic = new Graphic(ptCurrent, markerSymbolblue);
                drawPointLayer.addGraphic(graphic);
                if (activity.equals("点距测量")) {
                    //绘制当前线段
                    getlength(drawLayer);
                    // 计算当前线段的长度
                    Polyline polyline1 = (Polyline) GeometryEngine.project(polyline, mapCalculate.getSpatialReference(), SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR));
                    String length = new String();
                    if (Math.round(polyline1.calculateLength2D()) > 1000) {
                        String string = Double.toString(Math.round(polyline1.calculateLength2D()) / 1000.0);
                        String s = Util.saveTwoU(string);
                        length = s + " 千米";
                    } else {
                        String string = Double.toString(Math.round(polyline1.calculateLength2D()));
                        String s = Util.saveTwoU(string);
                        length = s + " 米";
                    }
                    textTvjieguo.setText(length);
                } else if (activity.equals("面积测量")) {
                    //绘制临时多边形
                    getArea(drawLayer);
                    //计算当前面积
                    Polygon polygonNow = (Polygon) GeometryEngine.project(polygon, mapCalculate.getSpatialReference(), SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR));
                    Log.d("tag", String.valueOf(mapCalculate.getSpatialReference()));
                    String sArea = getAreaString(polygonNow.calculateArea2D());
                    Log.d("tag", polygonNow.calculateArea2D() + "");
                    textTvjieguo.setText(sArea);
                }
            }
            ptPrevious = ptCurrent;
        }
    };

    //测量距离
    private void getlength(GraphicsLayer drawLayer) {
        polyline = new Polyline();
        Point startPoint = null;
        Point endPoint = null;
        for (int i = 1; i < points.size(); i++) {
            startPoint = points.get(i - 1);
            endPoint = points.get(i);
            Line line1 = new Line();
            line1.setStart(startPoint);
            line1.setEnd(endPoint);
            polyline.addSegment(line1, false);
        }
        drawLayer.removeAll();
        Graphic g = new Graphic(polyline, lineSymbol);
        drawLayer.addGraphic(g);
    }

    //面积测量
    private void getArea(GraphicsLayer drawLayer) {
        polygon = new Polygon();
        Point startPoint = null;
        Point endPoint = null;
        for (int i = 1; i < points.size(); i++) {
            startPoint = points.get(i - 1);
            endPoint = points.get(i);
            Line line1 = new Line();
            line1.setStart(startPoint);
            line1.setEnd(endPoint);

            polygon.addSegment(line1, false);
            //类型，属性，图片
        }
        drawLayer.removeAll();
        Graphic g = new Graphic(polygon, fillSymbol);
        drawLayer.addGraphic(g);
    }

    private String getAreaString(double dValue) {
        long area = Math.abs(Math.round(dValue));
        String sArea = "";
        // 顺时针绘制多边形，面积为正，逆时针绘制，则面积为负
        if (area >= 1000000) {
            double dArea = area / 1000000.0;
            String string = Double.toString(dArea);
            sArea = Util.saveTwoU(string) + " 平方公里";
        } else {
            String string = Double.toString(area);
            sArea = Util.saveTwoU(string) + " 平方米";
        }


        return sArea;
    }

    /**
     * eventbus接收地图位置
     *
     * @param extent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetExtent(MapExtent extent) {
        this.extent = extent;
    }

}
