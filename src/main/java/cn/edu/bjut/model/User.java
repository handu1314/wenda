package cn.edu.bjut.model;

/**
 * Created by Administrator on 2017/3/2.
 */
public class User {
    private int userId;
    private String name;

    public User(int userId,String name){
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
