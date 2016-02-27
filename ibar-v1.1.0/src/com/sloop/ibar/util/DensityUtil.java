/**
 * @Title: DensityUtil.java
 * @Package com.sloop.ibar.util
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月19日 下午2:13:45
 * @version V1.0
 */

package com.sloop.ibar.util;

import android.content.Context;

/**
 * @ClassName: DensityUtil
 * @author sloop
 * @date 2015年3月19日 下午2:13:45
 */

public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
