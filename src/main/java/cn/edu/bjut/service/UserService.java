package cn.edu.bjut.service;

import cn.edu.bjut.dao.LoginTicketDAO;
import cn.edu.bjut.dao.UserDAO;
import cn.edu.bjut.model.LoginTicket;
import cn.edu.bjut.model.User;
import cn.edu.bjut.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import java.util.*;

/**
 * Created by Administrator on 2017/3/6.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public Map<String,String> register(String name,String password){
        Map<String,String> msg = new HashMap<String,String>();
        if(StringUtils.isBlank(name)){
            msg.put("msg","用户名不能为空");
            return msg;
        }
        if(StringUtils.isBlank(password)){
            msg.put("msg","密码不能为空");
            return msg;
        }

        User user = userDAO.getUserByName(name);
        if(user != null){
            msg.put("msg","用户名已存在");
            return msg;
        }

        user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,6).replaceAll("-",""));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        Random random = new Random();
        user.setHeadUrl(String.format("http://images.newcoder.com/head/%d.png",random.nextInt(1000)));
        userDAO.insertUser(user);

        msg.put("ticket",addLoginTicket(user.getId()));

        return msg;
    }

    public Map<String,String> login(String name,String password){
        Map<String,String> msg = new HashMap<String,String>();
        if(StringUtils.isBlank(name)){
            msg.put("msg","用户名不能为空");
            return msg;
        }
        if(StringUtils.isBlank(password)){
            msg.put("msg","密码不能为空");
            return msg;
        }

        User user = userDAO.getUserByName(name);
        if(user == null){
            msg.put("msg","用户名不存在");
            return msg;
        }

        if(!user.getPassword().equals(WendaUtil.MD5(password+user.getSalt()))){
            msg.put("msg","密码错误");
        }else {
            msg.put("ticket",addLoginTicket(user.getId()));
        }
        return msg;
    }

    private String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();

        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(now.getTime() + 3600*24*10);
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.insert(loginTicket);

        return loginTicket.getTicket();
    }

    public int addUser(User user){
        return userDAO.insertUser(user);
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public User getUserById(int id){
        return userDAO.getUserById(id);
    }

    public void deleteUserById(int id){
        userDAO.deleteUser(id);
    }
}
