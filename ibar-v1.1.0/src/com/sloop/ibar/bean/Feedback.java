/**
 * @Title: Feedback.java
 * @Package com.sloop.ibar.bean
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��3��8�� ����4:44:07
 * @version V1.0
 */

package com.sloop.ibar.bean;

import cn.bmob.v3.BmobObject;

/**
 * @ClassName: Feedback
 * @author sloop
 * @date 2015��3��8�� ����4:44:07
 */

public class Feedback extends BmobObject {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��������
	 */
	private String description;
	
	/**
	 * ��ϵ��ʽ
	 */
	private String contact;

	public Feedback(String description, String contact) {
		this.description = description;
		this.contact = contact;
	}

}
