package com.gangbeng.tiandituhb.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghao
 * @date 2018-07-31
 */

public class Util {

    public static boolean isCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        boolean iscollect = false;
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data != null) {
            for (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean poisBean : data) {
                if (poisBean.getId().equals(bean.getId())) {
                    iscollect = true;
                    return iscollect;
                }
            }
        }
        return iscollect;
    }

    public static void setCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        if (data == null) data = new ArrayList<>();
        data.add(bean);
        SharedUtil.saveSerializeObject("collectpoint", data);
    }

    public static void cancelCollect(NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean bean) {
        List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean> data = (List<NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean>) SharedUtil.getSerializeObject("collectpoint");
        for (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean poisBean: data) {
            if (bean.getId().equals(poisBean.getId())) {
                data.remove(poisBean);
                break;
            }
        }
        SharedUtil.saveSerializeObject("collectpoint", data);
    }

    public static String saveTwoU(String s) {
        int i = s.length() - s.indexOf(".");
        if (!"".equals(s)) {
            if (s.indexOf(".") > 0 && i > 2) {
                s = s.substring(0, s.indexOf(".") + 3);
            } else if (s.indexOf(".") > 0 && i == 2) {
                s = s + "0";
            } else if (s.indexOf(".") < 0) {
                s = s + ".00";
            }
        }else {
            s="0.00";
        }
        return s;
    }

    public static String secondToHour(String second){
        String time="";
        double aDouble = Double.valueOf(second);
        long h = (long) (aDouble/3600);
        if (h==0){
            long m = (long)(aDouble/60);
            time=m+"分钟";
        }else {
            aDouble=aDouble-h*3600;
            long m = (long)(aDouble/60);
            time=h+"小时"+m+"分钟";
        }
        return time;
    }

    public static String picPathToBase64(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        String str = bitmapToBase64(bitmap);
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
        return str;
    }

    public static String bitmapToBase64(Bitmap bitmap){
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
