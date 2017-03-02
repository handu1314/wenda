package cn.edu.bjut.controler;

import cn.edu.bjut.service.WenDaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/3/2.
 */
@Controller
public class SettingController {
    @Autowired
    WenDaService wenDaService;
    @RequestMapping(value = "/setting",method = {RequestMethod.GET})
    @ResponseBody
    public String setting(){
        return  wenDaService.getMessage(1) + "setting";
    }
}
