package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AroundLVAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.PointBean;
import com.gangbeng.tiandituhb.event.ChannelEvent;
import com.gangbeng.tiandituhb.event.EndPoint;
import com.gangbeng.tiandituhb.event.IsStart;
import com.gangbeng.tiandituhb.event.StartPoint;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.utils.DensityUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;

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

/**
 * @author zhanghao
 * @date 2018-08-02
 */

public class PlanActivity extends BaseActivity {
    @BindView(R.id.start)
    TextView startView;
    @BindView(R.id.end)
    TextView endView;
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
    private List<String> data = new ArrayList<>();
    private AroundLVAdapter aroundLVAdapter;
    private ArrayList<String> strings;
    private List<Map<String, Object>> record;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_plan);
        String channel = channelEvent.getChannel();
        if (channel.equals("route")) {
            setToolbarTitle("路线规划");
            setRightImageBtnText("搜索");
        } else {
            setToolbarTitle("导航");
            setRightImageBtnText("开始");
        }
        if (startPoint != null)
            startView.setText(startPoint.getName());
        if (endPoint != null)
            endView.setText(endPoint.getName());
        record = (List<Map<String, Object>>) SharedUtil.getSerializeObject("routerecord");
        if (record != null) {
            List<String> strings = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : record) {
                StartPoint start = (StartPoint) stringObjectMap.get("startView");
                EndPoint end = (EndPoint) stringObjectMap.get("endView");
                strings.add(start.getName() + "-" + end.getName());
            }
            data.addAll(strings);
            Collections.reverse(strings);
            aroundLVAdapter = new AroundLVAdapter(this, strings);
            lvPlan.setAdapter(aroundLVAdapter);
            DensityUtil.getTotalHeightofListView(lvPlan);
            tvClearPlan.setVisibility(View.VISIBLE);
        }
        lvPlan.setOnItemClickListener(listitem);
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
        if (startView != null)
            startView.setText(startPoint.getName());
    }

    /**
     * 获取终止点坐标
     *
     * @param endPoint
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetEndpoint(EndPoint endPoint) {
        this.endPoint = endPoint;
        if (endView != null)
            endView.setText(endPoint.getName());
    }

    @Override
    protected void setRightClickListen() {
        if (endPoint == null) {
            ShowToast("请先选择终点");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("startView", startPoint);
        map.put("endView", endPoint);
        if (record == null) record = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : record) {
            StartPoint start = (StartPoint) stringObjectMap.get("startView");
            EndPoint end = (EndPoint) stringObjectMap.get("endView");
            if (start.getName().equals(startPoint.getName()) && end.getName().equals(endPoint.getName())) {
                record.remove(stringObjectMap);
                break;
            }
        }
        record.add(map);
        SharedUtil.saveSerializeObject("routerecord", record);
        List<String> strings = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : record) {
            StartPoint start = (StartPoint) stringObjectMap.get("startView");
            EndPoint end = (EndPoint) stringObjectMap.get("endView");
            strings.add(start.getName() + "-" + end.getName());
        }
        data.addAll(strings);
        Collections.reverse(strings);
        aroundLVAdapter = new AroundLVAdapter(this, strings);
        lvPlan.setAdapter(aroundLVAdapter);
        DensityUtil.getTotalHeightofListView(lvPlan);
        tvClearPlan.setVisibility(View.VISIBLE);
        if (channelEvent.getChannel().equals("navi")){
            Gps startGps = PositionUtil.gps84_To_Gcj02(Double.valueOf(startPoint.getY()), Double.valueOf(startPoint.getX()));
            Gps endGps = PositionUtil.gps84_To_Gcj02(Double.valueOf(endPoint.getY()), Double.valueOf(endPoint.getX()));
            List<Gps>points=new ArrayList<>();
            points.add(startGps);
            points.add(endGps);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) points);
            skip(GPSNaviActivity.class,bundle,false);
        }
        if (channelEvent.getChannel().equals("route")){
            Gps startGps=new Gps(Double.parseDouble(startPoint.getY()),Double.parseDouble(startPoint.getX()));
            Gps endGps =new Gps(Double.parseDouble(endPoint.getY()),Double.parseDouble(endPoint.getX()));
            startGps.setName(startPoint.getName());
            endGps.setName(endPoint.getName());
            List<Gps>points=new ArrayList<>();
            points.add(startGps);
            points.add(endGps);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) points);
            skip(RoutActivity.class,bundle,false);
        }
    }

    @OnClick({R.id.start, R.id.end, R.id.tv_clear_plan, R.id.img_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_change:
                if (endPoint == null) {
                    ShowToast("请先选择终点");
                    return;
                }
                endView.setText(startPoint.getName());
                startView.setText(endPoint.getName());
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
//                EventBus.getDefault().postSticky(new ChannelEvent("route"));
                skip(AroundActivity.class, false);
                break;
            case R.id.tv_clear_plan:
                data.clear();
                record.clear();
                aroundLVAdapter.removeAll();
                DensityUtil.getTotalHeightofListView(lvPlan);
                tvClearPlan.setVisibility(View.GONE);
                SharedUtil.removeData("routerecord");
                break;
        }
    }

    AdapterView.OnItemClickListener listitem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> stringObjectMap = record.get(record.size() - position - 1);
            StartPoint start = (StartPoint) stringObjectMap.get("startView");
            EndPoint end = (EndPoint) stringObjectMap.get("endView");
            if (!start.getName().equals("当前位置")) {
                startPoint = start;
            }else {
                startPoint=new StartPoint();
                startPoint.setX(ptpoint.getX());
                startPoint.setY(ptpoint.getY());
                startPoint.setName("当前位置");
            }
            if (!end.getName().equals("当前位置")) {
                endPoint = end;
            }else {
                endPoint=new EndPoint();
                endPoint.setX(ptpoint.getX());
                endPoint.setY(ptpoint.getY());
                endPoint.setName("当前位置");
            }
            startView.setText(startPoint.getName());
            endView.setText(endPoint.getName());

        }
    };

}
