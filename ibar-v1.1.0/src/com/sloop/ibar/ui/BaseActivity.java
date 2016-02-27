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
 * ���� �����ṩһЩ����������
 * 
 * @author admin
 *
 */
public class BaseActivity extends Activity {
	/** 1�� */
	public final static int Second = 1000;
	private Toast mToast = null;
	private Message mMessage = null;

	/**
	 * �û���Ϣ
	 */
	public static UserInfo userInfo;
	/**
	 * �û�������
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
	 * ����һ��toast
	 * 
	 * @param text ��Ҫ��ʾ����Ϣ
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
	 * ��handler������Ϣ
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
	 * �˳���¼
	 */
	public void logout() {
		toast("�˳���¼");
		BmobUser.logOut(this); // �û��ǳ�
		userInfo.clean(); // ��ձ����û���Ϣ
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	/**
	 * ��ע��ҳ��
	 */
	protected void loadRegisterUI() {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	/**
	 * �򿪵�¼ҳ��
	 */
	protected void loadLoginUI() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	/**
	 * ������������ҳ��
	 */
	protected void loadPerfectUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * �������ݸ���ҳ��
	 * 
	 * @Title: loadUpdataUI void
	 */
	protected void loadUserInfoUI() {
		Intent intent = new Intent(this, UserInfoActiviry.class);
		startActivity(intent);
	}

	/**
	 * �򿪹��ܽ���
	 */
	protected void loadMainUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * �����ý���
	 */
	protected void loadSettingUI() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}

}
