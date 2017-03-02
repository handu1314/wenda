package cn.edu.bjut.controler;

/**
 * Created by Administrator on 2017/3/2.
 */

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.rmi.MarshalledObject;
import java.security.PublicKey;
import java.util.*;

@Controller
public class IndexControler {

    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public String index(HttpSession session){
        return "Hello bjut" + session.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") String type){
        return String.format("Profile page of %s , %d , type= %s",groupId,userId,type);
    }

    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value","value1");
        List<String> colors = Arrays.asList(new String[] {"red","black","yellow"});
        model.addAttribute("colors",colors);
        Map<String,String> map = new HashMap<>();
        for (int i=0;i<4;++i) {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        return "home";
    }

    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session,
                          @CookieValue("JSESSIONID") String cookieId){
        StringBuilder sb = new StringBuilder();

        sb.append(cookieId + "<br>");

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            sb.append(headers.nextElement() + "<br>");
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie:" + cookie.getName() + "value: " + cookie.getValue());
            }
        }

        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getRemotePort() + "<br>");


        response.addHeader("bjut","hello");
        response.addCookie(new Cookie("loginUser","admin"));

        return sb.toString();
    }

    @RequestMapping(path={"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession session){
        session.setAttribute("msg","hello world");
        RedirectView rv = new RedirectView("/");
        if(code == 301){
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return rv;
    }

    @RequestMapping(path={"/expection"},method = {RequestMethod.GET})
    public String redirect(@RequestParam("admin") String admin){
        if(admin.equals("admin")){
            return "redirect:/";
        }else {
                throw new IllegalArgumentException("error argument!");
        }
    }

    @ExceptionHandler()
    @ResponseBody
    public String expectionHandler(Exception e){
        return "error :" + e.getMessage();
    }

}
