package com.gangbeng.tiandituhb.utils;

import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.gangbeng.tiandituhb.application.MyApplication;


public class ToastUtil {

	public static Toast toast;
	public static int NOTIFICATION_ID = 8633170;

	public static void toast(String notice) {
		if (notice != null && notice.length() > 0) {
				toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), notice,
						Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	public static void toastInThread(String notice) {
		Message msg = MyApplication.getInstance().mHandler.obtainMessage(0, 0,
				0);
		msg.obj = notice;
		MyApplication.getInstance().mHandler.sendMessage(msg);
	}

	public static void toastLongInThread(String notice) {
		Message msg = MyApplication.getInstance().mHandler.obtainMessage(1, 0,
				0);
		msg.obj = notice;
		MyApplication.getInstance().mHandler.sendMessage(msg);
	}

	public static void toastLong(String notice) {
		if (notice != null && notice.length() > 0) {
			if (toast == null) {
				toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), notice,
						Toast.LENGTH_SHORT);
			} else {
				toast.setText(notice);
			}
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	public static void toastInThread(int rId) {
		String message = "";
		message =MyApplication.getInstance().getApplicationContext().getString(rId);
		toastInThread(message);
	}

	public static void toast(int rId) {
		String message = "";
		message =MyApplication.getInstance().getApplicationContext().getString(rId);
		toast(message);
	}
	
	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}



}
