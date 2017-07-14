package sliang.vocalbularybook.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.youdao.sdk.ydtranslate.Translate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sliang.vacalbularybook.StrangeWordBook;
import sliang.vacalbularybook.Word;
import sliang.vocalbularybook.DaoSession;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.StrangeWordBookDao;
import sliang.vocalbularybook.VocalbularyApplication;
import sliang.vocalbularybook.base.fragment.BaseFragment;
import sliang.vocalbularybook.ui.adapter.SearchAdapter;

/**
 * Created by Administrator on 2017/7/13.
 */

public class StrangeBookFragment extends BaseFragment {

    @BindView(R.id.list_rv)
    RecyclerView listRv;
    private DaoSession daoSession;
    private SearchAdapter searchAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragmet_strange_book;
    }

    @Override
    protected void initView() {
        daoSession = VocalbularyApplication.getInstance().getDaoSession();
        searchAdapter = new SearchAdapter(getBaseActivity(),new ArrayList());
        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        listRv.setAdapter(searchAdapter);

        setListenner();
    }

    private void setListenner() {

    }

    @Override
    protected void loadData() {
        List<StrangeWordBook> list = queryFromDao();
//        ArrayList<Translate> translates = getTranslates(list);
        ArrayList<Word> words = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StrangeWordBook strangeWordBook = list.get(i);
            Word word = strangeWordBook.getWord();
            words.add(word);
        }
        showWord(words);
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
    private ArrayList<Translate> getTranslates(List<StrangeWordBook> words) {
        ArrayList<Translate> translates = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            StrangeWordBook strangeWordBook = words.get(i);
            Word word =strangeWordBook.getWord();
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
    public List queryFromDao( ) {
        StrangeWordBookDao strangeWordBookDao = daoSession.getStrangeWordBookDao();
        List<StrangeWordBook> strangeWordBooks = strangeWordBookDao.loadAll();
        return strangeWordBooks;
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
    private void showWord(List<Word> words) {
//        ArrayList<TranslateData> translateDatas = new ArrayList<>();
//        for (int i = 0; i < translates.size(); i++) {
//            Translate translate = translates.get(i);
//            TranslateData td = new TranslateData(
//                    System.currentTimeMillis(), translate);
//            translateDatas.add(td);
//        }

        searchAdapter.addData(words);
        searchAdapter.notifyDataSetChanged();
    }


}
