package cn.edu.bjut.controller;

import cn.edu.bjut.model.*;
import cn.edu.bjut.service.CommentService;
import cn.edu.bjut.service.QuestionService;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
public class QuestionController {
    @Autowired
    UserService userService;

    @Autowired
    HostLoginUser hostLoginUser;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            if(hostLoginUser.getUser() == null){
                return WendaUtil.getJSONString(999,"未登录");
            }
            question.setUserId(hostLoginUser.getUser().getId());
            question.setCommentCount(0);
            questionService.addQuestion(question);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发布问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "发布问题失败");
    }

    @RequestMapping(value = "/question/{id}", method = {RequestMethod.GET})
    public String questionIndex(@PathVariable("id") int id, Model model) {

        Question question = questionService.getQuestionById(id);
        model.addAttribute("question",question);

        List<Comment> commentList = commentService.getCommentsByEntity(id, EntityType.ENTITY_QUESTION,0,10);
        List<ViewObject> comments = new ArrayList<ViewObject>();
        for (Comment comment:commentList) {
            ViewObject viewObject = new ViewObject();
            viewObject.put("comment",comment);
            viewObject.put("user",userService.getUserById(comment.getUserId()));
            comments.add(viewObject);
        }

        model.addAttribute("comments",comments);

        return "detail";

    }
}
