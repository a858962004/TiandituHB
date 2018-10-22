package com.gangbeng.tiandituhb.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.gangbeng.tiandituhb.presenter.DeletePicPresenter;
import com.gangbeng.tiandituhb.presenter.EditDKPresenter;
import com.gangbeng.tiandituhb.presenter.UploadPhotoPresenter;
import com.gangbeng.tiandituhb.utils.ContentUriUtil;
import com.gangbeng.tiandituhb.utils.RequestUtil;
import com.gangbeng.tiandituhb.utils.SharedUtil;
import com.gangbeng.tiandituhb.utils.Upphoto;
import com.gangbeng.tiandituhb.utils.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.Serializable;
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
 * @author zhanghao
 * @date 2018-09-10
 */

public class PointBackActivity extends BaseActivity implements BaseView {

    @BindView(R.id.ed_name_feed)
    EditText edNameFeed;
    @BindView(R.id.ed_didian_feed)
    TextView edDidianFeed;
    @BindView(R.id.ed_miaoshu_feed)
    EditText edMiaoshuFeed;
    @BindView(R.id.grid_pointfeed)
    GridView gridPointfeed;
    @BindView(R.id.input_popupwindows_camera)
    Button inputPopupwindowsCamera;
    @BindView(R.id.input_popupwindows_Photo)
    Button inputPopupwindowsPhoto;
    @BindView(R.id.input_popupwindows_cancel)
    Button inputPopupwindowsCancel;

    private List<String> uris = new ArrayList<>();
    private static PointBackActivity activity;
    private List<File> files = new ArrayList<>();
    private List<String> removePid = new ArrayList<>();
    private AddPhotoAdapter askGridAdpter;
    private File photofile;
    private Upphoto mUpphoto;
    private String path;
    private List<Point> points;
    private UserEvent user;
    private BasePresenter presenter, editPresenter, photoPresenter,deletePresenter;
    private DKHCInfo data;
    private View takephoteview;
    private File cameraphoto;
    private ArrayList<String> ids=new ArrayList<>();
    private List<String> deleteids=new ArrayList<>();
    private List<String> picurl=new ArrayList<>();
    private List<String> successpic=new ArrayList<>();

    public static PointBackActivity getInstence() {
        return activity;
    }

