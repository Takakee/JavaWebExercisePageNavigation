package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.接收ajax传来的要删除的usernames
		String userNames = request.getParameter("userNames");
		// 2.调用dao进行处理
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao dao = new UserDao(conn);
		boolean result=false;
		try {
			result = dao.deleteUsers(userNames);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 存放返回信息的Map
		Map<String, Object> map = new HashMap<String, Object>();
		if (result) {
			map.put("code", 0);
			map.put("info", "删除成功!");
		} else {
			map.put("code", 1);
			map.put("info", "删除失败!");
		}

		// 3.调用谷歌的Gson库将map类型数据转换为json字符串
		String jsonStr = new Gson().toJson(map);
		// 字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
