package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.StartPoint;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-02
 */

public class PlanActivity extends BaseActivity {
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.relative_select_address)
    RelativeLayout relativeSelectAddress;
    @BindView(R.id.lv_plan)
    ListView lvPlan;
    @BindView(R.id.tv_clear_plan)
    TextView tvClearPlan;
    @BindView(R.id.img_change)
    ImageView imgChange;
    private PointBean ptpoint;
    private ChannelEvent channelEvent;
    private StartPoint startPoint;
    private EndPoint endPoint;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_plan);
        String channel = channelEvent.getChannel();
        if (channel.equals("route")) {
            setToolbarTitle("路线规划");
            setRightImageBtnText("搜索");
            if (startPoint != null)
                start.setText(startPoint.getName());
            if (endPoint != null)
                end.setText(endPoint.getName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(StartPoint.class);
        EventBus.getDefault().removeStickyEvent(EndPoint.class);
        EventBus.getDefault().removeStickyEvent(IsStart.class);
    }

    /**
     * 获取定位点
     *
     * @param pointBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetPoint(PointBean pointBean) {
        ptpoint = pointBean;
    }

    /**
     * 获取模块信息
     *
     * @param channelEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetChannel(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }

    /**
     * 获取起始点信息
     *
     * @param startPoint
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetStartpoint(StartPoint startPoint) {
        this.startPoint = startPoint;
        if (start != null)
            start.setText(startPoint.getName());
    }

    /**
     * 获取终止点坐标
     *
     * @param endPoint
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetEndpoint(EndPoint endPoint) {
        this.endPoint = endPoint;
        if (end != null)
            end.setText(endPoint.getName());
    }


    @OnClick({R.id.start, R.id.end, R.id.tv_clear_plan,R.id.img_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_change:
                if (endPoint==null){
                    ShowToast("请先选择终点");
                    return;
                }
                end.setText(startPoint.getName());
                start.setText(endPoint.getName());
                StartPoint startPoint = new StartPoint();
                startPoint.setName(this.startPoint.getName());
                startPoint.setX(this.startPoint.getX());
                startPoint.setY(this.startPoint.getY());
                this.startPoint.setName(endPoint.getName());
                this.startPoint.setX(endPoint.getX());
                this.startPoint.setY(endPoint.getY());
                endPoint.setName(startPoint.getName());
                endPoint.setX(startPoint.getX());
                endPoint.setY(startPoint.getY());
                break;
            case R.id.start:
            case R.id.end:
                IsStart isStart = new IsStart();
                if (view.getId() == R.id.start) isStart.setIsstart(true);
                if (view.getId() == R.id.end) isStart.setIsstart(false);
                EventBus.getDefault().postSticky(isStart);
                EventBus.getDefault().postSticky(new ChannelEvent("route"));
                skip(AroundActivity.class, false);
                break;
            case R.id.tv_clear_plan:
                break;
        }
    }

}