    @Override
    protected void initView() {
        setToolbarTitle("添加信息点");
        setRightImageBtnText("保存");
        activity = this;
        setContentLayout(R.layout.activity_pointfeedback);
        takephoteview = findViewById(R.id.include_commit_takephote);
        takephoteview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoteview.setVisibility(View.GONE);
            }
        });
        mUpphoto = new Upphoto(this);
        uris.add("0");
        askGridAdpter = new AddPhotoAdapter(this, uris, true, click);
        gridPointfeed.setAdapter(askGridAdpter);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        if (bundleExtra != null) {
            setToolbarTitle("信息点修改");
            setview(bundleExtra);
        }
        user = (UserEvent) SharedUtil.getSerializeObject("user");
        presenter = new AddDKPresenter(this);
        editPresenter = new EditDKPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void setRightClickListen() {
        String name = String.valueOf(edNameFeed.getText());
        String didian = String.valueOf(edDidianFeed.getText());
        String miaoshu = String.valueOf(edMiaoshuFeed.getText());
        if (name.equals("")) {
            ShowToast("请添加地址名称");
            return;
        }
        if (didian.equals("")) {
            ShowToast("请添加地图信息");
            return;
        }
        if (miaoshu.equals("")) {
            ShowToast("请添加地点描述");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        // HH:mm:ss //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("loginname", user.getLoginname());
        parameter.put("dkbh", name);
        parameter.put("type", "point");
        parameter.put("geomtrystring", didian);
        parameter.put("result", miaoshu);
        parameter.put("checkman", user.getUsername());
        parameter.put("checktime", time);
        parameter.put("infostate", "1");
        if (data == null) {
            presenter.setRequest(parameter);
        }
        if (data != null) {
            parameter.put("id", data.getID());
            editPresenter.setRequest(parameter);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PubConst.SHOW_PHOTO_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    path = ContentUriUtil.getPath(this, uri);
                    if (resultCode == RESULT_OK) {
                        photofile = new File(path);
                        files.add(photofile);
                        chageGridByFiles(files);
                    }
                }
                break;
            case PubConst.SHOW_PHOTO:
                if (cameraphoto != null) {
                    files.add(cameraphoto);
                    chageGridByFiles(files);
                }
                break;
        }
    }


    AddPhotoAdapter.OnGridViewClick click = new AddPhotoAdapter.OnGridViewClick() {
        @Override
        public void addPhoto(int position) {
            takephoteview.setVisibility(View.VISIBLE);
        }

        @Override
        public void showPhoto(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) uris);
            bundle.putInt("position", position);
            skip(ShowPhotosActivity.class, bundle, false);
        }

        @Override
        public void onCancel(int position) {
            if (position>picurl.size()-1){
                files.remove(position-picurl.size());
            }else {
                picurl.remove(position);
                deleteids.add(ids.get(position));
                ids.remove(position);
            }
            chageGridByFiles(files);
        }
    };

    /**
     * 根据files修改gridview
     *
     * @param files
     */
    private void chageGridByFiles(List<File> files) {
        List<String> uri = new ArrayList<>();
        uris.clear();
        if (picurl.size()>0){
            for (String s : picurl) {
                uris.add(s);
            }
        }
        if (files != null && files.size() > 0) {
            for (File file1 : files) {
                uris.add(file1.getPath());
            }
        }
        uri.addAll(uris);
        if (uris.size() < 3) {
            uri.add("0");
        }
        askGridAdpter.setData(uri);
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
        edDidianFeed.setText(string);
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
    public void setData(Object bean) {
        if (bean instanceof SoapObject) {
            SoapObject soapObject = (SoapObject) bean;
            String result = RequestUtil.getSoapObjectValue(soapObject, "result");
            String errReason = RequestUtil.getSoapObjectValue(soapObject, "errReason");
            String okString = RequestUtil.getSoapObjectValue(soapObject, "okString");
            String id = "";
            if (photoPresenter == null) {
                if (result.equals("ok")) {
                    showMsg("信息保存成功");
                    if (deleteids.size()>0){
                        deletePresenter=new DeletePicPresenter(this);
                        for (String deleteid : deleteids) {
                            Map<String,Object>parameter=new HashMap<>();
                            parameter.put("picid",deleteid);
                            deletePresenter.setRequest(parameter);
                        }
                    }
                    if (files.size() > 0) {
                        if (data != null) {
                            id = data.getID();
                        } else {
                            id = okString;
                        }
//                        showLoadingDialog("请稍后","正在上传照片",false);
                        uploadPhoto(id);
//                        final String finalId = id;
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }).start();

                    } else {
                        DKListActivity.getInstence().setNetWork("1");
                        if (data != null) DKDitailActivity.getInstence().finish();
                        finish();
                    }
                } else {
                    showMsg("添加失败：" + errReason);
                }
            } else {
                if (result.equals("ok")) {
                    successpic.add(successpic.size()+"");
                    if (successpic.size()==files.size()){
//                        photoPresenter=null;
//                        canelLoadingDialog();
                        showMsg("照片保存成功");
                        DKListActivity.getInstence().setNetWork("1");
                        if (data != null) DKDitailActivity.getInstence().finish();
                        finish();
                    }
                } else {
                    showMsg("照片保存失败");
                    DKListActivity.getInstence().setNetWork("1");
                    if (data != null) DKDitailActivity.getInstence().finish();
                    finish();
                }
            }
        }
    }

    private void uploadPhoto(String okString) {
        for (File file : files) {
            String name = file.getName();
            String path = file.getPath();
            String string = Util.picPathToBase64(path);
            photoPresenter = new UploadPhotoPresenter(this);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("loginname", user.getLoginname());
            parameter.put("dkid", okString);
            parameter.put("fname", name);
            parameter.put("data", string);
            photoPresenter.setRequest(parameter);
        }

    }

    public void setview(Bundle bundle) {
        data = (DKHCInfo) bundle.getSerializable("data");
        ArrayList<String> photo = bundle.getStringArrayList("photo");
         ids = bundle.getStringArrayList("ids");
        if (photo.size() > 0) {
            uris.clear();
            for (String string : photo) {
                uris.add(string);
                picurl.add(string);
            }
            if (uris.size()<3)uris.add("0");
            askGridAdpter.setData(uris);
        }
        edNameFeed.setText(data.getDKID());
        edDidianFeed.setText(data.getGeometryStr());
        edMiaoshuFeed.setText(data.getResult());
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

    @OnClick({R.id.ed_didian_feed, R.id.input_popupwindows_camera, R.id.input_popupwindows_Photo, R.id.input_popupwindows_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_didian_feed:
                Bundle bundle = new Bundle();
                bundle.putString("activity", "添加信息点");
                skip(CalculateMapActivity.class, bundle, false);
                break;
            case R.id.input_popupwindows_camera:
                takephoteview.setVisibility(View.GONE);
                cameraphoto = mUpphoto.photo();
                break;
            case R.id.input_popupwindows_Photo:
                takephoteview.setVisibility(View.GONE);
                mUpphoto.photo_album();
                break;
            case R.id.input_popupwindows_cancel:
                takephoteview.setVisibility(View.GONE);
                break;
        }
    }

}
