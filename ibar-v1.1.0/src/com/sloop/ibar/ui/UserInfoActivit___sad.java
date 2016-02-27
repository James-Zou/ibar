package com.sloop.ibar.ui;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sloop.ibar.R;
import com.sloop.ibar.bean.IbarUser;
import com.sloop.ibar.util.GetAppPath;
import com.sloop.ibar.util.ImageLoadOptions;
import com.sloop.widget.TagGroup;

/**
 * ��������
 * 
 * @author admin
 *
 */
@SuppressLint({ "NewApi" })
public class UserInfoActivit___sad extends BaseActivity implements OnClickListener {
	private static final int ѡ��ͷ�� = 101;
	private static final int ѡ����Ȥ = 102;
	/** ��ǩ�� */
	private TagGroup mTagGroup;
	/** ��ɫ */
	int color;
	/** ͷ�� */
	private ImageView perfect_iv_head;
	/** �ǳ� */
	private EditText perfect_et_nickname;
	/** ��ɰ�ť */
	private Button perfect_bt_choose;
	/** ��ɰ�ť */
	private Button perfect_bt_over;
	/** ѡ��ͷ��Ի��� */
	private AlertDialog getHeadDialog;

	/** ͷ�� */
	private File headFile;
	/** ͷ�񳤿�ֵ */
	private int crop = 200;
	/** �û� */
	private IbarUser ibarUser;
	/** ͷ���ļ��洢·�� */
	public String HeadPicPath;
	/** ͼƬ������ */
	public ImageLoader imageLoader = null;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		}
		init();
	};

	/**
	 * ����&ҳ���ʼ��
	 */
	private void init() {

		perfect_iv_head = (ImageView) findViewById(R.id.perfect_iv_head);
		perfect_et_nickname = (EditText) findViewById(R.id.perfect_et_nickname);
		perfect_bt_choose = (Button) findViewById(R.id.perfect_bt_choose);
		perfect_bt_over = (Button) findViewById(R.id.perfect_bt_over);

		perfect_bt_choose.setOnClickListener(this);
		perfect_iv_head.setOnClickListener(this);
		perfect_bt_over.setOnClickListener(this);

		BmobUserManager userManager = BmobUserManager.getInstance(context);
		ibarUser = userManager.getCurrentUser(IbarUser.class);
		HeadPicPath = GetAppPath.getAppFilePath(context, "/sloop/ibar/head/") + "head_" + ibarUser.getEmail() + ".png";
		headFile = new File(HeadPicPath);

		autoFillPage();
	}

	/**
	 * �Զ����ҳ����Ϣ
	 * 
	 * @Title: autoFillPage
	 * @return void
	 */
	private void autoFillPage() {

		// ���ͷ��
		String avatar = ibarUser.getAvatar();

		if (!TextUtils.isEmpty(avatar)) {
			imageLoader.displayImage(avatar, perfect_iv_head, ImageLoadOptions.getOptions());
		} else {
			perfect_iv_head.setImageResource(R.drawable.head_bg);
		}

		// ����ǳ�
		String nick = ibarUser.getNick();
		perfect_et_nickname.setText(nick);

		// ����ǩ
		String tags[] = ibarUser.getTags();
		color = getResources().getColor(R.color.baseColor_blue);
		mTagGroup = (TagGroup) findViewById(R.id.tag_group_large);
		if (tags != null && tags.length > 0) {
			mTagGroup.setTags(tags);
		}
		mTagGroup.setBrightColor(color);
	}

	/**
	 * ����¼�������
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.perfect_iv_head:
			chooseHeadImage();
			break;
		case R.id.perfect_bt_choose:
			break;
		case R.id.perfect_bt_over:
			over();
			break;

		default:
			break;
		}

	}

	/**
	 * ѡ���û�ͷ��
	 */
	private void chooseHeadImage() {

		if (getHeadDialog == null) {
			getHeadDialog = new AlertDialog.Builder(context).setItems(new String[] { "���", "���" }, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						intent.putExtra("output", Uri.fromFile(headFile));
						intent.putExtra("crop", "true");
						intent.putExtra("aspectX", 1);// �ü������
						intent.putExtra("aspectY", 1);
						intent.putExtra("outputX", crop);// ���ͼƬ��С
						intent.putExtra("outputY", crop);
						startActivityForResult(intent, 101);
					} else {
						Intent intent = new Intent("android.intent.action.PICK");
						intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
						intent.putExtra("output", Uri.fromFile(headFile));
						intent.putExtra("crop", "true");
						intent.putExtra("aspectX", 1);// �ü������
						intent.putExtra("aspectY", 1);
						intent.putExtra("outputX", crop);// ���ͼƬ��С
						intent.putExtra("outputY", crop);
						startActivityForResult(intent, ѡ��ͷ��);
					}
				}
			}).create();
		}
		if (!getHeadDialog.isShowing()) {
			getHeadDialog.show();
		}
	}

	/**
	 * ��ȡͷ����
	 * 
	 * @Override Title: onActivityResult
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == ѡ��ͷ��) {
				uploadAvatar();
			} else if (requestCode == ѡ����Ȥ) {
				// ������Ȥ��Ϣ
			}

			// ����������
		}
	}

	/**
	 * �ϴ�ͷ��
	 * 
	 * @Title: uploadAvatar void
	 */
	private void uploadAvatar() {
		final BmobFile bmobFile = new BmobFile(headFile);
		bmobFile.upload(context, new UploadFileListener() {

			@Override
			public void onSuccess() {
				String url = bmobFile.getFileUrl(context);

				toast("ͷ���ϴ��ɹ�");
				ibarUser.setAvatar(url);
				imageLoader.displayImage(url, perfect_iv_head, ImageLoadOptions.getOptions());
			}

			@Override
			public void onFailure(int arg0, String msg) {
				toast("ͷ���ϴ�ʧ�ܣ�" + msg);
			}
		});
	}

	/**
	 * ���
	 */
	private void over() {
		String nickName = perfect_et_nickname.getText().toString().trim();
		String[] tags = mTagGroup.getTags();

		// �ж��û��Ƿ�ѡ����Ȥ
		if (tags.length <= 0) {
			toast("������ѡ��һ����Ȥ");
			return;
		}

		// �ж��û��Ƿ���д�û���
		if (TextUtils.isEmpty(nickName)) {
			toast("����дһ���ǳ�");
			return;
		}

		ibarUser.setTags(tags);
		ibarUser.setNick(nickName);
		ibarUser.setPerfect(true);
		updataUserInfo(); // �����û���Ϣ

		loadMainUI();
	}

	/**
	 * �����û���Ϣ
	 */
	private void updataUserInfo() {

		ibarUser.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				toast("�û���Ϣ���³ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("�û���Ϣ����ʧ��" + arg0 + arg1);
			}
		});
	}

}
