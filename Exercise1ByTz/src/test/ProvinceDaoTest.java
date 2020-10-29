package test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.ProvinceCityDao;
import tools.DatabaseConnection;
import vo.ProvinceCity;

public class ProvinceDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnection dbc= new DatabaseConnection();
		Connection conn = dbc.getConnection();
		ProvinceCityDao pcDao = new ProvinceCityDao(conn);
		ProvinceCity province = new ProvinceCity();
		List<ProvinceCity> provinceList = new ArrayList<ProvinceCity>();
		List<ProvinceCity> cityList = new ArrayList<ProvinceCity>();
		try {
			provinceList = pcDao.queryProvince();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			cityList = pcDao.queryCity("0100");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
