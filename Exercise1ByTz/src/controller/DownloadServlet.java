package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DownloadDao;
import tools.DatabaseConnection;
import vo.Download;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.通过request来接收从a标签中附带的参数
		String id = request.getParameter("id");

		// 2.连接数据库查找对应的绝对路径path
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		DownloadDao dlDao = new DownloadDao(conn);
		// 准备download对象来接收
		Download dl = new Download();
		try {
			// 
			dl = dlDao.getById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3.获取下载的文件名
//		String path = dl.getPath();
//		String fileName = path.substring(path.lastIndexOf("\\")+1); // 此函数功能是去头
		String fileName = dl.getName();
		String path = this.getServletContext().getRealPath(dl.getPath());
		//4.设置以下载方式打开
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		// 获取响应的输出
		ServletOutputStream out = response.getOutputStream();
		
		// 5.根据path来加载文件
		InputStream in = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=in.read(buffer)) != -1) {
			// 写到响应中
			out.write(buffer, 0, len);			
		}
		// 关闭文件
		in.close();
		out.close();
	}

}
