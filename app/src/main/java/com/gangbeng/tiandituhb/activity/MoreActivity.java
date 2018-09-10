package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.MoreLVAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-08-04
 */

public class MoreActivity extends BaseActivity {
    @BindView(R.id.lv_more)
    ListView lvMore;
    MoreLVAdapter adapter;

    String[] names = new String[]{"收藏夹", "点距测量", "面积测量","绘图板","地图对比","添加信息点","信息反馈"};
    int[] resource = new int[]{R.mipmap.icon_collect, R.mipmap.icon_length, R.mipmap.icon_area,
            R.mipmap.icon_paint,R.mipmap.icon_comparison,R.mipmap.icon_feedback,R.mipmap.icon_feedback};

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_more);
        setToolbarTitle("更多");
        setToolbarRightVisible(false);
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("resource", resource[i]);
            data.add(map);
        }
        adapter = new MoreLVAdapter(this, data);
        lvMore.setAdapter(adapter);
        lvMore.setOnItemClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            switch (names[position]) {
                case "收藏夹":
                    skip(CollectActivity.class, false);
                    break;
                case "点距测量":
                    bundle.putString("activity","点距测量");
                    skip(CalculateMapActivity.class,bundle,false);
                    break;
                case "面积测量":
                    bundle.putString("activity","面积测量");
                    skip(CalculateMapActivity.class,bundle,false);
                    break;
                case "绘图板":
                    skip(PaintActivity.class,false);
                    break;
                case "地图对比":
                    skip(ComparisonActivity.class,false);
                    break;
                case "添加信息点":
                    bundle.putString("key","addPoint");
                    skip(MapActivity.class,bundle,false);
                    break;
                case "信息反馈":
                    skip(FeedBackActivity.class,false);
                    break;
            }
        }
    };
}
