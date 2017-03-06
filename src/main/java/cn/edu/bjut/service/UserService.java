package cn.edu.bjut.service;

import cn.edu.bjut.dao.UserDAO;
import cn.edu.bjut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/6.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public void addUser(User user){
        userDAO.insertUser(user);
    }
}
