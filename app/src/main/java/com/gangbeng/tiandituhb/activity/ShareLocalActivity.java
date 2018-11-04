package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.gangbeng.tiandituhb.adpter.SearchAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.MapExtent;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.presenter.GetUserPresenter;
import com.gangbeng.tiandituhb.presenter.UploadLocationPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.MyLogUtil;
import com.gangbeng.tiandituhb.utils.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-11-02
 */

public class ShareLocalActivity extends BaseActivity implements NewBaseView, BaseView {
    @BindView(R.id.map_sharelocal)
    MapView mapSharelocal;
    @BindView(R.id.change_sharelocal)
    CardView changeSharelocal;
    @BindView(R.id.mapviewscale_sharelocal)
    MapScaleView mapviewscaleSharelocal;
    @BindView(R.id.location_sharelocal)
    CardView locationSharelocal;
    @BindView(R.id.mapzoom_sharelocal)
    MapZoomView mapzoomSharelocal;
    @BindView(R.id.qtet_searchlocal)
    AutoCompleteTextView qtetSearchlocal;
    @BindView(R.id.bt_searchlocal)
    Button btSearchlocal;
    @BindView(R.id.tv1_local)
    TextView tv1Local;
    @BindView(R.id.tv7_local)
    TextView tv7Local;
    @BindView(R.id.rl1_local)
    RelativeLayout rl1Local;
    @BindView(R.id.tv3_local)
    TextView tv3Local;
    @BindView(R.id.tv4_local)
    TextView tv4Local;
    @BindView(R.id.rl_bottom_local)
    RelativeLayout rlBottomLocal;
    @BindView(R.id.progressBar2_local)
    ProgressBar progressBar2Local;
    @BindView(R.id.progressBar3_local)
    ProgressBar progressBar3Local;
    @BindView(R.id.progressBar1_local)
    ProgressBar progressBar1Local;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer;
    private LocationDisplayManager ldm;
    private Point lacation;
    private MapExtent extent;
    private NewBasePresenter uploadpresenter;
    private BasePresenter presenter, userPresenter;
    private UserEvent user;
    private PictureMarkerSymbol markerSymbolblue;
    private PictureMarkerSymbol markerSymbolgred;
    private String chooseuser = "";
    private Graphic chooseGraphic=null;
    private List<String> usernames = new ArrayList<>();

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_sharelocal);
        setToolbarTitle("位置共享");
        String string = Contant.ins().isLocalState() ? "关闭" : "打开";
        setRightImageBtnText(string);
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        setMapview();
        locationGPS();
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        uploadpresenter = new UploadLocationPresenter(this);
        presenter = new AroundSearchPresenter(this);
        userPresenter = new GetUserPresenter(this);
        getUserLocal();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void setRightClickListen() {
//        Contant.ins().setLocalState(!Contant.ins().isLocalState());
        String state = Contant.ins().isLocalState() ? "0" : "1";
        String label = Contant.ins().isLocalState() ? PubConst.LABLE_CLOSE_SHARE : PubConst.LABLE_START_SHARE;
        setLocal(state, label);
    }

    private void setLocal(String state, String label) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", user.getLoginname());
        parameter.put("username", user.getUsername());
        parameter.put("x", Double.valueOf(lacation.getX()));
        parameter.put("y", Double.valueOf(lacation.getY()));
        parameter.put("state", Integer.valueOf(state));
        uploadpresenter.setRequest(parameter, label);
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

        mapSharelocal.addLayer(mapServiceLayer, 0);
        mapSharelocal.addLayer(maptextLayer, 1);
        mapSharelocal.addLayer(mapRSServiceLayer, 2);
        mapSharelocal.addLayer(mapRStextLayer, 3);

        mapSharelocal.addLayer(map_lf, 4);
        mapSharelocal.addLayer(map_lf_text, 5);
        mapSharelocal.addLayer(map_lfimg, 6);
        mapSharelocal.addLayer(map_xzq, 7);

