package sliang.vocalbularybook.base.activity;

import android.view.View;

/**
 * Created by Administrator on 2017/6/24.
 */

public interface ITitleBar {

     void setTitle(String title);

     void hideDividerLine(boolean isHide);

     void showLeftImg(int rId);

     void showRightImg(int rId) ;

     void showRightText(String name);

     void setRightTextColor(String color);

     void setActionBackBtnEnable(boolean enable);
     void setActionBackBtnOnClickListenner(View.OnClickListener rightImgOnClickListenner);

     void setRightImgOnClickListenner(View.OnClickListener rightImgOnClickListenner) ;

     void setleftImgOnClickListenner(View.OnClickListener rightImgOnClickListenner);

     void setRightTextOnClickListenner(View.OnClickListener rightImgOnClickListenner) ;
}
