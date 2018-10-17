package com.gangbeng.tiandituhb.utils;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/4/25.
 */
public class RequestUtil {


    private static final String NAMESPACE = "http://tempuri.org/";
    public static final String url = "http://222.222.66.230:8001/LFTDTAPP/Services/WebService1.asmx";//正式服务器

    public static final String Login="Login";//登录接口
    public static final String AddUserInfo="AddUserInfo";//注册接口
    public static final String EditPassword="EditPassword";//修改密码接口

    public static String post(String methodName, Map<String,String> param){

        String object = "";

        // 指定WebService的命名空间和调用方法
        SoapObject soapObject = new SoapObject(NAMESPACE,methodName);
        //设置参数
        Set<String> keySet = param.keySet();
        for(String key:keySet){
            String value = param.get(key);
            soapObject.addProperty(key,value);
        }
        Log.i("TAG","------------"+methodName);
        // 生成调用WebService方法调用的soap信息，并且指定Soap版本(版本号与jar包一致)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        try {
            HttpTransportSE trans = new HttpTransportSE(url,100000);
            trans.call(NAMESPACE + methodName, envelope);
            Object result =  envelope.getResponse();
            Log.i("TAG","------------"+result.toString());
            object = result.toString();
            if("nouser".equals(object)){
                object = "";
            }
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return object;
        }
    }

    public static SoapObject postob(String methodName, Map<String,Object> param){
        SoapObject object = null;
        Log.i("TAG","------------"+methodName);

        // 指定WebService的命名空间和调用方法
        SoapObject soapObject = new SoapObject(NAMESPACE,methodName);
        //设置参数
        Set<String> keySet = param.keySet();
        for(String key:keySet){
            Object value = param.get(key);
            soapObject.addProperty(key,value);
        }
        // 生成调用WebService方法调用的soap信息，并且指定Soap版本(版本号与jar包一致)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        try {
            HttpTransportSE trans = new HttpTransportSE(url,100000);
            trans.call(NAMESPACE + methodName, envelope);
            object = (SoapObject) envelope.getResponse();
            if("nouser".equals(object)){
                object = null;
            }
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        finally {
            return object;
        }
    }

    public static String getSoapObjectValue(SoapObject soapObject,String name){
        String value="";
        int propertyCount = soapObject.getPropertyCount();
        if (propertyCount>0){
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                PropertyInfo propertyInfo=new PropertyInfo();
                soapObject.getPropertyInfo(i,propertyInfo);
                if (propertyInfo.getName().equals(name)){
                    Object value1 = propertyInfo.getValue();
                    value=value1.toString();
                    break;
                }
            }
        }
        return value;
    }

//    public static String checkUpdate() {
//        SoapObject soapObject = new SoapObject(NAMESPACE,NewcheckUpdate);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                SoapEnvelope.VER11);// 版本
//        envelope.bodyOut = soapObject ;
//        envelope.dotNet = true ;
//        envelope.setOutputSoapObject(soapObject) ;
//        HttpTransportSE trans = new HttpTransportSE(url,100000) ;
//        trans.debug = true ;	// 使用调试功能
//
//        try {
//            trans.call(NAMESPACE + NewcheckUpdate, envelope) ;
//            Object result = envelope.getResponse();
//            Log.i("TAG","------------"+result.toString());
//            String change = result.toString();
//            return change;
//
//        } catch (IOException r) {
//            r.printStackTrace();
//            return "";
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }


    public static boolean downLoadFile(String newFilename, String urlstr){

        int index = urlstr.lastIndexOf("/");
        String newurl = "";
        try {
            newurl = urlstr.substring(0,index)+"/"+ URLEncoder.encode(urlstr.substring(index+1,urlstr.length()),"UTF-8");
            Log.i("TAG","url"+newurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        boolean isSuccess = false;

        File dirs = new File(newFilename.substring(0,newFilename.lastIndexOf("/")));
        if(!dirs.exists()){
            dirs.mkdirs();
        }
        File file = new File(newFilename);
//如果目标文件已经存在，则删除。产生覆盖旧文件的效果
        if(file.exists())
        {
            file.delete();
        }
        try {
            // 构造URL
            URL url = new URL(newurl);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :"+contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(newFilename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
}
