package com.sloop.ibar.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 获取应用的相关文件（夹）路径
 * 
 * @author admin
 *
 */
public class GetAppPath {
	private GetAppPath() {
	};

	/**
	 * 获取系统下载目录的绝对路径
	 * 
	 * @return 下载目录的路径
	 */
	public static String getDownloadFilePath() {
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";
	}

	/**
	 * 获取该应用的私有文件夹下的文件夹路径
	 * 
	 * @param context
	 *            上下文
	 * @param yourAppPath
	 *            自定义目录
	 * @return 目录的绝对路径 返回路径为："/data/data/包名/自定义路径"
	 */
	public static String getPrivateFilePath(Context context, String yourAppPath) {
		String PATH = context.getFilesDir().getAbsolutePath() + yourAppPath;
		File file = new File(PATH);
		if (!(file.exists() && file.isDirectory())) {
			file.mkdirs();
		}

		return PATH;
	}

	/**
	 * 获取当前应用目录或者内存卡根目录路径(优先选择内存卡路径) 如果文件夹不存在会自动创建一个新的
	 * 
	 * @return 文件(夹)绝对路径
	 */
	public static String getFilePath(Context context) {
		return getAppFilePath(context, "");
	}

	/**
	 * 获取当前应用对应的文件夹 (优先选择外置路径)
	 * 
	 * @param context
	 *            上下文
	 * @param yourAppPath
	 *            你的App文件对应的路径 例如你的应用名称叫做sloop 那么你可以这样调用
	 *            GetAppPath.getAppFilePath(this, "/sloop/"); 如果用户有内存卡
	 *            那么返回路径一般为:"/mnt/sdcard/sloop/"
	 *            如果没有内存卡，返回路径为："/data/data/包名/sloop/"
	 * @return 文件夹绝对路径
	 */
	public static String getAppFilePath(Context context, String yourAppPath) {
		String PATH = null;
		if (外置存储卡是否存在()) {
			PATH = Environment.getExternalStorageDirectory() + yourAppPath;
		} else {
			PATH = context.getFilesDir().getAbsolutePath() + yourAppPath;
		}

		File file = new File(PATH);
		if (!(file.exists() && file.isDirectory())) {
			file.mkdirs();
		}
		return PATH;
	}

	/**
	 * 判断位置存储卡是否存在
	 * 
	 * @return true 存在 false 不存在
	 */
	public static boolean 外置存储卡是否存在() {

		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}
}
