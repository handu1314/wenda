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

    public static String getLikeKey(int entityId,int entityType){
        return BIZ_LIKE + SPILT + String.valueOf(entityId) + SPILT + String.valueOf(entityType);
    }
    public static String getDisLikeKey(int entityId,int entityType){
        return BIZ_DISLIKE + SPILT + String.valueOf(entityId) + SPILT + String.valueOf(entityType);
    }
    public static String getBizLikeEvent(){
        return BIZ_LIKE_EVENT;
    }
}
