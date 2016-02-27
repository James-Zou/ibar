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
	private View root; 						// ҳ����ڵ�
	private EditText et_email; 				// ����
	private EditText et_password; 			// ����
	private Button bt_login; 				// ��½��ť
	private LinearLayout ll_register;		// ע��
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
		ViewAnim.fadeIn(root, 1 * Second); // ����

		ibarUser = new IbarUser();
		et_email.setText(userInfo.getUserInfo(UserInfos.Email));	//�Զ����
		
		bt_login.setOnClickListener(this);
		ll_register.setOnClickListener(this);
	}

	/**
	 * ����¼�������
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
	 * ��½
	 */
	private void login() {
		final String email = et_email.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		
		if (!check(email, password)) {		 // ���ݼ��
			return;
		}

		ibarUser.setUsername(email);
		ibarUser.setPassword(password);
		ibarUser.setEmail(email);
		ibarUser.login(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				toast("��¼�ɹ�");
				userInfo.setUserInfo(UserInfos.Email, email);	//���ݹ̻�
				userInfo.setUserInfo(UserInfos.PSW, password);
				loadNextUI();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				toast("��¼ʧ��\n"+arg0+":"+arg1);
			}
		});
	}

	/**
	 * ����������Ϣ����һ������ ���Ƿ��Ѿ����ƹ����ϣ���Ӧ���� ����������ҳ�棩
	 */
	private void loadNextUI() {
		Toast.makeText(this, "����һ��ҳ��", 0).show();
		if (ibarUser.getPerfect()) {
			loadMainUI();	 // ����Ѿ����ƹ����Ͼʹ򿪸�Ӧҳ��
		} else {
			loadPerfectUI(); // �������������ҳ��
		}
		finish(); 			// ����ǰҳ�������ջ�Ƴ�
	}
	
	/*#***���ݼ��*********************************************************************************/

	/**
	 * ��½����ע��ǰ���ݼ��
	 * 
	 * @return true��ʾ���ݼ��ͨ���� false ��ʾ���ݼ����δͨ��
	 */
	private boolean check(String mail, String password) {
		// �ж��û������������Ƿ�Ϊ��
		if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
			toast("����������벻��Ϊ��");
			return false;
		} else if (!checkMail(mail)) {
			toast("���������ַ�Ƿ����");
			return false;
		} else if (!checkPassword(password)) {
			toast("����Ϊ6~16λ��ĸ��������");
			return false;
		}
		return true;
	}

	/**
	 * ��֤�����Ƿ���Ϲ���
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
