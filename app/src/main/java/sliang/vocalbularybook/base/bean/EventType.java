package sliang.vocalbularybook.base.bean;

/**
 * Created by Administrator on 2017/3/1.
 */

public class EventType {
    private String type;
    private Object  data;
    public EventType() {
    }

    public EventType(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
