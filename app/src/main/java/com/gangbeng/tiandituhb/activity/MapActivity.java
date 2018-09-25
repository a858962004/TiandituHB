package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.Util;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer;
    private GraphicsLayer pointlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent, choosePoint;
    private NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean;
    private String key;
    private boolean isFirstlocal = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            bean = (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean) bundleExtra.getSerializable("data");
            setbottom(bean);
        } else if (key.equals("addPoint")) {
            setToolbarTitle("添加信息点");
            setRightImageBtnText("完成");
            Toast.makeText(this,"选择图上一点并按完成按钮",Toast.LENGTH_LONG).show();
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei03);
            Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
            final PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
            picSymbol.setOffsetY(drawable1.getIntrinsicHeight() / 2);
            idMap.setOnSingleTapListener(new OnSingleTapListener() {
                @Override
                public void onSingleTap(float v, float v1) {
                    pointlayer.removeAll();
                    Point point = idMap.toMapPoint(v, v1);
                    choosePoint = point;
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
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("point", choosePoint);
            skip(PointBackActivity.class, bundle, false);
        }
    }

    private void setbottom(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        pointlayer.removeAll();
        rlBottom.setVisibility(View.VISIBLE);
        Point point = zoom2bean(bean.getGeometry().getCoordinates());
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei03);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
        picSymbol.setOffsetY(drawable1.getIntrinsicHeight() / 2);
        Graphic g = new Graphic(point, picSymbol);
        pointlayer.addGraphic(g);
        tvName.setText(bean.getProperties().get名称());
        tvAddress.setText(bean.getProperties().get地址());
        if (Util.isCollect(bean)) {
            imgCollect.setVisibility(View.GONE);
            imgCollect2.setVisibility(View.VISIBLE);
        } else {
            imgCollect.setVisibility(View.VISIBLE);
            imgCollect2.setVisibility(View.GONE);
        }
    }

    @NonNull
    private Point zoom2bean(List<Double> coordinates) {
        Point point = new Point();
        point.setX(coordinates.get(0));
        point.setY(coordinates.get(1));
        idMap.zoomToScale(point, 5000);
        return point;
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
        pointlayer = new GraphicsLayer();

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_xzq = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);

        idMap.addLayer(mapServiceLayer, 0);
        idMap.addLayer(maptextLayer, 1);
        idMap.addLayer(map_lf, 2);
        idMap.addLayer(map_lf_text, 3);
        idMap.addLayer(map_xzq, 4);
        idMap.addLayer(pointlayer, 5);
        idMap.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (map_xzq == o && status == STATUS.LAYER_LOADED) {
                    if (key.equals("point")) zoom2bean(bean.getGeometry().getCoordinates());
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
                zoom2bean(bean.getGeometry().getCoordinates());
                break;
            case R.id.tv_around:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EventBus.getDefault().postSticky(bean);
                EventBus.getDefault().postSticky(new ChannelEvent("around"));
                skip(AroundActivity.class, true);
                break;
            case R.id.tv_route:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EndPoint endPoint = new EndPoint();
                endPoint.setName(bean.getProperties().get名称());
                endPoint.setX(String.valueOf(bean.getGeometry().getCoordinates().get(0)));
                endPoint.setY(String.valueOf(bean.getGeometry().getCoordinates().get(1)));
                EventBus.getDefault().postSticky(endPoint);
                EventBus.getDefault().postSticky(new ChannelEvent("route"));
                skip(PlanActivity.class, true);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundleExtra = intent.getBundleExtra(PubConst.DATA);
        String key = bundleExtra.getString("key");
        if (key.equals("point")) {
            bean = (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean) bundleExtra.getSerializable("data");
            setbottom(bean);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Map Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
