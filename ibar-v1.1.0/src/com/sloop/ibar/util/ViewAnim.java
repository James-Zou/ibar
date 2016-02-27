package com.sloop.ibar.util;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * 各种view的动画特效
 * @author admin
 *
 */
public class ViewAnim {
	
	private ViewAnim(){};
	
	/**
	 * 加载页面动画:渐入
	 */
	public static void fadeIn(View view, int time) {
		AlphaAnimation aa = new AlphaAnimation(0f, 1.0f);
		aa.setDuration(time);
		view.startAnimation(aa);
	}
	
/*	public static void mainMenuOpen(){
		
	}*/
}
