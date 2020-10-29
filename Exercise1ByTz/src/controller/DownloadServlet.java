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
		// 1.ͨ��request�����մ�a��ǩ�и����Ĳ���
		String id = request.getParameter("id");

		// 2.�������ݿ���Ҷ�Ӧ�ľ���·��path
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		DownloadDao dlDao = new DownloadDao(conn);
		// ׼��download����������
		Download dl = new Download();
		try {
			// 
			dl = dlDao.getById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3.��ȡ���ص��ļ���
//		String path = dl.getPath();
//		String fileName = path.substring(path.lastIndexOf("\\")+1); // �˺���������ȥͷ
		String fileName = dl.getName();
		String path = this.getServletContext().getRealPath(dl.getPath());
		//4.���������ط�ʽ��
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		// ��ȡ��Ӧ�����
		ServletOutputStream out = response.getOutputStream();
		
		// 5.����path�������ļ�
		InputStream in = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=in.read(buffer)) != -1) {
			// д����Ӧ��
			out.write(buffer, 0, len);			
		}
		// �ر��ļ�
		in.close();
		out.close();
	}

}
