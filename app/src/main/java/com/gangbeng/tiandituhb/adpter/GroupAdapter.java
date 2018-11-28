package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2018-11-22
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private LayoutInflater mInflater;
    private List<Map<String, String>> data = new ArrayList<>();
    private GroupClick callback;
    private boolean hasdelet;
    private int selectPosition=-1;
    private Context context;

    public GroupAdapter(Context context, List<Map<String, String>> data, GroupClick callback, boolean hasdelet) {
        this.context = context;
        this.callback = callback;
        this.hasdelet = hasdelet;
        mInflater = LayoutInflater.from(context);
        this.data.add(new HashMap<String, String>());
        if (hasdelet) {
            this.data.add(new HashMap<String, String>());
        }
        this.data.addAll(data);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_group,
                parent, false);
        GroupViewHolder viewHolder = new GroupViewHolder(view);
        viewHolder.cardView = view.findViewById(R.id.card_group);
        viewHolder.imageView = view.findViewById(R.id.img_group);
        viewHolder.textView = view.findViewById(R.id.name_group);
        viewHolder.imgRL = view.findViewById(R.id.rl_img);
        return viewHolder;
    }

    public void setSelectItem(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, final int position) {
        if (position == 0) {
            holder.imageView.setImageResource(R.mipmap.icon_jia);
            holder.textView.setText("邀请");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.addCallBack();
                }
            });
        } else if (position == 1 && hasdelet) {
            holder.imageView.setImageResource(R.mipmap.icon_jian);
            holder.textView.setText("移除");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.removeCallBack();
                }
            });
        } else {
            String leader = data.get(position).get("leader");
            if (leader.equals("0")) {
                holder.imageView.setImageResource(R.mipmap.icon_duizhang);
            } else {
                holder.imageView.setImageResource(R.mipmap.icon_chengyuan);
            }
            holder.textView.setText(data.get(position).get("name"));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectItem(position);
                    callback.clickCallBack(position - 2, data.get(position));
                }
            });
        }
        if (position == selectPosition) {
            holder.imgRL.setBackgroundColor(context.getResources().getColor(R.color.lightblue));
        } else {
            holder.imgRL.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        RelativeLayout imgRL;

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface GroupClick {
        void addCallBack();

        void removeCallBack();

        void clickCallBack(int position, Map<String, String> data);
    }
}
