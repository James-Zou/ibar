package com.sloop.ibar.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo {
	private SharedPreferences Userinfo;

	public UserInfo(Context context) {
		Userinfo = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
	}

	public static enum UserInfos {
		Email, 	//����
		PSW,	//����
	}

	/**
	 * �����û���Ϣ��ʹ��ö�ٱ�֤���ݵİ�ȫ��
	 * 
	 * @param userInfo
	 * @param value
	 */
	public void setUserInfo(UserInfos userInfo, String value) {
		Userinfo.edit().putString("Info_" + userInfo, value).commit();
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param key
	 * @return ���ظü���Ӧ���û���Ϣ
	 */
	public String getUserInfo(UserInfos userInfo) {
		return Userinfo.getString("Info_" + userInfo, "");
	}

	/**
	 * ��������û����ػ���
	 */
	public void clean() {
		Editor editor = Userinfo.edit();
		editor.putString("Info_" + UserInfos.PSW, "");
		editor.putString("Info_" + UserInfos.Email, "");
		editor.commit();
	}
}
