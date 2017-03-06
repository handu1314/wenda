package cn.edu.bjut;

import cn.edu.bjut.dao.UserDAO;
import cn.edu.bjut.model.User;
import cn.edu.bjut.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class WendaApplicationTests {
	@Autowired
	UserService userService;
	@Test
	public void contextLoads() {
	}

	@Test
	public void userTest(){
		Random random = new Random();

		for(int i=0;i<4;++i){
			User user = new User();
			user.setName("user" + String.valueOf(i));
			user.setPassword(String.valueOf(random.nextInt(9999)));
			user.setHeadUrl(String.format("http://images.newcoder.com/head/%d.png",random.nextInt(1000)));
			user.setSalt("user" + String.valueOf(i*i));
			userService.addUser(user);
		}
	}

}
