package com.gangbeng.tiandituhb.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AddPhotoAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.base.BasePresenter;
import com.gangbeng.tiandituhb.base.BaseView;
import com.gangbeng.tiandituhb.bean.DKHCInfo;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.event.UserEvent;
import com.gangbeng.tiandituhb.presenter.AddDKPresenter;
import com.gangbeng.tiandituhb.presenter.EditDKPresenter;
import com.gangbeng.tiandituhb.utils.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/15.
 */

public class DKCheckActivity extends BaseActivity implements BaseView {

    @BindView(R.id.ed_bianhao)
    EditText edBianhao;
    @BindView(R.id.ed_xianzhuang)
    EditText edXianzhuang;
    @BindView(R.id.ed_dikuai)
    TextView edDikuai;
    @BindView(R.id.ed_sizhi)
    EditText edSizhi;
    @BindView(R.id.ed_wenti)
    EditText edWenti;
    @BindView(R.id.grid_feed)
    GridView gridFeed;
    private static DKCheckActivity activity;
    private List<String> uris = new ArrayList<>();
    private AddPhotoAdapter askGridAdpter;
    private List<Point> points=new ArrayList<>();
    private BasePresenter presenter,editPresenter;
    private UserEvent user;
    private DKHCInfo data;

    public static DKCheckActivity getInstence() {
        return activity;
    }

    @Override
    protected void initView() {
        setContentLayout(R.layout.activity_dkcheck);
        setToolbarTitle("地块核查");
        setRightImageBtnText("保存");
        activity = this;
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        if (bundleExtra != null) {
            setToolbarTitle("地块修改");
            setview(bundleExtra);
        }
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        presenter = new AddDKPresenter(this);
        editPresenter=new EditDKPresenter(this);
        uris.add("0");
        askGridAdpter = new AddPhotoAdapter(this, uris, null);
        gridFeed.setAdapter(askGridAdpter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void setRightClickListen() {
        String bianhao = String.valueOf(edBianhao.getText());
        String xianzhuang = String.valueOf(edXianzhuang.getText());
        String dikuai = String.valueOf(edDikuai.getText());
        String sizhi = String.valueOf(edSizhi.getText());
        String wenti = String.valueOf(edWenti.getText());
        if (bianhao.equals("")) {
            ShowToast("请输入地块编号");
            return;
        }
        if (xianzhuang.equals("")) {
            ShowToast("请输入利用现状");
            return;
        }
        if (dikuai.equals("")) {
            ShowToast("请添加地块信息");
            return;
        }
        if (sizhi.equals("")) {
            ShowToast("请输入四至信息");
            return;
        }
        if (wenti.equals("")) {
            ShowToast("请输入问题描述");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        // HH:mm:ss //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String type = points.size() > 1 ? "polygon" : "point";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", user.getLoginname());
        parameter.put("dkbh", bianhao);
        parameter.put("type", type);
        parameter.put("geomtrystring", dikuai);
        parameter.put("owner", xianzhuang);
        parameter.put("address", sizhi);
        parameter.put("result", wenti);
        parameter.put("checkman", user.getUsername());
        parameter.put("checktime", time);
        if (data==null) presenter.setRequest(parameter);
        if (date!=null) {
            parameter.put("id",data.getID());
            editPresenter.setRequest(parameter);
        }
    }

    @OnClick(R.id.ed_dikuai)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("activity", "地块核查");
        skip(CalculateMapActivity.class, bundle, false);
    }

    public void setPoint(List<Point> points) {
        this.points = points;
        List<Map<String, String>> maps = new ArrayList<>();
        for (Point point : points) {
            Map<String, String> map = new HashMap<>();
            map.put("x", String.valueOf(point.getX()));
            map.put("y", String.valueOf(point.getY()));
            maps.add(map);
        }
        Gson gson = new Gson();
        String string = gson.toJson(maps);
        edDikuai.setText(string);
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
        if (data instanceof SoapObject) {
            SoapObject soapObject = (SoapObject) data;
            String result = RequestUtil.getSoapObjectValue(soapObject, "result");
            String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
            String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
            if (result.equals("ok")) {
                showMsg("添加成功");
                DKListActivity.getInstence().setNetWork();
                if (data!=null) DKDitailActivity.getInstence().finish();
                finish();
            } else {
                showMsg("添加失败：" + errReason);
            }
        }

    }

    public void setview(Bundle bundle) {
        data = (DKHCInfo) bundle.getSerializable("data");
        edBianhao.setText(data.getDKID());
        edXianzhuang.setText(data.getOwner());
        edDikuai.setText(data.getGeometryStr());
        edSizhi.setText(data.getAddress());
        edWenti.setText(data.getResult());
        try {
            JSONArray jsonArray = new JSONArray(data.getGeometryStr());
            for (int i = 0; i < jsonArray.length(); i++) {
                Point point = new Point();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String x = jsonObject.getString("x");
                String y = jsonObject.getString("y");
                point.setX(Double.valueOf(x));
                point.setY(Double.valueOf(y));
                points.add(point);
            }
        } catch (Exception e) {
            return;
        }
    }
}
