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
 * 完善资料
 * 
 * @author admin
 *
 */
@SuppressLint({ "NewApi" })
public class UserInfoActivit___sad extends BaseActivity implements OnClickListener {
	private static final int 选择头像 = 101;
	private static final int 选择兴趣 = 102;
	/** 标签组 */
	private TagGroup mTagGroup;
	/** 颜色 */
	int color;
	/** 头像 */
	private ImageView perfect_iv_head;
	/** 昵称 */
	private EditText perfect_et_nickname;
	/** 完成按钮 */
	private Button perfect_bt_choose;
	/** 完成按钮 */
	private Button perfect_bt_over;
	/** 选择头像对话框 */
	private AlertDialog getHeadDialog;

	/** 头像 */
	private File headFile;
	/** 头像长宽值 */
	private int crop = 200;
	/** 用户 */
	private IbarUser ibarUser;
	/** 头像文件存储路径 */
	public String HeadPicPath;
	/** 图片加载器 */
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
	 * 数据&页面初始化
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
	 * 自动填充页面信息
	 * 
	 * @Title: autoFillPage
	 * @return void
	 */
	private void autoFillPage() {

		// 填充头像
		String avatar = ibarUser.getAvatar();

		if (!TextUtils.isEmpty(avatar)) {
			imageLoader.displayImage(avatar, perfect_iv_head, ImageLoadOptions.getOptions());
		} else {
			perfect_iv_head.setImageResource(R.drawable.head_bg);
		}

		// 填充昵称
		String nick = ibarUser.getNick();
		perfect_et_nickname.setText(nick);

		// 填充标签
		String tags[] = ibarUser.getTags();
		color = getResources().getColor(R.color.baseColor_blue);
		mTagGroup = (TagGroup) findViewById(R.id.tag_group_large);
		if (tags != null && tags.length > 0) {
			mTagGroup.setTags(tags);
		}
		mTagGroup.setBrightColor(color);
	}

	/**
	 * 点击事件监听器
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
	 * 选择用户头像
	 */
	private void chooseHeadImage() {

		if (getHeadDialog == null) {
			getHeadDialog = new AlertDialog.Builder(context).setItems(new String[] { "相机", "相册" }, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						intent.putExtra("output", Uri.fromFile(headFile));
						intent.putExtra("crop", "true");
						intent.putExtra("aspectX", 1);// 裁剪框比例
						intent.putExtra("aspectY", 1);
						intent.putExtra("outputX", crop);// 输出图片大小
						intent.putExtra("outputY", crop);
						startActivityForResult(intent, 101);
					} else {
						Intent intent = new Intent("android.intent.action.PICK");
						intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
						intent.putExtra("output", Uri.fromFile(headFile));
						intent.putExtra("crop", "true");
						intent.putExtra("aspectX", 1);// 裁剪框比例
						intent.putExtra("aspectY", 1);
						intent.putExtra("outputX", crop);// 输出图片大小
						intent.putExtra("outputY", crop);
						startActivityForResult(intent, 选择头像);
					}
				}
			}).create();
		}
		if (!getHeadDialog.isShowing()) {
			getHeadDialog.show();
		}
	}

	/**
	 * 获取头像结果
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
			if (requestCode == 选择头像) {
				uploadAvatar();
			} else if (requestCode == 选择兴趣) {
				// 更新兴趣信息
			}

			// 重新填充界面
		}
	}

	/**
	 * 上传头像
	 * 
	 * @Title: uploadAvatar void
	 */
	private void uploadAvatar() {
		final BmobFile bmobFile = new BmobFile(headFile);
		bmobFile.upload(context, new UploadFileListener() {

			@Override
			public void onSuccess() {
				String url = bmobFile.getFileUrl(context);

				toast("头像上传成功");
				ibarUser.setAvatar(url);
				imageLoader.displayImage(url, perfect_iv_head, ImageLoadOptions.getOptions());
			}

			@Override
			public void onFailure(int arg0, String msg) {
				toast("头像上传失败：" + msg);
			}
		});
	}

	/**
	 * 完成
	 */
	private void over() {
		String nickName = perfect_et_nickname.getText().toString().trim();
		String[] tags = mTagGroup.getTags();

		// 判断用户是否选择兴趣
		if (tags.length <= 0) {
			toast("请至少选择一个兴趣");
			return;
		}

		// 判断用户是否填写用户名
		if (TextUtils.isEmpty(nickName)) {
			toast("请填写一个昵称");
			return;
		}

		ibarUser.setTags(tags);
		ibarUser.setNick(nickName);
		ibarUser.setPerfect(true);
		updataUserInfo(); // 更新用户信息

		loadMainUI();
	}

	/**
	 * 更新用户信息
	 */
	private void updataUserInfo() {

		ibarUser.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				toast("用户信息更新成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("用户信息更新失败" + arg0 + arg1);
			}
		});
	}

}
