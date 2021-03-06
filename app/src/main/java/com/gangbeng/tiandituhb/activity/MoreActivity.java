package com.gangbeng.tiandituhb.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.MoreLVAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.NewBasePresenter;
import com.gangbeng.tiandituhb.base.NewBaseView;
import com.gangbeng.tiandituhb.constant.Contant;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.http.RequestUtil;
import com.gangbeng.tiandituhb.presenter.UpdatePresenter;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.ShowDialog;
import com.gangbeng.tiandituhb.utils.Util;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
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

public class MoreActivity extends BaseActivity implements NewBaseView {
    @BindView(R.id.lv_more)
    ListView lvMore;
    MoreLVAdapter adapter;

    public static MoreActivity activity;
    private NewBasePresenter updatepresenter;

    String[] names = new String[]{"登录/注册",
//            "地块核查",
            "添加信息点", "组队共享",
            "收藏夹", "点距测量", "面积测量", "绘图板", "地图对比", "地图卷帘", "信息反馈", "版本更新", "清除缓存", "免责声明"};
    int[] resource = new int[]{R.mipmap.icon_user,
//            R.mipmap.icon_dikuaihecha,
            R.mipmap.icon_tianjiaxinxi, R.mipmap.icon_zudui, R.mipmap.icon_shoucang1,
            R.mipmap.icon_dianju, R.mipmap.icon_mianji, R.mipmap.icon_huitu, R.mipmap.icon_duibi,
            R.mipmap.icon_juanlian, R.mipmap.icon_fankui, R.mipmap.icon_gengxin, R.mipmap.icon_delete, R.mipmap.icon_attention};

    public static MoreActivity instence() {
        return activity;
    }

    @Override
    protected void initView() {
        activity = this;
        setContentLayout(R.layout.activity_more);
        setToolbarTitle("更多");
        setToolbarRightVisible(false);
        updatepresenter = new UpdatePresenter(this);
        setListData();
    }

    public void setListData() {
        UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
        if (user != null) {
            names[0] = "个人中心";
        } else {
            names[0] = "登录/注册";
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("resource", resource[i]);
            data.add(map);
        }
        adapter = new MoreLVAdapter(this, data, Contant.ins().isnewest());
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
            UserEvent user = (UserEvent) SharedUtil.getSerializeObject("user");
            switch (names[position]) {
                case "登录/注册":
                    skip(LoginActivity.class, false);
                    break;
                case "地块核查":
                    if (user != null) {
                        bundle.putString("activity", "地块核查");
                        skip(DKListActivity.class, bundle, false);
                    } else {
                        ShowToast("此功能需登录后使用");
                    }
                    break;
                case "收藏夹":
                    skip(CollectActivity.class, false);
                    break;
                case "点距测量":
                    bundle.putString("activity", "点距测量");
                    skip(CalculateMapActivity.class, bundle, false);
                    break;
                case "面积测量":
                    bundle.putString("activity", "面积测量");
                    skip(CalculateMapActivity.class, bundle, false);
                    break;
                case "绘图板":
                    skip(PaintActivity.class, false);
                    break;
                case "地图对比":
                    skip(ComparisonActivity.class, false);
                    break;
                case "地图卷帘":
                    skip(ShadeActivity.class, false);
                    break;
                case "添加信息点":
                    if (user != null) {
                        bundle.putString("activity", "添加信息点");
                        skip(DKListActivity.class, bundle, false);
                    } else {
                        ShowToast("此功能需登录后使用");
                    }
                    break;
                case "信息反馈":
                    if (user != null) {
                        skip(FeedBackActivity.class, false);
                    } else {
                        ShowToast("此功能需登录后使用");
                    }
                    break;
                case "个人中心":
                    skip(UserActivity.class, false);
                    break;
//                case "位置共享":
//                    if (user != null) {
//                        Contant.ins().setLocalState(!Contant.ins().isLocalState());
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        ShowToast("此功能需登录后使用");
//                    }
//                    break;
                case "版本更新":
                    if (!Contant.ins().isnewest()) {
                        updatepresenter.setRequest(null, PubConst.LABLE_GETNEWVERSION);

//                        ShowDialog.update(MoreActivity.this, Contant.ins().getUpdateUrl());
                    } else {
                        ShowToast("已是最新版本");
                    }
                    break;
                case "组队共享":
                    if (user != null) {
                        skip(GroupActivity.class, false);
                    } else {
                        ShowToast("此功能需登录后使用");
                    }
                    break;
                case "免责声明":
                    skip(ExemptionActivity.class, false);
                    break;
                case "清除缓存":
                    ShowDialog.showAttention(MoreActivity.this, "清除缓存", "建议清除缓存后退出程序重新打开APP", new ShowDialog.DialogCallBack() {
                        @Override
                        public void dialogSure(DialogInterface dialog) {
                            dialog.dismiss();
                            ShowDialog.showDeletProgress(MoreActivity.this);
                        }

                        @Override
                        public void dialogCancle(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public void showMsg(String msg) {
        ShowToast(msg);
    }

    @Override
    public void showLoadingDialog(String lable, String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog(String lable) {
        dismissProcessDialog();
    }

    @Override
    public void setData(Object data, String lable) {
        String result = data.toString();
        if (!result.equals("")) {
            String url = result.substring(result.indexOf("=") + 1, result.indexOf(";"));
            ShowDialog.update(MoreActivity.this, url);
        } else {
            showMsg("下载地址获取失败");
        }

    }
}
