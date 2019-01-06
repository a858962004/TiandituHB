package com.gangbeng.tiandituhb.constant;


/**
 * Created by Administrator on 2018-05-29.
 */

public class PubConst {
    public final static String DATA = "data";// 跳转传值key值
    public final static String url = "http://api.tianditu.gov.cn/"; //天地图域名
    public static final int SHOW_PHOTO = 0;
    public static final int SHOW_PHOTO_ALBUM = 1;

    public final static String drive = "http://124.165.248.18:8720/newmap/rest/services/xiaoyi/route/NetWorkServer/route";

//    http://124.165.248.18:8720/newmap/rest/services/xiaoyi/route/NetWorkServer/route?points=111.77952852400398,37.15078227322101;111.77952852400398,37.15078227322101&type=shortest&format=gpx-track
//public final static String mapurl = "http://222.222.66.230:8719/newmapserver4/rest/services/dmdz/FeatureServer/";

    public final static String mapurl = "http://222.222.66.179:8719/newmap/rest/services/lftdtgx/dmdz/FeatureServer/";
    public final static String searchapi = "http://222.222.66.179:8719/newmap/rest/services/lftdtgx/dmdzzz/GeoCodeServer/geocode";
    public final static String aroundapi = "http://222.222.66.179:8719/newmap/rest/services/lftdtgx/dmdzzzys/FeatureServer/feature";
    public final static String weatherapi = "https://free-api.heweather.com/s6/weather";
    public final static String weatherKey = "336d33871eea4a8bbbea6b13f7d692f2";

    public final static String countryString = "{\"countries\":[{\"Latitude\":\"39.436466\",\"Longitude\":\"116.2999\",\"name\":\"固安\"}," +
            "{\"Latitude\":\"39.319717\",\"Longitude\":\"116.49809\",\"name\":\"永清\"},{\"Latitude\":\"39.757214\",\"Longitude\":\"117.007164\",\"name\":\"香河\"}," +
            "{\"Latitude\":\"38.699215\",\"Longitude\":\"116.64073\",\"name\":\"大城\"},{\"Latitude\":\"38.866802\",\"Longitude\":\"116.460106\",\"name\":\"文安\"}," +
            "{\"Latitude\":\"39.889267\",\"Longitude\":\"116.9865\",\"name\":\"大厂\"},{\"Latitude\":\"39.117332\",\"Longitude\":\"116.39202\",\"name\":\"霸州\"}," +
            "{\"Latitude\":\"39.982777\",\"Longitude\":\"117.07702\",\"name\":\"三河\"},{\"Latitude\":\"39.502567\",\"Longitude\":\"116.69454\",\"name\":\"安次\"}," +
            "{\"Latitude\":\"39.52193\",\"Longitude\":\"116.71371\",\"name\":\"广阳\"}]}";

    public final static String LABLE_START_SHARE = "startShare";//开启位置贡献
    public final static String LABLE_CLOSE_SHARE = "closeShare";//关闭位置共享
    public final static String LABLE_GET_SHARE = "getShare";//获取位置共享
    public final static String LABLE_NORMAL_QUIT = "LABLE_NORMAL_QUIT";//正常退出
    public final static String LABLE_UNNORMAL_QUIT = "LABLE_UNNORMAL_QUIT";//非正常退出

    public final static String LABLE_GETVERSION = "LABLE_GETVERSION";//获取版本号
    public final static String LABLE_GETNEWVERSION="LABLE_GETNEWVERSION";//获取下载链接

    public final static String LABLE_CREATEGROUP = "CREATEGROUP";//创建分组
    public final static String LABLE_ADDGROUP = "ADDGROUP";//添加分组
    public final static String LABLE_DELETEGROUP = "DELETEGROUP";//解除分组
    public final static String LABLE_EXITGROUP = "EXITGROUP";//退出分组
    public final static String LABLE_GETSHAREGROUP="GETSHAREGROUP";//获取用户所在组信息
    public final static String LABLE_GETGROUPLOCATION="GETGROUPLOCATION";//获取组员位置
    public final static String LABLE_ZOOMGETGROUP="ZOOMGETGROUP";//缩放时获取位置

//    //更新接口地址
//    public final static String update = "http://hb9.api.okayapi.com/?s=App.Table.Get&model_name=TiandituLFUpdate&check_code=_6F19660F12F207E04C917F3F380695F1&id=1&app_key=6F19660F12F207E04C917F3F380695F1&sign=5F65D01F75FF7EB8DD0838C246205E61";

}
