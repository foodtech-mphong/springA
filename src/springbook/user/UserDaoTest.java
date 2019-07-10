package springbook.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
@DirtiesContext
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

	@Autowired
	UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	
	@Autowired
	private ApplicationContext context;
	
	
	@Before
	public void setUp()
	{
		System.out.println("this.context> " + this.context);
		System.out.println("this        > " + this);
	
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mariadb://localhost:3306/test", "root", "root", true);
		dao.setDataSource(dataSource);
		
		//this.dao = this.context.getBean("userDao", UserDao.class);
		
		this.user1 = new User("usr1_id", "usr1_nm", "usr1_pw");
		this.user2 = new User("usr2_id", "usr2_nm", "usr2_pw");
		this.user3 = new User("usr3_id", "usr3_nm", "usr3_pw");
	}
	
	@Test
	public void addAndGet()
	throws ClassNotFoundException, SQLException
	{
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
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("usr99_id");
	}
	
}
