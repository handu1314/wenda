package cn.edu.bjut;

import cn.edu.bjut.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
public class TestRedis {

    public static void print(int index,Object o){
        System.out.println(String.format("%d: %s",index,o.toString()));
    }
    public static void testRedis(){
        Jedis jedis = new Jedis("192.168.126.128");
        jedis.flushDB();
        jedis.set("address","beijing");
        print(1,jedis.get("address"));
        jedis.lpush("names","hanxiao","duyanan","hanshuang");
        print(2,jedis.lindex("names",0));
        //设置有效时间（可用于验证码、缓存系统--将对象转序列化为文本）
        jedis.setex("yanzhengma",10,"123");
        //数值的操作
        jedis.set("pv","100");
        jedis.incr("pv");
        print(3,jedis.get("pv"));
        jedis.decr("pv");
        print(3,jedis.get("pv"));
        jedis.decrBy("pv",2);
        print(3,jedis.get("pv"));
        //通配符
        print(4,jedis.keys("*"));
        //List 列表
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            //存入一个值
            jedis.lpush(listName,"list-" + String.valueOf(i));
        }
        print(5,jedis.lrange(listName,0,10));
        print(5,jedis.lrange(listName,0,5));
        print(5,jedis.llen(listName));
        //弹出一个值
        print(5,jedis.lpop(listName));
        print(5,jedis.llen(listName));
        //取出指定索引出的值
        print(5,jedis.lindex(listName,3));
        //从中间动态插入
        print(5,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"list-3","hh"));
        print(5,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE,"list-3","xx"));
        print(5,jedis.lrange(listName,0,12));

        //hash 哈希表
        String userKey = "HashSet";
        //存入集合
        jedis.hset(userKey,"name","hanxiao");
        jedis.hset(userKey,"age","27");
        jedis.hset(userKey,"sex","male");
        jedis.hset(userKey,"address","beijing");
        //获取某个属性
        print(6,jedis.hget(userKey,"name"));
        //获取全部属性
        print(6,jedis.hgetAll(userKey));
        //判断是否存在某个属性
        print(7,jedis.hexists(userKey,"name"));
        print(7,jedis.hexists(userKey,"mail"));
        //取出keys，values
        print(7,jedis.hkeys(userKey));
        print(7,jedis.hvals(userKey));
        //如果不存在则存入，存在的话就不改写
        jedis.hsetnx(userKey,"name","dyn");
        jedis.hsetnx(userKey,"mail","test@qq.com");
        print(7,jedis.hgetAll(userKey));

        //set集合
        String setName1 = "set1";
        String setName2 = "set2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(setName1,String.valueOf(i));
            jedis.sadd(setName2,String.valueOf(i*i));
        }
        //显示所有属性
        print(8,jedis.smembers(setName1));
        print(8,jedis.smembers(setName2));
        //并集
        print(9,jedis.sunion(setName1,setName2));
        //差集
        print(10,jedis.sdiff(setName1,setName2));
        //交集（共同好友之类的）
        print(11,jedis.sinter(setName1,setName2));
        //是不是在集合中
        print(12,jedis.sismember(setName1,"10"));
        //删除某个元素
        jedis.srem(setName1,"5");
        print(13,jedis.smembers(setName1));
        //从一个集合中移动到另一个集合
        jedis.smove(setName2,setName1,"25");
        print(14,jedis.smembers(setName1));
        //随机抽1个或多个元素（可用于抽奖）
        print(15,jedis.srandmember(setName1,2));
        //集合当前有多少个元素
        print(15,jedis.scard(setName1));

        //优先队列（可用与排行榜）
        String rankKey = "rank";
        jedis.zadd(rankKey,80,"Jim");
        jedis.zadd(rankKey,85,"Ben");
        jedis.zadd(rankKey,75,"Lee");
        jedis.zadd(rankKey,65,"Lucy");
        jedis.zadd(rankKey,90,"Mei");
        //打印当前有多少个元素
        print(16,jedis.zcard(rankKey));
        //根据分值帅选
        print(17,jedis.zcount(rankKey,0,75));
        //查询某个元素的分数
        print(18,jedis.zscore(rankKey,"Mei"));
        //给某个元素加分
        jedis.zincrby(rankKey,2,"Mei");
        print(18,jedis.zscore(rankKey,"Mei"));
        //取出排名靠前的几个元素
        print(19,jedis.zrange(rankKey,1,3));
        print(19,jedis.zrevrange(rankKey,1,3));
        //取出分数范围内的元素
        for (Tuple tuple: jedis.zrangeByScoreWithScores(rankKey,"70","100")
                ) {
            print(20,tuple.getElement() + ":" + tuple.getScore());
        }
        // 查询某个元素的排名(正数倒数)
        print(21,jedis.zrank(rankKey,"Ben"));
        print(22,jedis.zrevrank(rankKey,"Ben"));

        //redis连接池
        JedisPool jedisPool = new JedisPool();
        for (int i = 0; i < 10; i++) {
            print(23,jedis.get("pv"));
            jedisPool.close();
        }

        //redis做缓存
        JSONPObject jsonpObject = new JSONPObject();
        User user  = new User();
        user.setId(1);
        user.setPassword("123");
        user.setHeadUrl("tets");
        user.setSalt("1111");
        //将对象序列化为json串
        jedis.set("user", JSON.toJSONString(user));
        print(24,jedis.get("user"));
        //将json串反序列化为User对象
        String jsonValue = jedis.get("user");
        User u = JSON.parseObject(jsonValue,User.class);
        print(25,u);
    }
    public static void main(String[] args) {
        testRedis();
//        Jedis jedis = new Jedis("192.168.126.128");
////        System.out.println(jedis.smembers("DISLIKE:2:7"));
////        //jedis.flushDB();
////        System.out.println(jedis.lrange("EVENT_LIKE",0,10));
//        jedis.set("CLOUD_APP_BLACK_KEY:HAN", "1");
//        System.out.println(jedis.get("CLOUD_APP_BLACK_KEY:HAN"));
//        System.out.println(jedis.get("CLOUD_APP_BLACK_KEY:xiao"));
//        System.out.println(jedis.del("CLOUD_APP_BLACK_KEY:HAN"));
//        System.out.println(jedis.get("CLOUD_APP_BLACK_KEY:HAN"));
//        jedis.set("CLOUD_APP_BLACK_KEY:HAN", "1");
//        System.out.println(jedis.get("CLOUD_APP_BLACK_KEY:HAN"));
//
//        Map m = new HashMap<>();
//        System.out.println(m.get("han"));
    }
}
