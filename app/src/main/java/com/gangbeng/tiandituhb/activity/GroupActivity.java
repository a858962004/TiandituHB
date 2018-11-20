package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-11-20
 */

public class GroupActivity extends BaseActivity {
    @BindView(R.id.map_group)
    MapView mapGroup;
    @BindView(R.id.change_group)
    CardView changeGroup;
    @BindView(R.id.mapviewscale_group)
    MapScaleView mapviewscaleGroup;
    @BindView(R.id.location_group)
    CardView locationGroup;
    @BindView(R.id.mapzoom_group)
    MapZoomView mapzoomGroup;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer;
    private LocationDisplayManager ldm;
    private Point lacation;
    private PictureMarkerSymbol markerSymbolblue,markerSymbolgred;
    private boolean isFirstlocal=true;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_group);
        setToolbarTitle("我的组队");
        setMapview();
        locationGPS();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
//        drawLayer = new GraphicsLayer();

        mapGroup.addLayer(mapServiceLayer, 0);
        mapGroup.addLayer(maptextLayer, 1);
        mapGroup.addLayer(mapRSServiceLayer, 2);
        mapGroup.addLayer(mapRStextLayer, 3);

        mapGroup.addLayer(map_lf, 4);
        mapGroup.addLayer(map_lf_text, 5);
        mapGroup.addLayer(map_lfimg, 6);
        mapGroup.addLayer(map_xzq, 7);

//        mapGroup.addLayer(drawLayer, 8);
        mapGroup.addLayer(drawPointLayer, 8);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
//        mapGroup.setOnSingleTapListener(onSingleTapListener);

//        fillSymbol = new SimpleFillSymbol(Color.RED);
//        fillSymbol.setAlpha(90);
//        lineSymbol = new SimpleLineSymbol(Color.RED, 2, SimpleLineSymbol.STYLE.SOLID);
//
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei02);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 90, 90);
        markerSymbolblue = new PictureMarkerSymbol(drawable1);
        markerSymbolblue.setOffsetY(drawable1.getIntrinsicHeight() / 2);
//
        Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_dingwei04);
        Drawable drawable3 = DensityUtil.zoomDrawable(drawable2, 90, 90);
        markerSymbolgred = new PictureMarkerSymbol(drawable3);
        markerSymbolgred.setOffsetY(drawable3.getIntrinsicHeight() / 2);

        mapGroup.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscaleGroup.refreshScaleView(mapGroup.getScale());
            }
        });
        mapzoomGroup.setMapView(mapGroup);
    }


    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = mapGroup.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lacation = new Point(location.getLongitude(), location.getLatitude());
                    if (isFirstlocal) {
                        mapGroup.zoomToScale(lacation, 50000);
                        mapviewscaleGroup.refreshScaleView(50000);
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


}
