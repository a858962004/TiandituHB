package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnPinchListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.MyLogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-09-03
 */

public class ComparisonActivity extends BaseActivity {
    @BindView(R.id.mapview1_comparison)
    MapView mapview1Comparison;
    @BindView(R.id.mapview2_comparison)
    MapView mapview2Comparison;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq, map_lf_text2, map_xzq2;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private LocationDisplayManager ldm, ldm2;
    private boolean isFirstlocal = true;
    private boolean isFirstlocal2 = true;
    private MapExtent extent;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_comparison);
        setToolbarRightVisible(false);
        setToolbarTitle("地图对比");
        setMapView();
//        locationGPS(ldm,mapview1Comparison);
//        locationGPS(ldm2,mapview2Comparison);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
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

        mapview1Comparison.addLayer(mapServiceLayer, 0);
        mapview1Comparison.addLayer(maptextLayer, 1);
        mapview1Comparison.addLayer(map_lf, 2);
        mapview1Comparison.addLayer(map_xzq, 3);
        mapview1Comparison.addLayer(map_lf_text, 4);

        mapview2Comparison.addLayer(mapRSServiceLayer, 0);
        mapview2Comparison.addLayer(mapRStextLayer, 1);
        mapview2Comparison.addLayer(map_lfimg, 2);
        mapview2Comparison.addLayer(map_xzq2, 3);
        mapview2Comparison.addLayer(map_lf_text2, 4);

        mapview1Comparison.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }
        });

        mapview1Comparison.setOnPanListener(new OnPanListener() {
            @Override
            public void prePointerMove(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("prePointerMove");
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointerMove(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("postPointerMove");
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("prePointerUp");
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("postPointerUp");
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }
        });

        mapview1Comparison.setOnPinchListener(new OnPinchListener() {
            @Override
            public void prePointersMove(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersMove(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointersDown(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersDown(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointersUp(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersUp(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview1Comparison.getScale();
                Point center = mapview1Comparison.getCenter();
                mapview2Comparison.zoomToScale(center, scale);
            }
        });

        mapview2Comparison.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }
        });

        mapview2Comparison.setOnPanListener(new OnPanListener() {
            @Override
            public void prePointerMove(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("prePointerMove");
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointerMove(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("postPointerMove");
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("prePointerUp");
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("postPointerUp");
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }
        });

        mapview2Comparison.setOnPinchListener(new OnPinchListener() {
            @Override
            public void prePointersMove(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersMove(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointersDown(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersDown(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void prePointersUp(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }

            @Override
            public void postPointersUp(float v, float v1, float v2, float v3, double v4) {
                double scale = mapview2Comparison.getScale();
                Point center = mapview2Comparison.getCenter();
                mapview1Comparison.zoomToScale(center, scale);
            }
        });

        mapview1Comparison.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                    mapview1Comparison.zoomToScale(extent.getCenter(), extent.getScale());
                    mapview2Comparison.zoomToScale(extent.getCenter(), extent.getScale());
                }
            }
        });
        mapview2Comparison.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lfimg) && status == STATUS.LAYER_LOADED) {
                    mapview1Comparison.zoomToScale(extent.getCenter(), extent.getScale());
                    mapview2Comparison.zoomToScale(extent.getCenter(), extent.getScale());
                }
            }
        });
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
//                    if (mapView == mapview2Comparison)
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
