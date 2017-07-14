package sliang.vocalbularybook.base.activity;

/**
 * Created by Administrator on 2017/6/23.
 */

public interface IStateView {
    void showEmpty();
    void showContent();
    void showRetry();
    void showLoading();

}
