package filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.RoleDao;
import tools.DatabaseConnection;
import vo.Role;

/**
 * Servlet Filter implementation class PermissionFilter
 */
@WebFilter("/PermissionFilter")
public class PermissionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PermissionFilter() {
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

		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		
		// ��ȡ��ǰ�����url-pattern��ַ
		String currentPath = request.getServletPath();
		System.out.println("����PermissionFilter------�ж��Ƿ�Ҫ��PermissionFilter���˵�url-patternΪ��"+currentPath);
		// ͨ��session�д�ŵ�role��ʵ����role
		String roleName = (String)session.getAttribute("role");
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		Role role = new Role();
		RoleDao roleDao = new RoleDao(conn);		
		role = roleDao.getByRoleName(roleName);
		// ͨ��role�����ȡ�ܹ����ʵ�����url-pattern
		List<String> urlList = role.getUrls(role);
		Boolean flag = false;
		for(int i=0;i<urlList.size(); i++)
		{
			System.out.println(urlList.get(i));
			if(currentPath.equals(urlList.get(i)))//����Ȩ��
			{
				flag = true;
				break;
			}		
		}
		
		if(flag==false) //��Ȩ�޷���
		{
			request.setAttribute("errorInf", "��û��Ȩ�޷��ʣ�");
			request.getRequestDispatcher("/error.jsp").forward(request, resp);
			return;
		}else { //��Ȩ�޷��ʣ�ֱ�ӷ���
			System.out.println("---��Ȩ�ޣ����Է��ʣ�����---");
			chain.doFilter(request, resp);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("*****��ʼ��PermissionFilter******");
	}

}
