package com.gangbeng.tiandituhb.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.GroupAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.presenter.ShareGroupPresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;
import com.gangbeng.tiandituhb.widget.MapScaleView;
import com.gangbeng.tiandituhb.widget.MapZoomView;
import com.github.library.bubbleview.BubbleTextView;

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
 * @date 2018-11-20
 */

public class GroupActivity extends BaseActivity implements NewBaseView {
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
    @BindView(R.id.bottom_group)
    RelativeLayout bottomGroup;

    private TianDiTuLFServiceLayer map_lf_text, map_lf, map_lfimg, map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer, mapRStextLayer, mapRSServiceLayer;
    private GraphicsLayer drawPointLayer,popupLayer;
    private LocationDisplayManager ldm;
    private Point lacation;
    private PictureMarkerSymbol markerSymbolblue, markerSymbolyellow;
    private boolean isFirstlocal = true;
    private GroupAdapter adapter;
    private String mcommend = "";
    private String createloginname;
    private NewBasePresenter presenter;
    private UserEvent user;
    private AlertDialog mdialog;
    private List<Map<String,String>>data=new ArrayList<>();
    private List<Map<String,String>>mdata=new ArrayList<>();
    private static GroupActivity activity;
    private boolean first=true;
    private Map<String, String> choosedata;

    public static GroupActivity getInstence(){
        return activity;
    }


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_group);
        setToolbarTitle("我的组队");
        setToolbarRightVisible(false);
        activity=this;
        setMapview();
        locationGPS();
        presenter = new ShareGroupPresenter(this);
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        Map<String,Object>parameter=new HashMap<>();
        parameter.put("loginname",user.getLoginname());
        presenter.setRequest(parameter,PubConst.LABLE_GETSHAREGROUP);
    }

    private void setListView() {
        UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
        List<Map<String, String>> listdata = new ArrayList<>();
        if (createloginname.equals(user.getLoginname())){
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(createloginname)){
                    Map<String, String> m = new HashMap<>();
                    m.put("loginname",loginname);
                    m.put("username", "我");
                    m.put("leader", "0");
                    m.put("x",x);
                    m.put("y",y);
                    listdata.add(m);
                }
            }
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(createloginname))continue;
                Map<String, String> map2 = new HashMap<>();
                map2.put("loginname",loginname);
                map2.put("username", username);
                map2.put("leader", "1");
                map2.put("x",x);
                map2.put("y",y);
                listdata.add(map2);
            }
        }else {
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(user.getLoginname())){
                    Map<String, String> m = new HashMap<>();
                    m.put("loginname",loginname);
                    m.put("username", "我");
                    m.put("leader", "1");
                    m.put("x",x);
                    m.put("y",y);
                    listdata.add(m);
                }
            }
            for (Map<String, String> map : data) {
                String loginname = map.get("loginname");
                String username = map.get("username");
                String leader="1";
                String x = map.get("x");
                String y = map.get("y");
                if (loginname.equals(user.getLoginname()))continue;
                if (loginname.equals(createloginname)){
                    leader="0";
                }
                Map<String, String> m = new HashMap<>();
                m.put("loginname",loginname);
                m.put("username", username);
                m.put("leader", leader);
                m.put("x",x);
                m.put("y",y);
                listdata.add(m);
            }
        }
        mdata=listdata;
        adapter = new GroupAdapter(this, listdata, adaptercallback, false);
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
        popupLayer = new GraphicsLayer();

        mapGroup.setMaxScale(500);
        mapGroup.addLayer(mapServiceLayer, 0);
        mapGroup.addLayer(maptextLayer, 1);
        mapGroup.addLayer(mapRSServiceLayer, 2);
        mapGroup.addLayer(mapRStextLayer, 3);

        mapGroup.addLayer(map_lf, 4);
        mapGroup.addLayer(map_lfimg, 5);
        mapGroup.addLayer(map_lf_text, 6);
        mapGroup.addLayer(map_xzq, 7);
        mapGroup.addLayer(drawPointLayer, 8);
        mapGroup.addLayer(popupLayer,9);

        mapRSServiceLayer.setVisible(false);
        mapRStextLayer.setVisible(false);
        map_lfimg.setVisible(false);
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_dingwei02);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 90, 90);
        markerSymbolblue = new PictureMarkerSymbol(drawable1);
        markerSymbolblue.setOffsetY(drawable1.getIntrinsicHeight() / 2);
