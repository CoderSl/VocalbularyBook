package sliang.vocalbularybook.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import sliang.vocalbularybook.R;
import sliang.vocalbularybook.base.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/13.
 */

public class SearchActivity extends BaseActivity {

    private int currentTabIndex;
    private Fragment[] fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {

        SearchFragment searchFragment = new SearchFragment();
        fragments=new  Fragment[]{
                searchFragment
        };
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0])
                .show(fragments[0]).commit();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void setListener() {

    }


    @Override
    protected void initPresenter() {

    }



    /**
     * 根据index切换fragment的显示
     *
     * @param index
     */
    public void changeFragment(int index, boolean isHotIn) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (currentTabIndex != index) {
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;
    }

}
