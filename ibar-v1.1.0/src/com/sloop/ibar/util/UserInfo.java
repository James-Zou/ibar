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
		Email, 	//邮箱
		PSW,	//密码
	}

	/**
	 * 设置用户信息（使用枚举保证数据的安全）
	 * 
	 * @param userInfo
	 * @param value
	 */
	public void setUserInfo(UserInfos userInfo, String value) {
		Userinfo.edit().putString("Info_" + userInfo, value).commit();
	}

	/**
	 * 获取用户信息
	 * 
	 * @param key
	 * @return 返回该键对应的用户信息
	 */
	public String getUserInfo(UserInfos userInfo) {
		return Userinfo.getString("Info_" + userInfo, "");
	}

	/**
	 * 清空所有用户本地缓存
	 */
	public void clean() {
		Editor editor = Userinfo.edit();
		editor.putString("Info_" + UserInfos.PSW, "");
		editor.putString("Info_" + UserInfos.Email, "");
		editor.commit();
	}
}
