package com.gangbeng.tiandituhb.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.esri.android.map.MapView;
import com.gangbeng.tiandituhb.R;

/**
 * @author zhanghao
 * @date 2018-10-14
 */

public class MapZoomView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private ImageView zoomin, zoomout;
    private MapView mapView;

    public MapZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_mapzoom, this, true);
        zoomin = view.findViewById(R.id.zoomin);
        zoomout = view.findViewById(R.id.zoomout);
        zoomin.setOnClickListener(this);
        zoomout.setOnClickListener(this);
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoomin:
                mapView.zoomin();
                break;
            case R.id.zoomout:
                mapView.zoomout();
                break;
        }
    }
}
