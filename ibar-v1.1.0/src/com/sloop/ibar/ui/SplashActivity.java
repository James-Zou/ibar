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
 * 应用的logo展示页面
 * 
 * @author admin
 * @version
 */
public class SplashActivity extends BaseActivity {
	private static final int SLEEP_VOER = 0; 	// 休眠结束
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏

		View root = findViewById(R.id.splash_rl_root);
		ViewAnim.fadeIn(root, 2 * Second); // 加载渐入动画效果

		Bmob.initialize(this, "991eecdc07a38c2309832f32a6b797a2"); // 初始化 BmobSDK
		delay();	//延时2秒再打开主界面
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == SLEEP_VOER){
					loadMainUI();		//打开主界面	
					finish();
				}
			}
		};

	}
	
	/**
	 * 延时登陆（防止页面消失过快，用户无法看到logo展示）
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
				sendHandleMsg(SLEEP_VOER, "休眠结束", handler);
			};
		}.start();
	}
}
