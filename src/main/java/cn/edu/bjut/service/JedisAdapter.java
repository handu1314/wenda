package cn.edu.bjut.service;

import cn.edu.bjut.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    private String password = "123456";
    @Override
    public void afterPropertiesSet() throws Exception {
        //jedisPool = new JedisPool("localhost");
        jedisPool = new JedisPool("192.168.126.128",6379);
    }

    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
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
            jedis.auth(password);
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
            jedis.auth(password);
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
            jedis.auth(password);
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return false;
    }

    public long lpush(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public List<String> brpop(int timeout, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("存入集合异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return null;
    }
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                try {
                    tx.close();
                } catch (IOException ioe) {
                    // ..
                }
            }

            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.auth(password);
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
