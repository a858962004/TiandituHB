package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.view.View;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseFragment;

/**
 * Created by Administrator on 2018/7/28.
 */

public class PeripheralQueryFragment extends BaseFragment {
    private static PeripheralQueryFragment fragment;

    public static PeripheralQueryFragment newInstance() {
        if (fragment == null) {
            fragment = new PeripheralQueryFragment();
        }
        return fragment;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_query;
    }
}
