package com.gangbeng.tiandituhb.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.activity.GPSNaviActivity;
import com.gangbeng.tiandituhb.base.BaseFragment;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.presenter.DrivePresenter;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuLFServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceLayer;
import com.gangbeng.tiandituhb.tiandituMap.TianDiTuTiledMapServiceType;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class CarFragment extends BaseFragment implements BaseView {

    @BindView(R.id.map_carfragment)
    MapView mapCarfragment;
    @BindView(R.id.tv_navi)
    TextView tvNavi;
    @BindView(R.id.tv_distence)
    TextView tvDistence;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    Unbinder unbinder;
    private List<Gps> points;
    private BasePresenter presenter;
    private TianDiTuLFServiceLayer map_lf_text, map_lf,map_xzq;
    private TianDiTuTiledMapServiceLayer maptextLayer, mapServiceLayer;
    private GraphicsLayer graphicsLayer, pointLayer;

    public static CarFragment newInstance(List<Gps> points) {
        Bundle args = new Bundle();
        args.putSerializable("points", (Serializable) points);
        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        presenter = new DrivePresenter(this);
        setMapView();
        getData();
    }

    private void setMapView() {
        ArcGISRuntime.setClientId("uK0DxqYT0om1UXa9");
        mapServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        maptextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
//        mapRSServiceLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
//        mapRStextLayer = new TianDiTuTiledMapServiceLayer(TianDiTuTiledMapServiceType.CIA_C);
        graphicsLayer = new GraphicsLayer();
        pointLayer = new GraphicsLayer();

        map_lf = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.VEC_C);
        map_lf_text = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.CVA_C);
//        map_lfimg = new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.IMG_C);
        map_xzq=new TianDiTuLFServiceLayer(TianDiTuTiledMapServiceType.XZQ_C);

        mapCarfragment.addLayer(mapServiceLayer, 0);
        mapCarfragment.addLayer(maptextLayer, 1);
//        mapCarfragment.addLayer(mapRSServiceLayer, 2);
//        mapCarfragment.addLayer(mapRStextLayer, 3);

        mapCarfragment.addLayer(map_lf, 2);
        mapCarfragment.addLayer(map_lf_text, 3);
        mapCarfragment.addLayer(map_xzq,4);
//        mapCarfragment.addLayer(map_lfimg, 6);
        mapCarfragment.addLayer(graphicsLayer, 5);
        mapCarfragment.addLayer(pointLayer, 6);

//        mapRSServiceLayer.setVisible(false);
//        mapRStextLayer.setVisible(false);
//        map_lfimg.setVisible(false);
    }

    private void getData() {
        Gps startgps = points.get(0);
        Gps endgps = points.get(1);
        String postStr = "{\"orig\":\"" + startgps.getWgLon() + "," + startgps.getWgLat() + "\",\"dest\":\""
                + endgps.getWgLon() + "," + endgps.getWgLat() + "\",\"style\":\"0\"} ";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("postStr", postStr);
        presenter.setRequest(parameter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            points = (List<Gps>) bundle.getSerializable("points");
        }
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setData(Object data) {
        if (data instanceof DriveRouteBean) {
            DriveRouteBean bean = (DriveRouteBean) data;
            List<DriveRouteBean.StreetLatLonBean> streetLatLon = bean.getStreetLatLon();
            if (streetLatLon.size() > 0) {
                mapCarfragment.setVisibility(View.VISIBLE);
                rlBottom.setVisibility(View.VISIBLE);
                tvNodata.setVisibility(View.GONE);
                Polyline polyline = new Polyline();
                Polyline polyline2 = new Polyline();
                for (int i = 0; i < streetLatLon.size() - 1; i++) {
                    Point start = new Point(streetLatLon.get(i).getX(), streetLatLon.get(i).getY());
                    Point end = new Point(streetLatLon.get(i + 1).getX(), streetLatLon.get(i + 1).getY());
                    Line line = new Line();
                    line.setStart(start);
                    line.setEnd(end);
                    polyline.addSegment(line, false);
                    polyline2.addSegment(line, false);
                }
                SimpleLineSymbol lineSymbol = new SimpleLineSymbol(getActivity().getColor(R.color.rout), 10, SimpleLineSymbol.STYLE.SOLID);
                SimpleLineSymbol lineSymbol2 = new SimpleLineSymbol(Color.BLACK, 3, SimpleLineSymbol.STYLE.DOT);
                Graphic graphic = new Graphic(polyline, lineSymbol);
                Graphic graphic2 = new Graphic(polyline2, lineSymbol2);
                graphicsLayer.addGraphic(graphic);
                graphicsLayer.addGraphic(graphic2);
                Point point = new Point(streetLatLon.get(0).getX(), streetLatLon.get(0).getY());
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_qidian);
                Drawable drawable1 = DensityUtil.zoomDrawable(drawable, 100, 100);
                PictureMarkerSymbol picSymbol = new PictureMarkerSymbol(drawable1);
                picSymbol.setOffsetY(drawable1.getIntrinsicHeight()/2);
                Graphic startgraphic = new Graphic(point, picSymbol);
                Point point2 = new Point(streetLatLon.get(streetLatLon.size() - 1).getX(), streetLatLon.get(streetLatLon.size() - 1).getY());
                Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_zhongdian);
                Drawable drawable21 = DensityUtil.zoomDrawable(drawable2, 100, 100);
                PictureMarkerSymbol picSymbol2 = new PictureMarkerSymbol(drawable21);
                picSymbol2.setOffsetY(drawable1.getIntrinsicHeight()/2);
                Graphic startgraphic2 = new Graphic(point2, picSymbol2);
                pointLayer.addGraphic(startgraphic);
                pointLayer.addGraphic(startgraphic2);
                mapCarfragment.setExtent(polyline);
                String distance = bean.getDistance();
                String duration = bean.getDuration();
                String time = Util.secondToHour(duration);
                tvDistence.setText("全程需" + time + ",共" + distance + "公里");
            } else {
                mapCarfragment.setVisibility(View.GONE);
                rlBottom.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
                showMsg("未搜索到驾车路线");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_navi)
    public void onViewClicked() {
        Gps startGps = PositionUtil.gps84_To_Gcj02(points.get(0).getWgLat(), points.get(0).getWgLon());
        Gps endGps = PositionUtil.gps84_To_Gcj02(points.get(1).getWgLat(), points.get(1).getWgLon());
        List<Gps>points=new ArrayList<>();
        points.add(startGps);
        points.add(endGps);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) points);
        skip(GPSNaviActivity.class,bundle,false);
    }
}
