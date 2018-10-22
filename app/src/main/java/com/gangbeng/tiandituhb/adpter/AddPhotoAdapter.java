package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gangbeng.tiandituhb.R;

import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-17
 */

public class AddPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String>uris;
    private OnGridViewClick listen;
    private boolean iscancel=true;
    public AddPhotoAdapter(Context context, List<String>uris,boolean iscancel, OnGridViewClick listen){
        this.context=context;
        this.uris=uris;
        this.listen=listen;
        this.iscancel=iscancel;
    }

    public void setData(List<String>uris){
        this.uris=uris;
        notifyDataSetChanged();
    }

    public void removeData(int position){
        if (position<uris.size()){
            this.uris.remove(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    @Override
    public Object getItem(int position) {
        return uris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.view_addphoto,null);
            viewHolder.relativeLayout =convertView.findViewById(R.id.rl_img);
            viewHolder.imageView=convertView.findViewById(R.id.img_addask);
            viewHolder.cancelTV=convertView.findViewById(R.id.tv_cancel_addask);
            viewHolder.noImgView=convertView.findViewById(R.id.noimg_addask);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (iscancel) viewHolder.cancelTV.setVisibility(View.VISIBLE);
        if (!iscancel) viewHolder.cancelTV.setVisibility(View.GONE);
        if (!uris.get(position).equals("0")) {
            viewHolder.relativeLayout.setVisibility(View.VISIBLE);
            viewHolder.noImgView.setVisibility(View.GONE);
            Glide.with(context)
                    .load(uris.get(position))
                    .into(viewHolder.imageView);
            viewHolder.imageView.setBackground(null);
            if (listen != null) {
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listen.showPhoto(position);
                    }
                });
                viewHolder.cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listen.onCancel(position);
                    }
                });
            }
        }else {
            viewHolder.relativeLayout.setVisibility(View.GONE);
            viewHolder.noImgView.setVisibility(View.VISIBLE);
            if (listen != null) {
                viewHolder.noImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listen.addPhoto(position);
                    }
                });
            }
        }

        return convertView;
    }

    class ViewHolder{
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView cancelTV;
        ImageView noImgView;
    }

    public interface OnGridViewClick{
        void addPhoto(int position);
        void showPhoto(int position);
        void onCancel(int position);
    }
}
