package com.sloop.ibar.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.sloop.ibar.R;
import com.sloop.ibar.bean.IbarUser;
import com.sloop.ibar.util.UserInfo.UserInfos;
import com.sloop.ibar.util.ViewAnim;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private View root; 						// 页面根节点
	private EditText et_email; 				// 邮箱
	private EditText et_password; 			// 密码
	private Button bt_login; 				// 登陆按钮
	private LinearLayout ll_register;		// 注册
	private IbarUser ibarUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	private void init() {
		root = findViewById(R.id.login_sv_root);
		et_email = (EditText) findViewById(R.id.login_et_email);
		et_password = (EditText) findViewById(R.id.login_et_password);
		bt_login = (Button) findViewById(R.id.login_bt_login);
		ll_register = (LinearLayout) findViewById(R.id.login_ll_register);
		ViewAnim.fadeIn(root, 1 * Second); // 淡入

		ibarUser = new IbarUser();
		et_email.setText(userInfo.getUserInfo(UserInfos.Email));	//自动填充
		
		bt_login.setOnClickListener(this);
		ll_register.setOnClickListener(this);
	}

	/**
	 * 点击事件处理器
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_bt_login:
			login();
			break;
		case R.id.login_ll_register:
			loadRegisterUI();
			break;
		}

	}
	
	
	/**
	 * 登陆
	 */
	private void login() {
		final String email = et_email.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		
		if (!check(email, password)) {		 // 数据检查
			return;
		}

		ibarUser.setUsername(email);
		ibarUser.setPassword(password);
		ibarUser.setEmail(email);
		ibarUser.login(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				toast("登录成功");
				userInfo.setUserInfo(UserInfos.Email, email);	//数据固化
				userInfo.setUserInfo(UserInfos.PSW, password);
				loadNextUI();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				toast("登录失败\n"+arg0+":"+arg1);
			}
		});
	}

	/**
	 * 根据配置信息打开下一个界面 （是否已经完善过资料？感应界面 ：完善资料页面）
	 */
	private void loadNextUI() {
		Toast.makeText(this, "打开下一个页面", 0).show();
		if (ibarUser.getPerfect()) {
			loadMainUI();	 // 如果已经完善过资料就打开感应页面
		} else {
			loadPerfectUI(); // 否则打开完善资料页面
		}
		finish(); 			// 将当前页面从任务栈移除
	}
	
	/*#***数据检查*********************************************************************************/

	/**
	 * 登陆或者注册前数据检查
	 * 
	 * @return true表示数据检查通过， false 表示数据检出啊未通过
	 */
	private boolean check(String mail, String password) {
		// 判断用户名或者密码是否为空
		if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
			toast("邮箱或者密码不能为空");
			return false;
		} else if (!checkMail(mail)) {
			toast("请检查邮箱地址是否错误");
			return false;
		} else if (!checkPassword(password)) {
			toast("密码为6~16位字母或者数字");
			return false;
		}
		return true;
	}

	/**
	 * 验证密码是否符合规则
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
