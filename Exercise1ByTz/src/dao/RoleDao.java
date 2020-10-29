package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Role;

public class RoleDao {
	// 1.�������ݿ����Ӷ���׼��sql���
	private Connection conn;
	private PreparedStatement pstmt;
	
	public RoleDao(Connection conn)
	{
		this.conn = conn;
	}
	
	// ��ѯ����role
	public Role getByRoleName(String inputRoleName)
	{
		String sql = "select * from t_role where roleName=?";
		
		try {
			this.pstmt = conn.prepareStatement(sql);
			this.pstmt.setString(1, inputRoleName);
			ResultSet rs = this.pstmt.executeQuery();
			if(rs.next())
			{
				Role role = new Role();
				role.setRoleName(rs.getString("roleName"));
				role.setResource(rs.getString("resource"));
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
