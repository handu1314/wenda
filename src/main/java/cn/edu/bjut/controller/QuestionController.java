package cn.edu.bjut.controller;

import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.model.Question;
import cn.edu.bjut.model.ViewObject;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
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
        List<ViewObject> vos = new ArrayList<ViewObject>();
        ViewObject v = new ViewObject();
        v.put("question", question);
        v.put("user", userService.getUserById(question.getUserId()));
        vos.add(v);

        model.addAttribute("vos",vos);

        return "index";

    }
}
