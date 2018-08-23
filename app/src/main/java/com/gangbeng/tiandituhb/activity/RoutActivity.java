package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.ViewpagerFragmentAdapter;
import com.gangbeng.tiandituhb.adpter.viewholder.FragmentHolder;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.fragment.BusFragment;
import com.gangbeng.tiandituhb.fragment.CarFragment;
import com.gangbeng.tiandituhb.gaodenaviutil.Gps;
import com.gangbeng.tiandituhb.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class RoutActivity extends BaseActivity {
    @BindView(R.id.img_bus)
    ImageView imgBus;
    @BindView(R.id.img_car)
    ImageView imgCar;
    @BindView(R.id.routtoolbar_leftImgBtn)
    ImageButton toolbarLeftImgBtn;
    @BindView(R.id.custom_rout)
    CustomViewPager customRout;

    private List<FragmentHolder> holders = new ArrayList<>();
    private ViewpagerFragmentAdapter adapter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_rout);
        setToolbarVisibility(false);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        List<Gps> points = (List<Gps>) bundleExtra.getSerializable("data");
        FragmentHolder holder = new FragmentHolder();
        holder.setTitle("公交");
        holder.setFragment(BusFragment.newInstance(points,points.get(0).getName(),points.get(1).getName()));
        holders.add(holder);
        FragmentHolder holder2 = new FragmentHolder();
        holder2.setTitle("驾车");
        holder2.setFragment(CarFragment.newInstance(points));
        holders.add(holder2);
        adapter = new ViewpagerFragmentAdapter(getSupportFragmentManager(), holders);
        customRout.setOffscreenPageLimit(0);
        customRout.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_bus, R.id.img_car, R.id.routtoolbar_leftImgBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bus:
                imgBus.setImageResource(R.mipmap.icon_bus_press);
                imgCar.setImageResource(R.mipmap.icon_car_normal);
                customRout.setCurrentItem(0);
                break;
            case R.id.img_car:
                imgBus.setImageResource(R.mipmap.icon_bus_normal);
                imgCar.setImageResource(R.mipmap.icon_car_press);
                customRout.setCurrentItem(1);
                break;
            case R.id.routtoolbar_leftImgBtn:
                finish();
                break;
        }
    }
}
