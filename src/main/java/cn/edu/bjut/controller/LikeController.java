package cn.edu.bjut.controller;

import cn.edu.bjut.async.EventModel;
import cn.edu.bjut.async.EventProducer;
import cn.edu.bjut.async.EventType;
import cn.edu.bjut.model.Comment;
import cn.edu.bjut.model.EntityType;
import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.service.CommentService;
import cn.edu.bjut.service.LikeService;
import cn.edu.bjut.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    @Autowired
    LikeService likeService;

    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path="/like",method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostLoginUser.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);
        EventModel eventModel = new EventModel(EventType.LIKE).setActorId(hostLoginUser.getUser().getId())
                .setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityOwnerId(comment.getUserId()).setExts("questionId",String.valueOf(comment.getEntityId()));
        eventProducer.fireEvent(eventModel);

        long likeCount = likeService.like(hostLoginUser.getUser().getId(),commentId,EntityType.ENTITY_COMMENT);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
    @RequestMapping(path="/dislike",method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId){
        if(hostLoginUser.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostLoginUser.getUser().getId(),commentId,EntityType.ENTITY_COMMENT);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
