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
		// 从request中获取ajax传过来的userName
		String userName = request.getParameter("userName");
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		User u = new User();
		u = userDao.getByUserName(userName);
		
		//Map存放返回信息
		Map<String, Object> map = new HashMap<String, Object>();
		if(u==null)
		{
			map.put("code", 0);
		}
		else {
			map.put("code", 1);
		}
		System.out.println("CheckUserExist已经检测输入的userName在不在了");
		
		// 调用谷歌的Gson库将map类型转换为json字符串
		String jsonStr = new Gson().toJson(map);
		// 字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
