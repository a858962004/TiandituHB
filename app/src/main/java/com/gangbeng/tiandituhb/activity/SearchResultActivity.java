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
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.presenter.AroundSearchPresenter;
import com.gangbeng.tiandituhb.presenter.SearchPresenter;
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
    private BasePresenter presenter,aroundpresenter;
    private String key;
    private String keywords;
    private int allpage=0;
    private ChannelEvent channelEvent;
    private IsStart isStart;
    private static SearchResultActivity activity;
    private String geo="";

    public static SearchResultActivity getInstence(){
        return activity;
    }


    @Override
    protected void initView() {
        activity=this;
        setContentLayout(R.layout.activity_searchresult);
        setToolbarTitle("查询结果");
        setToolbarRightVisible(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        setView(bundleExtra);
    }

    private void setView(Bundle bundleExtra) {
        NewSearchBean bean = (NewSearchBean)bundleExtra.getSerializable("data");
        key = bundleExtra.getString("key");
        keywords = bundleExtra.getString("keywords");
        if (bundleExtra.getString("geo") != null) {
            geo=bundleExtra.getString("geo");
        }
        total=Integer.valueOf(bean.getHeader().getTotalItemsCount());
        tvPage.setText("共"+total+"条数据");
        if (total%20==0) {
            allpage=total/20;
        }else {
            allpage=total/20+1;
        }
        presenter = new SearchPresenter(this);
        aroundpresenter=new AroundSearchPresenter(this);
        boolean isroute=true;
        if (channelEvent.getChannel().equals("navi")||channelEvent.getChannel().equals("route")){
            isroute=false;
        }
        adpter=new SearchResultAdpter(this,bean.getContent().getFeatures().getFeatures(),isroute);
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

    /**
     * 当前频道信息
     * @param channelEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }

    /**
     * 选择起始点还是终止点
     * @param isStart
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(IsStart isStart) {
        this.isStart = isStart;
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
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("maxitems","20");
            parameter.put("page",currentpage);
            parameter.put("where",keywords);
            if (key.equals("around") ) {
                //周边搜索
                parameter.put("geo",geo);
                aroundpresenter.setRequest(parameter);
            }else {
                presenter.setRequest(parameter);
            }
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
        if (data instanceof NewSearchBean) {
            NewSearchBean bean=(NewSearchBean)data;
            adpter.addData(bean.getContent().getFeatures().getFeatures());
            refreshLayout.finishLoadMore();
        }
    }

    SearchAdpterCallBack callBack=new SearchAdpterCallBack() {
        @Override
        public void aroundclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
            AroundActivity.getInstence().finish();
            EventBus.getDefault().postSticky(bean);
            EventBus.getDefault().postSticky(new ChannelEvent("around"));
            skip(AroundActivity.class,true);
        }

        @Override
        public void routeclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean, String name) {
            AroundActivity.getInstence().finish();
            EndPoint endPoint = new EndPoint();
            endPoint.setName(name);
            String x=String.valueOf(bean.getGeometry().getCoordinates().get(0));
            String y=String.valueOf(bean.getGeometry().getCoordinates().get(1));
            endPoint.setX(x);
            endPoint.setY(y);
            EventBus.getDefault().postSticky(endPoint);
            EventBus.getDefault().postSticky(new ChannelEvent("route"));
            skip(PlanActivity.class,true);
        }

        @Override
        public void itemclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
            if (!channelEvent.getChannel().equals("navi")&&!channelEvent.getChannel().equals("route")){
                Bundle bundle = new Bundle();
                bundle.putString("key","point");
                bundle.putSerializable("data",bean);
                skip(MapActivity.class,bundle,false);
            }else {
                AroundActivity.getInstence().finish();
                String name="";
                if (!bean.getProperties().get简称().equals("")){
                    name=bean.getProperties().get简称();
                }else {
                    if (!bean.getProperties().get名称().equals("")){
                        name=bean.getProperties().get名称();
                    }else {
                        if (!bean.getProperties().get兴趣点().equals("")){
                            name=bean.getProperties().get兴趣点();
                        }else {
                            if (!bean.getProperties().get描述().equals("")){
                                name=bean.getProperties().get描述();
                            }else {
                                name=bean.getProperties().get备注();
                            }
                        }
                    }
                }
                if (isStart.isstart()){
                    StartPoint startPoint = new StartPoint();
                    startPoint.setName(name);
                    String x=String.valueOf(bean.getGeometry().getCoordinates().get(0));
                    String y=String.valueOf(bean.getGeometry().getCoordinates().get(1));
                    startPoint.setX(x);
                    startPoint.setY(y);
                    EventBus.getDefault().postSticky(startPoint);
                }else {
                    EndPoint endPoint = new EndPoint();
                    endPoint.setName(name);
                    String x=String.valueOf(bean.getGeometry().getCoordinates().get(0));
                    String y=String.valueOf(bean.getGeometry().getCoordinates().get(1));
                    endPoint.setX(x);
                    endPoint.setY(y);
                    EventBus.getDefault().postSticky(endPoint);
                }
                skip(PlanActivity.class,true);
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundleExtra = intent.getBundleExtra(PubConst.DATA);
        setView(bundleExtra);
    }
}
