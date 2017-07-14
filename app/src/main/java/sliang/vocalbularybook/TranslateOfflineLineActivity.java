package sliang.vocalbularybook;

/**
 * @(#)BrowserActivity.java, 2013年10月22日. Copyright 2013 Yodao, Inc. All rights
 *                           reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.youdao.localtransengine.EnLineTranslator;
import com.youdao.localtransengine.LanguageConvert;
import com.youdao.sdk.ydtranslate.Translate;

import java.util.ArrayList;
import java.util.List;

import sliang.vocalbularybook.utils.ToastUtils;

/**
 * @author lukun
 */
public class TranslateOfflineLineActivity extends Activity {

    // 评论列表
    private ListView questList;

    private TranslateAdapter adapter;

    private List<TranslateData> list = new ArrayList<TranslateData>();

    private EditText fanyiInputText;

    private InputMethodManager imm;

    private TextView fanyiBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(Activity.RESULT_OK);
        try {
            getWindow().requestFeature(Window.FEATURE_PROGRESS);
            getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_ON);
        } catch (Exception e) {}
        setContentView(R.layout.translate_line_offline_list);

        fanyiInputText = (EditText) findViewById(R.id.fanyiInputText);

        fanyiBtn = (TextView) findViewById(R.id.fanyiBtn);

        questList = (ListView) findViewById(R.id.commentList);

        imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

        View view = this.getLayoutInflater().inflate(R.layout.translate_head,
                questList, false);
        questList.addHeaderView(view);
        adapter = new TranslateAdapter(this, list);

        questList.setAdapter(adapter);

        fanyiBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                query();

            }
        });

        /*
         * 可以查词之前设置离线词库路径，若不设置，采用默认路径
         * Environment.getExternalStorageDirectory().getAbsolutePath() +
         * "/Youdao/localdict/"; 开发者应保证设置的路径或者默认路径下存在离线词库文件
         */
        // EnWordTranslator.initDictPath("/183/");
    }

    private void query() {
        String input = fanyiInputText.getText().toString();
        if (TextUtils.isEmpty(input)) {
            ToastUtils.show("请输入要查询的词");
        }
        Translate translate = EnLineTranslator.lookup(input, LanguageConvert.AUTO);
        if (translate == null) {
            ToastUtils.show("找不到翻译结果或者YoudaoApplication 未初始化");
            return;
        }
        TranslateData td = new TranslateData(System.currentTimeMillis(),
                translate);
        list.add(td);
        adapter.notifyDataSetChanged();
        questList.setSelection(list.size() - 1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                imm.hideSoftInputFromWindow(fanyiInputText.getWindowToken(), 0);
            }
        }, 100);
        fanyiInputText.setText("");
    }

    public void loginBack(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void finish() {
        super.finish();
    }



}
