package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProvinceCityDao;
import tools.DatabaseConnection;
import vo.ProvinceCity;

import com.google.gson.Gson;
/**
 * Servlet implementation class QueryProvinceCityServlet
 */
@WebServlet("/QueryProvinceCityServlet")
public class QueryProvinceCityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String provinceCode = request.getParameter("provinceCode");
		String jsonStr = "";
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		ProvinceCityDao pcDao = new ProvinceCityDao(conn);
		if(provinceCode==null)
		{ // û�������������ʾ��ѯ����ʡ���б�
			List<ProvinceCity> provinceList = new ArrayList<ProvinceCity>();
			try {
				provinceList = pcDao.queryProvince();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonStr = new Gson().toJson(provinceList);
		}
		else { // ����ʡ��code����ѯ��Ӧ�ĳ�����Ϣ
			List<ProvinceCity> cityList = new ArrayList<ProvinceCity>();
			try {
				cityList = pcDao.queryCity(provinceCode);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonStr = new Gson().toJson(cityList);
		}
		// �ַ�������ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
