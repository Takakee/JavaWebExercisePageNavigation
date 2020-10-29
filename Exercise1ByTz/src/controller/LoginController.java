package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import tools.DatabaseConnection;
import vo.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Step1: 接收html传过来的参数 
		String inputUserName = request.getParameter("userName");
		String inputPassword = request.getParameter("password");
		String inputVCode = request.getParameter("VerifyCode");
		String setFreeLogin = request.getParameter("setFreeLogin");
		
		// Step2:先判断验证码是否正确
		// 先拿到正确的验证码，通过request从session中拿到生成的正确的验证码串
		HttpSession session = request.getSession();
		String rightVCode = (String)session.getAttribute("verityCode");
		// 校验验证码
		if(inputVCode.equalsIgnoreCase(rightVCode))
		{
			System.out.println("验证码正确哦!");
		}
		else {
			System.out.println("验证码不正确！淦！");
			request.setAttribute("errorInf", "验证码输入不正确！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
		// Step3:判断用户名和密码是否正确
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		// 准备User接收
		User u = new User();
		System.out.println("准备好user来接收了！");
		try {
			// 
			u = userDao.getByUserName(inputUserName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 判断查询是否为空
		if(u!=null)
		{
			if(inputPassword.equals(u.getPassword()))
			{
				// 登录成功！
				System.out.println("登陆成功！");
				
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
				
				request.setAttribute("userName", u.getChrName());
				response.sendRedirect("main.jsp");
				//request.getRequestDispatcher("main.jsp").forward(request, response);
//				response.sendRedirect("main.jsp");
				return;
				
			}
			else {
				// 密码不正确！
				System.out.println("密码不正确！");
				request.setAttribute("errorInf", "密码不正确！");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}
		else {
			// 用户不存在
			System.out.println("用户不存在！");
			request.setAttribute("errorInf", "用户不存在！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
	}

}
