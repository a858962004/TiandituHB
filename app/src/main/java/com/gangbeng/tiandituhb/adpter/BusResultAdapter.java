package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-21
 */

public class BusResultAdapter extends BaseAdapter {
    private Context context;
    private List<BusBean.ResultsBean.LinesBean> data;

    public BusResultAdapter(Context context, List<BusBean.ResultsBean.LinesBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_busrout, null);
            viewHolder.textView = convertView.findViewById(R.id.tv_busitem);
            viewHolder.textView2=convertView.findViewById(R.id.tv2_busitem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String name="";
        String lineName = data.get(position).getLineName();
        String[] split = lineName.split("|");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("|")) {
                split[i]="→";
            }
            if (i!=split.length-1){
                name+=split[i];
            }
        }
        viewHolder.textView.setText(name);
        int stations = 0;
        float distence = 0;
        String length="";
        for (BusBean.ResultsBean.LinesBean.SegmentsBean segmentsBean : data.get(position).getSegments()) {
            if (segmentsBean.getSegmentLine().size() > 0) {
                String segmentStationCount = segmentsBean.getSegmentLine().get(0).getSegmentStationCount();
                if (!segmentStationCount.equals(""))
                    stations += Integer.valueOf(segmentStationCount);
                String segmentDistance = segmentsBean.getSegmentLine().get(0).getSegmentDistance();
                if (!segmentDistance.equals(""))
                    distence += Float.valueOf(segmentDistance);
            }
        }
        if (distence > 1000) {
            String string = String.valueOf(distence / 1000.0);
            String s = Util.saveTwoU(string);
            length = s + " 公里";
        } else {
            length = distence + " 米";
        }
        viewHolder.textView2.setText("总长"+length+";共"+stations+"站");
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView textView2;
    }
}
