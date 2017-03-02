package cn.edu.bjut.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/2.
 */
@Service
public class WenDaService {
    public String getMessage(int userId){
        return "Hello" + String.valueOf(userId);
    }
}
