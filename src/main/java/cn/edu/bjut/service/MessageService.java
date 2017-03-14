package cn.edu.bjut.service;

import cn.edu.bjut.dao.MessageDAO;
import cn.edu.bjut.model.Message;
import cn.edu.bjut.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */
@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message){
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationDetail(String conversation,int offset,int limit){
        return messageDAO.selectConversationDetail(conversation,offset,limit);
    }

    public List<Message> selectConversationList(int userId,int offset,int limit){
        return messageDAO.selectConversationList(userId,offset,limit);
    }

    public int getUnreadCount(int userId,String conversationId){
        return messageDAO.getUnreadCount(userId,conversationId);
    }

    public void updateHasRead(int userId,String conversationId){
        messageDAO.updateHasRead(userId,conversationId);
    }
}
