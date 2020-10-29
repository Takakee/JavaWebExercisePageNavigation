package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.ProvinceCity;

public class ProvinceCityDao {
	//1.设置数据库连接对象和准备sql语句
		private Connection conn;
		private PreparedStatement pstmt;
		
		public ProvinceCityDao(Connection conn)
		{
			this.conn = conn;		
		}
		
		public List<ProvinceCity> queryProvince() throws SQLException
		{
			ArrayList<ProvinceCity> list = new ArrayList<ProvinceCity>();
			String sql = "select * from t_provincecity where id like '__00'";
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();
			
			while(rs.next())
			{
				ProvinceCity province = new ProvinceCity();
				province.setId(rs.getString("id"));
				province.setChrName(rs.getString("chrName"));				
				list.add(province);
			}
			rs.close();
			System.out.println(list);
			return list;
		}
		
		public List<ProvinceCity> queryCity(String provinceId) throws SQLException
		{
			String provincePreId = provinceId.substring(0, 2); 
			ArrayList<ProvinceCity> list = new ArrayList<ProvinceCity>();
			String sql = "select * from t_provincecity where id like '"+provincePreId+"__'";
			this.pstmt = this.conn.prepareStatement(sql);
			ResultSet rs = this.pstmt.executeQuery();
			
			while(rs.next())
			{
				if(rs.getString("id").equals(provinceId))
					continue;
				else {
					ProvinceCity province = new ProvinceCity();
					province.setId(rs.getString("id"));
					province.setChrName(rs.getString("chrName"));				
					list.add(province);
				}				
			}
			rs.close();
			System.out.println(list);
			return list;
		}
}
