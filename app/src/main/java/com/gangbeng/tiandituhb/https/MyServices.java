package com.gangbeng.tiandituhb.https;


import com.gangbeng.tiandituhb.bean.GeocoderBean;
import com.gangbeng.tiandituhb.bean.SearchBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zhanghao
 * @fileName MyServices
 * @date 2018-02-01
 */

public interface MyServices {

    @GET("geocoder")
    Observable<GeocoderBean> geocoder(@Query("postStr") String postStr, @Query("type")String type);
    //逆地理编码查询

    @GET("search")
    Observable<SearchBean> search(@Query("postStr")String postStr, @Query("type")String type);
    //普通poi搜索

}
