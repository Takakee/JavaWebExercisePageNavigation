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
		// ����ͼƬ������
		System.out.println("����servlet��������");
		CreateImage createImage = new CreateImage();
		// �������4λ�ַ���
		String vCode = createImage.CreateCode();
		// ��ȡhttpSession����
		HttpSession session = request.getSession();
		// ��������4λ����ַ�������session��
		session.setAttribute("verityCode", vCode);
		// ���÷��ص����ݱ����ʽ
		response.setContentType("img/jpeg");
		// �������������Ӧ����--��֤��ͼƬ��һ�ξ�ˢ��һ�Σ����Բ����л���
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		// ������֤��ʧЧʱ��
		response.setDateHeader("Expires", 0);
		// ���ֽ�������ȥ
		BufferedImage image = createImage.CreateImage(vCode);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.flush();
		out.close();
	}

}
