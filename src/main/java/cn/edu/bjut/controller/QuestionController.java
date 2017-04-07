package cn.edu.bjut.controller;

import cn.edu.bjut.model.*;
import cn.edu.bjut.service.*;
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
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

    @Autowired
    HostLoginUser hostLoginUser;

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
            viewObject.put("likedCount",likeService.getLikedCount(comment.getId(),EntityType.ENTITY_COMMENT));
            if(hostLoginUser.getUser() == null){
                viewObject.put("liked",0);
            }else {
                viewObject.put("liked",likeService.getLikedStatus(hostLoginUser.getUser().getId(),comment.getId(),EntityType.ENTITY_COMMENT));
            }

            comments.add(viewObject);
        }

        model.addAttribute("comments",comments);

        List<ViewObject> followUsers = new ArrayList<ViewObject>();
        // 获取关注的用户信息
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, id, 20);
        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            User u = userService.getUserById(userId);
            if (u == null) {
                continue;
            }
            vo.put("name", u.getName());
            vo.put("headUrl", u.getHeadUrl());
            vo.put("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);
        if (hostLoginUser.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostLoginUser.getUser().getId(), EntityType.ENTITY_QUESTION, id));
        } else {
            model.addAttribute("followed", false);
        }

        return "detail";

    }
}
