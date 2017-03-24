package cn.edu.bjut.service;

import cn.edu.bjut.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;
    /**
     *@Author: hanxiao
     *@Description: 赞
     *@Date: 12:18 2017/3/23
    */
    public long like(int userId,int entityId,int entityType){
        //加入到喜欢集合
        String likeKey = RedisUtil.getLikeKey(entityId,entityType);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        //从不喜欢集合移除
        String disLikeKey = RedisUtil.getDisLikeKey(entityId,entityType);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));
        //返回被喜欢的数量
        return jedisAdapter.scard(likeKey);
    }
    /**
     *@Author: hanxiao
     *@Description:踩
     *@Date: 12:18 2017/3/23
    */
    public long disLike(int userId,int entityId,int entityType){
        //加入到不喜欢集合
        String disLikeKey = RedisUtil.getDisLikeKey(entityId,entityType);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));
        //从喜欢集合移除
        String likeKey = RedisUtil.getLikeKey(entityId,entityType);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        //返回被喜欢的数量
        return jedisAdapter.scard(likeKey);
    }
    /**
     *@Author: hanxiao
     *@Description:获取当前用户对某个问题的喜欢状态
     *@Date: 12:21 2017/3/23
    */
    public int getLikedStatus(int userId,int entityId,int entityType){
        String likeKey = RedisUtil.getLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }else {
            String disLikeKey = RedisUtil.getDisLikeKey(entityId,entityType);
            return jedisAdapter.sismember(disLikeKey,String.valueOf(userId)) ? -1 : 0;
        }
    }
    /**
     *@Author: hanxiao
     *@Description:获取被喜欢的数量
     *@Date: 12:23 2017/3/23
    */
    public long getLikedCount(int entityId,int entityType){
        String likeKey = RedisUtil.getLikeKey(entityId,entityType);
        return jedisAdapter.scard(likeKey);
    }
}
