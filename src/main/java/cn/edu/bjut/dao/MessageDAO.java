package cn.edu.bjut.dao;

import cn.edu.bjut.model.Comment;
import cn.edu.bjut.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = "to_id, from_id, created_date, content, conversation_id, has_read";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values " +
            "(#{toId},#{fromId},#{createdDate},#{content},#{conversationId},#{hasRead})"})
    int addMessage(Message message);

    @Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,"where conversation_id=#{conversationId} limit #{offset},#{limit}"})
    List<Message> selectConversationDetail(@Param("conversationId") String conversationId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);

    @Select({"select ",INSERT_FIELDS," , count(id) as id from (select * from ",TABLE_NAME,
            "where from_id = #{userId} or to_id=#{userId} order by created_date desc) tt " +
            "group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectConversationList(@Param("userId") int userId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME,"where has_read = 0 and to_id=#{userId}  and conversation_id=#{conversationId}"})
    public int getUnreadCount(@Param("userId") int userId,@Param("conversationId") String conversationId);

    @Update({"update ",TABLE_NAME,"set has_read = 1 where to_id=#{userId} and conversation_id=#{conversationId}"})
    public void updateHasRead(@Param("userId") int userId,
                              @Param("conversationId") String conversationId);

}
