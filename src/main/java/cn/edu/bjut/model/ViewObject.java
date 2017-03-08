package cn.edu.bjut.model;

import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/8.
 */
public class ViewObject {
    private Map<String,Object> map = new HashMap<String,Object>();
    public void put(String key,Object value){
        map.put(key,value);
    }
    public Object get(String key){
        return map.get(key);
    }
}
