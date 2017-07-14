package sliang.vocalbularybook.base.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import sliang.vocalbularybook.R;


/**
 * Created by Administrator on 2017/6/24.
 */

public class TitleBar extends FrameLayout {
    private ImageView actionBackBtn;
    private ImageView actionRightImg;
    private ImageView actionLeftImg;
    private TextView actionTitle;
    private TextView actionSubTitle;
    private View navbar_line;
    private boolean backBtnEnable=true;

    public boolean isBackBtnEnable() {
        return backBtnEnable;
    }

    public void setBackBtnEnable(boolean backBtnEnable) {
        this.backBtnEnable = backBtnEnable;
    }

    public TitleBar(@NonNull Context context) {
        super(context);
        initTitle();
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTitle();
    }



    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTitle();
    }

    private void initTitle() {
        View titleBar = LayoutInflater.from(getContext()).inflate(R.layout.common_navbar, null);
        actionBackBtn = (ImageView) titleBar.findViewById(R.id.action_back_btn);
        actionRightImg = (ImageView)titleBar.findViewById(R.id.action_right_img);
        actionLeftImg = (ImageView) titleBar.findViewById(R.id.action_left_img);
        actionTitle = (TextView) titleBar.findViewById(R.id.action_title);
        actionSubTitle = (TextView) titleBar.findViewById(R.id.action_sub_title);
        // 添加隐藏线
        navbar_line = titleBar.findViewById(R.id.navbar_line);
        addView(titleBar);
    }
    public void setTitle(String title) {
            actionTitle.setText(title);
    }
    public void hideDividerLine(boolean isHide) {
        if (isHide) {
            navbar_line.setVisibility(View.GONE);
        } else {
            navbar_line.setVisibility(View.VISIBLE);
        }
    }

    public void showLeftImg(int rId) {
            actionBackBtn.setVisibility(View.GONE);
            actionLeftImg.setVisibility(View.VISIBLE);
            actionLeftImg.setImageResource(rId);
    }

    public void showRightImg(int rId) {
            actionSubTitle.setVisibility(View.GONE);
            actionRightImg.setVisibility(View.VISIBLE);
            actionRightImg.setImageResource(rId);
    }

    public void showRightText(String name) {

            actionRightImg.setVisibility(View.GONE);
            actionSubTitle.setVisibility(View.VISIBLE);
            actionSubTitle.setText(name);

    }

    public void setRightTextColor(String color) {
            actionRightImg.setVisibility(View.GONE);
            actionSubTitle.setVisibility(View.VISIBLE);
            actionSubTitle.setTextColor(Color.parseColor(color));

    }

    public void setActionBackBtnEnable(boolean enable) {
        this.backBtnEnable = enable;
    }

    public void setActionBackBtnOnClickListenner(OnClickListener rightImgOnClickListenner) {
        this.actionBackBtn.setOnClickListener(rightImgOnClickListenner);
    }

    public void setRightImgOnClickListenner(OnClickListener rightImgOnClickListenner) {
        actionRightImg.setOnClickListener(rightImgOnClickListenner);
    }

    public void setleftImgOnClickListenner(OnClickListener rightImgOnClickListenner) {
        actionLeftImg.setOnClickListener(rightImgOnClickListenner);
    }

    public void setRightTextOnClickListenner(OnClickListener rightImgOnClickListenner) {
        actionSubTitle.setOnClickListener(rightImgOnClickListenner);
    }

}
