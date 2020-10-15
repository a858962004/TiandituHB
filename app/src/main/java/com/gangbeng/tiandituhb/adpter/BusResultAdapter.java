package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.bean.NewBusBean;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-21
 */

public class BusResultAdapter extends BaseAdapter {
    private Context context;
    private List<NewBusBean.SegmentsBean> data;

    public BusResultAdapter(Context context, List<NewBusBean.SegmentsBean> data) {
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
//        String name="";
//        String lineName = data.get(position).getLineName();
//        String[] split = lineName.split("|");
//        for (int i = 0; i < split.length; i++) {
//            if (split[i].equals("|")) {
//                split[i]="→";
//            }
//            if (i!=split.length-1){
//                name+=split[i];
//            }
//        }
        String name="";
        String descriptin="";
        if (data.get(position).getMode().equals("walking")){
            name+="步行"+data.get(position).getDistance()+"米";
            if (!data.get(position).getDeparture_stop().equals("[]")){
                name+=data.get(position).getDeparture_stop()+"公交站";
            }
            if (position==data.size()-1){
                name+="，到达目的地";
            }
            descriptin+="预计用时"+Util.secondToHour(data.get(position).getDuration());
        }else if (data.get(position).getMode().equals("bus")){
            name+="换乘"+data.get(position).getName()+"到"+data.get(position).getArrival_stop()+"下车";
            if (position==data.size()-1){
                name+="，到达目的地";
            }
            descriptin+="全程共计"+data.get(position).getVia_num()+"站，预计用时"+Util.secondToHour(data.get(position).getDuration());
        }
        viewHolder.textView.setText(name);
        viewHolder.textView2.setText(descriptin);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView textView2;
    }
}
