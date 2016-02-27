/**
 * @Title: BaseFragment.java
 * @Package com.sloop.ibar.ui.fragment
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月7日 下午12:45:08
 * @version V1.0
 */

package com.sloop.ibar.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sloop.ibar.ui.LoginActivity;
import com.sloop.ibar.ui.MainActivity;
import com.sloop.ibar.ui.RegisterActivity;
import com.sloop.ibar.util.UserInfo;

/**
 * fragment的基类
 * @ClassName: BaseFragment
 * @author sloop
 * @date 2015年3月7日 下午12:45:08
 */

public class BaseFragment extends Fragment {
	protected Context context;
	private Toast mToast = null;
	private Message mMessage=null;
	/** 用户信息 */		public static UserInfo userInfo;
	/** 用户管理者 */	public BmobUserManager userManager;
	/** 图片加载器 */	public ImageLoader imageLoader = null;
	
	public BaseFragment(Context context){
		this.context = context;
		//组件初始化
		userManager = BmobUserManager.getInstance(context);
		if (userInfo==null) {
			userInfo =  new UserInfo(context);
		}
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		}
	}
	
	/**
	 * 发出一个toast
	 * @param text		需要显示的信息
	 */
	public void toast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text,Toast.LENGTH_LONG);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	/**
	 * 信息发送器
	 * @Title: sendHandleMsg
	 * @param what			信息标识码
	 * @param message		信息内容
	 * @param mHandler 		发送给哪个handler
	 */
	public void sendHandleMsg(int what, Object message, Handler mHandler) {
		mMessage = new Message();
		mMessage.what = what;
		mMessage.obj = message;
		mHandler.sendMessage(mMessage);
	}
	
	/**
	 * 打开注册页面
	 */
	protected void loadRegisterUI() {
		Intent intent = new Intent(context, RegisterActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 打开登录页面
	 */
	protected void loadLoginUI() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 加载完善资料页面
	 */
	protected void loadPerfectUI() {
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 加载数据更新页面
	 * @Title: loadUpdataUI void
	 */
	protected void loadUpdataUI() {
	}
	
	
	/**
	 * 打开功能界面
	 */
	protected void loadMainUI() {
	}
	
	/**
	 * 打开反馈页面
	 */
	protected void loadFeedbackUI() {
	}

	/**
	 * 打开关于页面
	 */
	protected void loadAboutUI() {
	}
	
}
