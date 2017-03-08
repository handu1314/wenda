package cn.edu.bjut.dao;

import cn.edu.bjut.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title, content, created_date, user_id, comment_count";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    public void deleteQuestion(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
}
