package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseFragment;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.presenter.DrivePresenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class CarFragment extends BaseFragment implements BaseView {

    private List<Gps> points;
    private BasePresenter presenter;

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
        Gps startgps = points.get(0);
        Gps endgps = points.get(1);
        String postStr = "{\"orig\":\"" + startgps.getWgLon() + "," + startgps.getWgLat() + "\",\"dest\":\""
                + endgps.getWgLon() + "," + endgps.getWgLat() + "\",\"style\":\"0\"} ";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("postStr", postStr);
//        presenter.setRequest(parameter);
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

    @Override
    public void setData(Object data) {

    }
}
