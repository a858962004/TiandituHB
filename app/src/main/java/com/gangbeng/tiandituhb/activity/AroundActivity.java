package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.SortGridViewAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.widget.SourcePanel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-07-30
 */

public class AroundActivity extends BaseActivity {

    @BindView(R.id.source_around)
    SourcePanel sourceAround;
    private SortGridViewAdapter gridViewAdapter;

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_around);
        setToolbarVisibility(true);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        String key = bundleExtra.getString("key");
        if (key.equals("search")){
            setRightImageBtnText("取消");
            showEditText();
        }else {
            setToolbarRightVisible(false);
            setToolbarTitle("以当前位置为中心");
        }

        int[] sortImgs = new int[]{R.mipmap.icon_cfood, R.mipmap.icon_wfood, R.mipmap.icon_coffee, R.mipmap.icon_starthotail
                , R.mipmap.icon_hotail, R.mipmap.icon_car, R.mipmap.icon_ktv, R.mipmap.icon_relax
                , R.mipmap.icon_park, R.mipmap.icon_tourist, R.mipmap.icon_center, R.mipmap.icon_shop
                , R.mipmap.icon_football, R.mipmap.icon_hospital, R.mipmap.icon_school, R.mipmap.icon_science};
        String[] sortStrs = new String[]{"中餐馆","西餐馆","咖啡馆","星级酒店","连锁酒店","汽车服务","娱乐场所","休闲场所"
                ,"公园广场","风景名胜","购物中心","超市","体育场馆","医院","学校","科技场馆"};
        gridViewAdapter = new SortGridViewAdapter(this, sortImgs, sortStrs);
        sourceAround.setAdapter(gridViewAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
