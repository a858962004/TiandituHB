package com.gangbeng.tiandituhb.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AddPhotoAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.DKHCInfo;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-10-21
 */

public class DKDitailActivity extends BaseActivity {

    @BindView(R.id.map_dkditail)
    MapView mapDkditail;
    @BindView(R.id.ed_bianhao)
    TextView edBianhao;
    @BindView(R.id.ed_lyxz)
    TextView edLyxz;
    @BindView(R.id.ed_sz)
    TextView edSz;
    @BindView(R.id.ed_wt)
    TextView edWt;
    @BindView(R.id.grid_feed)
    GridView gridFeed;

    private static DKDitailActivity activity;
    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer, drawLayer;
    private DKHCInfo dkhcInfo;
    private List<String> uris = new ArrayList<>();
    private AddPhotoAdapter askGridAdpter;
    private GoogleApiClient client;
    private PictureMarkerSymbol markerSymbolred;
    private SimpleFillSymbol fillSymbol;
    private SimpleLineSymbol lineSymbol;
    private Polygon polygon;
    private List<Point> points = new ArrayList<>();

    public static DKDitailActivity getInstence(){
        return activity;
    }

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_dkditail);
        setToolbarTitle("地块信息");
        setRightImageBtnText("修改");
        activity=this;
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        dkhcInfo = (DKHCInfo) bundleExtra.getSerializable("data");
        edBianhao.setText(dkhcInfo.getDKID());
        edLyxz.setText(dkhcInfo.getOwner());
        edSz.setText(dkhcInfo.getAddress());
        edWt.setText(dkhcInfo.getResult());
        uris.add("0");
        askGridAdpter = new AddPhotoAdapter(this, uris, null);
        gridFeed.setAdapter(askGridAdpter);
        setMapView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setMapView() {
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        drawPointLayer = new GraphicsLayer();
        drawLayer = new GraphicsLayer();

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);

        mapDkditail.addLayer(mapServiceLayer, 0);
        mapDkditail.addLayer(maptextLayer, 1);
        mapDkditail.addLayer(mapRSServiceLayer, 2);
        mapDkditail.addLayer(mapRStextLayer, 3);

        mapDkditail.addLayer(map_lf, 4);
        mapDkditail.addLayer(map_lf_text, 5);
        mapDkditail.addLayer(map_lfimg, 6);
        mapDkditail.addLayer(map_xzq, 7);
        mapDkditail.addLayer(drawLayer, 8);
        mapDkditail.addLayer(drawPointLayer, 9);
        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);

        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingweizhen);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 130, 130);
        markerSymbolred = new PictureMarkerSymbol(drawable1);
        markerSymbolred.setOffsetY(drawable1.getIntrinsicHeight() / 2);
        fillSymbol = new SimpleFillSymbol(Color.RED);
        fillSymbol.setAlpha(90);
        lineSymbol = new SimpleLineSymbol(Color.RED, 2, SimpleLineSymbol.STYLE.SOLID);

        String geometryStr = dkhcInfo.getGeometryStr();

        try {
            JSONArray jsonArray = new JSONArray(geometryStr);
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
            Graphic graphic = new Graphic(point, markerSymbolred);
            drawPointLayer.addGraphic(graphic);
        }
        if (points.size()>1)
        getArea(drawLayer);
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
        Geometry geometry = g.getGeometry();
        mapDkditail.setExtent(geometry);
    }

    @Override
    protected void setRightClickListen() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",dkhcInfo);
        skip(DKCheckActivity.class,bundle,false);
    }
}
