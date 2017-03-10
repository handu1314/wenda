package cn.edu.bjut.controller;

import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.model.Question;
import cn.edu.bjut.service.QuestionService;
import cn.edu.bjut.service.UserService;
import cn.edu.bjut.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setUserId(hostLoginUser.getUser().getId());
            question.setCommentCount(0);
            questionService.addQuestion(question);
            return WendaUtil.getJSONString(0);
        }catch (Exception e){
            logger.error("发布问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1,"发布问题失败");
    }
}
