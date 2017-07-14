package sliang.vocalbularybook.base.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by qiang on 17-4-13.
 */

public class TUtil {

    public static <T> T getT(Object o,int i){

        try {
            return  ((Class<T>)
                    ((ParameterizedType)
                            (o.getClass().getGenericSuperclass()))
                            .getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public static Class getTClass(Object o,int i){

        try {
            return  ((Class)
                    ((ParameterizedType)
                            (o.getClass().getGenericSuperclass()))
                            .getActualTypeArguments()[i]);
        }catch (ClassCastException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }


    public static Type[] getSuperTypes(Object o){

        try{
            return ((ParameterizedType)(o.getClass().getGenericSuperclass())).getActualTypeArguments();

        }catch (Exception e){
            e.printStackTrace();
        }
        return new Type[0];
    }


    public static Class<?> forName(String className){

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
