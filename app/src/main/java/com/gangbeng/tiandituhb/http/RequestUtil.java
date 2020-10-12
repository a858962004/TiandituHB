package com.gangbeng.tiandituhb.http;

import android.util.Log;

import com.gangbeng.tiandituhb.bean.DKHCInfo;
import com.gangbeng.tiandituhb.utils.MyLogUtil;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/4/25.
 */
public class RequestUtil {


    private static final String NAMESPACE = "http://tempuri.org/";
    public static final String url = "http://120.211.57.11:8001/LFTDTAPP/Services/WebService1.asmx";//正式服务器

    public static final String Login="Login";//登录接口
    public static final String AddUserInfo="AddUserInfo";//注册接口
    public static final String EditPassword="EditPassword";//修改密码接口
    public static final String GetUserInfo="GetUserInfo";//获取用户信息
    public static final String AddFeedBackInfo="AddFeedBackInfo";//添加信息反馈
    public static final String GetDKCheckInfo="GetDKCheckInfo";//获取地块列表
    public static final String AddDKCheckInfo="AddDKCheckInfo";//添加地块信息
    public static final String EditDKCheckInfo="EditDKCheckInfo";//修改地块信息
    public static final String SubmitDKCheckInfo="SubmitDKCheckInfo";//提交地块信息
    public static final String DeleteDKCheckInfo="DeleteDKCheckInfo";//删除地块信息
    public static final String UploadDKCheckPic="UploadDKCheckPic";//上传照片
    public static final String GetDKCheckPics="GetDKCheckPics";//获取照片
    public static final String DeleteDKCheckPic="DeleteDKCheckPic";//删除照片
    public static final String UploadLocation="UploadLocation";//上传位置共享信息
    public static final String GetNewestLocation="GetNewestLocation";//获取共享位置信息
    public static final String GetHistoryLocationByLoginname="GetHistoryLocationByLoginname";//获取历史共享信息

    public static final String CreateShareGroup="CreateShareGroup";//新建共享组
    public static final String AddtoShareGroup="AddtoShareGroup";//加入共享组
    public static final String DeleteShareGroup="DeleteShareGroup";//解散当前共享组
    public static final String ExitShareGroup="ExitShareGroup";//退出当前共享组
    public static final String GetShareGroup="GetShareGroup";//获取用户所在组信息
    public static final String GetGroupShareLocation="GetGroupShareLocation";//获取当前组成员位置
    public static final String getNewVersion="getNewVersion";//获取版本
    public static final String getVersion="getVersion";//获取版本号


    public static String post(String methodName, Map<String,String> param){
        String object = "";
        // 指定WebService的命名空间和调用方法
        SoapObject soapObject = new SoapObject(NAMESPACE,methodName);
        if (param!=null){
            //设置参数
            Set<String> keySet = param.keySet();
            for(String key:keySet){
                String value = param.get(key);
                soapObject.addProperty(key,value);
            }
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
        if (param!=null){
            //设置参数
            Set<String> keySet = param.keySet();
            for(String key:keySet){
                Object value = param.get(key);
                soapObject.addProperty(key,value);
            }
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


    public static String postobByBody(String methodName, Map<String,Object> param){
        String object = "";
        Log.i("TAG","------------"+methodName);

        // 指定WebService的命名空间和调用方法
        SoapObject soapObject = new SoapObject(NAMESPACE,methodName);
        if (param!=null){
            //设置参数
            Set<String> keySet = param.keySet();
            for(String key:keySet){
                Object value = param.get(key);
                soapObject.addProperty(key,value);
            }
        }
        // 生成调用WebService方法调用的soap信息，并且指定Soap版本(版本号与jar包一致)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        try {
            HttpTransportSE trans = new HttpTransportSE(url,100000);
            trans.call(NAMESPACE + methodName, envelope);

//            object = (SoapObject) envelope.getResponse();
            MyLogUtil.showLog(envelope.bodyIn.toString());

//            if("nouser".equals(object)){
//                object = null;
//            }
            object= envelope.bodyIn.toString();
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
                    if (value1!=null)
                    value=value1.toString();
                    break;
                }
            }
        }
        return value;
    }


    public static List<SoapObject> getObjectValue(SoapObject soapObject, String name){
        List<SoapObject>soapObjects=new ArrayList<>();
        int propertyCount = soapObject.getPropertyCount();
        if (propertyCount>0){
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                PropertyInfo propertyInfo=new PropertyInfo();
                soapObject.getPropertyInfo(i,propertyInfo);
                if (propertyInfo.getName().equals(name)){
                    SoapObject soapObject1 = (SoapObject)propertyInfo.getValue();
                    soapObjects.add(soapObject1);
                }
            }
        }
        return soapObjects;
    }

    public static DKHCInfo soapObjectToDKHCInfo(SoapObject soapObject){
        DKHCInfo dkhcInfo=new DKHCInfo();
        dkhcInfo.setAddLoginName(getSoapObjectValue(soapObject,"AddLoginName"));
        dkhcInfo.setAddress(getSoapObjectValue(soapObject,"Address"));
        dkhcInfo.setAddTime(getSoapObjectValue(soapObject,"AddTime"));
        dkhcInfo.setArea(getSoapObjectValue(soapObject,"Area"));
        dkhcInfo.setCheckman(getSoapObjectValue(soapObject,"Checkman"));
        dkhcInfo.setCheckTime(getSoapObjectValue(soapObject,"CheckTime"));
        dkhcInfo.setCounty(getSoapObjectValue(soapObject,"County"));
        dkhcInfo.setDKID(getSoapObjectValue(soapObject,"DKID"));
        dkhcInfo.setGeometryStr(getSoapObjectValue(soapObject,"GeometryStr"));
        dkhcInfo.setGeometryType(getSoapObjectValue(soapObject,"GeometryType"));
        dkhcInfo.setID(getSoapObjectValue(soapObject,"ID"));
        dkhcInfo.setInfostate(getSoapObjectValue(soapObject,"infostate"));
        dkhcInfo.setNote(getSoapObjectValue(soapObject,"Note"));
        dkhcInfo.setOwner(getSoapObjectValue(soapObject,"Owner"));
        dkhcInfo.setPicUrl(getSoapObjectValue(soapObject,"PicUrl"));
        dkhcInfo.setResult(getSoapObjectValue(soapObject,"result"));
        dkhcInfo.setSubmitstate(getSoapObjectValue(soapObject,"submitstate"));
        dkhcInfo.setTown(getSoapObjectValue(soapObject,"town"));
        dkhcInfo.setUpdateTime(getSoapObjectValue(soapObject,"UpdateTime"));
        dkhcInfo.setVillege(getSoapObjectValue(soapObject,"Villege"));
        return dkhcInfo;
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
