package cn.edu.bjut.async;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);
    private int value;
    private EventType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
