package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

//import com.gangbeng.tiandituhb.adpter.AroundLVAdapter;
import com.gangbeng.tiandituhb.application.MyApplication;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.SortGridViewAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.widget.SourcePanel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-07-30
 */

public class AroundActivity extends BaseActivity {

    @BindView(R.id.source_around)
    SourcePanel sourceAround;
    @BindView(R.id.lv_around)
    ListView lvAround;

    private SortGridViewAdapter gridViewAdapter;
//    private SearchRecord searchRecord;
//    private SearchRecordDao searchRecordDao;
//    private AroundLVAdapter aroundLVAdapter;

    private int[] sortImgs = new int[]{R.mipmap.icon_cfood, R.mipmap.icon_wfood, R.mipmap.icon_coffee, R.mipmap.icon_starthotail
            , R.mipmap.icon_hotail, R.mipmap.icon_car, R.mipmap.icon_ktv, R.mipmap.icon_relax
            , R.mipmap.icon_park, R.mipmap.icon_tourist, R.mipmap.icon_center, R.mipmap.icon_shop
            , R.mipmap.icon_football, R.mipmap.icon_hospital, R.mipmap.icon_school, R.mipmap.icon_science};
    private String[] sortStrs = new String[]{"中餐馆", "西餐馆", "咖啡馆", "星级酒店", "连锁酒店", "汽车服务", "娱乐场所", "休闲场所"
            , "公园广场", "风景名胜", "购物中心", "超市", "体育场馆", "医院", "学校", "科技场馆"};

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_around);
        setToolbarVisibility(true);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        String key = bundleExtra.getString("key");
        if (key.equals("search")) {
            setRightImageBtnText("取消");
            showEditText();
        } else {
            setToolbarRightVisible(false);
            setToolbarTitle("以当前位置为中心");
        }
        gridViewAdapter = new SortGridViewAdapter(this, sortImgs, sortStrs);
        sourceAround.setAdapter(gridViewAdapter);
        sourceAround.setOnItemClickListener(sourceclick);
//        searchRecord = new SearchRecord();
//        searchRecordDao = MyApplication.getInstance().getDaoSession().getSearchRecordDao();
        getRecord();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    AdapterView.OnItemClickListener sourceclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String sortStr = sortStrs[position];
//            addRecord(sortStr);
        }
    };

//    private void addRecord(String sortStr) {
//        List<SearchRecord> searchRecords = searchRecordDao.loadAll();
//        if (searchRecords.size() > 0) {
//            for (SearchRecord record : searchRecords) {
//                if (record.getName().equals(sortStr)) {
//                    searchRecordDao.delete(record);
//                }
//            }
//        }
//        searchRecord.setName(sortStr);
//        searchRecordDao.insert(searchRecord);
//        if (aroundLVAdapter != null) {
//            aroundLVAdapter.addData(searchRecord);
//        }else {
//            searchRecords.add(searchRecord);
//            aroundLVAdapter=new AroundLVAdapter(this,searchRecords);
//        }
//
//    }

    private void getRecord() {
//        List<SearchRecord> searchRecords = searchRecordDao.loadAll();
//        if (searchRecords.size()>0) aroundLVAdapter=new AroundLVAdapter(this,searchRecords);
    }

}
