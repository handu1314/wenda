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
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(hostLoginUser.getUser() == null){
            httpServletResponse.sendRedirect("/reglogin?next="+httpServletRequest.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
