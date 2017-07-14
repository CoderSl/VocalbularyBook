package sliang.vocalbularybook.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.youdao.sdk.ydtranslate.Translate;

import java.util.List;

import butterknife.BindView;
import sliang.vocalbularybook.R;
import sliang.vocalbularybook.TranslateData;
import sliang.vocalbularybook.base.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/13.
 */

public class WordDetailActivity extends BaseActivity {
    @BindView(R.id.query_tv)
    TextView queryTv;
    @BindView(R.id.phonogram_tv)
    TextView phonogramTv;
    @BindView(R.id.explain_tv)
    TextView explainTv;
    private TranslateData translateData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_word_detail;
    }

    @Override
    public void initView() {
        Bundle bundleExtra = getIntent().getBundleExtra(PARAM_INTENT);
         translateData = (TranslateData) bundleExtra.getSerializable("translateData");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {
        String query = translateData.getQuery();
        Translate translate = translateData.getTranslate();
        String phonetic = translate.getPhonetic();
        List<String> translations = translate.getTranslations();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < translations.size(); i++) {
            stringBuilder.append(translations.get(i)+"  ");
        }
        queryTv.setText(query);
        phonogramTv.setText("/  "+phonetic+"  /");
        explainTv.setText(stringBuilder.toString());
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void initPresenter() {

    }

}
