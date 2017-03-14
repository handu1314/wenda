package cn.edu.bjut.controller;

import cn.edu.bjut.model.Comment;
import cn.edu.bjut.model.EntityType;
import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.service.CommentService;
import cn.edu.bjut.service.QuestionService;
import cn.edu.bjut.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/14.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService commentService;

    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path="/addComment",method = {RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("questionId") int questionId){
        try {
            Comment comment = new Comment();
            if (hostLoginUser.getUser() == null) {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            } else {
                comment.setUserId(hostLoginUser.getUser().getId());
            }
            comment.setCreatedDate(new Date());
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);

            commentService.addComment(comment);

            int count = commentService.getCommentCount(questionId,EntityType.ENTITY_QUESTION);

            questionService.updateCommentCount(questionId,count);
        }catch (Exception e){
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
