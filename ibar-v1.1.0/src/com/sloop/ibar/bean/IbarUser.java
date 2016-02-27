package com.sloop.ibar.bean;

import cn.bmob.im.bean.BmobChatUser;

/**
 * 趣吧用户信息
 * 
 * @author admin
 *
 *         包含的属性有：
 *
 *         默认属性： 用户名（与邮箱相同，用于登录） 密码 邮箱 昵称 头像（url）
 *
 *         扩展属性： 性别 头像（文件） 是否完善过用户资料
 */
public class IbarUser extends BmobChatUser {

	private static final long serialVersionUID = 1L;
	/** 性别 */
	private boolean sex;
	/** 是否完善信息 */
	private boolean Perfect;
	/**
	 * 注意，该条信息暂时不对手机客户端提供设置方法，只能通过服务器认证
	 */
	private boolean VIP;
	/** 标签 */
	private String[] tags;


	/**
	 * 创建一个新的实例 IbarUser. 
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
	 * 判断这个字符串是否包含于tag标签中
	 * @Title: isIncludeTag
	 * @param tag
	 * @return boolean	
	 * true 表示该集合中存在该标签
	 * false表示该集合中不存在该标签
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