//
        Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_dingwei07);
        Drawable drawable3 = DensityUtil.zoomDrawable(drawable2, 90, 90);
        markerSymbolyellow = new PictureMarkerSymbol(drawable3);
        markerSymbolyellow.setOffsetY(drawable3.getIntrinsicHeight() / 2);

        mapGroup.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {
                mapviewscaleGroup.refreshScaleView(mapGroup.getScale());
                refreshGroupLocation();
            }
        });
        mapzoomGroup.setMapView(mapGroup);
    }

    public void refreshGroupLocation() {
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("loginname",user.getLoginname());
        presenter.setRequest(parameter, PubConst.LABLE_ZOOMGETGROUP);
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


    @OnClick({R.id.change_group, R.id.location_group,R.id.tv_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_group:
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
            case R.id.location_group:
                mapGroup.zoomToScale(lacation, 50000);
                break;
            case R.id.tv_set:
                Bundle bundle = new Bundle();
                bundle.putString("commend",mcommend);
//                bundle.putSerializable("data", (Serializable) mdata);
                bundle.putString("createloginname",createloginname);
                skip(GroupSetActivity.class,bundle,false);
                break;
        }
    }


    GroupAdapter.GroupClick adaptercallback = new GroupAdapter.GroupClick() {
        @Override
        public void addCallBack() {
            // 从API11开始android推荐使用android.content.ClipboardManager
            // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mcommend);
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
            choosedata=data;
            setChoosePopup(data);
            String x = data.get("x");
            String y = data.get("y");
            mapGroup.zoomToScale(new Point(Double.valueOf(x),Double.valueOf(y)),mapGroup.getScale());
        }
    };

    ShowDialog.CreateDialogCallBack callBack = new ShowDialog.CreateDialogCallBack() {

        @Override
        public void addGroup(AlertDialog dialog, String commend) {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("loginname", user.getLoginname());
            parameter.put("groupid", commend);
            presenter.setRequest(parameter, PubConst.LABLE_ADDGROUP);
            mdialog=dialog;
            mcommend=commend;
        }

        @Override
        public void createGroup(AlertDialog dialog) {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("loginname", user.getLoginname());
            presenter.setRequest(parameter, PubConst.LABLE_CREATEGROUP);
            mdialog=dialog;
        }
    };

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String lable, String title, String msg, boolean flag) {
        switch (lable) {
            case PubConst.LABLE_ADDGROUP:
            case PubConst.LABLE_CREATEGROUP:
            case PubConst.LABLE_GETGROUPLOCATION:
                showProcessDialog(title, msg, flag);
                break;
        }
    }

    @Override
    public void canelLoadingDialog(String lable) {
        switch (lable) {
            case PubConst.LABLE_ADDGROUP:
            case PubConst.LABLE_CREATEGROUP:
            case PubConst.LABLE_GETGROUPLOCATION:
                dismissProcessDialog();
                break;
        }
    }

    @Override
    public void setData(Object data, String lable) {
        SoapObject soapObject = (SoapObject) data;
        switch (lable) {
            case PubConst.LABLE_CREATEGROUP:
                String result = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result.equals("ok")) {
                    showMsg("创建成功");
                    mdialog.dismiss();
                    tvCommend.setText("队伍口令 " + okString);
                    tvCommend.setVisibility(View.VISIBLE);
                    bottomGroup.setVisibility(View.VISIBLE);
                    Map<String, Object> parameter = new HashMap<>();
                    parameter.put("loginname", user.getLoginname());
                    parameter.put("username", user.getUsername());
                    parameter.put("x", String.valueOf(lacation.getX()));
                    parameter.put("y", String.valueOf(lacation.getY()));
                    parameter.put("state", "1");
                    presenter.setRequest(parameter,PubConst.LABLE_START_SHARE);
                    Contant.ins().setLocalState(true);
                } else {
                    showMsg("创建失败");
                }
                break;
            case PubConst.LABLE_ADDGROUP:
                String result2 = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason2 = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString2 = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result2.equals("ok")) {
                    showMsg("加入成功");
                    mdialog.dismiss();
                    tvCommend.setText("队伍口令 " + mcommend);
                    tvCommend.setVisibility(View.VISIBLE);
                    Map<String, Object> parameter = new HashMap<>();
                    parameter.put("loginname", user.getLoginname());
                    parameter.put("username", user.getUsername());
                    parameter.put("x", String.valueOf(lacation.getX()));
                    parameter.put("y", String.valueOf(lacation.getY()));
                    parameter.put("state", "1");
                    presenter.setRequest(parameter,PubConst.LABLE_START_SHARE);
                    Contant.ins().setLocalState(true);
                } else {
                    showMsg("未找到该组队");
                }
                break;
            case PubConst.LABLE_GETSHAREGROUP:
                String result5 = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason5 = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString5 = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result5.equals("ok")) {
                    mcommend=okString5.substring(0,okString5.lastIndexOf(";"));
                    createloginname=okString5.substring(okString5.lastIndexOf(";")+1,okString5.length());
                    tvCommend.setText("队伍口令 " + mcommend);
                    tvCommend.setVisibility(View.VISIBLE);
                    Map<String,Object>parameter=new HashMap<>();
                    parameter.put("loginname",user.getLoginname());
                    presenter.setRequest(parameter,PubConst.LABLE_GETGROUPLOCATION);
                } else {
                    ShowDialog.showCreateGroup(GroupActivity.this, callBack);
                }
                break;
            case PubConst.LABLE_GETGROUPLOCATION:
                    if (!soapObject.toString().equals("anyType{}")) {
                        drawPointLayer.removeAll();
                        List<Point>points=new ArrayList<>();
                        List<SoapObject> newestLocation = RequestUtil.getObjectValue(soapObject, "NewestLocation");
                        for (SoapObject object : newestLocation) {
                            String id = RequestUtil.getSoapObjectValue(object, "ID");
                            String loginname = RequestUtil.getSoapObjectValue(object, "loginname");
                            String username = RequestUtil.getSoapObjectValue(object, "username");
                            String x = RequestUtil.getSoapObjectValue(object, "x");
                            String y = RequestUtil.getSoapObjectValue(object, "y");
                            Point point = new Point(Double.valueOf(x), Double.valueOf(y));
                            points.add(point);
                            Map<String,String>map=new HashMap<>();
                            map.put("loginname",loginname);
                            map.put("username",username);
                            map.put("x", x);
                            map.put("y", y);
                            this.data.add(map);
                            String state = RequestUtil.getSoapObjectValue(object, "state");
                            String updateTime = RequestUtil.getSoapObjectValue(object, "updateTime");
                            addDrawPoint(loginname, id, username, x, y, state, updateTime);
                        }
                        bottomGroup.setVisibility(View.VISIBLE);
                        recyclerGroup.setVisibility(View.VISIBLE);
                        setListView();
                        setPopup();
                        if (points.size()>1){
                            Polygon polygon = MapUtil.getPolygon(points);
                            Envelope envelope=new Envelope();
                            polygon.queryEnvelope(envelope);
                            mapGroup.setExtent(envelope);
                            try {
                                Thread.sleep(300);
                                mapGroup.zoomout();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
//                        setCallout(mdata.get(0));
                    }
                break;
            case PubConst.LABLE_START_SHARE:
                String result6 = RequestUtil.getSoapObjectValue(soapObject, "result");
                String errReason6 = RequestUtil.getSoapObjectValue(soapObject, "errReason");
                String okString6 = RequestUtil.getSoapObjectValue(soapObject, "okString");
                if (result6.equals("ok")) {
                    Map<String,Object>parameter=new HashMap<>();
                    parameter.put("loginname",user.getLoginname());
                    presenter.setRequest(parameter,PubConst.LABLE_GETSHAREGROUP);
                } else {
                    showMsg(okString6);
                }
                break;
            case PubConst.LABLE_ZOOMGETGROUP:
                if (!soapObject.toString().equals("anyType{}")) {
                    drawPointLayer.removeAll();
                    this.data.clear();
                    List<SoapObject> newestLocation = RequestUtil.getObjectValue(soapObject, "NewestLocation");
                    for (SoapObject object : newestLocation) {
                        String id = RequestUtil.getSoapObjectValue(object, "ID");
                        String loginname = RequestUtil.getSoapObjectValue(object, "loginname");
                        String username = RequestUtil.getSoapObjectValue(object, "username");
                        String x = RequestUtil.getSoapObjectValue(object, "x");
                        String y = RequestUtil.getSoapObjectValue(object, "y");
                        Map<String,String>map=new HashMap<>();
                        map.put("loginname",loginname);
                        map.put("username",username);
                        map.put("x", x);
                        map.put("y", y);
                        this.data.add(map);
                        String state = RequestUtil.getSoapObjectValue(object, "state");
                        String updateTime = RequestUtil.getSoapObjectValue(object, "updateTime");
                        addDrawPoint(loginname, id, username, x, y, state, updateTime);
                    }
                    bottomGroup.setVisibility(View.VISIBLE);
                    recyclerGroup.setVisibility(View.VISIBLE);
                    setListView();
                    setPopup();
                    if (choosedata!=null){
                        boolean haschoosedata=false;
                        for (int i = 0; i < this.mdata.size(); i++) {
                            Map<String, String> map = this.mdata.get(i);
                            String loginname = map.get("loginname");
                            if (choosedata.get("loginname").equals(loginname)){
                                haschoosedata=true;
                                adapter.setSelectItem(i+1);
                                setChoosePopup(map);
                            }
                        }
                        if (!haschoosedata){
                            choosedata=null;
                            adapter.setSelectItem(-1);
                        }
                    }
                }else {
                    ShowDialog.showAttention(GroupActivity.this, "请注意", "对不起，您已被队长移出该组队！", new ShowDialog.DialogCallBack() {
                        @Override
                        public void dialogSure(DialogInterface dialog) {
                            finish();
                            dialog.dismiss();
                        }

                        @Override
                        public void dialogCancle(DialogInterface dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
                }

                break;
        }

    }

    private void setPopup() {
        popupLayer.removeAll();
        for (Map<String, String> map : mdata) {
            String username = map.get("username");
            String loginname = map.get("loginname");
            String x = map.get("x");
            String y = map.get("y");
            addPopupGraphic(loginname, username, x, y);
        }
    }
    private void setChoosePopup(Map<String,String>data){
        String chooseloginname = data.get("loginname");
        int[] popupLayerGraphicIDs = popupLayer.getGraphicIDs();
        int[] drawPointLayerGraphicIDs = drawPointLayer.getGraphicIDs();
        for (int popupLayerGraphicID : popupLayerGraphicIDs) {
            Graphic graphic = popupLayer.getGraphic(popupLayerGraphicID);
            String loginname = String.valueOf(graphic.getAttributeValue("loginname"));
            String username = String.valueOf(graphic.getAttributeValue("username"));
            String x = String.valueOf(graphic.getAttributeValue("x"));
            String y = String.valueOf(graphic.getAttributeValue("y"));
            if (chooseloginname.equals(loginname)){
                popupLayer.removeGraphic(popupLayerGraphicID);
                addPopupGraphic(loginname, username, x, y);
                break;
            }
        }
        for (int drawPointLayerGraphicID : drawPointLayerGraphicIDs) {
            Graphic graphic = drawPointLayer.getGraphic(drawPointLayerGraphicID);
            String loginname = String.valueOf(graphic.getAttributeValue("loginname"));
            String id = String.valueOf(graphic.getAttributeValue("id"));
            String username = String.valueOf(graphic.getAttributeValue("username"));
            String x = String.valueOf(graphic.getAttributeValue("x"));
            String y = String.valueOf(graphic.getAttributeValue("y"));
            String state = String.valueOf(graphic.getAttributeValue("state"));
            String updateTime = String.valueOf(graphic.getAttributeValue("updateTime"));
            if (chooseloginname.equals(loginname)){
                drawPointLayer.removeGraphic(drawPointLayerGraphicID);
                addDrawPoint(loginname, id, username, x, y, state, updateTime);
                break;
            }
        }

        int[] drawPointLayerGraphicIDs1 = drawPointLayer.getGraphicIDs();
        drawPointLayer.clearSelection();
        for (int drawPointLayerGraphicID : drawPointLayerGraphicIDs1) {
            Graphic graphic1 = drawPointLayer.getGraphic(drawPointLayerGraphicID);
            String loginname1 = String.valueOf(graphic1.getAttributeValue("loginname"));
            if (loginname1.equals(data.get("loginname"))){
                drawPointLayer.setSelectedGraphics(new int[]{drawPointLayerGraphicID},true);
            }
        }

    }

    private void addDrawPoint(String loginname, String id, String username, String x, String y, String state, String updateTime) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id", id);
        parameter.put("loginname", loginname);
        parameter.put("username", username);
        parameter.put("x", x);
        parameter.put("y", y);
        parameter.put("state", state);
        parameter.put("updateTime", updateTime);
        PictureMarkerSymbol symbol = loginname.equals(createloginname) ? markerSymbolyellow : markerSymbolblue;
        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
        final Graphic graphic = new Graphic(point, symbol, parameter);
        drawPointLayer.addGraphic(graphic);

    }

    private void addPopupGraphic(String loginname, String username, String x, String y) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_popup, null);
        BubbleTextView textView=inflate.findViewById(R.id.popup_text);
        textView.setText(username);
        Bitmap viewbitmap = DensityUtil.convertViewToBitmap(inflate);
        Drawable drawable = new BitmapDrawable(viewbitmap);
        PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 90, 90);
        symbol.setOffsetY(drawable1.getIntrinsicHeight()*3/2);
        Point point = new Point(Double.valueOf(x), Double.valueOf(y));
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("loginname", loginname);
        parameter.put("username", username);
        parameter.put("x", x);
        parameter.put("y", y);
        Graphic graphic = new Graphic(point, symbol,parameter);
        popupLayer.addGraphic(graphic);
    }
}
