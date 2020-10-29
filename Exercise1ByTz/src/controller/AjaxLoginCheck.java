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
		// ����������������ʽΪutf-8����ֹ��������
		request.setCharacterEncoding("utf-8");
		// 1.��ȡ����������ֵ����input��typeѡ����button��js����ajax��������
		String inputUserName = request.getParameter("userName");
		String inputPassword = request.getParameter("password");
		String inputVCode = request.getParameter("VerifyCode");
		String setFreeLogin = request.getParameter("setFreeLogin");
		
		// 2.��ȡHttpSession����
		HttpSession session = request.getSession();
		// ���õ���ȷ����֤�룬ͨ��request��session���õ����ɵ���ȷ����֤�봮
		String rightVCode = (String)session.getAttribute("verityCode");
		// ���� ��ŷ�����Ϣ��Map
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("--------\n AjaxLoginCheck�У�\n��ȷ��vcCode��"+rightVCode
				+"��\n�����vcCode��"+inputVCode
				+"��\n��������userName��"+inputUserName
				+"��\n��������password��"+inputPassword
				+"��\n��������setFreeLogin��"+setFreeLogin
				+"----------");
		if(!(inputVCode.equalsIgnoreCase(rightVCode))) {
			// ��֤�벻��ȷ
			map.put("code", 1);
			map.put("info", "��֤�벻��ȷ��");
		}else {
			// ��֤����ȷ
			// #��ʼ�ж��û�����������
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
			{ // �û���������
				map.put("code", 2);
				map.put("info", "�û��������ڣ�");
			}else { // �û�������
				if(!inputPassword.equals(u.getPassword())) { 
					//���벻��ȷ
					map.put("code", 3);
					map.put("info", "���벻��ȷ��");
				}else { // �û��������붼��ȷ
					System.out.println("��½�ɹ���");
					
					request.setAttribute("userName", u.getChrName());
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
					map.put("code", 0);
					map.put("info", "��¼�ɹ���");
				}
			}
		}
		//���ùȸ��Gson�⽫map��������ת��Ϊjson�ַ���
		String jsonStr = new Gson().toJson(map);
		//�ַ�������ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
