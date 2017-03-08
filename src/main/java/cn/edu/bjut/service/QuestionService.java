package cn.edu.bjut.service;

import cn.edu.bjut.dao.QuestionDAO;
import cn.edu.bjut.dao.QuestionDAO;
import cn.edu.bjut.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    public List<Question> selectLatestQuestions(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){
        return questionDAO.addQuestion(question);
    }
}
