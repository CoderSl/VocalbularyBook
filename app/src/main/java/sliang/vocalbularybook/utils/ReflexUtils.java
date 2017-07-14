package sliang.vocalbularybook.utils;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/14.
 */

public class ReflexUtils {


    /**
     *
     * 通过反射暴力获取私有字段
     *
     *  @param
     *
     *  @return
     *
     */
    public  static  Object getClassFiled(Class aClass,Object o,String filed){
        try {
            Field declaredField = aClass.getDeclaredField(filed);
            declaredField.setAccessible(true);
            Object o1 = declaredField.get(o);
            return o1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
