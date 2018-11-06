package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

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
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.gangbeng.tiandituhb.widget.MyDiscreteSeekBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-11-06
 */

public class ShadeActivity extends BaseActivity {
    @BindView(R.id.mapview1_shade)
    MapView mapview1Shade;
    @BindView(R.id.mapview2_shade)
    MapView mapview2Shade;
    @BindView(R.id.seekbar_shade)
    MyDiscreteSeekBar seekbarShade;
    @BindView(R.id.mapviewscale_shade)
    MapScaleView mapviewscaleShade;
    @BindView(R.id.mapzoom_shade)
    MapZoomView mapzoomShade;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq, map_lf_text2, map_xzq2;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private MapExtent extent;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_shade);
        setToolbarTitle("地图卷帘");
        setToolbarRightVisible(false);
        setMapView();
        seekbarShade.setProgress(100);
        seekbarShade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyLogUtil.showLog(progress);
                int screenHeight = DensityUtil.getScreenHeight(ShadeActivity.this);
                int i = screenHeight * (100 - progress) / 100;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); //添加相应的规则
                params.setMargins(0, 0, 0, i);//左上右下
                mapview2Shade.setLayoutParams(params);
                setMapView2Center();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setMapView2Center() {
        Point center = mapview2Shade.getCenter();
        Point screenPoint = mapview2Shade.toScreenPoint(center);
        Point point = mapview1Shade.toMapPoint((float) screenPoint.getX(), (float) screenPoint.getY());
        mapview2Shade.zoomToScale(point, mapview1Shade.getScale());
        mapviewscaleShade.refreshScaleView(mapview1Shade.getScale());
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

        mapview1Shade.addLayer(mapServiceLayer, 0);
        mapview1Shade.addLayer(maptextLayer, 1);
        mapview1Shade.addLayer(map_lf, 2);
        mapview1Shade.addLayer(map_xzq, 3);
        mapview1Shade.addLayer(map_lf_text, 4);

        mapview2Shade.addLayer(mapRSServiceLayer, 0);
        mapview2Shade.addLayer(mapRStextLayer, 1);
        mapview2Shade.addLayer(map_lfimg, 2);
        mapview2Shade.addLayer(map_xzq2, 3);
        mapview2Shade.addLayer(map_lf_text2, 4);
        mapzoomShade.setMapView(mapview1Shade);

        mapview1Shade.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {
                setMapView2Center();
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                setMapView2Center();
            }
        });

        mapview1Shade.setOnPanListener(new OnPanListener() {
            @Override
            public void prePointerMove(float v, float v1, float v2, float v3) {
                setMapView2Center();
            }

            @Override
            public void postPointerMove(float v, float v1, float v2, float v3) {
                setMapView2Center();
            }

            @Override
            public void prePointerUp(float v, float v1, float v2, float v3) {
                setMapView2Center();
            }

            @Override
            public void postPointerUp(float v, float v1, float v2, float v3) {
                MyLogUtil.showLog("postPointerUp");
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setMapView2Center();
            }
        });

        mapview1Shade.setOnPinchListener(new OnPinchListener() {
            @Override
            public void prePointersMove(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }

            @Override
            public void postPointersMove(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }

            @Override
            public void prePointersDown(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }

            @Override
            public void postPointersDown(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }

            @Override
            public void prePointersUp(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }

            @Override
            public void postPointersUp(float v, float v1, float v2, float v3, double v4) {
                setMapView2Center();
            }
        });

        mapview2Shade.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {
                setMapView1Center();
            }

            @Override
            public void postAction(float v, float v1, double v2) {
                setMapView1Center();
            }
        });

        mapview2Shade.setOnPanListener(new OnPanListener() {
            @Override
            public void prePointerMove(float v, float v1, float v2, float v3) {
                setMapView1Center();
            }

            @Override
            public void postPointerMove(float v, float v1, float v2, float v3) {
                setMapView1Center();
            }

            @Override
            public void prePointerUp(float v, float v1, float v2, float v3) {
                setMapView1Center();
            }

            @Override
            public void postPointerUp(float v, float v1, float v2, float v3) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setMapView1Center();
            }
        });

        mapview2Shade.setOnPinchListener(new OnPinchListener() {
            @Override
            public void prePointersMove(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }

            @Override
            public void postPointersMove(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }

            @Override
            public void prePointersDown(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }

            @Override
            public void postPointersDown(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }

            @Override
            public void prePointersUp(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }

            @Override
            public void postPointersUp(float v, float v1, float v2, float v3, double v4) {
                setMapView1Center();
            }
        });

        mapview1Shade.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                    mapview1Shade.zoomToScale(extent.getCenter(), extent.getScale());
                    mapview2Shade.zoomToScale(extent.getCenter(), extent.getScale());
                    mapviewscaleShade.refreshScaleView(extent.getScale());
                }
            }
        });
        mapview2Shade.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lfimg) && status == STATUS.LAYER_LOADED) {
                    mapview1Shade.zoomToScale(extent.getCenter(), extent.getScale());
                    mapview2Shade.zoomToScale(extent.getCenter(), extent.getScale());
                }
            }
        });

    }

    private void setMapView1Center() {
        Point mapcenter = mapview1Shade.getCenter();
        Point screenpoint = mapview1Shade.toScreenPoint(mapcenter);
        Point point1 = mapview2Shade.toMapPoint(screenpoint);
        mapview1Shade.zoomToScale(point1, mapview2Shade.getScale());
        mapviewscaleShade.refreshScaleView(mapview1Shade.getScale());
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
