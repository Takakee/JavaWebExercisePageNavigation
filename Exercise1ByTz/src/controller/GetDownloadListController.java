package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DownloadDao;
import tools.DatabaseConnection;
import vo.Download;

/**
 * Servlet implementation class GetDownloadListController
 */
@WebServlet("/GetDownloadListController")
public class GetDownloadListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取数据库下载列表
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		// 准备download单个对象和集合对象来接收
		List<Download> downloadList = new ArrayList<Download>();
		Download dl = new Download();
		
		DownloadDao dlDao = new DownloadDao(conn);
		try {
			downloadList = dlDao.getAllDownload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 2.存到request域对象中
		request.setAttribute("DownloadList", downloadList);
		// 3.转发到download.jsp页面
		request.getRequestDispatcher("/download.jsp").forward(request, response);
//		response.sendRedirect("http://localhost:8080/Exercise1ByTz/download.jsp");
	}

}
