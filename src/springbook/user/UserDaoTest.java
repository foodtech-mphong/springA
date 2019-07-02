package springbook.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class UserDaoTest {

	public static void main(String[] args)
	throws ClassNotFoundException, SQLException
	{
		ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
	
		User user = new User();
		user.setId("ship13");
		user.setName("홍민표");
		user.setPassword("married");

		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		
		
		
		if(!user.getName().equals(user2.getName()))
		{
			System.out.println("테스트 실패 (name)");
		}
		else if(!user.getPassword().equals(user2.getPassword()))
		{
			System.out.println("테스트 실패 (password)");
		}
		else
		{
			System.out.println("조회 테스트 성공");
		}
	}

	
	@Test
	public void addAndGet()
	throws ClassNotFoundException, SQLException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("usr1_id", "usr1_nm", "usr1_pw");
		User user2 = new User("usr2_id", "usr2_nm", "usr2_pw");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
		
		
		
	}
	
	
	@Test
	public void count()
	throws SQLException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("usr1_id", "usr1_nm", "usr1_pw");
		User user2 = new User("usr2_id", "usr2_nm", "usr2_pw");
		User user3 = new User("usr3_id", "usr3_nm", "usr3_pw");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));

	}
	
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure()
	throws ClassNotFoundException, SQLException
	{
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("usr99_id");
	}
	
}
