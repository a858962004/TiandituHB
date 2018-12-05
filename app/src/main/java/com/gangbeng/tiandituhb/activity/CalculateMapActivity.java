package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
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
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.utils.Util;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.change_calulate)
    CardView changeCalulate;
    @BindView(R.id.location_calculate)
    CardView locationCalculate;
    @BindView(R.id.mapviewscale)
    MapScaleView mapviewscale;
    @BindView(R.id.mapzoom)
    MapZoomView mapzoom;
    @BindView(R.id.btnclear)
    LinearLayout btnclear;
    @BindView(R.id.btnchexiao)
    LinearLayout btnchexiao;
    @BindView(R.id.calculate)
    LinearLayout calculate;
    @BindView(R.id.img_dingweizhen)
    ImageView imgDingweizhen;
    @BindView(R.id.img_dingweizhen2)
    ImageView imgDingweizhen2;
    @BindView(R.id.btv_calculate)
    BubbleTextView btvCalculate;
    @BindView(R.id.ll_bottombt)
    LinearLayout llBottombt;

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
    private PictureMarkerSymbol markerSymbolblue, markerSymbolred;
    private SimpleFillSymbol fillSymbol;
    private SimpleLineSymbol lineSymbol;
    private Polyline polyline;
    private Polygon polygon = null;//记录绘制过程中的多边形
    private MapExtent extent;
    private String dikuai = "";
    private List<List<Point>> oldPoints = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Callout callout;
    private Point changePoint = null;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_calculatemap);
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        activity = bundleExtra.getString("activity");
        if (bundleExtra.getString("dikuai") != null) {
            dikuai = bundleExtra.getString("dikuai");
        }
        setToolbarTitle(activity);
        if (activity.equals("点距测量")) {
            textTvjuli.setText("距离");
            textTvjieguo.setText("0.0米");
        } else if (activity.equals("面积测量")) {
            textTvjuli.setText("面积");
            textTvjieguo.setText("0.0平方米");
        } else if (activity.equals("地块核查") || activity.equals("添加信息点")) {
            calculate.setVisibility(View.GONE);
            setToolbarRightVisible(true);
            setRightImageBtnText("完成");
        }
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        setMapview();
        locationGPS();
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

        mapCalculate.addLayer(drawLayer, 8);
        mapCalculate.addLayer(drawPointLayer, 9);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        mapCalculate.setOnSingleTapListener(onSingleTapListener);

        fillSymbol = new SimpleFillSymbol(Color.RED);
        fillSymbol.setAlpha(90);
        lineSymbol = new SimpleLineSymbol(Color.RED, 2, SimpleLineSymbol.STYLE.SOLID);

        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingweizhen);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 130, 130);
        markerSymbolblue = new PictureMarkerSymbol(drawable1);
        markerSymbolblue.setOffsetY(drawable1.getIntrinsicHeight() / 2);

        Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_dingweizhen2);
        Drawable drawable3 = DensityUtil.zoomDrawable(drawable2, 130, 130);
        markerSymbolred = new PictureMarkerSymbol(drawable3);
        markerSymbolred.setOffsetY(drawable3.getIntrinsicHeight() / 2);

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
                    if (!dikuai.equals("")) {
                        if (points.size() == 1) {
                            mapCalculate.zoomToScale(points.get(0), 50000);
                        } else {
                            Envelope envelope = new Envelope();
                            polygon.queryEnvelope(envelope);
                            mapCalculate.setExtent(envelope);
                            try {
                                Thread.sleep(300);
                                mapCalculate.zoomout();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            mapCalculate.zoomToScale(envelope.getCenter(), 70000);
                        }
                        mapviewscale.refreshScaleView(extent.getScale());
                    } else {
                        mapCalculate.zoomToScale(extent.getCenter(), extent.getScale());
                        mapviewscale.refreshScaleView(extent.getScale());
                    }


                }
            }
        });
        mapzoom.setMapView(mapCalculate);
        if (!dikuai.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(dikuai);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Point point = new Point();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String x = jsonObject.getString("x");
                    String y = jsonObject.getString("y");
                    point.setX(Double.valueOf(x));
                    point.setY(Double.valueOf(y));
                    points.add(point);
                }
            } catch (Exception e) {
                return;
            }
            for (Point point : points) {
                Graphic graphic = new Graphic(point, markerSymbolblue);
                drawPointLayer.addGraphic(graphic);
            }
            if (points.size() > 1) getArea(drawLayer);
            ptStart = points.get(points.size() - 1);

            addOldPoint();
        }
    }

    private void addOldPoint() {
        List<Point> pointList = new ArrayList<>();
        for (Point point : points) {
            Point point1 = new Point();
            point1.setX(point.getX());
            point1.setY(point.getY());
            pointList.add(point1);
        }
        if (oldPoints.size() == 20) {
            oldPoints.remove(0);
        }
        oldPoints.add(pointList);
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
    protected void setRightClickListen() {
        if (changePoint != null) {
            llBottombt.setVisibility(View.VISIBLE);
            setToolbarTitle("地块核查");
            setRightImageBtnText("完成");
            imgDingweizhen.setVisibility(View.GONE);
            btvCalculate.setVisibility(View.GONE);
            changePoint = null;
            int[] drawPointLayerGraphicIDs = drawPointLayer.getGraphicIDs();
            int maxID=-1;
            for (int drawPointLayerGraphicID : drawPointLayerGraphicIDs) {
                if (drawPointLayerGraphicID>maxID) maxID=drawPointLayerGraphicID;
            }
            drawPointLayer.removeGraphics(new int[]{maxID});
            mapCalculate.setOnSingleTapListener(onSingleTapListener);
            mapCalculate.setOnPanListener(null);
        } else if (points.size() > 0) {
            if (activity.equals("添加信息点")) {
                PointBackActivity.getInstence().setPoint(points);
                finish();
            }
            if (activity.equals("地块核查")) {
                if (points.size() > 2) {
                    DKCheckActivity.getInstence().setPoint(points);
                    finish();
                } else {
                    ShowToast("请绘制面状要素");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @OnClick({R.id.btnclear, R.id.change_calulate, R.id.location_calculate, R.id.btnchexiao, R.id.btv_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnclear:
                if (callout.isShowing()){
                    callout.hide();
                }
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
                addOldPoint();
                break;
            case R.id.btnchexiao:
                if (callout.isShowing()){
                    callout.hide();
                }
                if (oldPoints.size() > 1) {
                    this.points.clear();
                    oldPoints.remove(oldPoints.size() - 1);
                    List<Point> points = oldPoints.get(oldPoints.size() - 1);
                    List<Point> addpoints = new ArrayList<>();
                    for (Point point : points) {
                        addpoints.add(new Point(point.getX(), point.getY()));
                    }
                    this.points.addAll(addpoints);
                    reDrawLayer();
                }
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
            case R.id.btv_calculate:
                Point center = mapCalculate.getCenter();
                for (int i = 0; i < points.size(); i++) {
                    if ((points.get(i).getX() == changePoint.getX()
                            && points.get(i).getY() == changePoint.getY())) {
                        points.get(i).setX(center.getX());
                        points.get(i).setY(center.getY());
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llBottombt.setVisibility(View.VISIBLE);
                        setToolbarTitle("地块核查");
                        setRightImageBtnText("完成");
                        reDrawLayer();
                        addOldPoint();
                        imgDingweizhen.setVisibility(View.GONE);
                        btvCalculate.setVisibility(View.GONE);
                        changePoint = null;
                        mapCalculate.setOnSingleTapListener(onSingleTapListener);
                        mapCalculate.setOnPanListener(null);
                    }
                });
                break;
        }
    }

    OnSingleTapListener onSingleTapListener = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
            int[] graphicIDs = drawPointLayer.getGraphicIDs(v, v1, 25);
            if (graphicIDs != null && graphicIDs.length > 0) {
                setCallout(graphicIDs);
            } else if (callout != null && callout.isShowing()) {
                callout.hide();
            } else {
                ptCurrent = mapCalculate.toMapPoint(new Point(v, v1));
                if (activity.equals("添加信息点")) {
                    ptStart = null;
                    points.clear();
                    drawLayer.removeAll();//第一次开始前，清空全部graphic
                    drawPointLayer.removeAll();
                }

                points.add(ptCurrent);
                if (ptStart == null) {//画线或多边形的第一个点
                    drawLayer.removeAll();//第一次开始前，清空全部graphic
                    drawPointLayer.removeAll();
                    ptStart = ptCurrent;
                    //绘制第一个点
                    Graphic graphic = new Graphic(ptStart, markerSymbolblue);
                    drawPointLayer.addGraphic(graphic);
                } else {//画线或多边形的其他点
                    setLengthArea();
                    //绘制其他点
                    Graphic graphic = new Graphic(ptCurrent, markerSymbolblue);
                    drawPointLayer.addGraphic(graphic);
                }
                ptPrevious = ptCurrent;
                addOldPoint();
            }
        }

    };

    private void setCallout(int[] graphicIDs) {
        LayoutInflater inflater = LayoutInflater
                .from(CalculateMapActivity.this);
        View view = inflater.inflate(R.layout.callout_map, null);
        TextView deletTV = view.findViewById(R.id.tv_delete_callout);
        TextView changeTV = view.findViewById(R.id.tv_change_callout);
        final Graphic graphic = drawPointLayer.getGraphic(graphicIDs[0]);
        final Point geometry = (Point) graphic.getGeometry();
        mapCalculate.zoomToScale(geometry, 5000);
        callout = mapCalculate.getCallout();
        callout.setStyle(R.xml.calloutstyle);
        callout.setOffset(0, 15);
        callout.show(geometry, view);
        deletTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < points.size(); i++) {
                    if (points.get(i).getX() == ((Point) graphic.getGeometry()).getX()
                            && points.get(i).getY() == ((Point) graphic.getGeometry()).getY()) {
                        points.remove(i);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reDrawLayer();
                                addOldPoint();
                                callout.hide();
                            }
                        });
                        break;
                    }
                }
            }
        });
        changeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llBottombt.setVisibility(View.GONE);
                setToolbarTitle("点位置修改");
                setRightImageBtnText("取消");
                changePoint = geometry;
                Graphic graphic1 = new Graphic(changePoint, markerSymbolred);
                drawPointLayer.addGraphic(graphic1);
                mapCalculate.setOnSingleTapListener(null);
                callout.hide();
                imgDingweizhen.setVisibility(View.VISIBLE);
                mapCalculate.setOnPanListener(new OnPanListener() {
                    @Override
                    public void prePointerMove(float v, float v1, float v2, float v3) {
                        if (imgDingweizhen.getVisibility() == View.VISIBLE) {
                            btvCalculate.setVisibility(View.GONE);
                            imgDingweizhen.setVisibility(View.GONE);
                            imgDingweizhen2.setVisibility(View.VISIBLE);
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
                        if (imgDingweizhen2.getVisibility() == View.VISIBLE) {
                            btvCalculate.setVisibility(View.VISIBLE);
                            imgDingweizhen2.setVisibility(View.GONE);
                            imgDingweizhen.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void reDrawLayer() {
        ptStart = null;
        drawPointLayer.removeAll();
        drawLayer.removeAll();
        if (points.size() > 0) {
            for (Point point : points) {
                Graphic graphic = new Graphic(point, markerSymbolblue);
                drawPointLayer.addGraphic(graphic);
            }
            if (points.size() > 0) setLengthArea();
            ptStart = points.get(points.size() - 1);
        }
    }

    private void setLengthArea() {
        if (activity.equals("点距测量")) {
            String length = "0.0 米";
            //绘制当前线段
            if (points.size() > 1) {
                getlength(drawLayer);
                // 计算当前线段的长度
                Polyline polyline1 = (Polyline) GeometryEngine.project(polyline, mapCalculate.getSpatialReference(), SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR));

                if (Math.round(polyline1.calculateLength2D()) > 1000) {
                    String string = Double.toString(Math.round(polyline1.calculateLength2D()) / 1000.0);
                    String s = Util.saveTwoU(string);
                    length = s + " 千米";
                } else {
                    String string = Double.toString(Math.round(polyline1.calculateLength2D()));
                    MyLogUtil.showLog(string);
                    String s = Util.saveTwoU(string);
                    length = s + " 米";
                }
            }

            textTvjieguo.setText(length);
        } else if (activity.equals("面积测量") || activity.equals("地块核查")) {
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

    //测量距离
    private void getlength(GraphicsLayer drawLayer) {
        polyline = new Polyline();
        Point startPoint = null;
        Point endPoint = null;
        for (int i = 1; i < points.size(); i++) {
            startPoint = points.get(i - 1);
            MyLogUtil.showLog(mapCalculate.toScreenPoint(startPoint));
            endPoint = points.get(i);
            MyLogUtil.showLog(mapCalculate.toScreenPoint(endPoint));
            MyLogUtil.showLog(mapCalculate.getScale());
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CalculateMap Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
