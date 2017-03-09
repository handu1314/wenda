package cn.edu.bjut.configuration;

import cn.edu.bjut.interceptor.LoginInterceptor;
import cn.edu.bjut.interceptor.PassPortInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/3/9.
 */
@Component
public class WenDaWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    PassPortInterceptor passPortInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passPortInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
