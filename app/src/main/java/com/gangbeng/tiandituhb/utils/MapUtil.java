package com.gangbeng.tiandituhb.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Administrator on 2017-06-20.
 */

public class MapUtil {

    private MapView mapView;
    static final Object sInstanceSync = new Object();
    private static MapUtil mapUtil;

    private MapUtil(MapView mapView) {
        this.mapView = mapView;
    }

    public static MapUtil getInstance(MapView mapView) {
        synchronized (sInstanceSync) {
            if (mapUtil == null) {
                mapUtil = new MapUtil(mapView);
            }
            return mapUtil;
        }
    }

    /**
     * 创建面覆盖物
     *
     * @param points 点集
     * @return 面要素
     */
    public static Polygon getPolygon(List<Point> points) {
        Polygon polygon = null;
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
        }
        return polygon;
    }

    public static Envelope getbestEnvelope(Envelope envelope){
        Envelope bestEnvelope = new Envelope();
        double xMax = envelope.getXMax();
        double xMin = envelope.getXMin();
        double yMax = envelope.getYMax();
        double yMin = envelope.getYMin();
        double xdifference=Math.abs(xMax-xMin);
        double ydifference=Math.abs(yMax-yMin);
        double maxlenth=xdifference>ydifference?xdifference:ydifference;



        return bestEnvelope;
    }

    /**
     * 根据中心点和距离生成缓冲区
     *
     * @param point
     * @param distance
     * @return Graphic
     */
    public static Graphic setDistanceGraphicsLayer(Point point, String distance) {

        SpatialReference sprf = SpatialReference.create(SpatialReference.WKID_WGS84);
        Unit unit = sprf.getUnit();
        double meter = Double.parseDouble(distance);
        if (unit.getUnitType() == Unit.UnitType.ANGULAR) {
            meter = Double.parseDouble(distance) / 111319.5;
        } else {
            unit = Unit.create(LinearUnit.Code.METER);
        }
        Polygon pg = null;
        Graphic gp = null;
        if (point != null) {
            pg = GeometryEngine.buffer(point, sprf, meter, unit); //null表示采用地图单位
            SimpleFillSymbol sfs = new SimpleFillSymbol(Color.RED);//面样式对象
            sfs.setAlpha(50);//设置透明度
            gp = new Graphic(pg, sfs);
//            distanceGraphicsLayer.addGraphic(gp);//添加到图层中
//            mapView.setExtent(pg);
        }
        return gp;
    }


    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
//    public static void goToGaode(Context context, String poiname, String lat, String lon, String dev, String style) {
//        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
//                .append("海淀区既有建筑物信息实时动态管理系统");
//        if (!TextUtils.isEmpty(poiname)) {
//            stringBuffer.append("&poiname=").append(poiname);
//        }
//        stringBuffer.append("&lat=").append(lat)
//                .append("&lon=").append(lon)
//                .append("&dev=").append(dev)
//                .append("&style=").append(style);
//
//        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
//        intent.setPackage("com.autonavi.minimap");
//        context.startActivity(intent);
//    }

    /**
     * 启动百度app进行导航
     *
     * @param context
     * @param lat     纬度
     * @param lon     经度
     */
    public static void goToBaidu(Context context, String lat, String lon) {
        Intent intent = null;
        try {
            intent = Intent.getIntent("intent://map/direction?" +
                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:" + lat + "," + lon + "|name:我的目的地" +        //终点
                    "&mode=driving&" +          //导航路线方式
                    "region=北京" +           //
                    "&src=海淀区既有建筑物信息实时动态管理系统#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
