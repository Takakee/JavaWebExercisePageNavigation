package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CreateImage;

/**
 * Servlet implementation class CreateImageController
 */
@WebServlet("/CreateImageController")
public class CreateImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 生成图片创造类
		System.out.println("进入servlet啦！！！");
		CreateImage createImage = new CreateImage();
		// 产生随机4位字符串
		String vCode = createImage.CreateCode();
		// 获取httpSession对象
		HttpSession session = request.getSession();
		// 将产生的4位随机字符串放在session中
		session.setAttribute("verityCode", vCode);
		// 设置返回的内容编码格式
		response.setContentType("img/jpeg");
		// 浏览器不缓存响应内容--验证码图片点一次就刷新一次，所以不能有缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		// 设置验证码失效时间
		response.setDateHeader("Expires", 0);
		// 以字节流发过去
		BufferedImage image = createImage.CreateImage(vCode);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.flush();
		out.close();
	}

}
