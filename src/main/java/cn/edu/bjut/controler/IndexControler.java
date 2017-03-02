package cn.edu.bjut.controler;

/**
 * Created by Administrator on 2017/3/2.
 */

import org.springframework.stereotype.Controller;

@Controller
public class IndexControler {

    public String index(){
        return "Hello bjut";
    }
}
