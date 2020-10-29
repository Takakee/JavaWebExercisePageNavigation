package controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
@WebFilter("/AutoLoginFilter")
public class AutoLoginFilter implements Filter {
	
	// �������ڽ���web.xml�е�init-param
	private String notCheckPath;
	
    /**
     * Default constructor. 
     */
    public AutoLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		// ��ServletRequest���Ͳ���ת��ΪHttpServletRequest����
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		// ��ȡ�����URL-pattern��ַ
		String path = request.getServletPath();
		System.out.println("����AutoLoginFilter------\n�ж��Ƿ�Ҫ��AutoLoginFilter���˵�url-patternΪ��"+path);
		// �����ַ���� ������б� ��Χ�ڣ���Ҫ�ж��Ƿ��Ѿ���¼
		String cookieUser = "";
		if(notCheckPath.indexOf(path)==-1) {
			//cookieֵ����session
			Cookie[] cookies = request.getCookies();
			for(Cookie c:cookies)
			{
				String name = c.getName();       // ��ȡcookie������
				if("userName".equals(name)) {
					String value = c.getValue(); // ��ȡcookie��ֵ
					System.out.println("cookie�е�userName��"+value);
					// ��cookie�е�ֵ����session					
					session.setAttribute(name, value);
					cookieUser = value;
				}
				if("password".equals(name))
				{
					String value = c.getValue();
					System.out.println("cookie�е�password��"+value);
					session.setAttribute(name, value);
				}
				if("role".equals(name))
				{
					String value = c.getValue();
					System.out.println("cookie�е�role��"+value);
					session.setAttribute(name, value);
				}
			}
			
			
			if(cookieUser=="") //δ��¼
			{
				request.setAttribute("errorInf", "����δ��¼Ŷ��");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
				return;
			}else { //�Ѿ���¼��ֱ�ӷ���
				System.out.println("---session�е�userNameΪ��"+session.getAttribute("userName")+"---");
				System.out.println("�ѵ�¼������������cookieUser:"+cookieUser);
				chain.doFilter(request, resp);
			}
		}
		else { // ����ĵ�ַ�� ������б��� ��ֱ�ӷ���
			chain.doFilter(request, resp);
		}
				
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("����AutoLoginFilter�ˣ�");
		// ��web.xml�����ļ��л�ȡinit-param����
		notCheckPath = fConfig.getInitParameter("notCheckPath");
	}

}
