package sliang.vocalbularybook.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youdao.sdk.ydtranslate.Translate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.TranslateData;
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
            final TranslateData translateData = (TranslateData) model;
            String query = translateData.getQuery();
            Translate translate = translateData.getTranslate();
            String phonetic = translate.getPhonetic();
            List<String> translations = translate.getTranslations();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i = 0; i < translations.size(); i++) {
                stringBuilder.append(translations.get(i)+"  ");
            }
            viewHolder.queryTv.setText(query);
            viewHolder.phonogramTv.setText("/  "+phonetic+"  /");
            viewHolder.explainTv.setText(stringBuilder.toString());
            viewHolder.mainLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("translateData",translateData);
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
}
