package com.gangbeng.tiandituhb.xmlparser;

import android.util.Log;

import com.gangbeng.tiandituhb.bean.DriveRouteBean;
import com.gangbeng.tiandituhb.bean.Goods;

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
                        routeBean=new DriveRouteBean();
                        streetLatLon=new ArrayList<>();
//                        goodsList = new ArrayList<Goods>();
                        Log.d("PULL", "开始解析");
                    }
                    break;
                    case XmlPullParser.START_TAG: {
                        switch (nodeName){
                            case "distance":
                                routeBean.setDistance(xmlPullParser.nextText());
                                break;
                            case "duration":
                                routeBean.setDuration(xmlPullParser.nextText());
                                break;
                            case "center":
                                routeBean.setCenter(xmlPullParser.nextText());
                                break;
                            case "scale":
                                routeBean.setScale(xmlPullParser.nextText());
                                break;
                            case "streetLatLon":
                                String s = xmlPullParser.nextText();
                                String[] split = s.split(";");
                                if (split.length>0) {
                                    for (int i = 0; i < split.length; i++) {
                                        DriveRouteBean.StreetLatLonBean streetLatLonBean = new DriveRouteBean.StreetLatLonBean();
                                        String s1 = split[i];
                                        String[] split1 = s1.split(",");
                                        streetLatLonBean.setX(Double.valueOf(split1[0]));
                                        streetLatLonBean.setX(Double.valueOf(split1[1]));
                                        streetLatLon.add(streetLatLonBean);
                                    }
                                }
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
