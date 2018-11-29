package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-29
 */

public class RemoveGroupAdpter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> data;
    private RemoveCallBack callBack;

    public RemoveGroupAdpter(Context context, List<Map<String, String>> data,RemoveCallBack callBack) {
        this.context = context;
        this.data = data;
        this.callBack=callBack;
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

    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RemoveViewholder viewholder;
        if (convertView == null) {
            viewholder = new RemoveViewholder();
            convertView = View.inflate(context, R.layout.item_removegroup, null);
            viewholder.img = convertView.findViewById(R.id.img_removegroup);
            viewholder.nameTV = convertView.findViewById(R.id.name_removegroup);
            viewholder.removeTV = convertView.findViewById(R.id.tv_removegroup);
            convertView.setTag(viewholder);
        } else {
            viewholder = (RemoveViewholder) convertView.getTag();
        }

        viewholder.img.setImageResource(R.mipmap.icon_chengyuan);
        viewholder.nameTV.setText(data.get(position).get("name"));
        viewholder.removeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.removeCallBack(position,data.get(position));
            }
        });
        return convertView;
    }

    class RemoveViewholder {
        ImageView img;
        TextView nameTV;
        TextView removeTV;
    }

    public interface RemoveCallBack{
        void removeCallBack(int position,Map<String,String> data);
    }
}
