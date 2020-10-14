package com.gangbeng.tiandituhb.xmlparser;

import android.util.Log;

import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.bean.Goods;
import com.gangbeng.tiandituhb.utils.MyLogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-08-01
 */

public class ParserXMLWithPull {
    private static List<Goods> goodsList = null;
    private static Goods goods = null;
    private static DriveRouteBean routeBean;
    private static List<DriveRouteBean.StreetLatLonBean> streetLatLon;
    private static InputStream is;

    public static DriveRouteBean getXmlContentForPull(String xml) {
        routeBean=new DriveRouteBean();
        streetLatLon=new ArrayList<>();
        try {
            is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(is, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: {
                        //开始解析时的初始化
//                        goodsList = new ArrayList<Goods>();
                        Log.d("PULL", "开始解析");
                    }
                    break;
                    case XmlPullParser.START_TAG: {
                        switch (nodeName){
                            case "rtept":
                                MyLogUtil.showLog(xmlPullParser.getAttributeValue(0));
                                DriveRouteBean.StreetLatLonBean streetLatLonBean = new DriveRouteBean.StreetLatLonBean();
                                streetLatLonBean.setX(Double.valueOf(xmlPullParser.getAttributeValue(1)));
                                streetLatLonBean.setY(Double.valueOf(xmlPullParser.getAttributeValue(0)));
                                streetLatLon.add(streetLatLonBean);
                                break;
                            case "desc":
//                                MyLogUtil.showLog(xmlPullParser.getText());
                                String dest=xmlPullParser.nextText();
                                routeBean.setDesc(dest);
                                break;
                        }
                    }
                    break;
                    case XmlPullParser.END_TAG: {
                        routeBean.setStreetLatLon(streetLatLon);
                    }
                    break;
                    case XmlPullParser.END_DOCUMENT: {
                        Log.d("PULL", "解析完成");
                    }
                    break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("PULL", "UnsupportedEncodingException");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d("PULL", "XmlPullParserException");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PULL", "IOException");
        }
        return routeBean;
    }
}
