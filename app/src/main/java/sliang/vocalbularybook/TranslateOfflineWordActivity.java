package sliang.vocalbularybook;

/**
 * @(#)BrowserActivity.java, 2013年10月22日. Copyright 2013 Yodao, Inc. All
 *                           rights reserved. YODAO PROPRIETARY/CONFIDENTIAL.
 *                           Use is subject to license terms.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.youdao.sdk.ydtranslate.EnWordTranslator;
import com.youdao.sdk.ydtranslate.Translate;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import sliang.vacalbularybook.Explain;
import sliang.vacalbularybook.Translations;
import sliang.vacalbularybook.Word;
import sliang.vocalbularybook.utils.ToastUtils;


/**
 * @author lukun
 */
public class TranslateOfflineWordActivity extends Activity {

	// 评论列表
	private ListView questList;

	private TranslateAdapter adapter;

	private List<TranslateData> list = new ArrayList<TranslateData>();

	private EditText fanyiInputText;

	private InputMethodManager imm;

	private TextView fanyiBtn;
	private DaoSession daoSession;

	private long wordId=1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(Activity.RESULT_OK);
		try {
			getWindow().requestFeature(Window.FEATURE_PROGRESS);
			getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
					Window.PROGRESS_VISIBILITY_ON);
		} catch (Exception e) {
		}
		setContentView(R.layout.translate_list);
		daoSession = DemoApplication.getInstance().getDaoSession();
		fanyiInputText = (EditText) findViewById(R.id.fanyiInputText);

		fanyiBtn = (TextView) findViewById(R.id.fanyiBtn);

		questList = (ListView) findViewById(R.id.commentList);

		imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

		View view = this.getLayoutInflater().inflate(R.layout.translate_head,
				questList, false);
		questList.addHeaderView(view);
		adapter = new TranslateAdapter(this, list);

		questList.setAdapter(adapter);

		fanyiBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String input = fanyiInputText.getText().toString();
				Word word = queryFromDao(input);
				Translate translate = new Translate();
				translate.setQuery(word.getName());
				translate.setExplains(word.getExplainStrings());
				translate.setTranslations(word.getExplainStrings());
				showWord(translate);
				if(word==null){
					findAndSaveWord(input);
				}


//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						read("dictionCopy.txt");
//					}
//				}).start();

			}
		});
		
		/*
		 * 可以查词之前设置离线词库路径，若不设置，采用默认路径
		 * Environment.getExternalStorageDirectory().getAbsolutePath() +
		 * "/Youdao/localdict/"; 开发者应保证设置的路径或者默认路径下存在离线词库文件
		 */
 	 EnWordTranslator.initDictPath("/Youdao/localdict/");
 	 
 	 //离线包下载下来，需要解压离线文件，执行一次即可
 	 if(!EnWordTranslator.isInited()){
  		EnWordTranslator.init();
 	 }
 	 
	}

	private void query(String input) {

		if (TextUtils.isEmpty(input)) {
			ToastUtils.show("请输入要查询的词");
		}
		Translate translate = EnWordTranslator.lookupNative(input);
		if(translate == null){
			ToastUtils.show("sorry,词丢了或者YouDaoApplication未初始化（未调用init）或者未授权");
			return;
		}
		showWord(translate);
 
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

	public Word queryFromDao(String input){
		WordDao wordDao = daoSession.getWordDao();
		QueryBuilder<Word> wordQueryBuilder = wordDao.queryBuilder();
		Word unique = wordQueryBuilder.where(WordDao.Properties.Name.eq(input)).build().unique();
		return unique;
	}

	public void findAndSaveWord(String input){

		if (TextUtils.isEmpty(input)) {
			return;
		}
		Translate translate = EnWordTranslator.lookupNative(input);
		if (translate == null) {
			return;
		}

		showWord(translate);



		final ExplainDao explainDao = daoSession.getExplainDao();
		final TranslationsDao translationsDao = daoSession.getTranslationsDao();
		WordDao wordDao = daoSession.getWordDao();
		List<String> explains = translate.getExplains();
		List<String> translations = translate.getTranslations();
		final Word word =new Word();
		wordId=wordDao.count();
		word.setId(wordId+1);

		for(int i=0;i<explains.size();i++){
			String s = explains.get(i);
			long id = word.getId();
			Explain explain = new Explain(id, s);
			explainDao.insert(explain);
		}

		for(int i=0;i<translations.size();i++){
			String s = translations.get(i);
			long id = word.getId();
			Translations explain = new Translations(id, s);
			translationsDao.insert(explain);
		}

		String phonetic = translate.getPhonetic();
		String query = translate.getQuery();
		String ukPhonetic = translate.getUkPhonetic();
		String usPhonetic = translate.getUsPhonetic();
		word.setName(query);
		word.setPhonetic(phonetic);
		word.setUkPhonetic(ukPhonetic);
		word.setUsPhonetic(usPhonetic);
		wordDao.insert(word);
		wordId++;
	}

	private void showWord(Translate translate) {
		TranslateData td = new TranslateData(
				System.currentTimeMillis(), translate);
		list.add(td);
		adapter.notifyDataSetChanged();
		questList.setSelection(list.size() - 1);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				imm.hideSoftInputFromWindow(
						fanyiInputText.getWindowToken(), 0);
			}
		}, 100);
		fanyiInputText.setText("");
	}

	public   void read(String filePath){
		long startTime = System.currentTimeMillis();
		Log.e("sl","start---------");
		BufferedSource bufferedSource = null;
		try {

			String path = Environment.getExternalStorageDirectory().getPath();
			File file = new File(path, filePath);
//            File file = new File(filePath);
			Source source = Okio.source(file);
			bufferedSource = Okio.buffer(source);
			String read="";
			do{
				read = bufferedSource.readUtf8Line();
				if(read==""){
					continue;
				}
				try {
					findAndSaveWord(read);
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
