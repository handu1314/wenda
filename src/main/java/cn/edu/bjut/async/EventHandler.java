package cn.edu.bjut.async;

import java.util.List;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/24.
 */
public interface EventHandler {
    void doHandler(EventModel eventModel);
    List<EventType> getSurpportEventTyps();
}
