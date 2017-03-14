package cn.edu.bjut.dao;

import cn.edu.bjut.model.Comment;
import cn.edu.bjut.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "user_id, content, created_date, entity_id, entity_type, status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values " +
            "(#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType}"})
    public int getCommentCount(@Param("entityId") int entityId,
                               @Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    public int updateStatus(@Param("entityId") int entityId,
                            @Param("entityType") int entityType,
                            @Param("status")int status);

    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                        @Param("entityType") int entityType,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
}
