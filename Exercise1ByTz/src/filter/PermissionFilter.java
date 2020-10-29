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
		
		// 获取当前请求的url-pattern地址
		String currentPath = request.getServletPath();
		System.out.println("进入PermissionFilter------判断是否要被PermissionFilter过滤的url-pattern为："+currentPath);
		// 通过session中存放的role来实例化role
		String roleName = (String)session.getAttribute("role");
		DatabaseConnection dbc = new DatabaseConnection();
		Connection conn = dbc.getConnection();
		Role role = new Role();
		RoleDao roleDao = new RoleDao(conn);		
		role = roleDao.getByRoleName(roleName);
		// 通过role对象获取能够访问的所有url-pattern
		List<String> urlList = role.getUrls(role);
		Boolean flag = false;
		for(int i=0;i<urlList.size(); i++)
		{
			System.out.println(urlList.get(i));
			if(currentPath.equals(urlList.get(i)))//若有权限
			{
				flag = true;
				break;
			}		
		}
		
		if(flag==false) //无权限访问
		{
			request.setAttribute("errorInf", "您没有权限访问！");
			request.getRequestDispatcher("/error.jsp").forward(request, resp);
			return;
		}else { //有权限访问，直接放行
			System.out.println("---有权限，可以访问，放行---");
			chain.doFilter(request, resp);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("*****初始化PermissionFilter******");
	}

}
