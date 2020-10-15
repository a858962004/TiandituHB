package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.NewBusBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @author zhanghao
 * @date 2018-08-20
 */

public class BusModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String origin = String.valueOf(parameter.get("origin"));
        String destination = String.valueOf(parameter.get("destination"));
        OkHttpUtils.get()
                .url(PubConst.directionurl)
                .addParams("origin",origin)
                .addParams("destination",destination)
                .addParams("city","廊坊")
                .addParams("output","JSON")
                .addParams("key",PubConst.gaodeDirectionKey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NewBusBean busBean=new NewBusBean();
                        try {
                            JSONObject resObj=new JSONObject(response);
                            busBean.setStatus(resObj.getString("status"));
                            List<NewBusBean.SegmentsBean> segmentList=new ArrayList<NewBusBean.SegmentsBean>();
                            busBean.setSegments(segmentList);
                            JSONObject routeObj=resObj.getJSONObject("route");
                            if (routeObj!=null){
                                JSONObject transits = routeObj.getJSONArray("transits").getJSONObject(0);//获取第一条规划线路
                                JSONArray segments = transits.getJSONArray("segments");
                                for (int i = 0; i < segments.length(); i++) {
                                    JSONObject segement = segments.getJSONObject(i);
                                    if (!segement.getString("walking").equals("[]")){
                                        JSONObject walking = segement.getJSONObject("walking");
                                        JSONArray steps = walking.getJSONArray("steps");
                                        JSONObject stepBean = steps.getJSONObject(steps.length() - 1);
                                        NewBusBean.SegmentsBean segmentsBean = new NewBusBean.SegmentsBean();
                                        segmentList.add(segmentsBean);
                                        segmentsBean.setMode("walking");
                                        segmentsBean.setDistance(walking.getString("distance"));
                                        segmentsBean.setDuration(walking.getString("duration"));
                                        segmentsBean.setDeparture_stop(stepBean.getString("assistant_action"));
                                    }
                                    if (!segement.getString("bus").equals("[]")){
                                        JSONObject busline = segement.getJSONObject("bus").getJSONArray("buslines").getJSONObject(0);
                                        NewBusBean.SegmentsBean segmentsBean = new NewBusBean.SegmentsBean();
                                        segmentList.add(segmentsBean);
                                        segmentsBean.setMode("bus");
                                        segmentsBean.setDistance(busline.getString("distance"));
                                        segmentsBean.setDuration(busline.getString("duration"));
                                        segmentsBean.setDeparture_stop(busline.getJSONObject("departure_stop").getString("name"));
                                        segmentsBean.setArrival_stop(busline.getJSONObject("arrival_stop").getString("name"));
                                        segmentsBean.setName(busline.getString("name"));
                                        segmentsBean.setVia_num(busline.getString("via_num"));
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        back.success(busBean);
                    }
                });
    }


}
