package cn.edu.bjut;

import cn.edu.bjut.service.UserService;
import cn.edu.bjut.model.Question;
import cn.edu.bjut.model.User;
import cn.edu.bjut.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = WendaApplication.class)
//@Sql("/init-schema.sql")
public class WendaApplicationTests {
	@Autowired
	UserService userService;
	@Autowired
	QuestionService questionService;
	@Test
	public void contextLoads() {
		Random random = new Random();

		for(int i=0;i<4;++i){
			User user = new User();
			user.setName("user" + String.valueOf(i));
			user.setPassword(String.valueOf(random.nextInt(9999)));
			user.setHeadUrl(String.format("http://images.newcoder.com/head/%d.png",random.nextInt(1000)));
			user.setSalt("user" + String.valueOf(i*i));
			userService.addUser(user);

			user.setPassword("1234567");
			userService.updateUser(user);
		}

		//userService.deleteUserById(1);
	}

	@Test
	public void QuestionDaoTest(){
		Random random = new Random();

		for(int i=0;i<4;++i){
			User user = new User();
			user.setName("user" + String.valueOf(i));
			user.setPassword(String.valueOf(random.nextInt(9999)));
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setSalt("user" + String.valueOf(i*i));
			userService.addUser(user);

			user.setPassword("1234567");
			userService.updateUser(user);
		}

		for(int i=0;i<4;++i){
			Question q = new Question();
			q.setCommentCount(i);
			q.setContent("test");
			Date now = new Date();
			now.setTime(now.getTime() + 1000);
			q.setCreatedDate(now);
			q.setTitle("title" + i);
			q.setUserId(i);

			questionService.addQuestion(q);
		}

		System.out.print(questionService.selectLatestQuestions(0,0,4));
	}

}
