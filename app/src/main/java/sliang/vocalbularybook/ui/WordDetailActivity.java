package sliang.vocalbularybook.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import sliang.vocalbularybook.R;
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
    @BindView(R.id.speek_tv)
    TextView speekTv;
    private String speakUrl;
    private List<String> translations;
    private String query;
    private String phonetic;

    @Override
    public int getLayoutId() {
        return R.layout.activity_word_detail;
    }

    @Override
    public void initView() {
        Bundle bundleExtra = getIntent().getBundleExtra(PARAM_INTENT);
        translations = (List<String>) bundleExtra.getSerializable("translations");
         query = bundleExtra.getString("query");
         phonetic = bundleExtra.getString("phonetic");
        speakUrl=bundleExtra.getString("speakUrl");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {


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
        speekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Uri解析一个网络地址

                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    Uri uri = Uri.parse(speakUrl);
                    mediaPlayer.setDataSource(WordDetailActivity.this, uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initPresenter() {

    }

}
