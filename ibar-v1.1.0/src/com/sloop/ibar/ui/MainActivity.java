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
 * ������תվ �����������ҳ���ҳ��ת��������ͬ��
 * 
 * @ClassName: MainActivity
 * @author sloop
 * @date 2015��3��7�� ����4:47:02
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	// ��ͼ���
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	// ��λ���
	private LocationClient mLocationClient;
	private MyLocationLintener mLocationListener;
	private boolean isFiestIn = true;
	private double mLatitude;
	private double mLongtitude;
	// �Զ��嶨λͼ��
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener mOrientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;
	// ���������
	private BitmapDescriptor mMarker;
	private View mMarkerLy;
	// �˵����
	private boolean menuIsClose = true;
	public int[] res = { R.id.menu0_main, R.id.menu1_find, R.id.menu2_info, R.id.menu3_get, R.id.menu4_square, R.id.menu5_set };
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private int space = 150; // ���˵���ť֮����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());// ��ʼ��context��Ϣ���÷���Ҫ��setContentView����֮ǰʵ��
		setContentView(R.layout.activity_main);

		initView(); // ��ʼ���������
		initMenu();// ��ʼ���˵�
		initLocation();// ��ʼ����λ
		initMarker(); // ��ʼ��������
		initMapListener();// ��ʼ����ͼ��ص��¼�������
	}

	// �������ڹ���ʼ***************************************************************************/
	@Override
	protected void onStart() {
		super.onStart();
		mBaiduMap.setMyLocationEnabled(true); // ������ͼ����λ
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
		mOrientationListener.start();// ��ʼ�������
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
		mOrientationListener.stop();// ֹͣ�������
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	// �������ڹ������***************************************************************************/

	// ��ʼ�������ʼ*****************************************************************************/

	/** ��ʼ��������� */
	private void initView() {
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu); // �������ű��� ������500m
	}

	/** ��ʼ���˵� */
	private void initMenu() {
		for (int i = 0; i < res.length; i++) {
			ImageView imageView = (ImageView) findViewById(res[i]);
			imageView.setOnClickListener(this);
			imageViewList.add(imageView);
		}
	}

	/** ��ʼ����λ */
	private void initLocation() {
		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationLintener();
		mLocationClient.registerLocationListener(mLocationListener);

		// ��λ���
		LocationClientOption option = new LocationClientOption();// ��mLocationClient��������
		option.setCoorType("bd09ll"); // ��������
		option.setIsNeedAddress(true); // �Ƿ񷵻ص�ǰλ��
		option.setOpenGps(true); // �Ƿ��GPS
		option.setScanSpan(1000); // ÿ���೤ʱ�����»�ȡһ�Σ�1�룩
		mLocationClient.setLocOption(option);

		// �������
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.gps_position);// ��ʼ��λ��ͼ��
		mOrientationListener = new MyOrientationListener(this);// ��ʼ������λ
		mOrientationListener.setmOnOrientationListener(new OnOrientationListener() {// ���÷���λ������
					@Override
					public void onRrientationChanged(float x) {
						mCurrentX = x;
					}
				});
	}

	/** ��ʼ�������� */
	private void initMarker() {
		mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		mMarkerLy = findViewById(R.id.marker_ly);
	}

	// ��ʼ���������*****************************************************************************/

	// ��ʼ����������ʼ*****************************************************************************/

	/** ��ʼ����ͼ��صļ����� */
	private void initMapListener() {
		// ���ø�����ĵ���¼�
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

				// �����ͼ����ĸ����ﵯ������
				InfoWindow infoWindow;
				TextView tv = new TextView(MainActivity.this);
				tv.setBackgroundResource(R.drawable.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(info.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));

				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 50; // ����ƫ����������ƫ��50px��
				// ����γ��ת��Ϊ��Ļ����ʵ�ʵ�λ��
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

				infoWindow = new InfoWindow(tv, ll, 0);
				mBaiduMap.showInfoWindow(infoWindow);
				mMarkerLy.setVisibility(View.VISIBLE);
				return true;
			}
		});
		// ���õ�ͼ����¼�
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
	 * �Զ���λ�ü�����
	 * 
	 * @ClassName: MyLocationLintener
	 * @author sloop
	 * @date 2015��3��19�� ����6:35:42
	 */
	private class MyLocationLintener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Builder builder = new MyLocationData.Builder();// ��ȡ������ �����ø������
			builder.accuracy(location.getRadius());// ����
			builder.latitude(location.getLatitude());// ��γ��
			builder.longitude(location.getLongitude());// ��γ��
			builder.direction(mCurrentX);// ����
			MyLocationData data = builder.build();
			mBaiduMap.setMyLocationData(data);

			// �Զ���ͼ��
			MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// ���¾�γ��
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			// ����ǵ�һ�λ�ȡ����λ��Ϣ�ͽ��û�λ������Ϊ��Ļ����
			if (isFiestIn) {
				loactionInCenter(location.getLatitude(), location.getLongitude());
				isFiestIn = false;
				Toast.makeText(MainActivity.this, "λ�ã�" + location.getAddrStr(), Toast.LENGTH_LONG).show();
			}
		}
	}

	/** ���������еľ�γ����Ϊ��Ļ���� */
	private void loactionInCenter(double mLatitude, double mLongtitude) {
		LatLng latLng = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}

	// ��ʼ������������*****************************************************************************/

	/**
	 * ��Ӹ������ʱû��ʹ�ã�
	 * 
	 * @Title: addOverlays void
	 */
	/*
	 * private void addOverlays(List<Info> infos) { mBaiduMap.clear(); //
	 * ��������һЩͼ�� LatLng latLng = null; Marker marker = null; OverlayOptions
	 * options;
	 * 
	 * for (Info info : infos) { // ��γ�� latLng = new LatLng(info.getLatitude(),
	 * info.getLongtitude()); // ͼ�� options = new
	 * MarkerOptions().position(latLng).icon(mMarker).zIndex(5); marker =
	 * (Marker) mBaiduMap.addOverlay(options); Bundle arg0 = new Bundle();
	 * arg0.putSerializable("info", info); marker.setExtraInfo(arg0); }
	 * 
	 * MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
	 * mBaiduMap.setMapStatus(msu); }
	 */

	// �˵��Լ������¼�����ʼ****************************************************************

	/** ����¼��������� */
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

	/** �л��˵� */
	private void toggleMenu() {
		if (menuIsClose) {
			openMenu();
		} else {
			closeMenu();
		}
	}

	/** �򿪲˵� */
	private void openMenu() {
		for (int i = res.length - 1; i >= 1; i--) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0f, i * space);
			animator.setDuration(300);
			animator.setStartDelay(80 * (res.length - i));
			animator.start();
		}
		menuIsClose = false;
	}

	/** �رղ˵� */
	private void closeMenu() {
		for (int i = res.length - 1; i >= 1; i--) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", i * space, 0f);
			animator.setDuration(300);
			animator.setStartDelay(80 * i);
			animator.start();
		}
		menuIsClose = true;
	}

	/** �Բ˵����ͷ��ؼ������¶��� */
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
	// �˵��Լ������¼��������****************************************************************
}
