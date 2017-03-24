package cn.edu.bjut.service;

import cn.edu.bjut.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool jedisPool;
    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool();
    }
    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }
    public long srem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }
    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }
    public boolean sismember(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return false;
    }
}
