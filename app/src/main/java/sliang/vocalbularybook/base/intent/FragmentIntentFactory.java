package sliang.vocalbularybook.base.intent;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

import sliang.vocalbularybook.base.activity.BaseActivity;
import sliang.vocalbularybook.base.activity.BaseConstant;

/**
 * Fragment页面跳转
 * 
 * @author wjy
 * 
 */
public class FragmentIntentFactory extends IntentFactory {
	private Fragment fragment;

	public FragmentIntentFactory(Fragment fragment) {
		super((BaseActivity) fragment.getActivity());
		this.fragment = fragment;
	}

	public void goToCropImage(String path, Uri requestUri, int size) {
		File file = new File(path);
		if (file.exists()) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(file), "image/*");
			// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", size);
			intent.putExtra("outputY", size);
			intent.putExtra("return-data", false);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra(MediaStore.EXTRA_OUTPUT, requestUri);
			fragment.startActivityForResult(intent, BaseConstant.CROP_PIC);
		}
	}

	public void goToOthersForResult(Class<?> cls, Bundle bundle, int requestCode) {
		Intent intent = new Intent(fragment.getActivity(), cls);
		intent.putExtra(BaseConstant.PARAM_INTENT, bundle);
		fragment.startActivityForResult(intent, requestCode);
	}

	public void backForResult(Class<?> cls, Bundle bundle, int resultCode) {
		Intent intent = new Intent();
		if (cls != null) {
			intent.setClass(fragment.getActivity(), cls);
		}
		intent.putExtra(BaseConstant.PARAM_INTENT, bundle);
		fragment.getActivity().setResult(resultCode, intent);
		fragment.getActivity().finish();

	}

	public void goToPhoto(File file) {
		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			fragment.startActivityForResult(intent, BaseConstant.TAKE_PHOTOS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goToGallery() {
		try {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			fragment.startActivityForResult(intent, BaseConstant.SELECT_PICS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
