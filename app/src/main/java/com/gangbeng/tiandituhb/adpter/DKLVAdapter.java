package com.gangbeng.tiandituhb.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;
import com.gangbeng.tiandituhb.utils.RequestUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-10-21
 */

public class DKLVAdapter extends BaseAdapter {
    private Context context;
    private List<SoapObject>data;
    private List<SoapObject>checkData;
    private int state;
    private boolean isAllCheck;
    private CallBack callBack;

    public DKLVAdapter(Context context,List<SoapObject>data,int state,boolean isAllCheck,CallBack callBack){
        this.context=context;
        this.data=data;
        this.state=state;
        this.isAllCheck=isAllCheck;
        this.callBack=callBack;
        checkData=new ArrayList<>();
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

    public List<SoapObject> getCheckData(){
        return checkData;
    }

    public void setState(int state){
        this.state=state;
        notifyDataSetChanged();
    }

    public int getState(){
        return state;
    }

    public boolean getAllCheck(){
        return isAllCheck;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dklist, null);
            viewHolder.tv_name=convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time=convertView.findViewById(R.id.tv_time);
            viewHolder.tv_hcr=convertView.findViewById(R.id.tv_hcr2);
            viewHolder.tv_wt=convertView.findViewById(R.id.tv_wt2);
            viewHolder.checkBox=convertView.findViewById(R.id.cb);
            viewHolder.relativeLayout=convertView.findViewById(R.id.rl_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        SoapObject soapObject = data.get(position);
        String dkid = RequestUtil.getSoapObjectValue(soapObject, "DKID");
        String checkman = RequestUtil.getSoapObjectValue(soapObject, "checkman");
        String result = RequestUtil.getSoapObjectValue(soapObject, "result");
        String checkTime = RequestUtil.getSoapObjectValue(soapObject, "checkTime");
        checkTime=checkTime.substring(0,checkTime.indexOf("T"));
        viewHolder.tv_name.setText(dkid);
        viewHolder.tv_time.setText(checkTime);
        viewHolder.tv_hcr.setText(checkman);
        viewHolder.tv_wt.setText(result);
        if (state==0)viewHolder.checkBox.setVisibility(View.GONE);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkData.add(data.get(position));
                }else {
                    SoapObject soapObject1 = data.get(position);
                    for (int i = 0; i < checkData.size(); i++) {
                        SoapObject soapObject2 = checkData.get(i);
                        if (soapObject1==soapObject2){
                            checkData.remove(i);
                            break;
                        }
                    }
                }
                if (callBack!=null) callBack.checkBoxChangeListen();
            }
        });
        if (state==1){
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder.checkBox.setChecked(!finalViewHolder.checkBox.isChecked());
                }
            });
        }
        viewHolder.checkBox.setChecked(isAllCheck);
        return convertView;
    }



    public class ViewHolder{
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_hcr;
        private TextView tv_wt;
        public CheckBox checkBox;
        private RelativeLayout relativeLayout;
    }

    public interface CallBack{
        void checkBoxChangeListen();
    }

}
