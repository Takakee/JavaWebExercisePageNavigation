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
		// Step1: ����html�������Ĳ��� 
		String inputUserName = request.getParameter("userName");
		String inputPassword = request.getParameter("password");
		String inputVCode = request.getParameter("VerifyCode");
		String setFreeLogin = request.getParameter("setFreeLogin");
		
		// Step2:���ж���֤���Ƿ���ȷ
		// ���õ���ȷ����֤�룬ͨ��request��session���õ����ɵ���ȷ����֤�봮
		HttpSession session = request.getSession();
		String rightVCode = (String)session.getAttribute("verityCode");
		// У����֤��
		if(inputVCode.equalsIgnoreCase(rightVCode))
		{
			System.out.println("��֤����ȷŶ!");
		}
		else {
			System.out.println("��֤�벻��ȷ���ƣ�");
			request.setAttribute("errorInf", "��֤�����벻��ȷ��");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
		// Step3:�ж��û����������Ƿ���ȷ
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		UserDao userDao = new UserDao(conn);
		// ׼��User����
		User u = new User();
		System.out.println("׼����user�������ˣ�");
		try {
			// 
			u = userDao.getByUserName(inputUserName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �жϲ�ѯ�Ƿ�Ϊ��
		if(u!=null)
		{
			if(inputPassword.equals(u.getPassword()))
			{
				// ��¼�ɹ���
				System.out.println("��½�ɹ���");
				
				Cookie nameCookie = new Cookie("userName", u.getChrName());
				Cookie passwordCookie = new Cookie("password", u.getPassword());
				Cookie roleCookie = new Cookie("role", u.getRole());
				// ����cookie				
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
				// ���벻��ȷ��
				System.out.println("���벻��ȷ��");
				request.setAttribute("errorInf", "���벻��ȷ��");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}
		else {
			// �û�������
			System.out.println("�û������ڣ�");
			request.setAttribute("errorInf", "�û������ڣ�");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
	}

}
