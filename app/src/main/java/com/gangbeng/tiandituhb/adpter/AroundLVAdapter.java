package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.adpter.viewholder.AroundHolder;
import com.gangbeng.tiandituhb.bean.RecordBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */

public class AroundLVAdapter extends BaseAdapter {
    private Context context;
    private List<RecordBean> searchRecords;
    public AroundLVAdapter(Context context,List<RecordBean> searchRecords){
        this.context=context;
        this.searchRecords=searchRecords;
    }

    @Override
    public int getCount() {
        return searchRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return searchRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public void removeAll(){
        this.searchRecords.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AroundHolder aroundHolder;
        if (convertView == null) {
            aroundHolder=new AroundHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_search,null);
            aroundHolder.textView=convertView.findViewById(R.id.tv_search_item);
            convertView.setTag(aroundHolder);
        }else {
            aroundHolder= (AroundHolder) convertView.getTag();
        }
        aroundHolder.textView.setText(searchRecords.get(position).getData());
        return convertView;
    }
}
