package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.Util;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-01
 */

public class MapActivity extends BaseActivity {
    @BindView(R.id.id_map)
    MapView idMap;
    @BindView(R.id.img_collect)
    ImageView imgCollect;
    @BindView(R.id.img_collect2)
    ImageView imgCollect2;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    @BindView(R.id.tv_around)
    TextView tvAround;
    @BindView(R.id.tv_route)
    TextView tvRoute;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg_text, map_lfimg;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer pointlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent,choosePoint;
    private SearchBean.PoisBean bean;
    private String key;
    private boolean isFirstlocal=true;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_map);
        setToolbarTitle("查询结果");
        setToolbarRightVisible(false);
        setMapView();
        locationGPS();
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        key = bundleExtra.getString("key");
        if (key.equals("point")) {
            bean = (SearchBean.PoisBean) bundleExtra.getSerializable("data");
            setbottom(bean);
        }else if (key.equals("addPoint")){
            setToolbarTitle("添加信息点");
            setRightImageBtnText("完成");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_choosepoint);
            Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 130, 130);
            final PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
            idMap.setOnSingleTapListener(new OnSingleTapListener() {
                @Override
                public void onSingleTap(float v, float v1) {
                    pointlayer.removeAll();
                    Point point = idMap.toMapPoint(v, v1);
                    choosePoint=point;
                    Graphic graphic = new Graphic(point, picSymbol);
                    pointlayer.addGraphic(graphic);
                }
            });
        }
    }

    @Override
    protected void setRightClickListen() {
        if (choosePoint == null) {
            ShowToast("请先选择位置");
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("point",choosePoint);
            skip(PointBackActivity.class,bundle,false);
        }
    }

    private void setbottom(SearchBean.PoisBean bean) {
        pointlayer.removeAll();
        rlBottom.setVisibility(View.VISIBLE);
        Point point = zoom2bean(bean);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_maplocation_red);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 130, 130);
        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
        Graphic g = new  Graphic(point, picSymbol);
        pointlayer.addGraphic(g);
        tvName.setText(bean.getName());
        tvAddress.setText(bean.getAddress());
        if (Util.isCollect(bean)) {
            imgCollect.setVisibility(View.GONE);
            imgCollect2.setVisibility(View.VISIBLE);
        } else {
            imgCollect.setVisibility(View.VISIBLE);
            imgCollect2.setVisibility(View.GONE);
        }
    }

    @NonNull
    private Point zoom2bean(SearchBean.PoisBean bean) {
        String lonlat = bean.getLonlat();
        String x=lonlat.substring(0,lonlat.indexOf(" "));
        String y=lonlat.substring(lonlat.indexOf(" "),lonlat.length());
        Point point = new Point();
        point.setX(Double.valueOf(x));
        point.setY(Double.valueOf(y));
        idMap.zoomToScale(point, 5000);
        return point;
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
        pointlayer=new GraphicsLayer();

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_lfimg_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        idMap.addLayer(mapServiceLayer, 0);
        idMap.addLayer(maptextLayer, 1);
        idMap.addLayer(mapRSServiceLayer, 2);
        idMap.addLayer(mapRStextLayer, 3);

        idMap.addLayer(map_lf, 4);
        idMap.addLayer(map_lf_text, 5);
        idMap.addLayer(map_lfimg, 6);
        idMap.addLayer(map_lfimg_text, 7);
        idMap.addLayer(pointlayer,8);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        map_lfimg_text.setVisible(false);
        idMap.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (map_lfimg_text == o && status == STATUS.LAYER_LOADED) {
                    if (key.equals("point")) zoom2bean(bean);
                }
            }
        });

    }

    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = idMap.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    ptCurrent = new Point(location.getLongitude(), location.getLatitude());
                    if (isFirstlocal) {
                        idMap.zoomToScale(ptCurrent, 50000);

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

    @OnClick({R.id.img_collect, R.id.img_collect2, R.id.rl_item, R.id.tv_around, R.id.tv_route})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_collect:
                imgCollect.setVisibility(View.GONE);
                imgCollect2.setVisibility(View.VISIBLE);
                Util.setCollect(bean);
                break;
            case R.id.img_collect2:
                imgCollect.setVisibility(View.VISIBLE);
                imgCollect2.setVisibility(View.GONE);
                Util.cancelCollect(bean);
                break;
            case R.id.rl_item:
                zoom2bean(bean);
                break;
            case R.id.tv_around:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EventBus.getDefault().postSticky(bean);
                EventBus.getDefault().postSticky(new ChannelEvent("around"));
                skip(AroundActivity.class,true);
                break;
            case R.id.tv_route:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EndPoint endPoint = new EndPoint();
                endPoint.setName(bean.getName());
                String lonlat = bean.getLonlat();
                String x=lonlat.substring(0,lonlat.indexOf(" "));
                String y=lonlat.substring(lonlat.indexOf(" "),lonlat.length());
                endPoint.setX(x);
                endPoint.setY(y);
                EventBus.getDefault().postSticky(endPoint);
                EventBus.getDefault().postSticky(new ChannelEvent("route"));
                skip(PlanActivity.class,true);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundleExtra = intent.getBundleExtra(PubConst.DATA);
        String key = bundleExtra.getString("key");
        if (key.equals("point")) {
            bean = (SearchBean.PoisBean) bundleExtra.getSerializable("data");
            setbottom(bean);
        }
    }
}
