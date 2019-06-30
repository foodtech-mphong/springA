package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao 
{
	
	private ConnectionMaker connectionMaker;
	private Connection c;
	private User user;
	
	
	


	
	public void setConnectionMaker(ConnectionMaker connectionMaker)
	{
		this.connectionMaker = connectionMaker;
	}
	
	
	
	public void add(User user)
	throws ClassNotFoundException, SQLException
	{
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("insert into test.users(id, name, password) values(?, ?, ?)");
		
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public User get(String id)
	throws ClassNotFoundException, SQLException
	{
		this.c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("select * from test.users where id = ?");
		
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		rs .next();
		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));
		
		rs.close ();
		ps.close();
		c.close();
		
		return this.user;
	}

/***
	private Connection getConnection()
	throws ClassNotFoundException, SQLException
	{
		Class.forName("org.mariadb.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test", "root", "root");
		
		return c;
	}
***/					
}