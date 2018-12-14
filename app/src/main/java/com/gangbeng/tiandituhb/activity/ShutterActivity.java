package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-09-25
 */

public class ShutterActivity extends BaseActivity {

    @BindView(R.id.mapview1_shutter)
    MapView mapview1Shutter;
    @BindView(R.id.mapview2_shutter)
    MapView mapview2Shutter;
    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq, map_lf_text2, map_xzq2;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private LocationDisplayManager ldm, ldm2;
    private boolean isFirstlocal = true;
    private boolean isFirstlocal2 = true;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_shutter);
        setMapView();
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
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);
        map_lf_text2 = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_xzq2 = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);

        mapview1Shutter.setMaxScale(500);
        mapview1Shutter.addLayer(mapServiceLayer, 0);
        mapview1Shutter.addLayer(maptextLayer, 1);
        mapview1Shutter.addLayer(map_lf, 2);
        mapview1Shutter.addLayer(map_xzq, 3);
        mapview1Shutter.addLayer(map_lf_text, 4);

        mapview2Shutter.setMaxScale(500);
        mapview2Shutter.addLayer(mapRSServiceLayer, 0);
        mapview2Shutter.addLayer(mapRStextLayer, 1);
        mapview2Shutter.addLayer(map_lfimg, 2);
        mapview2Shutter.addLayer(map_xzq2, 3);
        mapview2Shutter.addLayer(map_lf_text2, 4);

        Point center2 = mapview2Shutter.getCenter();
        Point point = mapview2Shutter.toScreenPoint(center2);
        Point point1 = mapview1Shutter.toMapPoint(new Float(point.getX()), new Float(point.getY()));


    }



//    private void locationGPS(LocationDisplayManager ldm, final MapView mapView) {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
//        if (ldm == null) {
//            ldm = mapView.getLocationDisplayManager();
//            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
//            ldm.start();
//            ldm.setLocationListener(new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Point ptCurrent = new Point(location.getLongitude(), location.getLatitude());
//                    if (isFirstlocal || isFirstlocal2) {
//                        mapView.zoomToScale(ptCurrent, 50000);
//                    }
//                    if (mapView == mapview1Comparison)
//                        isFirstlocal = false;
//                    if (mapView == mapview2Shutter)
//                        isFirstlocal2 = false;
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            });
//        } else {
//            ldm.resume();
//        }
//    }


}
