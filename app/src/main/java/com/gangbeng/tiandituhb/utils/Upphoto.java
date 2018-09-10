package com.gangbeng.tiandituhb.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.gangbeng.tiandituhb.constant.PubConst;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


/**
 * User: Cloud(32325596@qq.com)
 * Date: 2017-12-25
 * Time: 10:39
 * project: a
 * Describe
 */
public class Upphoto {
    private Activity ac;
    private File mImage;
    private Uri imageUri;
    public Upphoto(Activity ac){
        this.ac=ac;
    }
    public File photo() {
        File image=null;
        Calendar calendar = Calendar.getInstance();
        String picName = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE)
                + calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + ".jpg";
        File file;


        mImage = new File(Environment.getExternalStorageDirectory() + File.separator
                + "houseCollect" + File.separator + "Cash" + File.separator, picName);
        file = mImage;

        //创建File对象,用于存储选择的照片
//        outputImage = new File(Environment.getExternalStorageDirectory() + File.separator
//                + "HouseCollect" + File.separator + "Cash" + File.separator, picName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(file);
        //隐式意图启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 启动相机程序

        ac.startActivityForResult(intent, PubConst.SHOW_PHOTO);
        image=mImage;
        return image;
    }
    public void photo_album() {
        //隐式意图启动相机
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 启动相机程序

        ac.startActivityForResult(intent, PubConst.SHOW_PHOTO_ALBUM);

    }
}
