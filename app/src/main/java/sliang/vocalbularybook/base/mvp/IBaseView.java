package sliang.vocalbularybook.base.mvp;


/**
 * Created by qiang on 17-4-13.
 */

public interface IBaseView<T> {

    /**
     * Toast提示
     * @param msg 消息内容
     * @param requestTag 请求标识，用以区分多次调用
     */
    void toast(String msg, String requestTag);


    /**
     * Toast提示
     * @param
     * @param
     */
    void doToast(int msg);
    /**
     * loading
     * @param requestTag
     */
    void showProgress(String requestTag);

    /**
     * 取消loading
     * @param requestTag
     */
    void cancelProgress(String requestTag);


    /**
     * loaded callback
     * @param data
     * @param requestTag
     * @param resultCode
     */
    void loadDataSuccess(T data, String requestTag, int resultCode);


    /**
     * 请求出错
     * @param e
     * @param requestTag
     * @param resultCode
     */
    void loadDataError(Throwable e, String requestTag, int resultCode);



}
