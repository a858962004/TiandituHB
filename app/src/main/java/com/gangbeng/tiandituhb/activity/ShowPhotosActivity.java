package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.ViewpagerFragmentAdapter;
import com.gangbeng.tiandituhb.adpter.viewholder.FragmentHolder;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.fragment.ShowPhotosFragment;
import com.gangbeng.tiandituhb.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-07-13
 */

public class ShowPhotosActivity extends BaseActivity {

    @BindView(R.id.vp_start)
    CustomViewPager vpStart;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    @BindView(R.id.tv_showphotos)
    TextView tvShowphotos;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_start);
        setToolbarVisibility(false);
        llStart.setVisibility(View.VISIBLE);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        List<String> data = (List<String>) bundleExtra.getSerializable("data");
        int position = bundleExtra.getInt("position");
        setFragment(position, data);
    }

    private void setFragment(final int position, final List<String> data) {
        List<FragmentHolder> holders = new ArrayList<>();
        for (String s : data) {
            FragmentHolder holder = new FragmentHolder();
            holder.setFragment(ShowPhotosFragment.newInstance(s));
            holders.add(holder);
        }
        ViewpagerFragmentAdapter adapter = new ViewpagerFragmentAdapter(getSupportFragmentManager(), holders);
        vpStart.setHorizontalScroll(true);
        vpStart.setOffscreenPageLimit(holders.size());
        vpStart.setAdapter(adapter);
        vpStart.setCurrentItem(position);
        tvShowphotos.setText((position+1)+"/"+data.size());
        vpStart.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvShowphotos.setText((position+1)+"/"+data.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
