package com.sloop.ibar.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * ��ȡӦ�õ�����ļ����У�·��
 * 
 * @author admin
 *
 */
public class GetAppPath {
	private GetAppPath() {
	};

	/**
	 * ��ȡϵͳ����Ŀ¼�ľ���·��
	 * 
	 * @return ����Ŀ¼��·��
	 */
	public static String getDownloadFilePath() {
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";
	}

	/**
	 * ��ȡ��Ӧ�õ�˽���ļ����µ��ļ���·��
	 * 
	 * @param context
	 *            ������
	 * @param yourAppPath
	 *            �Զ���Ŀ¼
	 * @return Ŀ¼�ľ���·�� ����·��Ϊ��"/data/data/����/�Զ���·��"
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
	 * ��ȡ��ǰӦ��Ŀ¼�����ڴ濨��Ŀ¼·��(����ѡ���ڴ濨·��) ����ļ��в����ڻ��Զ�����һ���µ�
	 * 
	 * @return �ļ�(��)����·��
	 */
	public static String getFilePath(Context context) {
		return getAppFilePath(context, "");
	}

	/**
	 * ��ȡ��ǰӦ�ö�Ӧ���ļ��� (����ѡ������·��)
	 * 
	 * @param context
	 *            ������
	 * @param yourAppPath
	 *            ���App�ļ���Ӧ��·�� �������Ӧ�����ƽ���sloop ��ô�������������
	 *            GetAppPath.getAppFilePath(this, "/sloop/"); ����û����ڴ濨
	 *            ��ô����·��һ��Ϊ:"/mnt/sdcard/sloop/"
	 *            ���û���ڴ濨������·��Ϊ��"/data/data/����/sloop/"
	 * @return �ļ��о���·��
	 */
	public static String getAppFilePath(Context context, String yourAppPath) {
		String PATH = null;
		if (���ô洢���Ƿ����()) {
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
	 * �ж�λ�ô洢���Ƿ����
	 * 
	 * @return true ���� false ������
	 */
	public static boolean ���ô洢���Ƿ����() {

		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}
}
