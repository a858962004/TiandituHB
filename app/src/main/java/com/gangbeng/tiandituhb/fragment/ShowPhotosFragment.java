package com.gangbeng.tiandituhb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.base.BaseFragment;

import butterknife.BindView;

/**
 * @author zhanghao
 * @date 2018-07-13
 */

public class ShowPhotosFragment extends BaseFragment {
    @BindView(R.id.img_showphotos)
    ImageView imgShowphotos;
    private String picurl;

    public static ShowPhotosFragment newInstance(String picurl) {
        Bundle args = new Bundle();
        args.putString("picurl", picurl);
        ShowPhotosFragment fragment = new ShowPhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        Glide.with(this)
                .load(picurl)
                .into(imgShowphotos);
        imgShowphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_showphotos;
    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            picurl = bundle.getString("picurl");
        }
    }


}
