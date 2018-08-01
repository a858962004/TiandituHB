package com.gangbeng.tiandituhb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.SearchResultAdpter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.presenter.DrivePresenter;
import com.gangbeng.tiandituhb.presenter.SearchPresenter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class SearchResultActivity extends BaseActivity implements BaseView {
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.list_essence)
    ListView listEssence;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int start=0;
    private int currentpage=1;
    private int total=0;
    private PointBean ptpoint;
    private SearchResultAdpter adpter;
    private BasePresenter presenter;
    private String key;
    private String keywords;
    private int allpage=0;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_searchresult);
        setToolbarTitle("查询结果");
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        setView(bundleExtra);
    }

    private void setView(Bundle bundleExtra) {
        SearchBean bean = (SearchBean)bundleExtra.getSerializable("data");
        key = bundleExtra.getString("key");
        keywords = bundleExtra.getString("keywords");
        total=Integer.valueOf(bean.getCount());
        tvPage.setText("共"+total+"条数据");
        if (total%20==0) {
            allpage=total/20;
        }else {
            allpage=total/20+1;
        }
        presenter = new SearchPresenter(this);
//        drivepresenter=new DrivePresenter(this);
        adpter=new SearchResultAdpter(this,bean.getPois());
        adpter.setCallBack(callBack);
        listEssence.setAdapter(adpter);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        refreshLayout.setOnLoadMoreListener(onLoadMoreListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * eventbus接收返回对象
     *
     * @param pointBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(PointBean pointBean) {
        ptpoint = pointBean;
    }

    OnRefreshListener onRefreshListener=new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh();
        }
    };


    OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            if (currentpage==allpage){
                ShowToast("没有更多数据");
                refreshLayout.finishLoadMore();
                return;
            }
            currentpage++;
            start+=20;
            String postStr = "";
            Map<String, String> post = new HashMap<>();
            Gson gson = new Gson();
            if (key.equals("search")) {
                post.put("keyWord", keywords);
                post.put("level", "11");
                post.put("mapBound", "116.04577,39.70307,116.77361,40.09583");
                post.put("queryType", "1");
                post.put("count", "20");
                post.put("start", start+"");
                postStr = gson.toJson(post);
            } else {
                post.put("keyWord", keywords);
                post.put("level", "11");
                post.put("mapBound", "116.04577,39.70307,116.77361,40.09583");
                post.put("queryType", "3");
                post.put("pointLonlat", ptpoint.getX() + "," + ptpoint.getY());
                post.put("queryRadius", "10000");
                post.put("count", "20");
                post.put("start", start+"");
                postStr = gson.toJson(post);
            }
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("postStr", postStr);
            parameter.put("type", "query");
            presenter.setRequest(parameter);
        }
    };

    @Override
    public void showMsg(String msg) {
        return;
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        return;
    }

    @Override
    public void canelLoadingDialog() {
        return;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof SearchBean) {
            SearchBean bean=(SearchBean)data;
            adpter.addData(bean.getPois());
            refreshLayout.finishLoadMore();
        }
    }

    SearchAdpterCallBack callBack=new SearchAdpterCallBack() {
        @Override
        public void aroundclick(SearchBean.PoisBean bean) {
            EventBus.getDefault().postSticky(bean);
            Bundle bundle = new Bundle();
            bundle.putString("key","around");
            bundle.putString("address",bean.getName());
            skip(AroundActivity.class,bundle,false);
        }

        @Override
        public void routeclick(SearchBean.PoisBean bean) {
//            drivepresenter.setRequest(new HashMap<String, Object>());
        }

        @Override
        public void itemclick(SearchBean.PoisBean bean) {
            Bundle bundle = new Bundle();
            bundle.putString("key","point");
            bundle.putSerializable("data",bean);
            skip(MapActivity.class,bundle,false);
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundleExtra = intent.getBundleExtra(PubConst.DATA);
        setView(bundleExtra);
    }
}
