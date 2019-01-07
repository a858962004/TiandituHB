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
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFNewServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.Util;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-01
 */

public class MapActivity extends BaseActivity implements BaseView {
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
    @BindView(R.id.mapviewscale)
    MapScaleView mapviewscale;
    @BindView(R.id.ll_around)
    LinearLayout llAround;
    @BindView(R.id.ll_route)
    LinearLayout llRoute;
    @BindView(R.id.mapzoom)
    MapZoomView mapzoom;
    @BindView(R.id.change_map)
    CardView changeMap;
    @BindView(R.id.location_map)
    CardView locationMap;
    @BindView(R.id.ll_button)
    LinearLayout llButton;

    private TianDiTuLFNewServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer pointlayer;
    private LocationDisplayManager ldm;
    private Point ptCurrent, choosePoint;
    private NewSearchBean.ListBean bean;
    private String key;
    private boolean isFirstlocal = true;
    private BasePresenter presenter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private MapExtent extent;
    private IsStart isStart;
    private boolean isIMG = false;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_map);
        setToolbarTitle("查询结果");
        setToolbarRightVisible(false);
        presenter = new AroundSearchPresenter(this);
        locationGPS();
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        key = bundleExtra.getString("key");
        setMapView();

        if (key.equals("point")) {
            bean = (NewSearchBean.ListBean) bundleExtra.getSerializable("data");
            setbottom(bean);
        } else if (key.equals("addPoint")) {
//            mapviewscale.setVisibility(View.VISIBLE);
//            mapzoom.setVisibility(View.VISIBLE);
//            changeMap.setVisibility(View.VISIBLE);
//            locationMap.setVisibility(View.VISIBLE);
            llButton.setVisibility(View.GONE);
            setToolbarTitle("地图选点");
            setRightImageBtnText("确定");
            Toast.makeText(this, "选择图上一点并按确定按钮", Toast.LENGTH_LONG).show();
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei03);
            Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
            final PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
            picSymbol.setOffsetY(drawable1.getIntrinsicHeight() / 2);
            idMap.setOnSingleTapListener(new OnSingleTapListener() {
                @Override
                public void onSingleTap(float v, float v1) {
                    pointlayer.removeAll();
                    hideBottom();
                    Point point = idMap.toMapPoint(v, v1);
                    int scaleLevel = Util.getScaleLevel(idMap.getScale());
                    String distence = "";
                    switch (scaleLevel) {
                        case 0:
                            distence = "1000";
                            break;
                        case 1:
                            distence = "500";
                            break;
                        case 2:
                            distence = "200";
                            break;
                        case 3:
                            distence = "130";
                            break;
                        case 4:
                            distence = "80";
                            break;
                        case 5:
                            distence = "50";
                            break;
                        case 6:
                            distence = "30";
                            break;
                        case 7:
                            distence = "20";
                            break;
                        case 8:
                            distence = "3";
                            break;
                    }
                    setPointRequest(point, distence);
                }
            });
            idMap.setOnStatusChangedListener(new OnStatusChangedListener() {
                @Override
                public void onStatusChanged(Object o, STATUS status) {
                    if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                        idMap.zoomToScale(extent.getCenter(), extent.getScale());
                        idMap.zoomin();
                        mapviewscale.refreshScaleView(extent.getScale());
                    }
                }
            });

        }
    }

    @Override
    protected void setRightClickListen() {
        if (bean == null) {
            ShowToast("请先选择位置");
        } else {
            AroundActivity.getInstence().finish();
            if (isStart.isstart()) {
                StartPoint startPoint = new StartPoint();
                startPoint.setName(bean.get简称());
                String x = String.valueOf(bean.getX());
                String y = String.valueOf(bean.getY());
                startPoint.setX(x);
                startPoint.setY(y);
                EventBus.getDefault().postSticky(startPoint);
            } else {
                EndPoint endPoint = new EndPoint();
                endPoint.setName(bean.get简称());
                String x = String.valueOf(bean.getX());
                String y = String.valueOf(bean.getY());
                endPoint.setX(x);
                endPoint.setY(y);
                EventBus.getDefault().postSticky(endPoint);
            }
            skip(PlanActivity.class, true);
            finish();
        }
    }

    private void setbottom(NewSearchBean.ListBean bean) {
        pointlayer.removeAll();
        rlBottom.setVisibility(View.VISIBLE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.rl_bottom); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(bean.getX());
        coordinates.add(bean.getY());
        Point point = zoom2bean(coordinates);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei03);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
        PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
        picSymbol.setOffsetY(drawable1.getIntrinsicHeight() / 2);
        Graphic g = new Graphic(point, picSymbol);
        pointlayer.addGraphic(g);
        String name = bean.get简称();
        tvName.setText(name);
        tvName.setMaxLines(3);
        tvAddress.setText(bean.get地址());
        tvAddress.setMaxLines(3);
        if (Util.isCollect(bean)) {
            imgCollect.setVisibility(View.GONE);
            imgCollect2.setVisibility(View.VISIBLE);
        } else {
            imgCollect.setVisibility(View.VISIBLE);
            imgCollect2.setVisibility(View.GONE);
        }
    }

    private void hideBottom() {
        rlBottom.setVisibility(View.GONE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.id_tab_map); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscale.setLayoutParams(params);
    }

    @NonNull
    private Point zoom2bean(List<Double> coordinates) {
        Point point = new Point();
        point.setX(coordinates.get(0));
        point.setY(coordinates.get(1));
        idMap.zoomToScale(point, 500);
        idMap.zoomin();
        if (key.equals("point")) {

        } else if (key.equals("addPoint")) {
            idMap.zoomToScale(point, idMap.getScale());
        }

        return point;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setMapView() {
        if (key.equals("point")) {
            Contant.ins().setNewmaplevel(10);
        } else {
            Contant.ins().setNewmaplevel(Contant.ins().getMaplevel());
        }
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);

        pointlayer = new GraphicsLayer();

        map_lf = new TianDiTuLFNewServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFNewServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
        map_xzq = new TianDiTuLFNewServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);
        map_lfimg = new TianDiTuLFNewServiceLayer(TianDiTuTiledMapServiceType.IMG_C);

        idMap.setMaxScale(500);

        idMap.addLayer(mapServiceLayer, 0);
        idMap.addLayer(maptextLayer, 1);
        idMap.addLayer(mapRSServiceLayer, 2);
        idMap.addLayer(mapRStextLayer, 3);

        idMap.addLayer(map_lf, 4);
        idMap.addLayer(map_lf_text, 5);
        idMap.addLayer(map_lfimg, 6);
        idMap.addLayer(map_xzq, 7);
        idMap.addLayer(pointlayer, 8);
        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        idMap.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (map_xzq == o && status == STATUS.LAYER_LOADED) {
                    List<Double> coordinates = new ArrayList<>();
                    coordinates.add(bean.getX());
                    coordinates.add(bean.getY());
                    if (key.equals("point")) zoom2bean(coordinates);
                }
            }
        });
        idMap.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscale.refreshScaleView(idMap.getScale());
                setLayerVisibale();
                Contant.ins().setNewmaplevel(-1);
                map_xzq.refresh();
                map_lf.refresh();
                map_lf_text.refresh();
                map_lfimg.refresh();
            }
        });
        mapzoom.setMapView(idMap);
        mapviewscale.refreshScaleView(500);
    }

    private void setLayerVisibale() {
        if (isIMG) {
            if (idMap.getScale() > 9027.9993438721) {
                mapRSServiceLayer.setVisible(true);
                mapRStextLayer.setVisible(true);
                map_lfimg.setVisible(false);
//                map_lfimg_text.setVisible(false);
            } else {
                mapRSServiceLayer.setVisible(false);
                mapRStextLayer.setVisible(false);
                map_lfimg.setVisible(true);
//                map_lfimg_text.setVisible(true);
            }
        } else {
            if (idMap.getScale() > 36111.997375488) {
                mapServiceLayer.setVisible(true);
                maptextLayer.setVisible(true);
                map_lf.setVisible(false);
                map_lf_text.setVisible(false);
            } else {
                mapServiceLayer.setVisible(false);
                maptextLayer.setVisible(false);
                map_lf.setVisible(true);
                map_lf_text.setVisible(true);
            }
        }
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

    private void setPointRequest(Point point, String distence) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("maxitems", "20");
        parameter.put("page", "1");
        Graphic graphic = MapUtil.setDistanceGraphicsLayer(point, distence);
        Geometry geometry = graphic.getGeometry();
        Envelope envelope = new Envelope();
        geometry.queryEnvelope(envelope);
        String geo = envelope.getXMin() + "," + envelope.getYMin() + "," + envelope.getXMax() + "," + envelope.getYMax();
        parameter.put("geo", geo);
        parameter.put("where", "1=1");
        presenter.setRequest(parameter);
    }


    @OnClick({R.id.img_collect, R.id.img_collect2, R.id.rl_item, R.id.ll_around, R.id.ll_route, R.id.change_map, R.id.location_map})
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
                List<Double> coordinates = new ArrayList<>();
                coordinates.add(bean.getX());
                coordinates.add(bean.getY());
                zoom2bean(coordinates);
                break;
            case R.id.ll_around:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EventBus.getDefault().postSticky(bean);
                EventBus.getDefault().postSticky(new ChannelEvent("around"));
                skip(AroundActivity.class, true);
                break;
            case R.id.ll_route:
                AroundActivity.getInstence().finish();
                SearchResultActivity.getInstence().finish();
                EndPoint endPoint = new EndPoint();
                String name = bean.get简称();
                endPoint.setName(name);
                endPoint.setX(String.valueOf(bean.getX()));
                endPoint.setY(String.valueOf(bean.getY()));
                EventBus.getDefault().postSticky(endPoint);
                EventBus.getDefault().postSticky(new ChannelEvent("route"));
                skip(PlanActivity.class, true);
                break;
            case R.id.change_map:
                if (isIMG) {
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
                isIMG = !isIMG;
                setLayerVisibale();
                break;
            case R.id.location_map:
                idMap.zoomToScale(ptCurrent, 500);
                idMap.zoomin();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundleExtra = intent.getBundleExtra(PubConst.DATA);
        String key = bundleExtra.getString("key");
        if (key.equals("point")) {
            bean = (NewSearchBean.ListBean) bundleExtra.getSerializable("data");
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

    /**
     * eventbus接收地图位置
     *
     * @param extent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetExtent(MapExtent extent) {
        this.extent = extent;
    }

    /**
     * 选择起始点还是终止点
     *
     * @param isStart
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(IsStart isStart) {
        this.isStart = isStart;
    }

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void setData(Object data) {
        if (data instanceof NewSearchBean) {
            NewSearchBean bean = (NewSearchBean) data;
            if (bean != null) {
                List<NewSearchBean.ListBean> features1 = bean.getList();
                if (features1.size() > 0) {
                    this.bean = features1.get(0);
                    setbottom(this.bean);
                }
            } else {
                this.bean = null;
            }
        }

    }
}
