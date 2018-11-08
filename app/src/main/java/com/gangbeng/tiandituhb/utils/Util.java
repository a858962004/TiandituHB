package com.gangbeng.tiandituhb.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        for (NewSearchBean.ContentBean.FeaturesBeanX.FeaturesBean poisBean : data) {
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
        } else {
            s = "0.00";
        }
        return s;
    }

    public static String secondToHour(String second) {
        String time = "";
        double aDouble = Double.valueOf(second);
        long h = (long) (aDouble / 3600);
        if (h == 0) {
            long m = (long) (aDouble / 60);
            time = m + "分钟";
        } else {
            aDouble = aDouble - h * 3600;
            long m = (long) (aDouble / 60);
            time = h + "小时" + m + "分钟";
        }
        return time;
    }

    public static String picPathToBase64(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        String str = bitmapToBase64(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return str;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
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

    public static void setUnnormalQuit(String string) {
        //创建文件
        File sdCard = new File(Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF");//获取外部设备的目录
        File file = new File(sdCard, "quit.txt");//文件位置
        if (!sdCard.exists()) sdCard.mkdirs();
        try {
            FileOutputStream outputStream = new FileOutputStream(file);//打开文件输出流
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));//写入到缓存流
            writer.write(string);//从从缓存流写入
            writer.close();//关闭流
            MyLogUtil.showLog("写入成功");
        } catch (Exception exception) {
            MyLogUtil.showLog("写入失败");
        }
    }

    public static String getQuitString() {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF"+File.separator+"quit.txt"; //打开文件输入流
            File file =new File(filename);
            if (!file.exists()) {
                return "";
            }
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(filename);
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer); //读取文件内容
                while (len > 0) {
                    sb.append(new String(buffer, 0, len)); //继续将数据放到buffer中
                    len = inputStream.read(buffer);
                } //关闭输入流
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
