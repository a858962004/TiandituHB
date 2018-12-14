package com.gangbeng.tiandituhb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gangbeng.tiandituhb.bean.NewSearchBean;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
            String filename = Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF" + File.separator + "quit.txt"; //打开文件输入流
            File file = new File(filename);
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

    public static void setWeatherCash(String location, String string) {
        Calendar calendar = Calendar.getInstance(); //获取系统的日期
        // 年
        int year = calendar.get(Calendar.YEAR);
        // 月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //创建文件
        File sdCard = new File(Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF"
                + File.separator + "WeatherCash" + File.separator + year + "-" + month + "-" + day);//获取外部设备的目录
        File file = new File(sdCard, location + ".txt");//文件位置
        File parentFile = sdCard.getParentFile();
        if (!sdCard.exists()) {
            if (parentFile.exists()) {
                parentFile.delete();
            }
            sdCard.mkdirs();

        }
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


    public static String getWeatherCash(String name) {
        Calendar calendar = Calendar.getInstance(); //获取系统的日期
        // 年
        int year = calendar.get(Calendar.YEAR);
        // 月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF"
                    + File.separator + "WeatherCash" + File.separator + year + "-" + month + "-" + day + File.separator + name + ".txt"; //打开文件输入流
            File file = new File(filename);
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


    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
//            MainActivity.handler.sendEmptyMessage(COPY_FALSE);
        }
    }

    /**
     * @作者 Author
     * @创建日期 2017/12/1 0001
     * @创建时间 上午 8:39
     * @描述 —— 将android的文字转换为底图透明的图片
     */
    public static Bitmap creatCodeBitmap(String contents, Context context) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;

        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setTextSize(scale * 8);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(Color.BLACK);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());


        //tv.setBackgroundColor(Color.GREEN);

        tv.buildDrawingCache();
        Bitmap bitmapCode = tv.getDrawingCache();
        return bitmapCode;
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(Context context,String filePath) { // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) { Toast.makeText(context, "删除目录失败：" + filePath + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        } boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) { // 删除子文件
            if (file.isFile()) { flag = deleteSingleFile(context,file.getAbsolutePath());
                if (!flag) break;
            } // 删除子目录
            else if (file.isDirectory()) { flag = deleteDirectory(context,file .getAbsolutePath());
                if (!flag) break;
            } } if (!flag) { Toast.makeText(context, "删除目录失败！", Toast.LENGTH_SHORT).show();
            return false;
        } // 删除当前目录
        if (dirFile.delete()) { Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else { Toast.makeText(context, "删除目录：" + filePath + "失败！", Toast.LENGTH_SHORT).show();
            return false;
        } }


    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(Context context,String filePath$Name) { File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) { if (file.delete()) { Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
            return true;
        } else { Toast.makeText(context, "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
            return false;
        } } else { Toast.makeText(context, "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        } }
}
