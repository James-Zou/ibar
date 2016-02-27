/**
 * @Title: MainFragnent.java
 * @Package com.sloop.ibar.ui.fragment
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年3月6日 下午9:02:06
 * @version V1.0
 */

package com.sloop.ibar.ui.fragment;

import com.sloop.ibar.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: MainFragnent
 * @author sloop
 * @date 2015年3月6日 下午9:02:06
 */

public class AboutFragnent extends BaseFragment {

	/**
	 * 创建一个新的实例 MainFragnent. 
	 * @param context
	 */
	
	public AboutFragnent(Context context) {
		super(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_card_head, null);
		return view;
	}


}
