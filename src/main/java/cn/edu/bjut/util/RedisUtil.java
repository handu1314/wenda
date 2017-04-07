package cn.edu.bjut.util;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
public class RedisUtil {
    static String SPILT = ":";
    static String BIZ_LIKE = "LIKE";
    static String BIZ_DISLIKE = "DISLIKE";
    static String BIZ_LIKE_EVENT = "LIKE_EVENT";
    //获取粉丝
    static String BIZ_FOLLOWER = "FOLLOWER";
    //关注对象
    static String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityId,int entityType){
        return BIZ_LIKE + SPILT + String.valueOf(entityId) + SPILT + String.valueOf(entityType);
    }
    public static String getDisLikeKey(int entityId,int entityType){
        return BIZ_DISLIKE + SPILT + String.valueOf(entityId) + SPILT + String.valueOf(entityType);
    }
    public static String getBizLikeEvent(){
        return BIZ_LIKE_EVENT;
    }
    //某个实体的粉丝
    public static String getBizFollowerKey(int entityType,int entityId){
        return BIZ_FOLLOWER + SPILT + String.valueOf(entityType) + SPILT + String.valueOf(entityId);
    }
    //某个用户对某个实体的关注
    public static String getBizFolloweeKey(int userId,int entityType){
        return BIZ_FOLLOWEE + SPILT + String.valueOf(userId) + SPILT + String.valueOf(entityType);
    }
}
