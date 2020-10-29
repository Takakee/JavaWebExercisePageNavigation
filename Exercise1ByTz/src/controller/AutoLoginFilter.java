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
	
	// 设置用于接收web.xml中的init-param
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
		
		// 将ServletRequest类型参数转换为HttpServletRequest类型
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		// 获取请求的URL-pattern地址
		String path = request.getServletPath();
		System.out.println("进入AutoLoginFilter------\n判断是否要被AutoLoginFilter过滤的url-pattern为："+path);
		// 请求地址不在 免过滤列表 范围内，需要判断是否已经登录
		String cookieUser = "";
		if(notCheckPath.indexOf(path)==-1) {
			//cookie值放入session
			Cookie[] cookies = request.getCookies();
			for(Cookie c:cookies)
			{
				String name = c.getName();       // 获取cookie的名称
				if("userName".equals(name)) {
					String value = c.getValue(); // 获取cookie的值
					System.out.println("cookie中的userName："+value);
					// 将cookie中的值放入session					
					session.setAttribute(name, value);
					cookieUser = value;
				}
				if("password".equals(name))
				{
					String value = c.getValue();
					System.out.println("cookie中的password："+value);
					session.setAttribute(name, value);
				}
				if("role".equals(name))
				{
					String value = c.getValue();
					System.out.println("cookie中的role："+value);
					session.setAttribute(name, value);
				}
			}
			
			
			if(cookieUser=="") //未登录
			{
				request.setAttribute("errorInf", "您还未登录哦！");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
				return;
			}else { //已经登录，直接放行
				System.out.println("---session中的userName为："+session.getAttribute("userName")+"---");
				System.out.println("已登录，过滤器放行cookieUser:"+cookieUser);
				chain.doFilter(request, resp);
			}
		}
		else { // 请求的地址在 免过滤列表中 ，直接放行
			chain.doFilter(request, resp);
		}
				
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("调用AutoLoginFilter了！");
		// 从web.xml配置文件中获取init-param参数
		notCheckPath = fConfig.getInitParameter("notCheckPath");
	}

}
