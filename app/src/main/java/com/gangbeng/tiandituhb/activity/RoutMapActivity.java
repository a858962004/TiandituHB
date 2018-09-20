package com.gangbeng.tiandituhb.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-22
 */

public class RoutMapActivity extends BaseActivity {
    @BindView(R.id.id_routmap)
    MapView idRoutmap;
    @BindView(R.id.ditail_busitem)
    TextView ditailBusitem;
    @BindView(R.id.tv_busitem)
    TextView tvBusitem;
    @BindView(R.id.tv2_busitem)
    TextView tv2Busitem;

    private TianDiTuLFServiceLayer map_lf_text, map_lf,map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer;
    private LocationDisplayManager ldm;
    private GraphicsLayer graphicsLayer, pointLayer;
    private BusBean.ResultsBean.LinesBean linesBean;
    private String start,end;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        setContentLayout(R.layout.acitivity_routmap);
        setToolbarTitle("公交路线");
        setToolbarRightVisible(false);
        setMapView();
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        linesBean =
                (BusBean.ResultsBean.LinesBean) bundleExtra.getSerializable("data");
        start=bundleExtra.getString("start");
        end=bundleExtra.getString("end");
        List<BusBean.ResultsBean.LinesBean.SegmentsBean> segments = linesBean.getSegments();
        setBottomView(linesBean);
        setPolyLine(segments);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void setMapView() {
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
//        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
//        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);
        graphicsLayer = new GraphicsLayer();
        pointLayer = new GraphicsLayer();

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
//        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_xzq=new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);

        idRoutmap.addLayer(mapServiceLayer, 0);
        idRoutmap.addLayer(maptextLayer, 1);
//        idRoutmap.addLayer(mapRSServiceLayer, 2);
//        idRoutmap.addLayer(mapRStextLayer, 3);

        idRoutmap.addLayer(map_lf, 2);
        idRoutmap.addLayer(map_lf_text, 3);
//        idRoutmap.addLayer(map_lfimg, 6);
        idRoutmap.addLayer(map_xzq, 4);
        idRoutmap.addLayer(graphicsLayer, 5);
        idRoutmap.addLayer(pointLayer, 6);

//        mapRSServiceLayer.setVisible(false);
//        mapRStextLayer.setVisible(false);
//        map_lfimg.setVisible(false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setBottomView(BusBean.ResultsBean.LinesBean linesBean) {
        ditailBusitem.setVisibility(View.VISIBLE);
        String lineName = linesBean.getLineName();
        String name = "";
        String[] split = lineName.split("|");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("|")) {
                split[i] = "→";
            }
            if (i != split.length - 1) {
                name += split[i];
            }
        }
        tvBusitem.setText(name);
        int stations = 0;
        float distence = 0;
        String length = "";
        for (BusBean.ResultsBean.LinesBean.SegmentsBean segmentsBean : linesBean.getSegments()) {
            if (segmentsBean.getSegmentLine().size() > 0) {
                String segmentStationCount = segmentsBean.getSegmentLine().get(0).getSegmentStationCount();
                if (!segmentStationCount.equals(""))
                    stations += Integer.valueOf(segmentStationCount);
                String segmentDistance = segmentsBean.getSegmentLine().get(0).getSegmentDistance();
                if (!segmentDistance.equals(""))
                    distence += Float.valueOf(segmentDistance);
            }
        }
        if (distence > 1000) {
            String string = String.valueOf(distence / 1000.0);
            String s = Util.saveTwoU(string);
            length = s + " 公里";
        } else {
            length = distence + " 米";
        }
        tv2Busitem.setText("总长" + length + ";共" + stations + "站");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setPolyLine(List<BusBean.ResultsBean.LinesBean.SegmentsBean> segments) {
        Polyline allpoPolyline = new Polyline();
        for (int i = 0; i < segments.size(); i++) {
            int segmentType = segments.get(i).getSegmentType();
            List<BusBean.ResultsBean.LinesBean.SegmentsBean.SegmentLineBean> segmentLine = segments.get(i).getSegmentLine();
            if (segmentLine.size() > 0) {
                Polyline polyline = new Polyline();
                Polyline polyline2 = new Polyline();
                String linePoint = segmentLine.get(0).getLinePoint();
                String[] split = linePoint.split(";");
                for (int i2 = 0; i2 < split.length - 1; i2++) {
                    String x = split[i2].substring(0, split[i2].indexOf(","));
                    String y = split[i2].substring(split[i2].indexOf(",") + 1, split[i2].length());
                    String x2 = split[i2 + 1].substring(0, split[i2 + 1].indexOf(","));
                    String y2 = split[i2 + 1].substring(split[i2 + 1].indexOf(",") + 1, split[i2 + 1].length());
                    if (i == 0 && i2 == 0) {
                        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
                        Drawable drawable = getResources().getDrawable(R.mipmap.icon_qidian);
                        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
                        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
                        picSymbol.setOffsetY(drawable1.getIntrinsicHeight()/2);
                        Graphic startgraphic = new Graphic(point, picSymbol);
                        pointLayer.addGraphic(startgraphic);
                    }
                    if (i == segments.size() - 1 && i2 == split.length - 2) {
                        Point point = new Point(Double.valueOf(x2), Double.valueOf(y2));
                        Drawable drawable = getResources().getDrawable(R.mipmap.icon_zhongdian);
                        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
                        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
                        picSymbol.setOffsetY(drawable1.getIntrinsicHeight()/2);
                        Graphic startgraphic = new Graphic(point, picSymbol);
                        pointLayer.addGraphic(startgraphic);
                    }
                    Point start = new Point(Double.valueOf(x), Double.valueOf(y));
                    Point end = new Point(Double.valueOf(x2), Double.valueOf(y2));
                    Line line = new Line();
                    line.setStart(start);
                    line.setEnd(end);
                    if (segmentType == 1) {
                        Polyline polyline3 = new Polyline();
                        SimpleLineSymbol lineSymbol3 = new SimpleLineSymbol(Color.BLACK, 2, SimpleLineSymbol.STYLE.DASHDOT);
                        polyline3.addSegment(line, false);
                        Graphic graphic3 = new Graphic(polyline3, lineSymbol3);
                        graphicsLayer.addGraphic(graphic3);
                    } else {
                        polyline.addSegment(line, false);
                        polyline2.addSegment(line, false);
                        allpoPolyline.addSegment(line, false);
                    }
                }
                SimpleLineSymbol lineSymbol = new SimpleLineSymbol(getColor(R.color.rout), 10, SimpleLineSymbol.STYLE.SOLID);
                SimpleLineSymbol lineSymbol2 = new SimpleLineSymbol(Color.BLACK, 3, SimpleLineSymbol.STYLE.DOT);
                Graphic graphic = new Graphic(polyline, lineSymbol);
                Graphic graphic2 = new Graphic(polyline2, lineSymbol2);
                graphicsLayer.addGraphic(graphic);
                graphicsLayer.addGraphic(graphic2);
            }
        }

        idRoutmap.setExtent(allpoPolyline);
    }


    @OnClick(R.id.ditail_busitem)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",linesBean);
        bundle.putString("name",String.valueOf(tvBusitem.getText()));
        bundle.putString("distence",String.valueOf(tv2Busitem.getText()));
        bundle.putString("start",start);
        bundle.putString("end",end);
        skip(BusDitailActivity.class,bundle,false);
    }
}
