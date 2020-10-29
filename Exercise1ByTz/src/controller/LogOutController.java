package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogOutController
 */
@WebServlet("/LogOutController")
public class LogOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ɾ��session��cookie
		HttpSession session = request.getSession();
        session.invalidate();
        Cookie[] cookies = request.getCookies();//��ȡ�����cookie
        if(cookies!=null)
        {
        	for(Cookie c:cookies)
        	{
        		String name = c.getName();
        		if("userName".equals(name))
        		{
        			c.setMaxAge(0); //ɾ��cookie
        			c.setPath("/Exercise1ByTz");
        			response.addCookie(c);
        			System.out.println("***����cookie***");
        		}
        		if("password".equals(name))
        		{
        			c.setMaxAge(0); //ɾ��cookie
        			c.setPath("/Exercise1ByTz");
        			response.addCookie(c);
        		}
        		if("role".equals(name))
        		{
        			c.setMaxAge(0); //ɾ��cookie
        			c.setPath("/Exercise1ByTz");
        			response.addCookie(c);
        		}
        	}       
        }
        response.sendRedirect("http://localhost:8080/Exercise1ByTz/Login.html");
	}

}
