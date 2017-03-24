package cn.edu.bjut.async;

import cn.edu.bjut.service.JedisAdapter;
import cn.edu.bjut.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.sun.javafx.event.EventUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    @Autowired
    JedisAdapter jedisAdapter;
    private final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext = null;
    private Map<EventType,List<EventHandler>> config = new HashMap<EventType,List<EventHandler>>();
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            for(Map.Entry<String,EventHandler> entry : beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSurpportEventTyps();
                for(EventType type : eventTypes){
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisUtil.getBizLikeEvent();
                    List<String> events = jedisAdapter.brpop(0,key);
                    for(String message : events){
                        if(message.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message,EventModel.class);
                        if(!config.containsKey(eventModel.getEventType())){
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler eventHandler : config.get(eventModel.getEventType())){
                            eventHandler.doHandler(eventModel);
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
