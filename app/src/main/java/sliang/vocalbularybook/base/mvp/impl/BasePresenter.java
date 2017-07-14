package sliang.vocalbularybook.base.mvp.impl;


import android.app.Activity;

import sliang.vocalbularybook.base.mvp.IBasePresenter;


/**
 * Created by qiang on 17-4-13.
 */

public abstract class BasePresenter<M,V> implements IBasePresenter {


    public static final int SUCCESS_CODE = 200;

    public BasePresenter(M model, V view) {
        this.model = model;
        this.view = view;
    }

    public static final int FAIL_CODE = -1;

    private M model;

    public  void setModel(M model) {
        this.model = model;
    }

    private V view;

    public BasePresenter(){
//        model = TUtil.getT(this,0);
//        System.out.println(model==null);
    }

    public void setView(V v){
        view = v;
    }

    protected M getModel(){
        return model;
    }

    protected V getView(){
        return view;
    }



    @Override
    public void beforeStart(int requestTag) {

    }

    @Override
    public void afterDispose(int requestTag) {

    }

    @Override
    public void beforeStart(int requestTag, Object... args) {

    }

    @Override
    public void afterDispose(int requestTag, Object... args) {

    }

    public void onBack(Object o){
        if(o!=null && o instanceof Activity){
            ((Activity) o).finish();
        }
    }

    public void onDestory(){
//        try {
//            WrapType<BaseModel> wrap= new WrapType<BaseModel>(model);
//            if(wrap.isAccess()){
//                wrap.getOriginalOj().onDestory();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
