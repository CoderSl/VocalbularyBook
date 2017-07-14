
package sliang.vocalbularybook.base.intent;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.File;

import sliang.vocalbularybook.base.activity.BaseActivity;
import sliang.vocalbularybook.base.activity.BaseConstant;

/**
 * @author wjy
 */
public class IntentFactory implements IntentListener {
    private BaseActivity context;

    public IntentFactory(BaseActivity context) {
        this.context = context;
    }

    public void goToView(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            context.startActivity(intent);
        }
    }

    public void goToView(String path, Class<?> cls) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            Intent intent = new Intent(context, cls);
            intent.putExtra("imagePath", path);
            context.startActivity(intent);
        }
    }

    public void goToOthers(Class<?> cls) {
        goToOthers(cls, null);
    }

    public void goToOthersF(Class<?> cls) {
        goToOthers(cls);
        context.finish();
    }

    public void goToOthers(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(BaseConstant.PARAM_INTENT, bundle);
        context.startActivity(intent);
    }

    public void goToOthersF(Class<?> cls, Bundle bundle) {
        goToOthers(cls, bundle);
        context.finish();
    }

    public void upToHome(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(BaseConstant.PARAM_INTENT, bundle);
        context.startActivity(intent);
        context.finish();
    }

    public void upToHome(Class<?> cls) {
        upToHome(cls, null);
    }

    public void homeAction() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    public void goToWeb(String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    public void goToCall(String telePhoneNum) {
        if (TextUtils.isEmpty(telePhoneNum)) {
            return;
        }
        try {
            Uri uri = Uri.parse("tel:" + telePhoneNum);
            if (uri != null) {
                // Intent intent = new Intent(Intent.ACTION_CALL, uri);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ToastUtil.toast("该设备无法提供电话服务！");
        }
    }

    public void installApp(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    public void goToCropImage(String path, Uri requestUri, int size) {

    }

    public void goToOthersForResult(Class<?> cls, Bundle bundle, int requestCode) {

    }

    public void backForResult(Class<?> cls, Bundle bundle, int resultCode) {

    }

    public void goToPhoto(File file) {

    }

    public void goToGallery() {

    }

}
