package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.SearchResultAdpter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.callback.SearchAdpaterCancelBack;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.utils.SharedUtil;

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

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_collect);
        setToolbarTitle("收藏夹");
        setToolbarRightVisible(false);
        setData();
    }

    private void setData() {
        List<SearchBean.PoisBean> data = (List<SearchBean.PoisBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data.size() != 0 && data != null) {
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
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    SearchAdpterCallBack callBack = new SearchAdpterCallBack() {
        @Override
        public void aroundclick(SearchBean.PoisBean bean) {

        }

        @Override
        public void routeclick(SearchBean.PoisBean bean) {

        }

        @Override
        public void itemclick(SearchBean.PoisBean bean) {
            Bundle bundle = new Bundle();
            bundle.putString("key", "point");
            bundle.putSerializable("data", bean);
            skip(MapActivity.class, bundle, false);
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
}
