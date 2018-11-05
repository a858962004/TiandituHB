package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AroundLVAdapter;
import com.gangbeng.tiandituhb.adpter.SortGridViewAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.RecordBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.presenter.SearchPresenter;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.MapUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.widget.MyToolbar;
import com.gangbeng.tiandituhb.widget.SourcePanel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.gangbeng.tiandituhb.adpter.AroundLVAdapter;

/**
 * @author zhanghao
 * @date 2018-07-30
 */

public class AroundActivity extends BaseActivity implements BaseView {

    @BindView(R.id.source_around)
    SourcePanel sourceAround;
    @BindView(R.id.lv_around)
    ListView lvAround;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_choosepoint)
    LinearLayout llChoosepoint;
    @BindView(R.id.bt_local)
    Button btLocal;
    @BindView(R.id.bt_collect)
    Button btCollect;
    @BindView(R.id.bt_map)
    Button btMap;

    private SortGridViewAdapter gridViewAdapter;
    private AroundLVAdapter aroundLVAdapter;
    private MyToolbar toolbar;
    private BasePresenter presenter, aroundPresenter;
    private static AroundActivity activity;
    private ChannelEvent channelEvent;
    private String qury;
    private String geo = "";
    private IsStart isStart;

    public static AroundActivity getInstence() {
        return activity;
    }

    private int[] sortImgs = new int[]{R.mipmap.icon_gggl, R.mipmap.icon_zzcy, R.mipmap.icon_jrbx, R.mipmap.icon_jtrs
            , R.mipmap.icon_fclp, R.mipmap.icon_shfw, R.mipmap.icon_xxyl, R.mipmap.icon_lyfw
            , R.mipmap.icon_ylws, R.mipmap.icon_whmt, R.mipmap.icon_qthy};
    private String[] sortStrs = new String[]{"公共管理", "住宿餐饮", "金融保险", "交通运输", "房产楼盘", "生活服务", "休闲娱乐", "旅游服务"
            , "医疗卫生", "文化媒体", "其他行业"};

    private List<RecordBean> data = new ArrayList<>();
    private String key;
    private PointBean ptpoint;
    private String keyword;
    private NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean;

    @Override
    protected void initView() {
        activity = this;
        setContentLayout(R.layout.activity_around);
        setToolbarVisibility(true);
        toolbar = getToolBar();
        presenter = new SearchPresenter(this);
        aroundPresenter = new AroundSearchPresenter(this);
        key = channelEvent.getChannel();
        setView(channelEvent.getChannel());
        gridViewAdapter = new SortGridViewAdapter(this, sortImgs, sortStrs);
        sourceAround.setAdapter(gridViewAdapter);
        sourceAround.setOnItemClickListener(sourceclick);
        List<RecordBean> record = (List<RecordBean>) SharedUtil.getSerializeObject("record");
        if (record != null) {
            data.addAll(record);
            Collections.reverse(record);
            aroundLVAdapter = new AroundLVAdapter(this, record);
            lvAround.setAdapter(aroundLVAdapter);
            DensityUtil.getTotalHeightofListView(lvAround);
            tvClear.setVisibility(View.VISIBLE);
        }
        lvAround.setOnItemClickListener(listitem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 选择起始点还是终止点
     * @param isStart
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(IsStart isStart) {
        this.isStart = isStart;
    }

    /**
     * eventbus接收定位信息
     *
     * @param pointBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(PointBean pointBean) {
        ptpoint = pointBean;
    }

    /**
     * eventbus接收定位信息
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoi(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        this.bean = bean;
    }


    /**
     * eventbus接收channel
     *
     * @param channelEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetChannel(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }


    @Override
    protected void setRightClickListen() {
        String editText = String.valueOf(toolbar.getEditText());
        if (editText.equals("")) {
            showMsg("输入内容为空");
            return;
        }
        keyword = editText;
        for (RecordBean s : data) {
            if (keyword.equals(s.getData())) {
                data.remove(s);
                break;
            }
        }
        RecordBean recordBean = new RecordBean();
        recordBean.setData(keyword);
        recordBean.setInput(true);
        data.add(recordBean);
        List<RecordBean> adpterdata = new ArrayList<>();
        adpterdata.addAll(data);
        Collections.reverse(adpterdata);
        if (adpterdata == null) return;
        aroundLVAdapter = new AroundLVAdapter(AroundActivity.this, adpterdata);
        lvAround.setAdapter(aroundLVAdapter);
        DensityUtil.getTotalHeightofListView(lvAround);
        tvClear.setVisibility(View.VISIBLE);
        SharedUtil.saveSerializeObject("record", data);
        requestData(recordBean);
    }

    AdapterView.OnItemClickListener sourceclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            keyword = sortStrs[position];
            for (RecordBean s : data) {
                if (keyword.equals(s.getData())) {
                    data.remove(s);
                    break;
                }
            }
            RecordBean recordBean = new RecordBean();
            recordBean.setData(keyword);
            recordBean.setInput(false);
            data.add(recordBean);
            List<RecordBean> adpterdata = new ArrayList<>();
            adpterdata.addAll(data);
            Collections.reverse(adpterdata);
            aroundLVAdapter = new AroundLVAdapter(AroundActivity.this, adpterdata);
            lvAround.setAdapter(aroundLVAdapter);
            DensityUtil.getTotalHeightofListView(lvAround);
            tvClear.setVisibility(View.VISIBLE);
            SharedUtil.saveSerializeObject("record", data);
            requestData(recordBean);
        }
    };

    AdapterView.OnItemClickListener listitem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            RecordBean item = (RecordBean) aroundLVAdapter.getItem(position);
            requestData(item);
        }
    };

    private void requestData(RecordBean bean) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("maxitems", "20");
        parameter.put("page", "1");
        if (key.equals("search") || key.equals("route") || key.equals("navi")) {
            if (bean.isInput()) {
                qury = "'名称' like '%" + bean.getData() + "%'";
            } else {
                //分类查询条件
                qury = getQury(bean.getData());
            }
            parameter.put("where", qury);
            presenter.setRequest(parameter);
        } else {
            //周边查询
            Point point = new Point();
            if (this.bean != null) {
                point.setX(this.bean.getGeometry().getCoordinates().get(0));
                point.setY(this.bean.getGeometry().getCoordinates().get(1));
            } else {
                point.setX(Double.parseDouble(ptpoint.getX()));
                point.setY(Double.parseDouble(ptpoint.getY()));
            }
            qury = getQury(bean.getData());//分类查询条件
            Graphic graphic = MapUtil.setDistanceGraphicsLayer(point, "2000");
            Geometry geometry = graphic.getGeometry();
            Envelope envelope = new Envelope();
            geometry.queryEnvelope(envelope);
            geo = envelope.getXMin() + "," + envelope.getYMin() + "," + envelope.getXMax() + "," + envelope.getYMax();
            parameter.put("geo", geo);
            parameter.put("where", qury);
            aroundPresenter.setRequest(parameter);
        }
    }

    @OnClick(R.id.tv_clear)
    public void onViewClicked() {
        data.clear();
        aroundLVAdapter.removeAll();
        DensityUtil.getTotalHeightofListView(lvAround);
        tvClear.setVisibility(View.GONE);
        SharedUtil.removeData("record");
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
            if (bean.getHeader() == null) {
                showMsg("未查找到相应数据");
                return;
            }
            String count = String.valueOf(bean.getHeader().getTotalItemsCount());
            if (count.equals("0") || bean.getContent().getFeatures().getFeatures() == null || bean.getContent().getFeatures().getFeatures().size() == 0) {
                showMsg("未查找到相应数据");
                return;
            }
            List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> features = bean.getContent().getFeatures().getFeatures();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) bean);
            if (!geo.equals("")) {
                bundle.putString("geo", geo);
            }
            bundle.putString("key", key);
            bundle.putString("keywords", qury);
            skip(SearchResultActivity.class, bundle, false);
        }
    }

    private void setView(String channel) {
        if (channel.equals("search") || channel.equals("route") || channel.equals("navi")) {
            setRightImageBtnText("搜索");
            showEditText();
            if (channel.equals("route") || channel.equals("navi")) {
                sourceAround.setVisibility(View.GONE);
                llChoosepoint.setVisibility(View.VISIBLE);
            }
        } else {
            String address = "当前位置";
            if (bean != null) address = bean.getProperties().get名称();
            setToolbarRightVisible(false);
            hideEditText();
            setToolbarTitle(address + "周边");
        }
    }

    /**
     * 分类查询
     *
     * @return
     */
    public String getQury(String data) {
        String qury = "";
        switch (data) {
            case "公共管理":
                qury = "yifl = '01'";
                break;
            case "住宿餐饮":
                qury = "yifl = '06'";
                break;
            case "金融保险":
                qury = "yifl = '03    '";
                break;
            case "交通运输":
                qury = "yifl = '02'";
                break;
            case "房产楼盘":
                qury = "yifl = '04'";
                break;
            case "生活服务":
                qury = "yifl = '05'";
                break;
            case "休闲娱乐":
                qury = "yifl = '07'";
                break;
            case "旅游服务":
                qury = "yifl = '08'";
                break;
            case "医疗卫生":
                qury = "yifl = '09'";
                break;
            case "文化媒体":
                qury = "yifl = '10'";
                break;
            case "其他行业":
                qury = "yifl = '11'";
                break;
        }


        return qury;
    }

    @Override
    protected void setLeftClickListen() {
        finish();
        EventBus.getDefault().removeStickyEvent(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean.class);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().removeStickyEvent(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean.class);
        }
        return false;
    }

    @OnClick({R.id.bt_local, R.id.bt_collect, R.id.bt_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_local:
                if (isStart.isstart()){
                    StartPoint startPoint = new StartPoint();
                    startPoint.setName("当前位置");
                    String x=String.valueOf(ptpoint.getX());
                    String y=String.valueOf(ptpoint.getY());
                    startPoint.setX(x);
                    startPoint.setY(y);
                    EventBus.getDefault().postSticky(startPoint);
                }else {
                    EndPoint endPoint = new EndPoint();
                    endPoint.setName("当前位置");
                    String x=String.valueOf(ptpoint.getX());
                    String y=String.valueOf(ptpoint.getY());
                    endPoint.setX(x);
                    endPoint.setY(y);
                    EventBus.getDefault().postSticky(endPoint);
                }
                skip(PlanActivity.class,true);
                finish();
                break;
            case R.id.bt_collect:
                skip(CollectActivity.class,false);
                break;
            case R.id.bt_map:
                Bundle bundle = new Bundle();
                bundle.putString("key", "addPoint");
                skip(MapActivity.class, bundle, false);
                break;
        }
    }
}
