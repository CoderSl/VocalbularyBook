package sliang.vocalbularybook.base.views;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import sliang.vocalbularybook.R;


/**
 * Created by kevin on 15/5/29.
 */
public class MessageDialog extends Dialog implements View.OnClickListener {

    private LayoutInflater inflater;
    private View baseView;
    private TextView mTitle;
    private TextView mMessage;
    private TextView btnYes;
    private TextView btnNo;
    private Context context;

    private OnClickListener mOkListener;
    private OnClickListener mCancelListener;


    public MessageDialog(Context context) {
        super(context, R.style.Theme_loading_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        baseView = inflater.inflate(R.layout.dialog_message, null);
        btnYes = (TextView) baseView.findViewById(R.id.dialog_btn_yes);
        btnNo = (TextView) baseView.findViewById(R.id.dialog_btn_no);
        mTitle = (TextView) baseView.findViewById(R.id.title);
        mMessage = (TextView) baseView.findViewById(R.id.message);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        setContentView(baseView);
    }

    @Override
    public void show() {

//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogWindow.setAttributes(lp);

        if ("".equals(btnNo.getText().toString().trim())) {
            btnNo.setVisibility(View.GONE);
        }

        super.show();
    }


    public void setOkListener(OnClickListener mOkListener) {
        this.mOkListener = mOkListener;
    }

    public void setMessage(String message) {
        mMessage.setText(message);
    }

    public void setCancelListener(OnClickListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
    }

    public void setBtnYes(String txt) {
        this.btnYes.setText(txt);
    }

    public void setBtnNo(String txt) {
        btnNo.setText(txt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_yes:
                if (mOkListener != null) {
                    mOkListener.onClick(this, BUTTON_POSITIVE);
                }
               cancel();

                break;
            case R.id.dialog_btn_no:
                if (mCancelListener != null) {
                    mCancelListener.onClick(this, BUTTON_NEGATIVE);
                }
                cancel();
                break;
        }

    }
}
