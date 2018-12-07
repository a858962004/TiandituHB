package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.callback.SearchAdpaterCancelBack;
import com.gangbeng.tiandituhb.callback.SearchAdpterCallBack;
import com.gangbeng.tiandituhb.utils.Util;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class SearchResultAdpter extends BaseAdapter {
    private Context context;
    private List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data;
    private SearchAdpterCallBack callBack;
    private SearchAdpaterCancelBack cancelBack;
    private boolean isvisible;

    public SearchResultAdpter(Context context, List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data, boolean isvisible) {
        this.context = context;
        this.data = data;
        this.isvisible=isvisible;
    }

    public void setCallBack(SearchAdpterCallBack callBack){
        this.callBack=callBack;
    }

    public void setCancalBack(SearchAdpaterCancelBack cancalBack){
        this.cancelBack=cancalBack;
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

    public void addData(List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void removeData(int position){
        this.data.remove(position);
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
            holder.aroundLL=convertView.findViewById(R.id.ll_around);
            holder.routeLL=convertView.findViewById(R.id.ll_route);
            holder.ll=convertView.findViewById(R.id.ll_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name="";
        if (!data.get(position).getProperties().get简称().equals("")){
            name=data.get(position).getProperties().get简称();
        }else {
            if (!data.get(position).getProperties().get名称().equals("")){
                name=data.get(position).getProperties().get名称();
            }else {
                if (!data.get(position).getProperties().get兴趣点().equals("")){
                    name=data.get(position).getProperties().get兴趣点();
                }else {
                    if (!data.get(position).getProperties().get描述().equals("")){
                        name=data.get(position).getProperties().get描述();
                    }else {
                        name=data.get(position).getProperties().get备注();
                    }
                }
            }
        }
        holder.nameTV.setText(name);
        holder.addressTV.setText(data.get(position).getProperties().get地址());
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
                if (cancelBack!=null){
                    cancelBack.cancalCollect(position);
                }
            }
        });

        if (callBack!=null){
            holder.itemRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.itemclick(data.get(position));
                }
            });
            holder.aroundLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.aroundclick(data.get(position));
                }
            });
            final String finalName = name;
            holder.routeLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.routeclick(data.get(position), finalName);
                }
            });

        }
        if (isvisible){
            holder.ll.setVisibility(View.VISIBLE);
        }else {
            holder.ll.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView nameTV;
        TextView addressTV;
        RelativeLayout itemRL;
        TextView aroundTV;
        TextView routeTV;
        LinearLayout aroundLL;
        LinearLayout routeLL;
        ImageView collectIMG;
        ImageView collect2IMG;
        LinearLayout ll;
    }

}
