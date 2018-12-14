package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
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
import com.gangbeng.tiandituhb.utils.ScreenShotUtils;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.gangbeng.tiandituhb.widget.MyToolbar;
import com.gangbeng.tiandituhb.workbox.PathView;
import com.gangbeng.tiandituhb.workbox.PenStrockAndColorSelect;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-09-04
 */

public class PaintActivity extends BaseActivity {
    @BindView(R.id.ll_btn_group)
    LinearLayout llBtnGroup;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.mainRootView)
    RelativeLayout mainRootView;
    @BindView(R.id.strock_color_select)
    PenStrockAndColorSelect penStrockAndColorSelect;
    @BindView(R.id.mapview_paint)
    MapView mapviewPaint;
    @BindView(R.id.change_map)
    CardView changeMap;
    @BindView(R.id.location_map)
    CardView locationMap;
    @BindView(R.id.toolbar_paint)
    MyToolbar toolbarPaint;
    @BindView(R.id.save_paint)
    LinearLayout savePaint;
    @BindView(R.id.quit_paint)
    LinearLayout quitPaint;
    @BindView(R.id.mapviewscale)
    MapScaleView mapviewscale;
    @BindView(R.id.mapzoom)
    MapZoomView mapzoom;

    private PathView pathView = null;
    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent;
    private boolean isFirstlocal = true;
    private MapExtent extent;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_paint);
        setToolbarVisibility(false);
        toolbarPaint.setTitle("绘图板");
        toolbarPaint.setRightImageBtnText("绘制");
        toolbarPaint.setMyToolBarBtnListenter(new MyToolbar.MyToolBarBtnListenter() {
            @Override
            public void ImageRightBtnclick() {
                mapzoom.setVisibility(View.GONE);
                mapviewscale.setVisibility(View.GONE);
                toolbarPaint.setVisibility(View.GONE);
                changeMap.setVisibility(View.GONE);
                locationMap.setVisibility(View.GONE);
                rlBottom.setVisibility(View.VISIBLE);
                mainRootView.setVisibility(View.VISIBLE);
                savePaint.setVisibility(View.VISIBLE);
                quitPaint.setVisibility(View.VISIBLE);
                Bitmap viewBitmap = getViewBitmap(mapviewPaint);
                mapviewPaint.setVisibility(View.GONE);
                pathView = new PathView(getApplicationContext());
                pathView.addBackgroudImage(viewBitmap);
                mainRootView.addView(pathView);
            }

            @Override
            public void ImageLeftBtnclick() {
                finish();
            }

            @Override
            public void searchBtnclick(String content) {

            }
        });
        locationGPS();
        setMapView();
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

        mapviewPaint.setMaxScale(500);
        mapviewPaint.addLayer(mapServiceLayer, 0);
        mapviewPaint.addLayer(maptextLayer, 1);
        mapviewPaint.addLayer(mapRSServiceLayer, 2);
        mapviewPaint.addLayer(mapRStextLayer, 3);

        mapviewPaint.addLayer(map_lf, 4);
        mapviewPaint.addLayer(map_lf_text, 5);
        mapviewPaint.addLayer(map_lfimg, 6);
        mapviewPaint.addLayer(map_xzq, 7);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);

        mapviewPaint.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscale.refreshScaleView(mapviewPaint.getScale());
            }
        });

        mapviewPaint.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                    mapviewPaint.zoomToScale(extent.getCenter(), extent.getScale());
                    mapviewscale.refreshScaleView(extent.getScale());
                }
            }
        });
        mapzoom.setMapView(mapviewPaint);
    }

    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = mapviewPaint.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    ptCurrent = new Point(location.getLongitude(), location.getLatitude());
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

    public void eraser(View view) {
        pathView.useEraser();
    }

    public void stroke(View view) {
        llBtnGroup.setVisibility(View.GONE);
        penStrockAndColorSelect.setVisibility(View.VISIBLE);
        penStrockAndColorSelect.setCURRENT_TYPE(PenStrockAndColorSelect.STROCK_TYPE);
        penStrockAndColorSelect.setCallback(new PenStrockAndColorSelect.ColorSelectorCallback() {
            @Override
            public void onColorSelectCancel(PenStrockAndColorSelect sender) {
                penStrockAndColorSelect.setVisibility(View.GONE);
                llBtnGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onColorSelectChange(PenStrockAndColorSelect sender) {
                int position = sender.getPenPosition();
                penStrockAndColorSelect.setVisibility(View.GONE);
                llBtnGroup.setVisibility(View.VISIBLE);
                pathView.setPaintWidth(position);
            }
        });
    }

    //why  not  refresh? because of Xmode
    public void undo(View view) {
        pathView.undoPath();
    }

    public void color(View view) {
        llBtnGroup.setVisibility(View.GONE);
        penStrockAndColorSelect.setVisibility(View.VISIBLE);
        penStrockAndColorSelect.setCURRENT_TYPE(PenStrockAndColorSelect.COLOR_TYPE);
        penStrockAndColorSelect.setCallback(new PenStrockAndColorSelect.ColorSelectorCallback() {
            @Override
            public void onColorSelectCancel(PenStrockAndColorSelect sender) {
                penStrockAndColorSelect.setVisibility(View.GONE);
                llBtnGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onColorSelectChange(PenStrockAndColorSelect sender) {
                int position = sender.getPenPosition();
                penStrockAndColorSelect.setVisibility(View.GONE);
                llBtnGroup.setVisibility(View.VISIBLE);
                pathView.setPaintColor(position);
            }
        });

    }


    @OnClick({R.id.change_map, R.id.location_map, R.id.save_paint, R.id.quit_paint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_map:
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
            case R.id.location_map:
                mapviewPaint.zoomToScale(ptCurrent, 50000);
                break;
            case R.id.save_paint:
                savePaint.setVisibility(View.GONE);
                quitPaint.setVisibility(View.GONE);
                llBtnGroup.setVisibility(View.GONE);
                boolean flag = ScreenShotUtils.shotBitmap(PaintActivity.this);
                if (flag) {
                    ShowToast("图片保存成功" + Environment.getExternalStorageDirectory()
                            + File.separator + Environment.DIRECTORY_DCIM+File.separator +"tianditulf");
                    savePaint.setVisibility(View.VISIBLE);
                    quitPaint.setVisibility(View.VISIBLE);
                    llBtnGroup.setVisibility(View.VISIBLE);
                } else {
                    savePaint.setVisibility(View.VISIBLE);
                    quitPaint.setVisibility(View.VISIBLE);
                    llBtnGroup.setVisibility(View.VISIBLE);
                    ShowToast("图片保存失败");
                }
                break;
            case R.id.quit_paint:
                finish();
                break;
        }
    }


    private Bitmap getViewBitmap(MapView v) {
        v.clearFocus();
        v.setPressed(false);

        //能画缓存就返回false
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = null;
        while (cacheBitmap == null) {
            cacheBitmap = v.getDrawingMapCache(0, 0, v.getWidth(), v.getHeight());
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
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
