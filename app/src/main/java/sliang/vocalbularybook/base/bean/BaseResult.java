
package sliang.vocalbularybook.base.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/28.
 */
public class BaseResult implements Serializable {
    private String status;
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
