package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.bean.SearchBean;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class SearchResultAdpter extends BaseAdapter {
    private Context context;
    private List<SearchBean.PoisBean> data;

    public SearchResultAdpter(Context context, List<SearchBean.PoisBean> data) {
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

    public void addData(List<SearchBean.PoisBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_seardhresult, null);
            holder = new ViewHolder();
            holder.nameTV = convertView.findViewById(R.id.tv_name);
            holder.addressTV = convertView.findViewById(R.id.tv_address);
            holder.itemRL = convertView.findViewById(R.id.rl_item);
            holder.collectIMG = convertView.findViewById(R.id.img_collect);
            holder.collect2IMG=convertView.findViewById(R.id.img_collect2);
            holder.aroundTV = convertView.findViewById(R.id.tv_around);
            holder.routeTV = convertView.findViewById(R.id.tv_route);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTV.setText(data.get(position).getName());
        holder.addressTV.setText(data.get(position).getAddress());
        if (Util.isCollect(data.get(position))){
            holder.collectIMG.setVisibility(View.GONE);
            holder.collect2IMG.setVisibility(View.VISIBLE);
        }else {
            holder.collectIMG.setVisibility(View.VISIBLE);
            holder.collect2IMG.setVisibility(View.GONE);
        }
        final ViewHolder finalHolder = holder;
        holder.collectIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.collectIMG.setVisibility(View.GONE);
                finalHolder.collect2IMG.setVisibility(View.VISIBLE);
                Util.setCollect(data.get(position));
            }
        });
        holder.collect2IMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.collectIMG.setVisibility(View.VISIBLE);
                finalHolder.collect2IMG.setVisibility(View.GONE);
                Util.cancelCollect(data.get(position));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView nameTV;
        TextView addressTV;
        RelativeLayout itemRL;
        TextView aroundTV;
        TextView routeTV;
        ImageView collectIMG;
        ImageView collect2IMG;
    }
}
