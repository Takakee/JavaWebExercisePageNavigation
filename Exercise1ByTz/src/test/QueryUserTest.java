package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.PageBean;
import vo.User;

public class QueryUserTest {

	public static void main(String[] args) throws SQLException {
		// 1.初始化查询条件，存储在user中
		User u = new User("user", "", "", "user");
		PageBean page = new PageBean(3, 2, "userName", "asc");
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		
		ArrayList<User> list = userDao.query(u, page);
		int count = userDao.count(u, page);
	}

}
