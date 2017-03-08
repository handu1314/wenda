package cn.edu.bjut.controler;

import cn.edu.bjut.service.UserService;
import cn.edu.bjut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/3/6.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/addUser",method = {RequestMethod.GET})
    @ResponseBody
    public String setting(@RequestParam("name") String name,@RequestParam("password") String password,
                          @RequestParam("headUrl") String headUrl){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setHeadUrl(headUrl);
        userService.addUser(user);
        return "success";
    }

    @RequestMapping(value = "/updatePassword",method = {RequestMethod.GET})
    @ResponseBody
    public String updatePassword(@RequestParam("id") int id){
        User user = userService.getUserById(id);
        user.setPassword("hx");
        userService.updateUser(user);
        return "success";
    }

    @RequestMapping(value = "/deleteById",method = {RequestMethod.GET})
    @ResponseBody
    public String deleteById(@RequestParam("id") int id){
        userService.deleteUserById(id);
        return "success";
    }
}
