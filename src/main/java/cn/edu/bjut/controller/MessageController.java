package cn.edu.bjut.controller;

import cn.edu.bjut.service.MessageService;
import cn.edu.bjut.model.*;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(path="/msg/list",method = {RequestMethod.GET})
    public String getConversations(Model model){
        if(hostLoginUser.getUser() == null){
            return "redirect:/reglogin";
        }
        User user = hostLoginUser.getUser();
        List<Message> messageList = messageService.selectConversationList(user.getId(),0,10);
        List<ViewObject> conversations = new ArrayList<ViewObject>();
        for (Message message : messageList) {
            ViewObject vo = new ViewObject();
            vo.put("message",message);
            int targetId = message.getFromId() == user.getId() ? message.getToId() : message.getFromId();
            vo.put("user",userService.getUserById(targetId));
            vo.put("unread",messageService.getUnreadCount(hostLoginUser.getUser().getId(),message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations",conversations);
        return "letter";
    }

    @RequestMapping(path="/msg/detail",method = {RequestMethod.GET})
    public String getConversationDetail(Model model,@Param("conversationId") String conversationId){
        if(hostLoginUser.getUser() == null){
            return "redirect:/reglogin";
        }

        List<Message> messageList = messageService.getConversationDetail(conversationId,0,10);
        List<ViewObject> messages = new ArrayList<ViewObject>();
        for (Message message:messageList) {
            ViewObject vo = new ViewObject();
            vo.put("message",message);
            vo.put("user",userService.getUserById(message.getFromId()));
            messages.add(vo);


        }
        model.addAttribute("messages",messages);

        messageService.updateHasRead(hostLoginUser.getUser().getId(),conversationId);

        return "letterDetail";
    }


    @RequestMapping(path="/msg/addMessage",method = {RequestMethod.POST})
    @ResponseBody
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("toName") String toName){
        try {
            Message message = new Message();
            if(hostLoginUser.getUser() == null){
                return WendaUtil.getJSONString(999,"未登录");
            }else {
                message.setFromId(hostLoginUser.getUser().getId());
                message.setToId(userService.getUserByName(toName).getId());
                message.setContent(content);
                message.setCreatedDate(new Date());
                message.setHasRead(0);
                message.setConversationId();

                messageService.addMessage(message);
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("增加消息失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1,"发送私信失败");
    }
}
