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

	// 1.�������ݿ����Ӷ����׼��sql���
	private Connection conn;
	private PreparedStatement pstmt;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	// ��ѯ�����û�
	public User getByUserName(String inputUserName) {
		String sql = "select * from t_user where userName=?";

		try {
			this.pstmt = conn.prepareStatement(sql);
			this.pstmt.setString(1, inputUserName);
			// ִ��SQL���
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
		// ִ��SQL���
		if (this.pstmt.executeUpdate() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public ArrayList<User> query(User user, PageBean page) throws SQLException
	{
		ArrayList<User> list = new ArrayList<User>(); // ��Ų�ѯ����ļ���
		StringBuffer condition = new StringBuffer();// ��ѯ����
		if(user.getUserName()!=null && !"".equals(user.getUserName())){ // �ж��Ƿ��иò�ѯ����
			condition.append(" and userName like '%")
			.append(user.getUserName()).append("%'");
		}
		if(user.getChrName()!=null && !"".equals(user.getChrName())){ // �ж��Ƿ��иò�ѯ����
			condition.append(" and chrName like '%")
			.append(user.getChrName()).append("%'");
		}
		if(user.getRole()!=null && !"".equals(user.getRole())){ // �ж��Ƿ��иò�ѯ����
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
		StringBuffer condition = new StringBuffer();// ��ѯ����
		if(user.getUserName()!=null && !"".equals(user.getUserName())){ // �ж��Ƿ��иò�ѯ����
			condition.append(" and userName like '%")
			.append(user.getUserName()).append("%'");
		}
		if(user.getChrName()!=null && !"".equals(user.getChrName())){ // �ж��Ƿ��иò�ѯ����
			condition.append(" and chrName like '%")
			.append(user.getChrName()).append("%'");
		}
		if(user.getRole()!=null && !"".equals(user.getRole())){ // �ж��Ƿ��иò�ѯ����
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
		System.out.println("���ϰ���������ѯ�ļ�¼���У�"+count);
		return count;
	}
	
	/* �����ַ���userNamesɾ�����user */
	public boolean deleteUsers(String userNames) throws SQLException {
		// ��userNames���ա������ָ�Ϊ����
		String[] array = userNames.split(",");
		// ����sql���
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
		
		// ִ��SQL���
		if (this.pstmt.executeUpdate() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
