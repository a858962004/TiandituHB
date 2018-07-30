//package com.gangbeng.tiandituhb.adpter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.gangbeng.tiandituhb.R;
//import com.gangbeng.tiandituhb.adpter.viewholder.AroundHolder;
//import com.gangbeng.tiandituhb.bean.greendao.SearchRecord;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2018/7/30.
// */
//
//public class AroundLVAdapter extends BaseAdapter {
//    private Context context;
//    private List<SearchRecord> searchRecords;
//    public AroundLVAdapter(Context context,List<SearchRecord> searchRecords){
//        this.context=context;
//        this.searchRecords=searchRecords;
//    }
//
//    @Override
//    public int getCount() {
//        return searchRecords.size()+1;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        SearchRecord searchRecord=null;
//        if (position<searchRecords.size()){
//            searchRecord = searchRecords.get(position);
//        }
//        return searchRecord;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public void addData(SearchRecord searchRecord){
//        this.searchRecords.add(searchRecord);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        AroundHolder aroundHolder;
//        if (convertView == null) {
//            aroundHolder=new AroundHolder();
//            convertView= LayoutInflater.from(context).inflate(R.layout.item_search,null);
//            aroundHolder.textView=convertView.findViewById(R.id.tv_search_item);
//            aroundHolder.clearTV=convertView.findViewById(R.id.tv_clear_item);
//            convertView.setTag(aroundHolder);
//        }else {
//            aroundHolder= (AroundHolder) convertView.getTag();
//        }
//        if (position<searchRecords.size()){
//            aroundHolder.textView.setText(searchRecords.get(position).getName());
//        }else {
//            aroundHolder.textView.setVisibility(View.GONE);
//            aroundHolder.clearTV.setVisibility(View.VISIBLE);
//        }
//        return convertView;
//    }
//}
