package sliang.vocalbularybook.base.utils;

import java.lang.reflect.Field;

/**
 * Created by wanghao on 17-5-15.
 */

public class EntityVerify {

    private Object entity;

    private String msg;
    private int status;

    public EntityVerify(Object o){
        entity = o;
    }



    public boolean verify(){

        if(entity==null){
            return false;
        }
        Field field = null;
        try {
            field = entity.getClass().getDeclaredField("status");
            if(field!=null){
                field.setAccessible(true);
                int status = (int) field.get(entity);
                if(status==0){
                    setStatus(status);
                    return true;
                }else {
                    setStatus(status);
                    Field msgField = entity.getClass().getDeclaredField("msg");
                    if(msgField!=null){
                        msgField.setAccessible(true);
                        String msg = (String) msgField.get(entity);
                        setMsg(msg);
                    }else {
                        setMsg("错误,并且未发现msg");
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            field = null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            field = null;
        }catch (Exception e){
            e.printStackTrace();
            field = null;
        }


        if(field==null){
            setMsg("[Entrity Verify]解析错误");
        }

        return false;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
