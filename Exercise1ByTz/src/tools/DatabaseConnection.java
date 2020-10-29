package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import vo.User;

public class DatabaseConnection {
	// ���ݿ���������ļ�
	private static final String JDBCPROPERTY = "jdbc.properties";
	// ׼�����ݿ���Ĵ������
	private static String DBDRIVER = "";
	private static String DBURL = "";
	private static String DBUSER = "";
	private static String PASSWORD = "";

	private Connection conn; // ׼��һ�����ݿ����Ӷ���

	static {
		try {
			Properties property = new Properties();

			// ��ȡ�ֽ���Ŀ¼������ת��stream
			String path = User.class.getClassLoader().getResource("jdbc.properties").getPath();
			InputStream is = new BufferedInputStream(new FileInputStream(path));
			property.load(new InputStreamReader(is, "utf-8"));
			is.close();

			DBDRIVER = property.getProperty("DBDRIVER");
			DBURL = property.getProperty("DBURL");
			DBUSER = property.getProperty("DBUSER");
			PASSWORD = property.getProperty("PASSWORD");

			// ����������ֻ��ע��һ�ξ���
			Class.forName(DBDRIVER);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���췽����ʵ��������ʱ�������Ӷ���
	public DatabaseConnection() {
		try {
			this.conn = DriverManager.getConnection(DBURL, DBUSER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ֱ�ӷ���ʵ��������ʱ���������Ӷ���
	public Connection getConnection() {
		return this.conn;
	}

	// �ر����Ӷ���
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
