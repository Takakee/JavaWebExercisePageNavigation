package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Download;

public class DownloadDao {

	private Connection conn;
	private PreparedStatement pstmt;
	
	public DownloadDao(Connection conn) {
		this.conn = conn;
	}
	
	// 多个查询，用于显示整个下载列表
	public List<Download> getAllDownload() throws Exception{
		ArrayList<Download> downloadList = new ArrayList<Download>();
//		Download dl = new Download();
		String sql = "select * from t_downloadList";
		this.pstmt = this.conn.prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		
		while(rs.next()) {
			Download dl = new Download();
			dl.setId(rs.getInt("id"));
			dl.setName(rs.getString("name"));
			dl.setPath(rs.getString("path"));
			dl.setDescription(rs.getString("description"));
			dl.setSize(rs.getInt("size"));
			dl.setStar(rs.getInt("star"));
			dl.setImage(rs.getString("image"));
			downloadList.add(dl);
		}
		rs.close();
		System.out.println(downloadList);
		return downloadList;
	}
	// 单个查询
	public Download getById(int id) throws Exception {
		String sql = "select * from t_downloadList where id=?";
				
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, String.valueOf(id));
		ResultSet rs = this.pstmt.executeQuery();
		if(rs.next())
		{
			Download dl = new Download();
			
			dl.setId(rs.getInt("id"));
			dl.setName(rs.getString("name"));
			dl.setPath(rs.getString("path"));
			dl.setDescription(rs.getString("description"));
			dl.setSize(rs.getInt("size"));
			dl.setStar(rs.getInt("star"));
			dl.setImage(rs.getString("image"));	
			
			return dl;
		}
		
		return null;
	}
	
}