//        mapSharelocal.addLayer(drawLayer, 8);
        mapSharelocal.addLayer(drawPointLayer, 8);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        mapSharelocal.setOnSingleTapListener(onSingleTapListener);

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

        mapSharelocal.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscaleSharelocal.refreshScaleView(mapSharelocal.getScale());
            }
        });
        mapSharelocal.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (o.equals(map_lf) && status == STATUS.LAYER_LOADED) {
                    mapSharelocal.zoomToScale(extent.getCenter(), extent.getScale());
                    mapviewscaleSharelocal.refreshScaleView(extent.getScale());
                }


            }
        });
        mapzoomSharelocal.setMapView(mapSharelocal);
    }

    private void locationGPS() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 0);
        if (ldm == null) {
            ldm = mapSharelocal.getLocationDisplayManager();
            ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.NAVIGATION);
            ldm.start();
            ldm.setLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lacation = new Point(location.getLongitude(), location.getLatitude());
//                    if (Contant.ins().isLocalState()) setLocal("1", PubConst.LABLE_START_SHARE);
//                    getUserLocal();
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

    /**
     * eventbus接收地图位置
     *
     * @param extent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetExtent(MapExtent extent) {
        this.extent = extent;
    }


    @OnClick({R.id.change_sharelocal, R.id.location_sharelocal, R.id.tv7_local, R.id.bt_searchlocal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_sharelocal:
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
            case R.id.location_sharelocal:
                mapSharelocal.zoomToScale(lacation, 50000);
                break;
            case R.id.tv7_local:
                progressBar1Local.setVisibility(View.VISIBLE);
                progressBar2Local.setVisibility(View.VISIBLE);
                getUserLocal();
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("maxitems", "20");
                parameter.put("page", "1");
                Graphic polygon = MapUtil.setDistanceGraphicsLayer((Point) chooseGraphic.getGeometry(), "100");
                Geometry geometry = polygon.getGeometry();
                Envelope envelope = new Envelope();
                geometry.queryEnvelope(envelope);
                String geo = envelope.getXMin() + "," + envelope.getYMin() + "," + envelope.getXMax() + "," + envelope.getYMax();
                parameter.put("geo", geo);
                parameter.put("where", "1=1");
                presenter.setRequest(parameter);
                break;
            case R.id.bt_searchlocal:
                jianpandelete();
                String username = String.valueOf(qtetSearchlocal.getText());
                searchGraphicByUsername(username);
                break;
        }
    }

    private void searchGraphicByUsername(String username) {
        int[] graphicIDs = drawPointLayer.getGraphicIDs();
        for (int i = 0; i < graphicIDs.length; i++) {
            Graphic graphic = drawPointLayer.getGraphic(graphicIDs[i]);
            if (graphic.getAttributeValue("username").equals(username)) {
                setBottom(graphic);
                return;
            }
        }
        showMsg("为查找到该用户");
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
            progressBar2Local.setVisibility(View.GONE);
            NewSearchBean bean = (NewSearchBean) data;
            NewSearchBean.ContentBean content = bean.getContent();
            if (content != null) {
                NewSearchBean.ContentBean.FeaturesBeanX features = content.getFeatures();
                List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> features1 = features.getFeatures();
                if (features1.size() > 0) {
                    NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean featuresBean = features1.get(0);
                    tv3Local.setText(featuresBean.getProperties().get名称() + "附近");
                }
            } else {
                tv3Local.setText("未查找到位置");
            }
        }
        if (data instanceof SoapObject) {
            progressBar3Local.setVisibility(View.GONE);
            SoapObject object = (SoapObject) data;
            String mobilePhone1 = RequestUtil.getSoapObjectValue(object, "MobilePhone1");
            tv4Local.setText("电话：" + mobilePhone1);
        }

    }

    @Override
    public void setData(Object data, String lable) {
        SoapObject soapObject = (SoapObject) data;
        switch (lable) {
            case PubConst.LABLE_START_SHARE://打开共享回调
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    showMsg("开启成功");
                    setRightImageBtnText("关闭");
                } else {
                    showMsg("开启失败");
                }
                break;
            case PubConst.LABLE_CLOSE_SHARE://关闭共享回调
                String result2 = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason2 = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString2 = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result2.equals("ok")) {
                    showMsg("关闭成功");
                    setRightImageBtnText("打开");
                } else {
                    showMsg("关闭失败");
                }
                break;
            case PubConst.LABLE_GET_SHARE://获取所有位置回调
                progressBar1Local.setVisibility(View.GONE);
                if (!soapObject.toString().equals("anyType{}")) {
                    drawPointLayer.removeAll();
                    List<SoapObject> newestLocation = RequestUtil.getObjectValue(soapObject, "NewestLocation");
                    for (SoapObject object : newestLocation) {
                        String id = RequestUtil.getSoapObjectValue(object, "ID");
                        String loginname = RequestUtil.getSoapObjectValue(object, "loginname");
                        if (loginname.equals(user.getLoginname())) continue;
                        String username = RequestUtil.getSoapObjectValue(object, "username");
                        usernames.add(username);
                        String x = RequestUtil.getSoapObjectValue(object, "x");
                        String y = RequestUtil.getSoapObjectValue(object, "y");
                        String state = RequestUtil.getSoapObjectValue(object, "state");
                        String updateTime = RequestUtil.getSoapObjectValue(object, "updateTime");
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("loginname", loginname);
                        map.put("username", username);
                        map.put("x", x);
                        map.put("y", y);
                        map.put("state", state);
                        map.put("updateTime", updateTime);
                        PictureMarkerSymbol symbol = state.equals("0") ? markerSymbolgred : markerSymbolblue;
                        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
                        Graphic graphic = new Graphic(point, symbol, map);
                        drawPointLayer.addGraphic(graphic);
                        if (chooseuser.equals(loginname)) {
                            mapSharelocal.zoomToScale(point, mapSharelocal.getScale());
                        }
                    }
                    qtetSearchlocal.addTextChangedListener(textWatcher);
                }
                break;
        }

    }

    public void getUserLocal() {
        MyLogUtil.showLog("位置共享");
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("state", "");
        uploadpresenter.setRequest(parameter, PubConst.LABLE_GET_SHARE);
    }

    OnSingleTapListener onSingleTapListener = new OnSingleTapListener() {
        @Override
        public void onSingleTap(float v, float v1) {
            int[] graphicIDs = drawPointLayer.getGraphicIDs(v, v1, 25);
            if (graphicIDs != null && graphicIDs.length > 0) {
                int graphicID = graphicIDs[0];
                Graphic graphic = drawPointLayer.getGraphic(graphicID);
                setBottom(graphic);
            } else {
                chooseuser = "";
                hideBottom();
            }
        }
    };

    private void setBottom(Graphic graphic) {
        rlBottomLocal.setVisibility(View.VISIBLE);
        progressBar2Local.setVisibility(View.VISIBLE);
        progressBar3Local.setVisibility(View.VISIBLE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ABOVE, R.id.rl_bottom_local); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscaleSharelocal.setLayoutParams(params);
        Point point = (Point) graphic.getGeometry();
        mapSharelocal.zoomToScale(point, 5000);
        mapviewscaleSharelocal.refreshScaleView(5000);
        String username = String.valueOf(graphic.getAttributeValue("username"));
        tv1Local.setText(username);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("maxitems", "20");
        parameter.put("page", "1");
        Graphic polygon = MapUtil.setDistanceGraphicsLayer(point, "100");
        Geometry geometry = polygon.getGeometry();
        Envelope envelope = new Envelope();
        geometry.queryEnvelope(envelope);
        String geo = envelope.getXMin() + "," + envelope.getYMin() + "," + envelope.getXMax() + "," + envelope.getYMax();
        parameter.put("geo", geo);
        parameter.put("where", "1=1");
        presenter.setRequest(parameter);
        Map<String, Object> parameter2 = new HashMap<>();
        chooseGraphic=graphic;
        chooseuser = String.valueOf(graphic.getAttributeValue("loginname"));
        parameter2.put("loginname", chooseuser);
        userPresenter.setRequest(parameter2);

    }

    private void hideBottom() {
        rlBottomLocal.setVisibility(View.GONE);
        int i = DensityUtil.dip2px(this, 10);
        int i1 = DensityUtil.dip2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //添加相应的规则
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); //设置控件的位置
        params.setMargins(i1, 0, 0, i);//左上右下
        mapviewscaleSharelocal.setLayoutParams(params);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String search = String.valueOf(qtetSearchlocal.getText());
            List<String> array = new ArrayList<>();
            for (String username : usernames) {
                if (username.contains(search)) array.add(username);
            }
            SearchAdapter<String> adapter = new SearchAdapter<String>(ShareLocalActivity.this, android.R.layout.simple_list_item_1, array, SearchAdapter.ALL);
            qtetSearchlocal.setAdapter(adapter);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
