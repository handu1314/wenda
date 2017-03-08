package cn.edu.bjut.dao;

import cn.edu.bjut.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/8.
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id, ticket, expired, status";
    String SELECT_FIELDS = "id ," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{userId},#{ticket},#{expired},#{status})"})
    int insert(LoginTicket loginTicket);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where ticket=#{ticket}"})
    LoginTicket getLoginTicketByTicket(@Param("ticket") String ticket);

    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateLoginTicket(@Param("status") String status,@Param("ticket") String ticket);
}
