package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.AroundSearchBean;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * @author zhanghao
 * @date 2018-09-21
 */

public class AroundSearchModel implements BaseModel {
    @Override
    public void setRequest(Map<String, Object> parameter, final OnCallBack back) {
        String maxitems = String.valueOf(parameter.get("maxitems"));//每页条数
        String page = String.valueOf(parameter.get("page"));//当前页
        String where = String.valueOf(parameter.get("where"));//查询条件
        String geo = String.valueOf(parameter.get("geo"));//空间范围
        OkHttpUtils.get()
                .url(PubConst.aroundapi)
                .addParams("maxitems", maxitems)
                .addParams("geotype","bbox")
                .addParams("geo",geo)
                .addParams("page", page)
                .addParams("fieldsout", "*")
                .addParams("srsout", "EPSG:4490")
                .addParams("srsin", "EPSG:4490")
                .addParams("layer", "dmdzzzys")
                .addParams("format", "json")
                .addParams("where", where)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("{\"error\":\"没有符合条件的结果\"}")){
                            back.failed("未查找到相应数据");
                            return;
                        }
                        Gson gson = new Gson();
                        AroundSearchBean bean = gson.fromJson(response, AroundSearchBean.class);
                        if (bean.getHeader() == null) {
                            back.failed("未查找到相应数据");
                            return;
                        }
                        String count = String.valueOf(bean.getHeader().getTotalItemsCount());
                        if (count.equals("0") || bean.getContent().getFeatures().getFeatures() == null || bean.getContent().getFeatures().getFeatures().size() == 0) {
                            back.failed("未查找到相应数据");
                            return;
                        }
                        List<AroundSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> features = bean.getContent().getFeatures().getFeatures();
                        List<NewSearchBean.ListBean>data=new ArrayList<>();
                        for (AroundSearchBean.ContentBean.FeaturesBeanX.FeaturesBean feature : features) {
                            NewSearchBean.ListBean listBean=new NewSearchBean.ListBean();
                            listBean.setGid(feature.getId());
                            listBean.setX(feature.getGeometry().getCoordinates().get(0));
                            listBean.setY(feature.getGeometry().getCoordinates().get(1));
                            listBean.set简称(feature.getProperties().get简称());
                            listBean.set地址(feature.getProperties().get地址());
                            listBean.set代码(feature.getProperties().get代码());
                            listBean.setYif1(feature.getProperties().getYif1());
                            listBean.setErf1(feature.getProperties().getErf1());
                            listBean.setSanf1(feature.getProperties().getSanf1());
                            data.add(listBean);
                        }
                        NewSearchBean newSearchBean = new NewSearchBean();
                        newSearchBean.setTotal(bean.getHeader().getTotalItemsCount());
                        newSearchBean.setList(data);
                        back.success(newSearchBean);
                    }
                });
    }
}
