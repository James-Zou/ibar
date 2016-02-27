/**
 * @Title: BaseFragment.java
 * @Package com.sloop.ibar.ui.fragment
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��3��7�� ����12:45:08
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
 * fragment�Ļ���
 * @ClassName: BaseFragment
 * @author sloop
 * @date 2015��3��7�� ����12:45:08
 */

public class BaseFragment extends Fragment {
	protected Context context;
	private Toast mToast = null;
	private Message mMessage=null;
	/** �û���Ϣ */		public static UserInfo userInfo;
	/** �û������� */	public BmobUserManager userManager;
	/** ͼƬ������ */	public ImageLoader imageLoader = null;
	
	public BaseFragment(Context context){
		this.context = context;
		//�����ʼ��
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
	 * ����һ��toast
	 * @param text		��Ҫ��ʾ����Ϣ
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
	 * ��Ϣ������
	 * @Title: sendHandleMsg
	 * @param what			��Ϣ��ʶ��
	 * @param message		��Ϣ����
	 * @param mHandler 		���͸��ĸ�handler
	 */
	public void sendHandleMsg(int what, Object message, Handler mHandler) {
		mMessage = new Message();
		mMessage.what = what;
		mMessage.obj = message;
		mHandler.sendMessage(mMessage);
	}
	
	/**
	 * ��ע��ҳ��
	 */
	protected void loadRegisterUI() {
		Intent intent = new Intent(context, RegisterActivity.class);
		startActivity(intent);
	}
	
	/**
	 * �򿪵�¼ҳ��
	 */
	protected void loadLoginUI() {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}
	
	/**
	 * ������������ҳ��
	 */
	protected void loadPerfectUI() {
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * �������ݸ���ҳ��
	 * @Title: loadUpdataUI void
	 */
	protected void loadUpdataUI() {
	}
	
	
	/**
	 * �򿪹��ܽ���
	 */
	protected void loadMainUI() {
	}
	
	/**
	 * �򿪷���ҳ��
	 */
	protected void loadFeedbackUI() {
	}

	/**
	 * �򿪹���ҳ��
	 */
	protected void loadAboutUI() {
	}
	
}
