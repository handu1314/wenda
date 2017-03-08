package cn.edu.bjut.controller;

import cn.edu.bjut.service.UserService;
import cn.edu.bjut.model.User;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/reg",method = {RequestMethod.POST})
    public String register(@RequestParam("name") String name,
                           @RequestParam("password") String password,
                           Model model,
                           HttpServletResponse response){

        Map<String,String> msg = userService.register(name,password);
        if(!msg.containsKey("ticket")){
            model.addAttribute("msg",msg);
            return "login";
        }else {
            response.addCookie(new Cookie("ticket",msg.get("ticket")));
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/regLogin",method = {RequestMethod.GET})
    public String regLogin(){
        return "login";
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletResponse response){

        Map<String,String> msg = userService.login(name,password);
        if(!msg.containsKey("ticket")){
            model.addAttribute("msg",msg);
            return "login";
        }else {
            response.addCookie(new Cookie("ticket",msg.get("ticket")));
            return "redirect:/";
        }
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
