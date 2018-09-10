package com.gangbeng.tiandituhb.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.AddPhotoAdapter;
import com.gangbeng.tiandituhb.base.BaseActivity;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.gangbeng.tiandituhb.utils.ContentUriUtil;
import com.gangbeng.tiandituhb.utils.Upphoto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhanghao
 * @date 2018-09-10
 */

public class PointBackActivity extends BaseActivity {
    @BindView(R.id.ed_name_feed)
    EditText edNameFeed;
    @BindView(R.id.ed_loc_feed)
    TextView edLocFeed;
    @BindView(R.id.grid_feed)
    GridView gridFeed;
    @BindView(R.id.ed2_loc_feed)
    TextView ed2LocFeed;

    private List<String> uris = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private AddPhotoAdapter askGridAdpter;
    private File photofile;
    private Upphoto mUpphoto;
    private String path;

    @Override
    protected void initView() {
        setToolbarTitle("添加信息点");
        setRightImageBtnText("添加");
        setContentLayout(R.layout.activity_pointfeedback);
        Bundle bundleExtra = getIntent().getBundleExtra(PubConst.DATA);
        Point point = (Point) bundleExtra.getSerializable("point");
        mUpphoto = new Upphoto(this);
        edLocFeed.setText(point.getX()+"");
        ed2LocFeed.setText(point.getY()+"");
        uris.add("0");
        askGridAdpter = new AddPhotoAdapter(this, uris, click);
        gridFeed.setAdapter(askGridAdpter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        }
    }


    AddPhotoAdapter.OnGridViewClick click = new AddPhotoAdapter.OnGridViewClick() {
        @Override
        public void addPhoto() {
            mUpphoto.photo_album();
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
            files.remove(position);
            chageGridByFiles(files);
        }
    };

    /**
     * 根据files修改gridview
     * @param files
     */
    private void chageGridByFiles(List<File> files) {
        List<String> uri = new ArrayList<>();
        uris.clear();
        if (files.size() > 0) {
            for (File file1 : files) {
                uris.add(file1.getPath());
            }
            uri.addAll(uris);
        }
        if (uris.size() < 3) {
            uri.add("0");
        }
        askGridAdpter.setData(uri);
    }

}
