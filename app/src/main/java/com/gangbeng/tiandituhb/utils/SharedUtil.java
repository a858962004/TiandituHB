package com.gangbeng.tiandituhb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.gangbeng.tiandituhb.application.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * * SharedPreferences 的公具类
 *
 * @author teeker_bin
 */
public class SharedUtil
{
	private static final String SP_NAME = "config";

	private static SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

	private static Editor editor = sharedPreferences.edit();





	public static void removeData(String key)
	{
		editor.remove(key);
		editor.apply();
	}

	public static String getString(String key, String defaultValue)
	{
		return sharedPreferences.getString(key, defaultValue);
	}

	public static int getInt(String key, int defaultValue)
	{
		return sharedPreferences.getInt(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue)
	{
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	public static float getFloat(String key, float defaultValue)
	{
		return sharedPreferences.getFloat(key, defaultValue);
	}

	public static void setString(String key, String value)
	{
		if (value == null)
		{
			removeData(key);
		}
		else
		{
			editor.putString(key, value);
			editor.apply();
		}
	}

	public static long getLong(String key, long defaultValue)
	{
		return sharedPreferences.getLong(key, defaultValue);

	}

	public static void setLong(String key, long value)
	{
		editor.putLong(key, value);
		editor.apply();
	}

	public static void setInt(String key, int value)
	{
		editor.putInt(key, value);
		editor.apply();
	}

	public static void setBoolean(String key, boolean value)
	{
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static void setFloat(String key, float value)
	{
		editor.putFloat(key, value);
		editor.apply();
	}

	public static void clearData()
	{
		editor.clear();
		editor.apply();
	}


	/**
	 * 将obj 序列化后以指定的键值 存储在sp
	 *
	 * @param obj
	 * @param key
	 * @throws IOException
	 */
	public static void serialize(Object obj, String key) throws IOException
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		editor.putString(key, serStr);
//		editor.putString(key + "json", new Gson().toJson(obj));
		editor.apply();
	}

	/**
	 * 反序列化
	 *
	 * @param key
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerialize(String key) throws IOException, ClassNotFoundException
	{
		String string = getString(key, "");
		if (TextUtils.isEmpty(string))
		{
			return null;
		}
		else
		{
			String redStr = java.net.URLDecoder.decode(string, "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
			return object;
		}
	}

	/**
	 * 保存序列化对象
	 *
	 * @param key
	 * @param t
	 */
	public static <T> void saveSerializeObject(String key, T t)
	{
		try
		{
			SharedUtil.serialize(t, key);
		}
		catch (IOException e)
		{}
	}

	/**
	 * 取的序列化对象
	 *
	 * @param key
	 * @return
	 */
	public static Object getSerializeObject(String key)
	{
		try
		{
			return SharedUtil.deSerialize(key);
		}
		catch (Exception e)
		{}
		return null;
	}

	/**
	 * 启动页广告图的存储
	 */
	private final static String LAUNCH_AD = "launch_ad";

	private final static String LAUNCH_ADLINK = "launch_ad_link";

	private final static String LAUNCH_ADNAME = "launch_ad_name";

	public static void saveLaunchAd(String bannerUrl)
	{
		setString(LAUNCH_AD, bannerUrl);
	}

	public static void saveLaunchAdLink(String adLink)
	{
		setString(LAUNCH_ADLINK, adLink);
	}

	public static void saveLaunchAdName(String adLinkName)
	{
		setString(LAUNCH_ADNAME, adLinkName);
	}

	public static String getLaunchAdName()
	{
		return getString(LAUNCH_ADNAME, null);
	}

	public static String getLaunchAdLink()
	{
		return getString(LAUNCH_ADLINK, null);
	}

	public static String getLaunchAd()
	{
		return getString(LAUNCH_AD, null);
	}

}
