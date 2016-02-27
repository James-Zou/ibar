package com.sloop.ibar.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import cn.bmob.v3.Bmob;

import com.sloop.ibar.R;
import com.sloop.ibar.util.ViewAnim;

/**
 * Ӧ�õ�logoչʾҳ��
 * 
 * @author admin
 * @version
 */
public class SplashActivity extends BaseActivity {
	private static final int SLEEP_VOER = 0; 	// ���߽���
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // ȫ��

		View root = findViewById(R.id.splash_rl_root);
		ViewAnim.fadeIn(root, 2 * Second); // ���ؽ��붯��Ч��

		Bmob.initialize(this, "991eecdc07a38c2309832f32a6b797a2"); // ��ʼ�� BmobSDK
		delay();	//��ʱ2���ٴ�������
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == SLEEP_VOER){
					loadMainUI();		//��������	
					finish();
				}
			}
		};

	}
	
	/**
	 * ��ʱ��½����ֹҳ����ʧ���죬�û��޷�����logoչʾ��
	 */
	private void delay() {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2 * Second);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendHandleMsg(SLEEP_VOER, "���߽���", handler);
			};
		}.start();
	}
}
