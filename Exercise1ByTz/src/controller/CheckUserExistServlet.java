package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.User;

/**
 * Servlet implementation class CheckUserExistServlet
 */
@WebServlet("/CheckUserExistServlet")
public class CheckUserExistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��request�л�ȡajax��������userName
		String userName = request.getParameter("userName");
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		User u = new User();
		u = userDao.getByUserName(userName);
		
		//Map��ŷ�����Ϣ
		Map<String, Object> map = new HashMap<String, Object>();
		if(u==null)
		{
			map.put("code", 0);
		}
		else {
			map.put("code", 1);
		}
		System.out.println("CheckUserExist�Ѿ���������userName�ڲ�����");
		
		// ���ùȸ��Gson�⽫map����ת��Ϊjson�ַ���
		String jsonStr = new Gson().toJson(map);
		// �ַ�������ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
