package cn.edu.bjut.service;

import cn.edu.bjut.dao.QuestionDAO;
import cn.edu.bjut.dao.QuestionDAO;
import cn.edu.bjut.model.Question;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> selectLatestQuestions(int userId, int offset, int limit){
        System.out.println("query form database");
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){
        //过滤html标签
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question);
    }

    public Question getQuestionById(int id){
        return questionDAO.getQuestionById(id);
    }

    public void updateCommentCount(int id,int commentCount){
        questionDAO.updateCommentCount(id,commentCount);
    }
}
