package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.PageBean;
import vo.User;

/**
 * Servlet implementation class QueryUserServlet
 */
@WebServlet("/QueryUserServlet")
public class QueryUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ����ajax���Ĳ���
		String queryParams = request.getParameter("queryParams");
		String pageParams = request.getParameter("pageParams");
		System.out.println("��ѯ������"+queryParams);
		System.out.println("��ҳ������"+pageParams);
		
		// ��json�ַ�������ת��Ϊjava����
		// 1.���ڷ�ҳ����pageParams���Ƚ�����gsonתΪHashMap������HashmapתΪPage
		Gson gson = new GsonBuilder().serializeNulls().create();
		HashMap<String, Object> mapPage = gson.fromJson(pageParams, HashMap.class);
		PageBean page = new PageBean();
		page = page.getByHashMap(mapPage); //ת��Ϊpage����
		// 2.���ڲ�ѯ����queryParams��תΪһ��user������
		User user = new User();
		if(queryParams!=null) {
			user = gson.fromJson(queryParams, User.class);
		}
		// 3.����Daoִ�д���
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		
		try {
			ArrayList<User> rows = userDao.query(user, page);  //��ѯ
			int total = userDao.count(user, page);  // ��ѯ��¼����
			
			// ת��Ϊjson����
			HashMap<String, Object> mapReturn = new HashMap<String, Object>();
			mapReturn.put("rows", rows);
			mapReturn.put("total", total);
			String jsonStr = gson.toJson(mapReturn);
			// �ַ������
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(jsonStr);
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
