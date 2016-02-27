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
 * �û�ע��ҳ��
 * @author admin
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	public static final int SPLASH = 0;	//��������������һ��������Ϣ
	public static final int OTHERS = 1;
	
	private ScrollView root;			//ҳ����ڵ�
	private Button login;				//��¼
	private Button regist;				//ע��
	private EditText et_email;			//����
	private EditText et_password;		//����
	private EditText et_repassword;		//ȷ������
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}	
	
	/**
	 * ��ʼ��
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
	 * ����¼���������
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
	 * ע��
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
				toast("���ע��");
				loadPerfectUI();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("ע��ʧ��"+arg0+":"+arg1);
			}
		});
	}

	/**
	 * ���ݼ��
	 * ��ע��֮ǰ�����ݽ��м��
	 * @return
	 */
	private boolean check(String email, String password, String repassword) {
		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
			toast("����������벻��Ϊ��");
			return false;
		}
		if (!checkMail(email)) {
			toast("���������ַ�Ƿ���ȷ");
			return false;
		}
		if (!checkPassword(password)) {
			toast("���벻���Ϲ���");
			return false;
		}
		if (!password.equals(repassword)) {
			toast("�������벻һ��");
			return false;
		}
		return true;
	}
	/**
	 * ��֤�����Ƿ���Ϲ���6��16λ�����ֻ�����ĸ��
	 * @param password		��ҪУ�������
	 * @return				true(��ȷ)		false(����ȷ)
	 */
	private boolean checkPassword(String password) {
		String regex = "[\\w]{6,16}";
		return password.matches(regex);
	}

	/**
	 * ��֤�����Ƿ���ȷ
	 * @param email		�����ַ
	 * @return			true(��ȷ)		false(����ȷ)
	 */
	private boolean checkMail(String email) {
		//���ֻ�����ĸ����_����3��12�� @ ���ֻ�����ĸ����һ�λ��� (.��ĸ����һ�λ���)����1�ε�5��
		String reg = "[\\w]{3,20}@[\\w&&[^_]]+(\\.[a-zA-Z]+){1,5}";
		return email.matches(reg);
	}
	

}
