package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.viewholder.SortGridViewHolder;

/**
 * Created by 11655 on 2016/10/19.
 */

public class SortGridViewAdapter extends BaseAdapter {
    //图片资源id
    private int[] sortImgs;
    //文字资源
    private String[] sortStrs;
    //布局文件
    private int resourse;
    //布局填充器
    LayoutInflater inflater;
    public SortGridViewAdapter(Context context, int[] sortImgs, String[] sortStrs) {
        this.sortImgs = sortImgs;
        this.sortStrs = sortStrs;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sortImgs.length;
    }

    @Override
    public Object getItem(int position) {
        return sortImgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SortGridViewHolder vh;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_sourcepanel,null);
            vh = new SortGridViewHolder();
            vh.imageView = ((ImageView) convertView.findViewById(R.id.grid_view_iv));
            vh.textView = ((TextView) convertView.findViewById(R.id.grid_view_tv));
            convertView.setTag(vh);
        }else {
            vh = (SortGridViewHolder) convertView.getTag();
        }
        vh.imageView.setImageResource(sortImgs[position]);
        vh.textView.setText(sortStrs[position]);
        return convertView;
    }
}
