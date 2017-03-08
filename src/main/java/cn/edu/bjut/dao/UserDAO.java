package cn.edu.bjut.dao;

import cn.edu.bjut.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/3/3.
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password, head_url, salt";
    String SELECT_FIELDS = "id ," + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{password},#{headUrl},#{salt})"})
    int insertUser(User user);

    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    void updateUser(User user);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    public void deleteUser(int id);

    @Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME," where id=#{id}"})
    User getUserById(int id);

}
