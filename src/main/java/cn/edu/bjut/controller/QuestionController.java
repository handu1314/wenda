package cn.edu.bjut.controller;

import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.model.Question;
import cn.edu.bjut.service.QuestionService;
import cn.edu.bjut.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        Question question =  new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setUserId(hostLoginUser.getUser().getId());
        question.setCommentCount(0);
        questionService.addQuestion(question);
        return "index";
    }
}
