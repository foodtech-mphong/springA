package springbook;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;

public class testMain {

	public static void main(String[] args)
	throws ClassNotFoundException, SQLException
	{
		DaoFactory factory = new DaoFactory();
		UserDao dao1 = factory.userDao();
		UserDao dao2 = factory.userDao();
		
		System.out.println("1-1> " + dao1);
		System.out.println("1-2> " + dao2);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao3 = context.getBean("userDao", UserDao.class);
		UserDao dao4 = context.getBean("userDao", UserDao.class);

		System.out.println("2-1> " + dao3);
		System.out.println("2-2> " + dao4);

		
		
		
	}

}
