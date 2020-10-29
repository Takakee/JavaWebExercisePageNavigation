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
 * Servlet implementation class SignUpController
 */
@WebServlet("/SignUpController")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 从request中获取ajax传过来的userName
		String userName = request.getParameter("userName");
		String chrName = request.getParameter("chrName");
		String mail = request.getParameter("mail");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String password = request.getParameter("password");
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		User u = new User(userName, password, chrName, "user");
		UserDao userDao = new UserDao(conn);

		// Map存放返回信息
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			boolean result = userDao.insertUser(u);
			if (result) { // 插入成功，就是注册成功了
				map.put("code", 0);
			} else { // 插入失败
				map.put("code", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
