package cn.edu.bjut.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/9.
 */
@Component
public class HostLoginUser {
    public static ThreadLocal<User> users = new ThreadLocal<User>();
    public void setUser(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }
    public void removeUser(){
        users.remove();
    }
}
