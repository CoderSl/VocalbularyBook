package sliang.vocalbularybook.youdao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sliang.vocalbularybook.R;

public class MainActivity extends Activity {
    @BindView(R.id.titleBar)
    RelativeLayout titleBar;
    @BindView(R.id.startBtn)
    Button startBtn;
    @BindView(R.id.startBtn1)
    Button startBtn1;
    @BindView(R.id.startBtn2)
    Button startBtn2;
    @BindView(R.id.startBtn3)
    Button startBtn3;
    private Handler mHandler = new Handler();

    TextView startWelcomeCopyright;

    int alpha = 255;

    int b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        View startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper.toTranslateActivity(MainActivity.this);
            }
        });

        View startBtn1 = findViewById(R.id.startBtn1);
        startBtn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper.toHanyuActivity(MainActivity.this);
            }
        });

        View startBtn2 = findViewById(R.id.startBtn2);
        startBtn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper
                        .toTranslateOfflineWordActivity(MainActivity.this);
            }
        });

        View startBtn3 = findViewById(R.id.startBtn3);
        startBtn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper
                        .toTranslateOfflineLineActivity(MainActivity.this);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
