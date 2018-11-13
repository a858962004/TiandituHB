package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.constant.Contant;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-08-04
 */

public class MoreLVAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>>data;
    private boolean is;
    public MoreLVAdapter(Context context,List<Map<String,Object>>data,boolean is){
        this.context=context;
        this.data=data;
        this.is=is;
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
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_more,null);
            viewHolder=new ViewHolder();
            viewHolder.tv=convertView.findViewById(R.id.tv_name);
            viewHolder.img=convertView.findViewById(R.id.img_more);
            viewHolder.imageView=convertView.findViewById(R.id.img);
            viewHolder.tv_other=convertView.findViewById(R.id.tv_other);
            viewHolder.tv_next=convertView.findViewById(R.id.tv_next);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Map<String, Object> stringObjectMap = data.get(position);
        String name = String.valueOf(stringObjectMap.get("name"));
        Integer resource= (Integer) stringObjectMap.get("resource");
        viewHolder.tv.setText(name);
        viewHolder.img.setBackground(context.getResources().getDrawable(resource));
        if (String.valueOf(stringObjectMap.get("name")).equals("版本更新")&&!is){
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imageView.setVisibility(View.GONE);
        }
        if (String.valueOf(stringObjectMap.get("name")).equals("位置共享")){
            viewHolder.tv_next.setVisibility(View.GONE);
            viewHolder.tv_other.setVisibility(View.VISIBLE);
            if (Contant.ins().isLocalState()){
                viewHolder.tv_other.setBackground(context.getResources().getDrawable(R.mipmap.icon_startbt));
            }else {
                viewHolder.tv_other.setBackground(context.getResources().getDrawable(R.mipmap.icon_closebt));
            }

        }else {
            viewHolder.tv_next.setVisibility(View.VISIBLE);
            viewHolder.tv_other.setVisibility(View.GONE);
        }
        return convertView;
    }


    class ViewHolder{
        TextView tv;
        TextView img;
        TextView tv_other;
        TextView tv_next;
        ImageView imageView;

    }
}
