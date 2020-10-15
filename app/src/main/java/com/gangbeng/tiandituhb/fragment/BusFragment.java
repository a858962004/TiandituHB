package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.activity.RoutMapActivity;
import com.gangbeng.tiandituhb.adpter.BusResultAdapter;
import com.gangbeng.tiandituhb.base.BaseFragment;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.bean.NewBusBean;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.gaodenaviutil.PositionUtil;
import com.gangbeng.tiandituhb.presenter.BusPresenter;
import com.gangbeng.tiandituhb.utils.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class BusFragment extends BaseFragment implements BaseView {

    @BindView(R.id.lv_busrout)
    ListView lvBusrout;
    Unbinder unbinder;
    private BasePresenter presenter;
    private List<Gps> points;
    private List<BusBean.ResultsBean.LinesBean> lines;
    private String start,end;

    public static BusFragment newInstance(List<Gps> points,String start,String end) {
        Bundle args = new Bundle();
        args.putSerializable("points", (Serializable) points);
        args.putString("start",start);
        args.putString("end",end);
        BusFragment fragment = new BusFragment();
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
        presenter = new BusPresenter(this);
        Gps startgps = PositionUtil.gps84_To_Gcj02(points.get(0).getWgLat(),points.get(0).getWgLon());
        Gps endgps = PositionUtil.gps84_To_Gcj02(points.get(1).getWgLat(),points.get(1).getWgLon());
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("origin", Util.saveSixU(startgps.getWgLon()+"")+","+Util.saveSixU(startgps.getWgLat()+""));
        parameter.put("destination",Util.saveSixU(endgps.getWgLon()+"")+","+Util.saveSixU(endgps.getWgLat()+""));
        presenter.setRequest(parameter);
//        lvBusrout.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bus;
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
        if (data instanceof NewBusBean) {
            NewBusBean bean = (NewBusBean) data;
            if (bean.getStatus().equals("1")){
                List<NewBusBean.SegmentsBean> segments = bean.getSegments();
                BusResultAdapter adapter=new BusResultAdapter(getActivity(),segments);
                lvBusrout.setAdapter(adapter);
            }else {
                showMsg("请求错误！");
            }
        }
    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            points = (List<Gps>) bundle.getSerializable("points");
            start=bundle.getString("start");
            end=bundle.getString("end");
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

    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BusBean.ResultsBean.LinesBean linesBean = lines.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",linesBean);
            bundle.putString("start",start);
            bundle.putString("end",end);
            skip(RoutMapActivity.class,bundle,false);

        }
    };
}
