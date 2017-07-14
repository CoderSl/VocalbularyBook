package sliang.vocalbularybook.base.mvp.impl;


import sliang.vocalbularybook.base.mvp.IBaseModel;

import sliang.vocalbularybook.base.utils.TUtil;

/**
 * Created by qiang on 17-4-13.
 */

public abstract class BaseModel<S> implements IBaseModel {

    private S service;


    public BaseModel(){
        if(TUtil.getSuperTypes(this).length>0){
//            service = (S) ServiceGenerator.createDefaultService(TUtil.getTClass(this,0));
        }
        initialize();
    }

    public S getService(){

        return service;

    }

    public abstract void initialize();

}
