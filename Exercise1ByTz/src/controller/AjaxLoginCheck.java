package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.User;

/**
 * Servlet implementation class AjaxLoginCheck
 */
@WebServlet("/AjaxLoginCheck")
public class AjaxLoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		// 设置请求参数编码格式为utf-8，防止中文乱码
		request.setCharacterEncoding("utf-8");
		// 1.获取表单穿过来的值，是input的type选择了button在js中用ajax传过来的
		String inputUserName = request.getParameter("userName");
		String inputPassword = request.getParameter("password");
		String inputVCode = request.getParameter("VerifyCode");
		String setFreeLogin = request.getParameter("setFreeLogin");
		
		// 2.获取HttpSession对象
		HttpSession session = request.getSession();
		// 先拿到正确的验证码，通过request从session中拿到生成的正确的验证码串
		String rightVCode = (String)session.getAttribute("verityCode");
		// 生成 存放返回信息的Map
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("--------\n AjaxLoginCheck中：\n正确的vcCode："+rightVCode
				+"，\n输入的vcCode："+inputVCode
				+"，\n传过来的userName："+inputUserName
				+"，\n传过来的password："+inputPassword
				+"，\n传过来的setFreeLogin："+setFreeLogin
				+"----------");
		if(!(inputVCode.equalsIgnoreCase(rightVCode))) {
			// 验证码不正确
			map.put("code", 1);
			map.put("info", "验证码不正确！");
		}else {
			// 验证码正确
			// #开始判断用户名和密码了
			UserDao userDao = new UserDao(conn);
			User u = new User();
			try {
				u = userDao.getByUserName(inputUserName);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(u==null)
			{ // 用户名不存在
				map.put("code", 2);
				map.put("info", "用户名不存在！");
			}else { // 用户名存在
				if(!inputPassword.equals(u.getPassword())) { 
					//密码不正确
					map.put("code", 3);
					map.put("info", "密码不正确！");
				}else { // 用户名和密码都正确
					System.out.println("登陆成功！");
					
					request.setAttribute("userName", u.getChrName());
					Cookie nameCookie = new Cookie("userName", u.getChrName());
					Cookie passwordCookie = new Cookie("password", u.getPassword());
					Cookie roleCookie = new Cookie("role", u.getRole());
					// 设置cookie				
					if(Boolean.valueOf(setFreeLogin))
					{
						int cookieMaxAge=60*60*24*7;
						
						nameCookie.setMaxAge(cookieMaxAge);
						nameCookie.setPath("/Exercise1ByTz");
						
						passwordCookie.setMaxAge(cookieMaxAge);
						passwordCookie.setPath("/Exercise1ByTz");
						
						roleCookie.setMaxAge(cookieMaxAge);		
						roleCookie.setPath("/Exercise1ByTz");
					}
					response.addCookie(nameCookie);
					response.addCookie(passwordCookie);
					response.addCookie(roleCookie);
					map.put("code", 0);
					map.put("info", "登录成功！");
				}
			}
		}
		//调用谷歌的Gson库将map类型数据转换为json字符串
		String jsonStr = new Gson().toJson(map);
		//字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
