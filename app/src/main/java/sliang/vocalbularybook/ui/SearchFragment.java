package sliang.vocalbularybook.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.EnWordTranslator;
import com.youdao.sdk.ydtranslate.Translate;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sliang.vacalbularybook.Explain;
import sliang.vacalbularybook.Translations;
import sliang.vacalbularybook.Word;
import sliang.vacalbularybook.WordVoice;
import sliang.vocalbularybook.DaoSession;
import sliang.vocalbularybook.ExplainDao;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.TranslationsDao;
import sliang.vocalbularybook.WordDao;
import sliang.vocalbularybook.WordVoiceDao;
import sliang.vocalbularybook.base.fragment.BaseFragment;
import sliang.vocalbularybook.ui.adapter.SearchAdapter;
import sliang.vocalbularybook.utils.ReflexUtils;
import sliang.vocalbularybook.utils.StringUtils;
import sliang.vocalbularybook.youdao.DemoApplication;
import sliang.vocalbularybook.youdao.TranslateData;

/**
 * Created by Administrator on 2017/7/13.
 */

public class SearchFragment extends BaseFragment {

    @BindView(R.id.fanyiInputText)
    EditText fanyiInputText;
    @BindView(R.id.fanyiBtn)
    TextView fanyiBtn;
    @BindView(R.id.translateInputBar)
    RelativeLayout translateInputBar;
    @BindView(R.id.list_rv)
    RecyclerView listRv;
    private DaoSession daoSession;
    private SearchAdapter searchAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        daoSession = DemoApplication.getInstance().getDaoSession();
        searchAdapter = new SearchAdapter(getBaseActivity(),new ArrayList());
        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        listRv.setAdapter(searchAdapter);

        setListenner();
    }

    private void setListenner() {

        fanyiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                read("");
            }
        });

        fanyiInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchWord();
            }
        });
    }

    @Override
    protected void loadData() {

    }


    /**
     *
     * 搜索单词
     *
     *  @param
     *
     *  @return
     *
     */

    private void searchWord() {

        String input = fanyiInputText.getText().toString();
        if(TextUtils.isEmpty(input)){
            refreshAdapter();
            return;
        }
        refreshAdapter();
        if(input!=null){
            input= StringUtils.delEnter(input);
            input= input.trim();
        }

        List words = queryFromDao(input);

        if(words!=null&&words.size()>0){
            try{
                ArrayList<Translate> translates = getTranslates(words);
                showWord(translates);
            }catch (Exception e){
                Log.e("sl",e.toString());
            }
            return;
        }

        Translate translate = findWordFromYoudao(input);
        if(translate!=null){
            Observable
                    .just(translate)
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Action1<Translate>() {
                        @Override
                        public void call(Translate translate) {
                                saveToDao(translate);
                        }
                    });

            ArrayList objects = new ArrayList<Translate>();
            objects.add(translate);
            showWord(objects);
            return;
        }

        findWordFromYun(input);

    }


    /**
     *
     * 从云端获取
     *
     *  @param
     *
     *  @return
     *
     */
    private void findWordFromYun(String input) {


        // 若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("youdao").from(Language.CHINESE).to(Language.ENGLISH).build();

        Translator translator = Translator.getInstance(tps);
        translator.lookup(input, new TranslateListener() {

            @Override
            public void onResult(Translate result, String input) {
                TranslateData td = new TranslateData(
                        System.currentTimeMillis(), result);
                ArrayList<Translate> translates = new ArrayList<>();
                translates.add(result);
                saveToDao(result);
                showWord(translates);
            }

            @Override
            public void onError(TranslateErrorCode error) {

            }
        });
    }


    /**
     *
     * 有道entity转本地entity
     *
     *  @param
     *
     *  @return
     *
     */
    @NonNull
    private ArrayList<Translate> getTranslates(List words) {
        ArrayList<Translate> translates = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word word = (Word) words.get(i);
            Translate translate = new Translate();
            translate.setPhonetic(word.getPhonetic());
            translate.setQuery(word.getName());
            translate.setExplains(word.getExplainStrings());
            translate.setTranslations(word.getExplainStrings());
            if(word.getWordVoice()!=null){
                translate.setUSSpeakUrl(word.getWordVoice().getUSSpeakUrl());
            }
            translates.add(translate);
        }
        return translates;
    }


    /**
     *
     * 刷新界面
     *
     *  @param
     *
     *  @return
     *
     */

    private void refreshAdapter() {
        searchAdapter.clear();
        searchAdapter.notifyDataSetChanged();
    }


    /**
     *
     * 从数据库查询信息
     *
     *  @param
     *
     *  @return
     *
     */
    public List queryFromDao(String input) {
        WordDao wordDao = daoSession.getWordDao();
        QueryBuilder<Word> wordQueryBuilder = wordDao.queryBuilder();
        List<Word> list = wordQueryBuilder.where(WordDao.Properties.Name.like(input+"%")).build().list();
        return list;
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
            final ExplainDao explainDao = daoSession.getExplainDao();
            final TranslationsDao translationsDao = daoSession.getTranslationsDao();
            WordDao wordDao = daoSession.getWordDao();
            WordVoiceDao wordVoiceDao = daoSession.getWordVoiceDao();
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


    /**
     *
     * 显示查询结果到界面
     *
     *  @param
     *
     *  @return
     *
     */
    private void showWord(List<Translate> translates) {
        ArrayList<TranslateData> translateDatas = new ArrayList<>();
        for (int i = 0; i < translates.size(); i++) {
            Translate translate = translates.get(i);
            TranslateData td = new TranslateData(
                    System.currentTimeMillis(), translate);
            translateDatas.add(td);
        }

        searchAdapter.addData(translateDatas);
        searchAdapter.notifyDataSetChanged();
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
        Log.e("sl","start---------");
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
            Log.e("sl","done-----------------"+duringTime/1000);
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
}
