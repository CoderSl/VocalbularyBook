package sliang.vocalbularybook;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.ydtranslate.EnWordTranslator;
import com.youdao.sdk.ydtranslate.Translate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import sliang.vacalbularybook.Explain;
import sliang.vacalbularybook.Translations;
import sliang.vacalbularybook.Word;
import sliang.vacalbularybook.WordVoice;
import sliang.vocalbularybook.utils.FileUtils;
import sliang.vocalbularybook.utils.ReflexUtils;

/**
 * Created by Administrator on 2017/7/12.
 */

public class VocalbularyApplication extends Application {
    private static VocalbularyApplication youAppction;
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



    public static VocalbularyApplication getInstance() {
        return youAppction;
    }

    private void initDatabase() {

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        checkDB();

        mHelper = new DaoMaster.DevOpenHelper(this,"diction.db", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        new Thread(new Runnable() {
            @Override
            public void run() {
                read("");
            }
        }).start();

    }

    private void checkDB() {
        try {
            File databasePath = getDatabasePath("diction.db");
            if(databasePath.exists()){
                return;
            }
            InputStream open = getResources().openRawResource(R.raw.diction);
            FileUtils.writeFileFromIS(databasePath.getAbsolutePath(),open,false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }


    /**
     *
     * 手动导入词库时需要，正常情况用不到
     *
     *  @param
     *
     *  @return
     *
     */
    public   void read(String filePath){
        long startTime = System.currentTimeMillis();
        Log.e("diction","start---------");
        BufferedSource bufferedSource = null;
        try {
//
//            String path = Environment.getExternalStorageDirectory().getPath();
//            File file = new File(path, filePath);
            File file = new File("/storage/emulated/0/dictionCopy.txt");
            Source source = Okio.source(file);
            bufferedSource = Okio.buffer(source);
            String read="";
            do{
                read = bufferedSource.readUtf8Line();
                if(read==""){
                    continue;
                }
                try {
                    Translate wordFromYoudao = findWordFromYoudao(read);
                    saveToDao(wordFromYoudao);
                }catch (Exception e){
                    continue;
                }


            }while (read!=null);

            long doneTime = System.currentTimeMillis();
            long duringTime= doneTime - startTime;
            Log.e("diction","done-----------------"+duringTime/1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedSource) {
                    bufferedSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * 从本地有道词典查询单词
     *
     *  @param
     *
     *  @return
     *
     */
    public Translate findWordFromYoudao(String input) {

        if (TextUtils.isEmpty(input)) {
            return null;
        }
        Translate translate = EnWordTranslator.lookupNative(input);
        if (translate == null) {
            return null;
        }


        return translate;
    }

    /**
     *
     * 保存到本地数据库
     *
     *  @param
     *
     *  @return
     *
     */
    private void saveToDao(Translate translate) {
        try{
            final ExplainDao explainDao = mDaoSession.getExplainDao();
            final TranslationsDao translationsDao = mDaoSession.getTranslationsDao();
            WordDao wordDao = mDaoSession.getWordDao();
            WordVoiceDao wordVoiceDao = mDaoSession.getWordVoiceDao();
            List<String> explains = translate.getExplains();
            List<String> translations = translate.getTranslations();
            final Word word = new Word();
            long wordId = wordDao.count();
            word.setId(wordId + 1);

            for (int i = 0; i < explains.size(); i++) {
                String s = explains.get(i);
                long id = word.getId();
                Explain explain = new Explain(id, s);
                explainDao.insert(explain);
            }

            for (int i = 0; i < translations.size(); i++) {
                String s = translations.get(i);
                long id = word.getId();
                Translations explain = new Translations(id, s);
                translationsDao.insert(explain);
            }

            String phonetic = translate.getPhonetic();
            String query = translate.getQuery();
            String ukPhonetic = translate.getUkPhonetic();
            String usPhonetic = translate.getUsPhonetic();
            String  speakUrl = (String) ReflexUtils.getClassFiled(Translate.class, translate, "speakUrl");
            String  UKSpeakUrl = (String) ReflexUtils.getClassFiled(Translate.class, translate, "UKSpeakUrl");
            String  USSpeakUrl = (String) ReflexUtils.getClassFiled(Translate.class, translate, "USSpeakUrl");
            String  resultSpeakUrl = (String) ReflexUtils.getClassFiled(Translate.class, translate, "resultSpeakUrl");
            long count = wordVoiceDao.count();
            WordVoice wordVoice = new WordVoice(count+1,speakUrl,UKSpeakUrl,USSpeakUrl,resultSpeakUrl);
            wordVoiceDao.insert(wordVoice);
            word.setWordVoiceId(wordVoice.getWordVoiceId());
            word.setName(query);
            word.setPhonetic(phonetic);
            word.setUkPhonetic(ukPhonetic);
            word.setUsPhonetic(usPhonetic);
            wordDao.insert(word);
        }catch (Exception e){}

    }
}
