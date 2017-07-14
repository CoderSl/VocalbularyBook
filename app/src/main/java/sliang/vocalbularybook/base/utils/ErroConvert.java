package sliang.vocalbularybook.base.utils;

import android.text.TextUtils;

/**
 * Created by qiang on 17-5-24.
 */

public class ErroConvert {

    private int status;
    private String message;

    public ErroConvert(int status,String message){

        this.status = status;
        this.message = message;

    }

    public ErroConvert(String mergeMessage){
        parseFromMergeMessage(mergeMessage);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String mergeMessage(){
        return status+":"+message;
    }

    public void parseFromMergeMessage(String mergeMessage){
        if(!TextUtils.isEmpty(mergeMessage)){
            try {
                String[] erro = mergeMessage.split(":");
                setStatus(Integer.parseInt(erro[0]));
                setMessage(erro[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static boolean isMergeMessage(String message){

        if(!TextUtils.isEmpty(message)){
            try {
                String[] erro = message.split(":");
                int status = Integer.parseInt(erro[0]);
                String msg = erro[1];
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


}
