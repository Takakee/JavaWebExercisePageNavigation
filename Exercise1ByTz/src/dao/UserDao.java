package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.Download;
import vo.PageBean;
import vo.User;

public class UserDao {

	// 1.设置数据库连接对象和准备sql语句
	private Connection conn;
	private PreparedStatement pstmt;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	// 查询单个用户
	public User getByUserName(String inputUserName) {
		String sql = "select * from t_user where userName=?";

		try {
			this.pstmt = conn.prepareStatement(sql);
			this.pstmt.setString(1, inputUserName);
			// 执行SQL语句
			ResultSet rs = this.pstmt.executeQuery();
			if (rs.next()) {
				User u = new User();

				u.setUserName(rs.getString("userName"));
				u.setPassword(rs.getString("password"));
				u.setRole(rs.getString("role"));
				u.setChrName(rs.getString("chrName"));

				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean insertUser(User user) throws Exception {
		String sql = "insert into t_user values (?,?,?,?)";

		this.pstmt = conn.prepareStatement(sql);
		this.pstmt.setString(1, user.getUserName());
		this.pstmt.setString(2, user.getPassword());
		this.pstmt.setString(3, user.getChrName());
		this.pstmt.setString(4, user.getRole());
		// 执行SQL语句
		if (this.pstmt.executeUpdate() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public ArrayList<User> query(User user, PageBean page) throws SQLException
	{
		ArrayList<User> list = new ArrayList<User>(); // 存放查询结果的集合
		StringBuffer condition = new StringBuffer();// 查询条件
		if(user.getUserName()!=null && !"".equals(user.getUserName())){ // 判断是否有该查询条件
			condition.append(" and userName like '%")
			.append(user.getUserName()).append("%'");
		}
		if(user.getChrName()!=null && !"".equals(user.getChrName())){ // 判断是否有该查询条件
			condition.append(" and chrName like '%")
			.append(user.getChrName()).append("%'");
		}
		if(user.getRole()!=null && !"".equals(user.getRole())){ // 判断是否有该查询条件
			condition.append(" and role like '%")
			.append(user.getRole()).append("%'");
		}
		
		int begin = page.getPageSize() * (page.getPageNumber() - 1);
		String sql = "select userName,chrName,password,role ";
		sql = sql + " from t_user ";
		sql = sql + " where 1=1 ";
		sql = sql + condition + " order by "+ page.getSort() + " "
				+ page.getSortOrder() + " limit " + begin + ","
				+ page.getPageSize();
		
		System.out.println(sql);
		this.pstmt = this.conn.prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		
		while(rs.next()) {
			User userResult = new User();
			userResult.setUserName(rs.getString("userName"));
			userResult.setPassword(rs.getString("password"));
			userResult.setChrName(rs.getString("chrName"));
			userResult.setRole(rs.getString("role"));
			list.add(userResult);
		}
		rs.close();
		System.out.println(list);
		return list;
	}
	
	public int count(User user, PageBean page) throws SQLException
	{
		int count=0;
		StringBuffer condition = new StringBuffer();// 查询条件
		if(user.getUserName()!=null && !"".equals(user.getUserName())){ // 判断是否有该查询条件
			condition.append(" and userName like '%")
			.append(user.getUserName()).append("%'");
		}
		if(user.getChrName()!=null && !"".equals(user.getChrName())){ // 判断是否有该查询条件
			condition.append(" and chrName like '%")
			.append(user.getChrName()).append("%'");
		}
		if(user.getRole()!=null && !"".equals(user.getRole())){ // 判断是否有该查询条件
			condition.append(" and role like '%")
			.append(user.getRole()).append("%'");
		}
		
		int begin = page.getPageSize() * (page.getPageNumber() - 1);
		String sql = "select userName,chrName,password,role ";
		sql = sql + " from t_user ";
		sql = sql + " where 1=1 ";
		sql = sql + condition;
		
		this.pstmt = this.conn.prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		
		while(rs.next()) {
			count++;
		}
		rs.close();
		System.out.println("符合按照条件查询的记录共有："+count);
		return count;
	}
	
	/* 根据字符串userNames删除多个user */
	public boolean deleteUsers(String userNames) throws SQLException {
		// 将userNames按照“，”分割为数组
		String[] array = userNames.split(",");
		// 创建sql语句
		String sql = "delete from t_user where userName in(";
		StringBuffer sb = new StringBuffer("?");
		for(int i=0; i<array.length-1; i++)
		{
			sb.append(",?");
		}
		sql = sql + sb.toString() + ")";
		System.out.println(sql);
		
		this.pstmt = conn.prepareStatement(sql);
		for(int i=0; i<array.length; i++)
		{
			this.pstmt.setString(i+1, array[i]);
		}
		
		// 执行SQL语句
		if (this.pstmt.executeUpdate() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
