
package sliang.vocalbularybook.base.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.ButterKnife;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.base.activity.BaseActivity;
import sliang.vocalbularybook.base.intent.FragmentIntentFactory;
import sliang.vocalbularybook.base.intent.IntentListener;
import sliang.vocalbularybook.base.views.MessageDialog;



public abstract class BaseFragment extends Fragment {
    protected BaseActivity baseActivity;
    private IntentListener intentFactory;
    private View mEmptyView;

    protected View mContentView;
    private ViewGroup contentFl;
    private View mCommonLayout;
    private View titleBar;
    private ImageView actionBackBtn;
    private ImageView actionRightImg;
    private ImageView actionLeftImg;
    private TextView actionTitle;
    private TextView actionSubTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.baseActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFactory = new FragmentIntentFactory(this);//
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mCommonLayout = inflater.inflate(R.layout.common_activity_layout, container, false);
        contentFl = (ViewGroup) mCommonLayout.findViewById(R.id.content_fl);
        mContentView = inflater.inflate(getLayoutId(), contentFl, true);
        // ****************//
        ButterKnife.bind(this, mContentView);
        // ****************//
        initTitleBar(mCommonLayout);
        showTitleBar(false);
        initView();
        return mCommonLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void loadData();

    protected View getContentView() {
        return contentFl;
    }



    /**
     * 模拟后退
     */
    protected void popBackStack() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEmptyView = null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    /**
     * 去裁剪图片
     *
     * @param path 图片地址
     * @param requestUri 裁剪回调地址
     * @param size 裁剪大小
     */
    public void goToCropImage(String path, Uri requestUri, int size) {
        intentFactory.goToCropImage(path, requestUri, size);
    }

    public void goToView(String path) {
        intentFactory.goToView(path);
    }

    /**
     * 通过地址查看图片
     *
     * @param path 图片地址
     */
    public void goToView(String path, Class<?> cls) {
        intentFactory.goToView(path, cls);
    }

    /**
     * 单纯的页面跳转
     *
     * @param cls 跳转的页面
     */
    public void goToOthers(Class<?> cls) {
        intentFactory.goToOthers(cls);
    }

    /**
     * 页面跳转并关闭当前页面
     *
     * @param cls 跳转的页面
     */
    public void goToOthersF(Class<?> cls) {
        intentFactory.goToOthersF(cls);
    }

    /**
     * 带参数的页面跳转
     *
     * @param cls 跳转的页面
     * @param bundle 参数
     */
    public void goToOthers(Class<?> cls, Bundle bundle) {
        intentFactory.goToOthers(cls, bundle);
    }

    /**
     * 带参数的页面跳转并关闭当前页面
     *
     * @param cls 跳转的页面
     * @param bundle 参数
     */
    public void goToOthersF(Class<?> cls, Bundle bundle) {
        intentFactory.goToOthersF(cls, bundle);
    }

    /**
     * 带回调的页面跳转
     *
     * @param cls 跳转的页面
     * @param bundle 参数
     * @param requestCode 请求码
     */
    public void goToOthersForResult(Class<?> cls, Bundle bundle, int requestCode) {
        intentFactory.goToOthersForResult(cls, bundle, requestCode);
    }

    /**
     * 设置回调
     *
     * @param cls 回调的页面
     * @param bundle 参数
     * @param resultCode 返回码
     */
    public void backForResult(Class<?> cls, Bundle bundle, int resultCode) {
        intentFactory.backForResult(cls, bundle, resultCode);
    }

    /**
     * 让某一页面顶置
     *
     * @param bundle 参数
     */
    public void upToHome(Class<?> cls, Bundle bundle) {
        intentFactory.upToHome(cls, bundle);
    }

    /**
     * 让某一页面顶置
     */
    public void upToHome(Class<?> cls) {
        intentFactory.upToHome(cls);
    }

    public void homeAction() {
        intentFactory.homeAction();
    }

    /**
     * 跳转到网页
     *
     * @param url 网页地址
     */
    public void goToWeb(String url) {
        intentFactory.goToWeb(url);
    }

    /**
     * 打电话
     *
     * @param telePhoneNum 电话号码
     */
    public void goToCall(String telePhoneNum) {
        intentFactory.goToCall(telePhoneNum);
    }

    /**
     * 安装应用
     *
     * @param file
     */
    public void installApp(File file) {
        intentFactory.installApp(file);
    }

    public void goToPhoto(File file) {
        intentFactory.goToPhoto(file);
    }

    public void goToGallery() {
        intentFactory.goToGallery();
    }



    /**
     * 获取Activity基类对象
     *
     * @return BaseActivity对象
     */
    public BaseActivity getBaseActivity() {
        try {
            return (BaseActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void showTitleBar(boolean isShow) {
        if (titleBar != null) {
            titleBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    protected void setTitle(String title) {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
            actionTitle.setText(title);
        }
    }

    protected void showLeftImg(int rId) {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
            actionBackBtn.setVisibility(View.GONE);
            actionLeftImg.setVisibility(View.VISIBLE);
            actionLeftImg.setImageResource(rId);
        }
    }

    protected void showRightImg(int rId) {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
            actionSubTitle.setVisibility(View.GONE);
            actionRightImg.setVisibility(View.VISIBLE);
            actionRightImg.setImageResource(rId);
        }
    }

    protected void showRightText(String name) {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
            actionRightImg.setVisibility(View.GONE);
            actionSubTitle.setVisibility(View.VISIBLE);
            actionSubTitle.setText(name);
        }
    }

    protected void initTitleBar(View view) {
        titleBar = view.findViewById(R.id.title_bar);
        actionBackBtn = (ImageView) view.findViewById(R.id.action_back_btn);
        actionRightImg = (ImageView) view.findViewById(R.id.action_right_img);
        actionLeftImg = (ImageView) view.findViewById(R.id.action_left_img);
        actionTitle = (TextView) view.findViewById(R.id.action_title);
        actionSubTitle = (TextView) view.findViewById(R.id.action_sub_title);

        if (titleBar != null) {
            actionBackBtn.setVisibility(View.GONE);
        }
    }

    protected void showDialog(String title, String msg, String ok, String cancel,
                              DialogInterface.OnClickListener clickOk, DialogInterface.OnClickListener clickCancel,
                              DialogInterface.OnCancelListener onListener) {
        MessageDialog dialog = new MessageDialog(getContext());
        dialog.setTitle(title);
        dialog.setBtnYes(ok);
        dialog.setBtnNo(cancel);
        dialog.setMessage(msg);
        dialog.setOkListener(clickOk);
        dialog.setCancelListener(clickCancel);
        dialog.setOnCancelListener(onListener);
        dialog.show();
    }

    protected void showDialog(String msg, DialogInterface.OnClickListener clickOk) {
        MessageDialog dialog = new MessageDialog(getContext());
        dialog.setMessage(msg);
        dialog.setOkListener(clickOk);
        dialog.show();
    }

    protected void setRightImgOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        actionRightImg.setOnClickListener(rightImgOnClickListenner);
    }

    protected void setleftImgOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        actionLeftImg.setOnClickListener(rightImgOnClickListenner);
    }

    protected void setRightTextOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        actionSubTitle.setOnClickListener(rightImgOnClickListenner);
    }
}
