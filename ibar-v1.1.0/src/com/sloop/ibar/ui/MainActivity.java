package com.sloop.ibar.ui;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationData.Builder;
import com.baidu.mapapi.model.LatLng;
import com.sloop.ibar.R;
import com.sloop.map.bean.Info;
import com.sloop.map.listener.MyOrientationListener;
import com.sloop.map.listener.MyOrientationListener.OnOrientationListener;

/**
 * 核心中转站 负责调整各个页面的页面转换和数据同步
 * 
 * @ClassName: MainActivity
 * @author sloop
 * @date 2015年3月7日 下午4:47:02
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	// 地图相关
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	// 定位相关
	private LocationClient mLocationClient;
	private MyLocationLintener mLocationListener;
	private boolean isFiestIn = true;
	private double mLatitude;
	private double mLongtitude;
	// 自定义定位图标
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener mOrientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;
	// 覆盖物相关
	private BitmapDescriptor mMarker;
	private View mMarkerLy;
	// 菜单相关
	private boolean menuIsClose = true;
	public int[] res = { R.id.menu0_main, R.id.menu1_find, R.id.menu2_info, R.id.menu3_get, R.id.menu4_square, R.id.menu5_set };
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private int space = 150; // 主菜单按钮之间间隔

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());// 初始化context信息，该方法要再setContentView方法之前实现
		setContentView(R.layout.activity_main);

		initView(); // 初始化界面组件
		initMenu();// 初始化菜单
		initLocation();// 初始化定位
		initMarker(); // 初始化覆盖物
		initMapListener();// 初始化地图相关的事件监听器
	}

	// 生命周期管理开始***************************************************************************/
	@Override
	protected void onStart() {
		super.onStart();
		mBaiduMap.setMyLocationEnabled(true); // 开启地图允许定位
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
		mOrientationListener.start();// 开始方向监听
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		mOrientationListener.stop();// 停止方向监听
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	// 生命周期管理结束***************************************************************************/

	// 初始化组件开始*****************************************************************************/

	/** 初始化界面组件 */
	private void initView() {
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu); // 设置缩放比例 大致是500m
	}

	/** 初始化菜单 */
	private void initMenu() {
		for (int i = 0; i < res.length; i++) {
			ImageView imageView = (ImageView) findViewById(res[i]);
			imageView.setOnClickListener(this);
			imageViewList.add(imageView);
		}
	}

	/** 初始化定位 */
	private void initLocation() {
		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationLintener();
		mLocationClient.registerLocationListener(mLocationListener);

		// 定位相关
		LocationClientOption option = new LocationClientOption();// 对mLocationClient进行配置
		option.setCoorType("bd09ll"); // 坐标类型
		option.setIsNeedAddress(true); // 是否返回当前位置
		option.setOpenGps(true); // 是否打开GPS
		option.setScanSpan(1000); // 每隔多长时间重新获取一次（1秒）
		mLocationClient.setLocOption(option);

		// 方向相关
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.gps_position);// 初始化位置图标
		mOrientationListener = new MyOrientationListener(this);// 初始化方向定位
		mOrientationListener.setmOnOrientationListener(new OnOrientationListener() {// 设置方向定位监听器
					@Override
					public void onRrientationChanged(float x) {
						mCurrentX = x;
					}
				});
	}

	/** 初始化覆盖物 */
	private void initMarker() {
		mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		mMarkerLy = findViewById(R.id.marker_ly);
	}

	// 初始化组件结束*****************************************************************************/

	// 初始化监听器开始*****************************************************************************/

	/** 初始化地图相关的监听器 */
	private void initMapListener() {
		// 设置覆盖物的点击事件
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Bundle extraInfo = marker.getExtraInfo();
				Info info = (Info) extraInfo.getSerializable("info");
				ImageView iv = (ImageView) mMarkerLy.findViewById(R.id.info_img);
				TextView distance = (TextView) mMarkerLy.findViewById(R.id.info_distance);
				TextView name = (TextView) mMarkerLy.findViewById(R.id.info_name);
				TextView zan = (TextView) mMarkerLy.findViewById(R.id.info_zan);
				iv.setImageResource(info.getImageId());
				distance.setText(info.getDiatance());
				name.setText(info.getName());
				zan.setText(info.getZan() + "");

				// 点击地图上面的覆盖物弹出名称
				InfoWindow infoWindow;
				TextView tv = new TextView(MainActivity.this);
				tv.setBackgroundResource(R.drawable.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(info.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));

				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 50; // 设置偏移量（向上偏移50px）
				// 将经纬度转换为屏幕上面实际的位置
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

				infoWindow = new InfoWindow(tv, ll, 0);
				mBaiduMap.showInfoWindow(infoWindow);
				mMarkerLy.setVisibility(View.VISIBLE);
				return true;
			}
		});
		// 设置地图点击事件
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mMarkerLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();
			}
		});
	}

	/**
	 * 自定义位置监听器
	 * 
	 * @ClassName: MyLocationLintener
	 * @author sloop
	 * @date 2015年3月19日 下午6:35:42
	 */
	private class MyLocationLintener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Builder builder = new MyLocationData.Builder();// 获取创建器 并配置各类参数
			builder.accuracy(location.getRadius());// 精度
			builder.latitude(location.getLatitude());// 经纬度
			builder.longitude(location.getLongitude());// 经纬度
			builder.direction(mCurrentX);// 方向
			MyLocationData data = builder.build();
			mBaiduMap.setMyLocationData(data);

			// 自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			// 如果是第一次获取到定位信息就将用户位置设置为屏幕中心
			if (isFiestIn) {
				loactionInCenter(location.getLatitude(), location.getLongitude());
				isFiestIn = false;
				Toast.makeText(MainActivity.this, "位置：" + location.getAddrStr(), Toast.LENGTH_LONG).show();
			}
		}
	}

	/** 将输入其中的经纬度作为屏幕中心 */
	private void loactionInCenter(double mLatitude, double mLongtitude) {
		LatLng latLng = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}

	// 初始化监听器结束*****************************************************************************/

	/**
	 * 添加覆盖物（暂时没有使用）
	 * 
	 * @Title: addOverlays void
	 */
	/*
	 * private void addOverlays(List<Info> infos) { mBaiduMap.clear(); //
	 * 清除上面的一些图层 LatLng latLng = null; Marker marker = null; OverlayOptions
	 * options;
	 * 
	 * for (Info info : infos) { // 经纬度 latLng = new LatLng(info.getLatitude(),
	 * info.getLongtitude()); // 图标 options = new
	 * MarkerOptions().position(latLng).icon(mMarker).zIndex(5); marker =
	 * (Marker) mBaiduMap.addOverlay(options); Bundle arg0 = new Bundle();
	 * arg0.putSerializable("info", info); marker.setExtraInfo(arg0); }
	 * 
	 * MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
	 * mBaiduMap.setMapStatus(msu); }
	 */

	// 菜单以及按键事件处理开始****************************************************************

	/** 点击事件处理中心 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu0_main:
			toggleMenu();
			break;

		case R.id.menu2_info:
			loadUserInfoUI();
			break;

		case R.id.menu5_set:
			loadSettingUI();
			break;
		default:
			break;
		}
	}

	/** 切换菜单 */
	private void toggleMenu() {
		if (menuIsClose) {
			openMenu();
		} else {
			closeMenu();
		}
	}

	/** 打开菜单 */
	private void openMenu() {
		for (int i = res.length - 1; i >= 1; i--) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0f, i * space);
			animator.setDuration(300);
			animator.setStartDelay(80 * (res.length - i));
			animator.start();
		}
		menuIsClose = false;
	}

	/** 关闭菜单 */
	private void closeMenu() {
		for (int i = res.length - 1; i >= 1; i--) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", i * space, 0f);
			animator.setDuration(300);
			animator.setStartDelay(80 * i);
			animator.start();
		}
		menuIsClose = true;
	}

	/** 对菜单键和返回键的重新定义 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
	}
	// 菜单以及按键事件处理结束****************************************************************
}
