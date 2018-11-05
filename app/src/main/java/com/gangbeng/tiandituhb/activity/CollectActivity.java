package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.SearchResultAdpter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.callback.SearchAdpaterCancelBack;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.utils.SharedUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-08-04
 */

public class CollectActivity extends BaseActivity {
    @BindView(R.id.lv_collect)
    ListView lvCollect;
    @BindView(R.id.tv_note)
    TextView tvNote;

    private SearchResultAdpter adpter;
    private ChannelEvent channelEvent;
    private IsStart isStart;


    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_collect);
        setToolbarTitle("收藏夹");
        setToolbarRightVisible(false);
        setData();
    }

    private void setData() {
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data != null&&data.size() != 0) {
            tvNote.setVisibility(View.GONE);
            adpter = new SearchResultAdpter(this, data, false);
            lvCollect.setAdapter(adpter);
            adpter.setCallBack(callBack);
            adpter.setCancalBack(cancelBack);
        } else {
            tvNote.setVisibility(View.VISIBLE);
            tvNote.setText("暂无收藏记录");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    SearchAdpterCallBack callBack = new SearchAdpterCallBack() {
        @Override
        public void aroundclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {

        }

        @Override
        public void routeclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {

        }

        @Override
        public void itemclick(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
            if (channelEvent.getChannel().equals("route") || channelEvent.getChannel().equals("navi")) {
                AroundActivity.getInstence().finish();
                if (isStart.isstart()){
                    StartPoint startPoint = new StartPoint();
                    startPoint.setName(bean.getProperties().get名称());
                    String x=String.valueOf(bean.getGeometry().getCoordinates().get(0));
                    String y=String.valueOf(bean.getGeometry().getCoordinates().get(1));
                    startPoint.setX(x);
                    startPoint.setY(y);
                    EventBus.getDefault().postSticky(startPoint);
                }else {
                    EndPoint endPoint = new EndPoint();
                    endPoint.setName(bean.getProperties().get名称());
                    String x=String.valueOf(bean.getGeometry().getCoordinates().get(0));
                    String y=String.valueOf(bean.getGeometry().getCoordinates().get(1));
                    endPoint.setX(x);
                    endPoint.setY(y);
                    EventBus.getDefault().postSticky(endPoint);
                }
                skip(PlanActivity.class,true);
                finish();
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("key", "point");
                bundle.putSerializable("data", bean);
                skip(MapActivity.class, bundle, false);
            }
        }
    };

    SearchAdpaterCancelBack cancelBack = new SearchAdpaterCancelBack() {
        @Override
        public void cancalCollect(int position) {
            adpter.removeData(position);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setData();
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

    /**
     * 选择起始点还是终止点
     * @param isStart
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(IsStart isStart) {
        this.isStart = isStart;
    }
}
