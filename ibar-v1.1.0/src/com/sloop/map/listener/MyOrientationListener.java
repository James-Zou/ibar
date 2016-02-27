/**
 * @Title: MyOrientationListener.java
 * @Package com.sloop.map
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月17日 下午9:52:45
 * @version V1.0
 */

package com.sloop.map.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @ClassName: MyOrientationListener
 * @author sloop
 * @date 2015年3月17日 下午9:52:45
 */

public class MyOrientationListener implements SensorEventListener {

	private SensorManager mSensorManager;
	private Context mContext;
	private Sensor mSensor;

	private float lastX;

	public MyOrientationListener(Context context) {
		this.mContext = context;
	}

	/**
	 * 开始监听
	 * 
	 * @Title: start void
	 */
	@SuppressWarnings("deprecation")
	public void start() {
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

		if (mSensorManager != null) {
			// 获取方向传感器
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}

		if (mSensor != null) {
			mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
		}
	}

	/**
	 * 结束监听
	 * 
	 * @Title: stop void
	 */
	public void stop() {
		mSensorManager.unregisterListener(this);
	}

	/**
	 * 数据发生改变
	 * 
	 * @Override Title: onSensorChanged
	 * @param event
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {

		// 判断传感器类型为方向传感器
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			float x = event.values[SensorManager.DATA_X];
			if (Math.abs(x - lastX) > 1.0) {
				// 使用回调更新UI
				if (mOnOrientationListener != null) {
					mOnOrientationListener.onRrientationChanged(x);
				}
			}
			lastX = x;
		}
	}

	/**
	 * 精度发生改变
	 * 
	 * @Override Title: onAccuracyChanged
	 * @param sensor
	 * @param accuracy
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	// 对外回调
	private OnOrientationListener mOnOrientationListener;

	public void setmOnOrientationListener(OnOrientationListener mOnOrientationListener) {
		this.mOnOrientationListener = mOnOrientationListener;
	}

	public interface OnOrientationListener {
		// 方向发生变化
		void onRrientationChanged(float x);
	}

}
