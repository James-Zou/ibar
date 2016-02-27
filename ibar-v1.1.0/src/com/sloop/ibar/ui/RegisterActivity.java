package com.sloop.ibar.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import cn.bmob.v3.listener.SaveListener;

import com.sloop.ibar.R;
import com.sloop.ibar.bean.IbarUser;
import com.sloop.ibar.util.ViewAnim;

/**
 * 用户注册页面
 * @author admin
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	public static final int SPLASH = 0;	//开启这个界面的上一个界面信息
	public static final int OTHERS = 1;
	
	private ScrollView root;			//页面根节点
	private Button login;				//登录
	private Button regist;				//注册
	private EditText et_email;			//邮箱
	private EditText et_password;		//密码
	private EditText et_repassword;		//确认密码
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}	
	
	/**
	 * 初始化
	 */
	private void init() {
		root = (ScrollView) findViewById(R.id.register_sv_root);
		login = (Button) findViewById(R.id.register_bt_login);
		regist = (Button) findViewById(R.id.register_bt_regist);
		
		et_email = (EditText) findViewById(R.id.register_et_email);
		et_password = (EditText) findViewById(R.id.register_et_password);
		et_repassword = (EditText) findViewById(R.id.register_et_repassword);
		
		ViewAnim.fadeIn(root, 1*Second);
		
		login.setOnClickListener(this);
		regist.setOnClickListener(this);
	}

	/**
	 * 点击事件处理中心
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_bt_login:
			loadLoginUI();
			finish();
			break;
		case R.id.register_bt_regist:
			regist();
			break;
		}
	}
	
	
	/**
	 * 注册
	 */
	private void regist() {
		
		final String email = et_email.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		String repassword = et_repassword.getText().toString().trim();
		
		if (!check(email, password, repassword)) {
			return;
		}
		
		IbarUser ibarUser = new IbarUser();
		ibarUser.setUsername(email);
		ibarUser.setPassword(password);
		ibarUser.setEmail(email);
		ibarUser.setPerfect(false);
		
		ibarUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				toast("完成注册");
				loadPerfectUI();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("注册失败"+arg0+":"+arg1);
			}
		});
	}

	/**
	 * 数据检查
	 * 在注册之前对数据进行检查
	 * @return
	 */
	private boolean check(String email, String password, String repassword) {
		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
			toast("邮箱或者密码不能为空");
			return false;
		}
		if (!checkMail(email)) {
			toast("请检查邮箱地址是否正确");
			return false;
		}
		if (!checkPassword(password)) {
			toast("密码不符合规则");
			return false;
		}
		if (!password.equals(repassword)) {
			toast("两次密码不一致");
			return false;
		}
		return true;
	}
	/**
	 * 验证密码是否符合规则（6到16位的数字或者字母）
	 * @param password		需要校验的密码
	 * @return				true(正确)		false(不正确)
	 */
	private boolean checkPassword(String password) {
		String regex = "[\\w]{6,16}";
		return password.matches(regex);
	}

	/**
	 * 验证邮箱是否正确
	 * @param email		邮箱地址
	 * @return			true(正确)		false(不正确)
	 */
	private boolean checkMail(String email) {
		//数字或者字母或者_出现3到12次 @ 数字或者字母出现一次或多次 (.字母出现一次或多次)出现1次到5次
		String reg = "[\\w]{3,20}@[\\w&&[^_]]+(\\.[a-zA-Z]+){1,5}";
		return email.matches(reg);
	}
	

}
