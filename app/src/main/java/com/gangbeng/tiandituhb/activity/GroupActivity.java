package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.GroupAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.recycler_group)
    RecyclerView recyclerGroup;
    @BindView(R.id.tv_commend)
    TextView tvCommend;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer;
    private LocationDisplayManager ldm;
    private Point lacation;
    private PictureMarkerSymbol markerSymbolblue, markerSymbolgred;
    private boolean isFirstlocal = true;
    private GroupAdapter adapter;
    private String commend = "123456";

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_group);
        setToolbarTitle("我的组队");
        setToolbarRightVisible(false);
        setMapview();
        locationGPS();
        setListView();
        tvCommend.setText("队伍口令 "+commend);
    }

    private void setListView() {
        UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getUsername());
        map.put("leader", "0");
        data.add(map);
        for (int i = 0; i < 5; i++) {
            Map<String, String> map2 = new HashMap<>();
            map2.put("name", "成员" + i);
            map2.put("leader", "1");
            data.add(map2);
        }
        adapter = new GroupAdapter(this, data, callback, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerGroup.setLayoutManager(linearLayoutManager);
        recyclerGroup.setAdapter(adapter);
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


    @OnClick({R.id.change_group, R.id.location_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_group:
                if (map_lfimg.isVisible()) {
                    map_lfimg.setVisible(false);
//                    map_lfimg_text.setVisible(false);
                    map_lf.setVisible(true);
                    map_lf_text.setVisible(true);
                    mapRSServiceLayer.setVisible(false);
                    mapRStextLayer.setVisible(false);
                    mapServiceLayer.setVisible(true);
                    maptextLayer.setVisible(true);
                } else {
                    map_lfimg.setVisible(true);
//                    map_lfimg_text.setVisible(true);
                    map_lf.setVisible(false);
                    map_lf_text.setVisible(false);
                    mapRSServiceLayer.setVisible(true);
                    mapRStextLayer.setVisible(true);
                    mapServiceLayer.setVisible(false);
                    maptextLayer.setVisible(false);
                }
                break;
            case R.id.location_group:
                mapGroup.zoomToScale(lacation, 50000);
                break;
        }
    }

    GroupAdapter.GroupClick callback = new GroupAdapter.GroupClick() {
        @Override
        public void addCallBack() {
            // 从API11开始android推荐使用android.content.ClipboardManager
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(commend);
            ShowDialog.showAttention(GroupActivity.this, "组队口令已复制", "队伍口令已复制到剪切板中，可粘贴发送给好友！", new ShowDialog.DialogCallBack() {
                @Override
                public void dialogSure(DialogInterface dialog) {
                    dialog.dismiss();
                }

                @Override
                public void dialogCancle(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }

        @Override
        public void removeCallBack() {

        }

        @Override
        public void clickCallBack(int position, Map<String, String> data) {

        }
    };
}
