package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.view.View;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseFragment;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class CarFragment extends BaseFragment {

    public static CarFragment newInstance() {
        Bundle args = new Bundle();
        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }
}
