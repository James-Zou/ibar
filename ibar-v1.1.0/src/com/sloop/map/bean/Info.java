/**
 * @Title: Info.java
 * @Package com.sloop.map.bean
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��3��18�� ����8:56:38
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
 * @date 2015��3��18�� ����8:56:38
 */

public class Info implements Serializable{

	private static final long serialVersionUID = -1306992595693513858L;
	private double latitude; // ����
	private double longtitude; // ά��
	private int imageId; // ��ʾ�ڵ�ͼ����ͼƬ��Id
	private String name; // �̼�����
	private String diatance; // ����
	private int zan; // ���޵�����

	public static List<Info> infos = new ArrayList<Info>();

	static {
		infos.add(new Info(34.242652, 108.971171, R.drawable.a01, "Ӣ�׹���С�ù�", "����209��", 1456));
		infos.add(new Info(34.242952, 108.972171, R.drawable.a02, "ɳ������ϴԡ����", "����897��", 456));
		infos.add(new Info(34.242852, 108.973171, R.drawable.a03, "�廷��װ��", "����249��", 1456));
		infos.add(new Info(34.242152, 108.971971, R.drawable.a04, "���׼�����С��", "����679��", 1456));
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
