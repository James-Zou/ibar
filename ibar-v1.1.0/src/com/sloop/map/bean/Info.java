/**
 * @Title: Info.java
 * @Package com.sloop.map.bean
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月18日 下午8:56:38
 * @version V1.0
 */

package com.sloop.map.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



import com.sloop.ibar.R;

/**
 * @ClassName: Info
 * @author sloop
 * @date 2015年3月18日 下午8:56:38
 */

public class Info implements Serializable{

	private static final long serialVersionUID = -1306992595693513858L;
	private double latitude; // 经度
	private double longtitude; // 维度
	private int imageId; // 显示在地图上面图片的Id
	private String name; // 商家名称
	private String diatance; // 距离
	private int zan; // 点赞的数量

	public static List<Info> infos = new ArrayList<Info>();

	static {
		infos.add(new Info(34.242652, 108.971171, R.drawable.a01, "英伦贵族小旅馆", "距离209米", 1456));
		infos.add(new Info(34.242952, 108.972171, R.drawable.a02, "沙井国际洗浴会所", "距离897米", 456));
		infos.add(new Info(34.242852, 108.973171, R.drawable.a03, "五环服装城", "距离249米", 1456));
		infos.add(new Info(34.242152, 108.971971, R.drawable.a04, "老米家泡馍小炒", "距离679米", 1456));
	}

	public Info(double latitude, double longtitude, int imageId, String name, String diatance, int zan) {
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.imageId = imageId;
		this.name = name;
		this.diatance = diatance;
		this.zan = zan;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiatance() {
		return diatance;
	}

	public void setDiatance(String diatance) {
		this.diatance = diatance;
	}

	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

}
