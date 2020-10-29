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
		// 接收ajax传的参数
		String queryParams = request.getParameter("queryParams");
		String pageParams = request.getParameter("pageParams");
		System.out.println("查询参数："+queryParams);
		System.out.println("分页参数："+pageParams);
		
		// 将json字符串参数转换为java对象
		// 1.对于分页参数pageParams，先将其由gson转为HashMap，再由Hashmap转为Page
		Gson gson = new GsonBuilder().serializeNulls().create();
		HashMap<String, Object> mapPage = gson.fromJson(pageParams, HashMap.class);
		PageBean page = new PageBean();
		page = page.getByHashMap(mapPage); //转换为page对象
		// 2.对于查询条件queryParams，转为一个user对象存放
		User user = new User();
		if(queryParams!=null) {
			user = gson.fromJson(queryParams, User.class);
		}
		// 3.调用Dao执行处理
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		
		try {
			ArrayList<User> rows = userDao.query(user, page);  //查询
			int total = userDao.count(user, page);  // 查询记录总数
			
			// 转换为json数据
			HashMap<String, Object> mapReturn = new HashMap<String, Object>();
			mapReturn.put("rows", rows);
			mapReturn.put("total", total);
			String jsonStr = gson.toJson(mapReturn);
			// 字符流输出
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
