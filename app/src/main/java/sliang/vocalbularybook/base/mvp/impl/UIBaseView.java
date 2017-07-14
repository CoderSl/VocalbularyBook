package sliang.vocalbularybook.base.mvp.impl;

import android.content.Context;

/**
 * Created by qiang on 17-4-13.
 */

public interface UIBaseView{


    /**
     * 面向Presenter中需要Context的需求
     * @return
     */
    Context getContext();

    void finish();



}
