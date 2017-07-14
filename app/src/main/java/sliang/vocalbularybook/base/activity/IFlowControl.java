package sliang.vocalbularybook.base.activity;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/6/24.
 */

public interface IFlowControl {
    /**
     * 得到所要填充到activity的布局文件的id
     *
     * @return 所要填充到activity的布局文件的id
     */
     int getLayoutId();

    /**
     * 初始化View
     */
   void initView();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);

    /**
     * 加载用户数据
     */
   void loadData();

    /**
     * 初始化监听器
     */
     void setListener();

}
