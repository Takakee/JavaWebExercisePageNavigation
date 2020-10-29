package test;

import java.sql.Connection;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.User;

public class CheckUserExist {

	public static void main(String[] args) {
		String userName = "admin";
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		User u = new User();
		u = userDao.getByUserName(userName);
		if(u==null)
		{
			System.out.println("Ã»µÃ"+userName);
		}
		else {
			System.out.println("ÓÐ"+userName);
		}
	}

}
