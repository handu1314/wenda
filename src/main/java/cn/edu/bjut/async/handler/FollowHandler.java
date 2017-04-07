package cn.edu.bjut.async.handler;

import cn.edu.bjut.async.EventHandler;
import cn.edu.bjut.async.EventModel;
import cn.edu.bjut.async.EventType;
import cn.edu.bjut.model.EntityType;
import cn.edu.bjut.model.Message;
import cn.edu.bjut.model.User;
import cn.edu.bjut.service.MessageService;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSADMIN_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUserById(model.getActorId());

        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSurpportEventTyps() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
