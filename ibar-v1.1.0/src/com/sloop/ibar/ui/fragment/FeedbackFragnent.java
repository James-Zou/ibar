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

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.sloop.ibar.R;
import com.sloop.ibar.bean.Feedback;

/**
 * @ClassName: MainFragnent
 * @author sloop
 * @date 2015年3月6日 下午9:02:06
 */

public class FeedbackFragnent extends BaseFragment {
	private EditText feedback_descrip;
	private EditText feedback_contact;
	private Button submit;
	
	/**
	 * 创建一个新的实例 MainFragnent. 
	 * @param context
	 */
	
	public FeedbackFragnent(Context context) {
		super(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_feedback, null);
		feedback_descrip = (EditText) view.findViewById(R.id.feedback_descrip);
		feedback_contact = (EditText) view.findViewById(R.id.feedback_contact);
		submit = (Button) view.findViewById(R.id.feedback_submit);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String descrip = feedback_descrip.getText().toString().trim();
				String contact = feedback_contact.getText().toString().trim();
				
				if (TextUtils.isDigitsOnly(descrip)) {
					toast("问题描述不能为空");
					return;
				}
				
				Feedback feedback = new Feedback(descrip, contact);
				feedback.save(context, new SaveListener() {
					
					@Override
					public void onSuccess() {
						toast("问题已提交\n感谢您的反馈");
						feedback_contact.setText("");
						feedback_descrip.setText("");
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						toast("问题提交失败\n请稍后再试");
					}
				});
			}
		});
		
		return view;
	}


}
