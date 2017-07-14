package sliang.vocalbularybook.base.mvp;

/**
 * Created by qiang on 17-4-13.
 */

public interface IBasePresenter {


    /**
     * 在View层发起业务前，如有预处理操作，由View层调用此方法
     * @param requestTag
     */
    void beforeStart(int requestTag);

    /**
     * 在View层执行业务后，如有善后处理，由View层调用此方法(例：如已完成某项既定任务，UI已更新完毕，需要取消业务)
     * @param requestTag
     */
    void afterDispose(int requestTag);







    /**
     * 同 beforeStart(int requestTag); ，扩展一些场景下的传参需求
     * @param requestTag
     * @param args
     */
    void beforeStart(int requestTag, Object... args);

    /**
     * 同 afterDispose(int requestTag); ，扩展一些场景下的传参需求
     * @param requestTag
     * @param args
     */
    void afterDispose(int requestTag, Object... args);



}
