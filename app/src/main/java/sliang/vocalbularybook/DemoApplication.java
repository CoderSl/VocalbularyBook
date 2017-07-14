/**
 * @(#)DemoApplication.java, 2015年4月3日. Copyright 2012 Yodao, Inc. All rights
 *                           reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package sliang.vocalbularybook;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.youdao.sdk.app.YouDaoApplication;

import java.io.File;

/**
 * @author lukun
 */
public class DemoApplication extends Application {

    private static DemoApplication youAppction;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        YouDaoApplication.init(this,"7fba5f3934f2572d");//创建应用，每个应用都会有一个Appid，绑定对应的翻译服务实例，即可使用
        youAppction = this;
        initDatabase();
    }



    public static DemoApplication getInstance() {
        return youAppction;
    }

    private void initDatabase() {

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"/storage/emulated/0/diction.db", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }

}
