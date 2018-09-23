package com.gangbeng.tiandituhb.model;

import com.gangbeng.tiandituhb.base.BaseModel;
import com.gangbeng.tiandituhb.base.OnCallBack;
import com.gangbeng.tiandituhb.bean.NewSearchBean;
import com.gangbeng.tiandituhb.constant.PubConst;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
        StringBuilder url=new StringBuilder(PubConst.aroundapi);
        url.append("?layer=dmdz&text=&geotype=bbox&geo=")
                .append(geo)
                .append("&srsin=&where=")
                .append(where)
                .append("&fieldsout=&page=")
                .append(page)
                .append("&maxitems=")
                .append(maxitems)
                .append("&format=json");
        OkHttpUtils.get()
                .url(url.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        back.failed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NewSearchBean newSearchBean = gson.fromJson(response, NewSearchBean.class);
                        back.success(newSearchBean);
                    }
                });
    }
}
