package sliang.vocalbularybook.base.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.base.app.AppManager;
import sliang.vocalbularybook.base.intent.ActivityIntentFactory;
import sliang.vocalbularybook.base.intent.IntentListener;
import sliang.vocalbularybook.base.mvp.IBaseView;
import sliang.vocalbularybook.base.views.MessageDialog;
import sliang.vocalbularybook.base.views.StateView;
import sliang.vocalbularybook.base.views.TitleBar;

/**
 * Created by qiang on 17-4-13.
 */

public abstract class BaseActivity<P> extends FragmentActivity implements IStateView,ITitleBar,IFlowControl,IntentListener,IBaseView {


    public static final String PARAM_INTENT = "intentData";
    public static final int UPLOAD_ALIAS = 2017;
    private P presenter;
    private StateView stateView;
    private TitleBar titleBar;
    private FrameLayout contentFl;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    private ActivityIntentFactory intentFactory;

    private Map<Integer , Boolean> clickFlags=new HashMap<Integer ,Boolean >();
    public void setClickEnable(int who,boolean isClick){
        clickFlags.put(who,isClick);
    }

    public boolean getClickEnable(int who){
        if(clickFlags.get(who)==null){
            return false;
        }

        return clickFlags.get(who);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initPresenter();
        setContentView(R.layout.common_activity_layout);
        contentFl = (FrameLayout) findViewById(R.id.content_fl);
        if(getLayoutId()!=0){
            View contentView = LayoutInflater.from(this).inflate(getLayoutId(), contentFl, true);
            ButterKnife.bind(this,contentView);
        }
        initTitleBar();
        showTitleBar(false);
        intentFactory = new ActivityIntentFactory(this);
        initView();
        initData(savedInstanceState);
        setListener();
        loadData();
    }

    protected  void initTitleBar(){
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        if (titleBar != null) {
            titleBar.setActionBackBtnOnClickListenner(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(titleBar.isBackBtnEnable()){
                        finish();
                    }

                }
            });
        }
    }


    protected abstract void initPresenter();

    public P getPresenter(){

        if(presenter!=null){
            return presenter;
        }else {
            throw new IllegalStateException("请检查是否有配置Presenter");
        }
    }

    public Context getContext() {
        return this;
    }


    public void doToast(String msg){
        doToast(msg,null);
    }

    public void doToast(int msg){
        doToast(getResources().getText(msg).toString(),null);
    }

    public void doToast(final String msg, String requestTag) {
        if(Thread.currentThread() == getMainLooper().getThread()){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




    public void showDialog(String title, String msg, String ok, String cancel,
                           DialogInterface.OnClickListener clickOk, DialogInterface.OnClickListener clickCancel,
                           DialogInterface.OnCancelListener onListener) {
        MessageDialog dialog = new MessageDialog(this);
        dialog.setTitle(title);
        dialog.setBtnYes(ok);
        dialog.setBtnNo(cancel);
        dialog.setMessage(msg);
        dialog.setOkListener(clickOk);
        dialog.setCancelListener(clickCancel);
        dialog.setOnCancelListener(onListener);
        dialog.show();
    }


    public void showDialog(String msg, DialogInterface.OnClickListener clickOk) {
        MessageDialog dialog = new MessageDialog(this);
        dialog.setMessage(msg);
        dialog.setOkListener(clickOk);
        dialog.show();
    }

    public boolean showTitleBar(boolean isShow){
        if(titleBar!=null){
            titleBar.setVisibility(isShow?View.VISIBLE:View.GONE);
            return true;
        }
        return false;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showEmpty() {
        if (stateView!=null)
            stateView.showEmpty();
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    @Override
    public void hideDividerLine(boolean isHide) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.hideDividerLine(isHide);
    }

    @Override
    public void showLeftImg(int rId) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.showLeftImg(rId);
    }

    @Override
    public void showRightImg(int rId) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.showRightImg(rId);
    }

    @Override
    public void showRightText(String name) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.showRightText(name);
    }

    @Override
    public void setRightTextColor(String color) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setRightTextColor(color);
    }

    @Override
    public void setActionBackBtnEnable(boolean enable) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setActionBackBtnEnable(enable);
    }

    @Override
    public void setActionBackBtnOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setActionBackBtnOnClickListenner(rightImgOnClickListenner);
    }

    @Override
    public void setRightImgOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setRightImgOnClickListenner(rightImgOnClickListenner);
    }

    @Override
    public void setleftImgOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setleftImgOnClickListenner(rightImgOnClickListenner);
    }

    @Override
    public void setRightTextOnClickListenner(View.OnClickListener rightImgOnClickListenner) {
        if(!showTitleBar(true)){
            return;
        }
        titleBar.setRightTextOnClickListenner(rightImgOnClickListenner);
    }

    @Override
    public void goToCropImage(String path, Uri requestUri, int size) {
        intentFactory.goToCropImage(path,requestUri,size);
    }

    @Override
    public void goToView(String path) {
        intentFactory.goToView(path);
    }

    @Override
    public void goToView(String path, Class<?> cls) {
        intentFactory.goToView(path,cls);
    }

    @Override
    public void goToOthers(Class<?> cls) {
        intentFactory.goToOthers(cls);
    }

    @Override
    public void goToOthersF(Class<?> cls) {
        intentFactory.goToOthersF(cls);
    }

    @Override
    public void goToOthers(Class<?> cls, Bundle bundle) {
        intentFactory.goToOthers(cls,bundle);
    }

    @Override
    public void goToOthersF(Class<?> cls, Bundle bundle) {
        intentFactory.goToOthersF(cls,bundle);
    }

    @Override
    public void goToOthersForResult(Class<?> cls, Bundle bundle, int requestCode) {
        intentFactory.goToOthersForResult(cls,bundle,requestCode);
    }

    @Override
    public void backForResult(Class<?> cls, Bundle bundle, int resultCode) {
        intentFactory.backForResult(cls,bundle,resultCode);
    }

    @Override
    public void upToHome(Class<?> cls, Bundle bundle) {
        intentFactory.upToHome(cls,bundle);
    }

    @Override
    public void upToHome(Class<?> cls) {
        intentFactory.upToHome(cls);
    }

    @Override
    public void homeAction() {
        intentFactory.homeAction();
    }

    @Override
    public void goToWeb(String url) {
        intentFactory.goToWeb(url);
    }

    @Override
    public void goToCall(String telePhoneNum) {
        intentFactory.goToCall(telePhoneNum);
    }

    @Override
    public void installApp(File file) {
        intentFactory.installApp(file);
    }

    @Override
    public void goToPhoto(File file) {
        intentFactory.goToPhoto(file);
    }

    @Override
    public void goToGallery() {
        intentFactory.goToGallery();
    }

    @Override
    public void toast(String msg, String requestTag) {
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(String requestTag) {

    }

    @Override
    public void cancelProgress(String requestTag) {

    }

    @Override
    public void loadDataSuccess(Object data, String requestTag, int resultCode) {

    }

    @Override
    public void loadDataError(Throwable e, String requestTag, int resultCode) {

    }
}
