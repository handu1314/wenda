package cn.edu.bjut.controller;

import cn.edu.bjut.async.EventProducer;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    EventProducer eventProducer;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    public String register(@RequestParam("username") String name,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next",required = false) String next,
                           Model model,
                           HttpServletResponse response) {
        try {
            Map<String, String> msg = userService.register(name, password);
            if (!msg.containsKey("ticket")) {
                model.addAttribute("msg", msg);
                return "login";
            } else {
                response.addCookie(new Cookie("ticket", msg.get("ticket")));
                if(!StringUtils.isBlank(next)){
                    return "redirect:/" + next;
                }
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = "/reglogin", method = {RequestMethod.GET})
    public String regLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(@RequestParam("username") String name,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletResponse response) {

        Map<String, String> msg = userService.login(name, password);
        if (!msg.containsKey("ticket")) {
            model.addAttribute("msg", msg);
            return "login";
        } else {
            Cookie cookie = new Cookie("ticket",msg.get("ticket").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            //发送邮件
            //User user = userService.getUserByName(name);
            //eventProductor.fireEvent(new EventModel(EventType.LOGIN).setActorId(user.getId()));
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String loginOut(@CookieValue("ticket") String ticket) {

        userService.logout(ticket);
        return "redirect:/";
    }


    @RequestMapping(value = "/updatePassword", method = {RequestMethod.GET})
    @ResponseBody
    public String updatePassword(@RequestParam("id") int id) {
        User user = userService.getUserById(id);
        user.setPassword("hx");
        userService.updateUser(user);
        return "success";
    }

}
