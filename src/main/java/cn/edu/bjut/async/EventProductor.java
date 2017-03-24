package cn.edu.bjut.async;

import cn.edu.bjut.service.JedisAdapter;
import cn.edu.bjut.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
@Service
public class EventProductor {
    private final Logger logger = LoggerFactory.getLogger(EventProductor.class);
    @Autowired
    JedisAdapter jedisAdapter;
    public void fireEvent(EventModel eventModel){
        try {
            String key = RedisUtil.getBizLikeEvent();
            String json = JSON.toJSONString(eventModel);
            jedisAdapter.lpush(key,json);
        }catch (Exception e){
            logger.error("加入事件队列异常"+e.getMessage());
        }
    }
}
