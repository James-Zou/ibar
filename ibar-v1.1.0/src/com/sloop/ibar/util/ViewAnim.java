package com.sloop.ibar.util;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * ����view�Ķ�����Ч
 * @author admin
 *
 */
public class ViewAnim {
	
	private ViewAnim(){};
	
	/**
	 * ����ҳ�涯��:����
	 */
	public static void fadeIn(View view, int time) {
		AlphaAnimation aa = new AlphaAnimation(0f, 1.0f);
		aa.setDuration(time);
		view.startAnimation(aa);
	}
	
/*	public static void mainMenuOpen(){
		
	}*/
}
