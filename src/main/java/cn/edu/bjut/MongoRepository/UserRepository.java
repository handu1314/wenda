package cn.edu.bjut.MongoRepository;

import cn.edu.bjut.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hanxiao11 on 2017/7/10.
 */
public interface UserRepository extends MongoRepository<User,Integer> {
    public User findByName(String name);
}
