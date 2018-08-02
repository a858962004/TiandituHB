package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AroundLVAdapter;
import com.gangbeng.tiandituhb.adpter.SortGridViewAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.presenter.SearchPresenter;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.widget.MyToolbar;
import com.gangbeng.tiandituhb.widget.SourcePanel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private SortGridViewAdapter gridViewAdapter;
    private AroundLVAdapter aroundLVAdapter;
    private MyToolbar toolbar;
    private BasePresenter presenter;
    private static AroundActivity activity;
    private ChannelEvent channelEvent;

    public static AroundActivity getInstence() {
        return activity;
    }

    private int[] sortImgs = new int[]{R.mipmap.icon_cfood, R.mipmap.icon_wfood, R.mipmap.icon_coffee, R.mipmap.icon_starthotail
            , R.mipmap.icon_hotail, R.mipmap.icon_car, R.mipmap.icon_ktv, R.mipmap.icon_relax
            , R.mipmap.icon_park, R.mipmap.icon_tourist, R.mipmap.icon_center, R.mipmap.icon_shop
            , R.mipmap.icon_football, R.mipmap.icon_hospital, R.mipmap.icon_school, R.mipmap.icon_science};
    private String[] sortStrs = new String[]{"中餐馆", "西餐馆", "咖啡馆", "星级酒店", "连锁酒店", "汽车服务", "娱乐场所", "休闲场所"
            , "公园广场", "风景名胜", "购物中心", "超市", "体育场馆", "医院", "学校", "图书馆"};

    private List<String> data = new ArrayList<>();
    private String key;
    private PointBean ptpoint;
    private String keyword;
    private SearchBean.PoisBean bean;

    @Override
    protected void initView() {
        activity = this;
        setContentLayout(R.layout.activity_around);
        setToolbarVisibility(true);
        toolbar = getToolBar();
        presenter = new SearchPresenter(this);
        key=channelEvent.getChannel();
        setView(channelEvent.getChannel());
        gridViewAdapter = new SortGridViewAdapter(this, sortImgs, sortStrs);
        sourceAround.setAdapter(gridViewAdapter);
        sourceAround.setOnItemClickListener(sourceclick);
        List<String> record = (List<String>) SharedUtil.getSerializeObject("record");
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
    public void onGetPoi(SearchBean.PoisBean bean) {
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
        for (String s : data) {
            if (keyword.equals(s)) {
                data.remove(s);
                break;
            }
        }
        data.add(keyword);
        List<String> adpterdata = new ArrayList<>();
        adpterdata.addAll(data);
        Collections.reverse(adpterdata);
        if (adpterdata == null) return;
        aroundLVAdapter = new AroundLVAdapter(AroundActivity.this, adpterdata);
        lvAround.setAdapter(aroundLVAdapter);
        DensityUtil.getTotalHeightofListView(lvAround);
        tvClear.setVisibility(View.VISIBLE);
        SharedUtil.saveSerializeObject("record", data);
        requestData(keyword);
    }

    AdapterView.OnItemClickListener sourceclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            keyword = sortStrs[position];
            for (String s : data) {
                if (keyword.equals(s)) {
                    data.remove(s);
                    break;
                }
            }
            data.add(keyword);
            List<String> adpterdata = new ArrayList<>();
            adpterdata.addAll(data);
            Collections.reverse(adpterdata);
            aroundLVAdapter = new AroundLVAdapter(AroundActivity.this, adpterdata);
            lvAround.setAdapter(aroundLVAdapter);
            DensityUtil.getTotalHeightofListView(lvAround);
            tvClear.setVisibility(View.VISIBLE);
            SharedUtil.saveSerializeObject("record", data);
            requestData(sortStrs[position]);
        }
    };

    AdapterView.OnItemClickListener listitem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) aroundLVAdapter.getItem(position);
            requestData(item);
        }
    };

    private void requestData(String item) {
        String postStr = "";
        Map<String, String> post = new HashMap<>();
        Gson gson = new Gson();
        if (key.equals("search")||key.equals("route")||key.equals("navi")) {
            post.put("keyWord", item);
            post.put("level", "11");
            post.put("mapBound", "116.04577,39.70307,116.77361,40.09583");
            post.put("queryType", "1");
            post.put("count", "20");
            post.put("startView", "0");
            postStr = gson.toJson(post);
        } else {
            String Lonlat = "";
            if (bean != null) {
                Lonlat = bean.getLonlat().replace(" ", ",");
            } else {
                Lonlat = ptpoint.getX() + "," + ptpoint.getY();
            }
            post.put("keyWord", item);
            post.put("level", "11");
            post.put("mapBound", "116.04577,39.70307,116.77361,40.09583");
            post.put("queryType", "3");
            post.put("pointLonlat", Lonlat);
            post.put("queryRadius", "10000");
            post.put("count", "20");
            post.put("startView", "0");
            postStr = gson.toJson(post);
        }
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("postStr", postStr);
        parameter.put("type", "query");
        presenter.setRequest(parameter);
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
        if (data instanceof SearchBean) {
            SearchBean bean = (SearchBean) data;
            String count = bean.getCount();
            if (count.equals("0") || bean.getPois() == null || bean.getPois().size() == 0) {
                showMsg("未查找到相应数据");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", bean);
            bundle.putString("key", key);
            bundle.putString("keywords", keyword);
            skip(SearchResultActivity.class, bundle, false);
        }
    }

    private void setView(String channel) {
        if (channel.equals("search") || channel.equals("route") || channel.equals("navi")) {
            setRightImageBtnText("搜索");
            showEditText();
        } else {
            String address = "当前位置";
            if (bean != null) address = bean.getName();
            setToolbarRightVisible(false);
            hideEditText();
            setToolbarTitle(address + "周边");
        }
    }
}
