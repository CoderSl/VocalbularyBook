package sliang.vocalbularybook;

import android.app.Application;

import com.youdao.sdk.app.YouDaoApplication;

/**
 * Created by Administrator on 2017/7/12.
 */

public class VocalbularyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initYoudao();
    }

    private void initYoudao() {
        //注册应用ID ，建议在应用启动时，初始化，所有功能的使用都需要该初始化，调用一次即可，demo中在TranslateActivity类中
        YouDaoApplication.init(this, "7fba5f3934f2572d");
    }
}
