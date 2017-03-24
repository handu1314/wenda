package cn.edu.bjut.async.handler;

import cn.edu.bjut.async.EventHandler;
import cn.edu.bjut.async.EventModel;
import cn.edu.bjut.async.EventType;
import cn.edu.bjut.model.Message;
import cn.edu.bjut.service.MessageService;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSADMIN_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setConversationId();
        message.setCreatedDate(new Date());
        message.setContent("您好，用户" + userService.getUserById(eventModel.getActorId()).getName() + "" +
                "赞了您的评论，http://127.0.0.1:8080/question/"+eventModel.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSurpportEventTyps() {
        return Arrays.asList(EventType.LIKE);
    }
}
