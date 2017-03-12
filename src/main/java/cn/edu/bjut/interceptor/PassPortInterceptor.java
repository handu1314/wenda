package cn.edu.bjut.interceptor;

import cn.edu.bjut.dao.LoginTicketDAO;
import cn.edu.bjut.dao.UserDAO;
import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.model.LoginTicket;
import cn.edu.bjut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/9.
 */
@Component
public class PassPortInterceptor implements HandlerInterceptor{
    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if(httpServletRequest.getCookies() != null){
            for(Cookie c : httpServletRequest.getCookies()){
                if(c.getName().equals("ticket")){
                    ticket = c.getValue();
                }
            }
        }
        if(ticket != null){
            LoginTicket loginTicket = loginTicketDAO.getLoginTicketByTicket(ticket);
            if(loginTicket == null || loginTicket.getStatus() == 1 || loginTicket.getExpired().before(new Date())){
                return true;
            }else {
                User user = userDAO.getUserById(loginTicket.getUserId());
                hostLoginUser.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostLoginUser.getUser() != null){
            modelAndView.addObject("user",hostLoginUser.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostLoginUser.removeUser();
    }
}
