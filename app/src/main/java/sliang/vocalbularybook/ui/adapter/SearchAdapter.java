package sliang.vocalbularybook.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sliang.vacalbularybook.Explain;
import sliang.vacalbularybook.StrangeWordBook;
import sliang.vacalbularybook.Word;
import sliang.vocalbularybook.DaoSession;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.StrangeWordBookDao;
import sliang.vocalbularybook.VocalbularyApplication;
import sliang.vocalbularybook.base.activity.BaseActivity;
import sliang.vocalbularybook.base.adapter.CommonRcvAdapter;
import sliang.vocalbularybook.base.adapter.item.AdapterItem;
import sliang.vocalbularybook.ui.WordDetailActivity;

/**
 * Created by Administrator on 2017/7/13.
 */

public class SearchAdapter extends CommonRcvAdapter {

    private BaseActivity baseActivity;
    public SearchAdapter(BaseActivity activity,List data) {
        super(data);
        baseActivity=activity;
    }

    @NonNull
    @Override
    public AdapterItem onCreateItem(Object type) {
        return new SearchItem();
    }

    class SearchItem implements AdapterItem {
        ViewHolder viewHolder;
        @Override
        public int getLayoutResId() {
            return R.layout.item_rv_search;
        }

        @Override
        public void onBindViews(View root) {
             viewHolder = new ViewHolder(root);
        }

        @Override
        public void onSetViews() {

        }

        @Override
        public void onUpdateViews(Object model, int position) {
            final Word word = (Word) model;
            String query = word.getName();
            final String phonetic = word.getPhonetic();
            List<Explain> translations = word.getTranslations();
            final List<String> explainStrings = word.getExplainStrings();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i = 0; i < explainStrings.size(); i++) {
                stringBuilder.append(explainStrings.get(i)+"  ");
            }
            viewHolder.queryTv.setText(query);
            viewHolder.phonogramTv.setText("/  "+phonetic+"  /");
            viewHolder.explainTv.setText(stringBuilder.toString());
            viewHolder.mainLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToStangeBook(word);
                    Bundle bundle = new Bundle();
                    bundle.putString("phonetic",  word.getUsPhonetic());
                    bundle.putString("query",  word.getName());
                    bundle.putString("speakUrl",  word.getWordVoice().getUSSpeakUrl());
                    bundle.putSerializable("translations", (Serializable) explainStrings);
                    baseActivity.goToOthers(WordDetailActivity.class,bundle);
                }
            });
        }

         class ViewHolder {
            @BindView(R.id.query_tv)
            TextView queryTv;
            @BindView(R.id.phonogram_tv)
            TextView phonogramTv;
            @BindView(R.id.explain_tv)
            TextView explainTv;
             @BindView(R.id.main_ll)
             LinearLayout mainLl;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void addToStangeBook(Word word) {
        DaoSession daoSession = VocalbularyApplication.getInstance().getDaoSession();
        StrangeWordBookDao strangeWordBookDao = daoSession.getStrangeWordBookDao();
        long count = strangeWordBookDao.count();
        StrangeWordBook strangeWordBook = new StrangeWordBook(count,System.currentTimeMillis(),1,word.getId());
        strangeWordBookDao.insert(strangeWordBook);
    }
}
