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
		// ��request�л�ȡajax��������userName
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

		// Map��ŷ�����Ϣ
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			boolean result = userDao.insertUser(u);
			if (result) { // ����ɹ�������ע��ɹ���
				map.put("code", 0);
			} else { // ����ʧ��
				map.put("code", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
