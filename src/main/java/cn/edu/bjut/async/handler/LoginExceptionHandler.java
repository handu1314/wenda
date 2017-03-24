package cn.edu.bjut.async.handler;

import cn.edu.bjut.async.EventHandler;
import cn.edu.bjut.async.EventModel;
import cn.edu.bjut.async.EventType;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.MailSender;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;
    @Override
    public void doHandler(EventModel eventModel) {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("username",userService.getUserById(eventModel.getActorId()).getName());
        mailSender.sendWithHTMLTemplate("506106507@qq.com","登陆异常",
                "mails/login_exception.html",model);
    }

    @Override
    public List<EventType> getSurpportEventTyps() {
        return Arrays.asList(EventType.LOGIN);
    }
}
