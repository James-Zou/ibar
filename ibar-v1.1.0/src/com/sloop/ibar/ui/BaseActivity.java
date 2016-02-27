package com.sloop.ibar.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobUser;

import com.sloop.ibar.util.UserInfo;

/**
 * 基类 用于提供一些方法和属性
 * 
 * @author admin
 *
 */
public class BaseActivity extends Activity {
	/** 1秒 */
	public final static int Second = 1000;
	private Toast mToast = null;
	private Message mMessage = null;

	/**
	 * 用户信息
	 */
	public static UserInfo userInfo;
	/**
	 * 用户管理者
	 */
	public BmobUserManager userManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		userManager = BmobUserManager.getInstance(this);
		super.onCreate(savedInstanceState);

		if (userInfo == null) {
			userInfo = new UserInfo(getApplicationContext());
		}

	}

	/**
	 * 发出一个toast
	 * 
	 * @param text 需要显示的信息
	 */
	public void toast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});

		}
	}

	/**
	 * 向handler发送消息
	 */
	public void sendHandleMsg(int what, Object message, Handler mHandler) {
		if (mMessage == null) {
			mMessage = new Message();
		}
		mMessage.what = what;
		mMessage.obj = message;
		mHandler.sendMessage(mMessage);
	}

	/**
	 * 退出登录
	 */
	public void logout() {
		toast("退出登录");
		BmobUser.logOut(this); // 用户登出
		userInfo.clean(); // 清空本地用户信息
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	/**
	 * 打开注册页面
	 */
	protected void loadRegisterUI() {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	/**
	 * 打开登录页面
	 */
	protected void loadLoginUI() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	/**
	 * 加载完善资料页面
	 */
	protected void loadPerfectUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * 加载数据更新页面
	 * 
	 * @Title: loadUpdataUI void
	 */
	protected void loadUserInfoUI() {
		Intent intent = new Intent(this, UserInfoActiviry.class);
		startActivity(intent);
	}

	/**
	 * 打开功能界面
	 */
	protected void loadMainUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * 打开设置界面
	 */
	protected void loadSettingUI() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}

}
