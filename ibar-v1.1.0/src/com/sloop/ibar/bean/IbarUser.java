package com.sloop.ibar.bean;

import cn.bmob.im.bean.BmobChatUser;

/**
 * Ȥ���û���Ϣ
 * 
 * @author admin
 *
 *         �����������У�
 *
 *         Ĭ�����ԣ� �û�������������ͬ�����ڵ�¼�� ���� ���� �ǳ� ͷ��url��
 *
 *         ��չ���ԣ� �Ա� ͷ���ļ��� �Ƿ����ƹ��û�����
 */
public class IbarUser extends BmobChatUser {

	private static final long serialVersionUID = 1L;
	/** �Ա� */
	private boolean sex;
	/** �Ƿ�������Ϣ */
	private boolean Perfect;
	/**
	 * ע�⣬������Ϣ��ʱ�����ֻ��ͻ����ṩ���÷�����ֻ��ͨ����������֤
	 */
	private boolean VIP;
	/** ��ǩ */
	private String[] tags;


	/**
	 * ����һ���µ�ʵ�� IbarUser. 
	 */
	public IbarUser() {
		VIP=false;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	/**
	 * �ж�����ַ����Ƿ������tag��ǩ��
	 * @Title: isIncludeTag
	 * @param tag
	 * @return boolean	
	 * true ��ʾ�ü����д��ڸñ�ǩ
	 * false��ʾ�ü����в����ڸñ�ǩ
	 */
	public boolean isIncludeTag(String tag){
		
		for (int i = 0; i < tags.length; i++) {
			if (tags[i].equals(tag)) {
				return true;
			}
		}
		return false;
	}
	

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public boolean getPerfect() {
		return Perfect;
	}

	public void setPerfect(boolean perfect) {
		Perfect = perfect;
	}




}
