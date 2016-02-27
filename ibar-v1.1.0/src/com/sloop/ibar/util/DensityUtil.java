/**
 * @Title: DensityUtil.java
 * @Package com.sloop.ibar.util
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��3��19�� ����2:13:45
 * @version V1.0
 */

package com.sloop.ibar.util;

import android.content.Context;

/**
 * @ClassName: DensityUtil
 * @author sloop
 * @date 2015��3��19�� ����2:13:45
 */

public class DensityUtil {

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
