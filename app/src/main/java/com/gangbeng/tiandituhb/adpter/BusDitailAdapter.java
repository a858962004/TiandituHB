package com.gangbeng.tiandituhb.adpter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.bean.BusBean;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-23
 */

public class BusDitailAdapter extends BaseAdapter {
    private Context context;
    private List<BusBean.ResultsBean.LinesBean.SegmentsBean> data;
    private String start,end;

    public BusDitailAdapter(Context context, List<BusBean.ResultsBean.LinesBean.SegmentsBean> data,String start,String end) {
        this.context = context;
        this.data = data;
        this.start=start;
        this.end=end;
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

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_busditail, null);
            viewHolder.tv1 = convertView.findViewById(R.id.tv1_busitem);
            viewHolder.tv2 = convertView.findViewById(R.id.tv2_busitem);
            viewHolder.tv3 = convertView.findViewById(R.id.tv3_busitem);
            viewHolder.tv4 = convertView.findViewById(R.id.tv4_busitem);
            viewHolder.tv5 = convertView.findViewById(R.id.tv5_busitem);
            viewHolder.line = convertView.findViewById(R.id.line);
            viewHolder.rl1 = convertView.findViewById(R.id.rl1_busitem);
            viewHolder.rl2 = convertView.findViewById(R.id.rl2_busitem);
            viewHolder.rl3 = convertView.findViewById(R.id.rl3_busitem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (data.get(position).getSegmentType()) {
            case 1: //步行
                viewHolder.line.setBackgroundColor(context.getColor(R.color.grey));
                String direction = data.get(position).getSegmentLine().get(0).getSegmentDistance();
                String length = "";
                float distence = 0;
                if (!direction.equals("")) {
                    distence = Float.valueOf(direction);
                }
                if (distence > 1000) {
                    String string = String.valueOf(distence / 1000.0);
                    String s = Util.saveTwoU(string);
                    length = s + " 公里";
                } else {
                    length = distence + " 米";
                }
                viewHolder.tv3.setText("步行" + length);
                viewHolder.tv5.setText(data.get(position).getStationEnd().getName() + "上车");
                break;
            default:
                viewHolder.line.setBackgroundColor(context.getColor(R.color.skyblue));
                String text = "";
                if (position > 0 && data.get(position - 1).getSegmentType() == 1) {
                    text = data.get(position).getSegmentLine().get(0).getLineName()
                            + "(" + data.get(position).getSegmentLine().get(0).getSegmentStationCount() + "站)";
                } else {
                    text = "换乘" + data.get(position).getSegmentLine().get(0).getLineName()
                            + "(" + data.get(position).getSegmentLine().get(0).getSegmentStationCount() + "站)";
                }
                viewHolder.tv3.setText(text);
                viewHolder.tv5.setText(data.get(position).getStationEnd().getName() + "下车");
                break;
        }
        if (position == 0) {
            viewHolder.tv1.setBackground(context.getResources().getDrawable(R.mipmap.icon_point));
            viewHolder.rl1.setVisibility(View.VISIBLE);
            viewHolder.tv2.setText("起点（"+start+"）");
            viewHolder.tv4.setBackground(context.getResources().getDrawable(R.mipmap.icon_buspoint));
        } else if (position == data.size() - 1) {
            viewHolder.rl1.setVisibility(View.GONE);
            viewHolder.tv4.setBackground(context.getResources().getDrawable(R.mipmap.icon_point));
            viewHolder.tv5.setText("终点（"+end+"）");
        } else {
            viewHolder.rl1.setVisibility(View.GONE);
            viewHolder.tv4.setBackground(convertView.getResources().getDrawable(R.mipmap.icon_buspoint));
        }
        return convertView;
    }


    class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView line;
        RelativeLayout rl1;
        RelativeLayout rl2;
        RelativeLayout rl3;
    }
}
